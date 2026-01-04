/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * cgiwatch.c
 *
 * This NSAPI service function counts the number of CGI requests thtat have 
 * been served by this server.  Hopefully it also demonstrates several things:
 *    - how to override a builtin NSAPI function (one provided by netscape)
 *    - how to use cookies
 *    - how to use the refresh header
 *    - how to dynamically count what activity on the server
 *
 * Overriding builtins:
 *    To override server builtins, we'll use the func_replace() function.
 *    This will allow us to insert our own nsapi function in place of the 
 *    builtin cgi handler.
 *
 * Cookies:
 *    This NSAPI module uses the cookie.c example for manipulating cookies.
 *    In particular, we'll send back a cookie to the client which contains
 *    a timestamp and the number of CGI requests served at that timestamp.
 *    The next time the client accesses this URI, it will send the cookie.
 *    We'll use the data to compute the CGI requests per second which have
 *    have occurred.
 *    
 * Refresh Header:
 *    The refresh header is a capability built into the Netscape Navigator
 *    which informs the client to "refresh" a given page with some frequency.
 *    We'll use the refresh header to get an updated statistics counter 
 *    on the client.
 *    
 * How to install this example:
 *    Add the following lines to your init.conf with the other Init
 *    directives at the bottom of the file:
 *    On Unixes
 *    Init fn=load-modules shlib=<path to cgiwatch.so> funcs=cgiwatch_init,cgiwatch_service
 *
 *    On windows NT
 *    Init fn=load-modules shlib=<path to cgiwatch.dll> funcs=cgiwatch_init,cgiwatch_service
 *
 *    Init fn=cgiwatch_init
 *
 *    In the NameTrans section of the default object in obj.conf add:
 *
 *    NameTrans fn=assign-name from=/cgiwatch name=cgiwatch stop=true
 *
 *    Finally, add this object definition to the end of the obj.conf file:
 *
 *    <Object name=cgiwatch>
 *    Service method=(GET|HEAD) fn=cgiwatch_service
 *    </Object>
 *
 *    You can restrict access to cgiwatch by accessing it from the
 *    "Styles|Edit Style" item in the Admin Server.
 *
 * How to run this example:
 *    Once installed, you'll be able to access the URL
 *         http://yourserver/cgiwatch
 *    It will show the CGI statistics for your machine.  You can also access
 *    the URL
 *         http://yourserver/cgiwatch?refresh=1
 *    This will show you the same page, but it will automatically update 
 *    every second.
 *    
 *    Now, with the navigator window up, if you start accessing CGIs on your
 *    server, you should see the counts change in the navigator.
 */

#include "nsapi.h"
#include "cookie.h"

/* --- Globals --- */
static CRITICAL cgiwatch_lock;
static int      cgiwatch_counter;
static FuncPtr  cgiwatch_cgisend;
static int      cgiwatch_start_time;

/* --- Forwards --- */
static int cgiwatch_send(pblock *pb, Session *sn, Request *rq);


/*
 * cgiwatch_init()
 * Initialization function.  
 *
 */
#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int
cgiwatch_init(pblock *pb, Session *sn, Request *rq)
{
    cgiwatch_lock = crit_init();
    cgiwatch_counter = 0;
    cgiwatch_start_time = time(NULL);

    /* Replace the server's CGI handler */
    cgiwatch_cgisend = func_replace("send-cgi", cgiwatch_send);

    if (!cgiwatch_cgisend) {
        ereport(LOG_FAILURE, "cgiwatch: failed to replace cgi handler");
        return REQ_ABORTED;
    }
 
    return REQ_PROCEED;
}

#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int
cgiwatch_service(pblock *pb, Session *sn, Request *rq)
{
    char *refresh, *refresh_val;
#define BUFFER_LENGTH 512
    char output_buffer[BUFFER_LENGTH];
    int output_buffer_len;
    pblock *cookie_pblock;
    float cgi_per_second = 0.0;
    float total_cgi_per_second = 0.0;
    int timenow = 0;
    int timelast = 0;
    int countnow = 0;
    int countlast = 0;
    char int_str[12];

    /* See if client asked for automatic refresh in query string */
    if ((refresh = pblock_findval("query", rq->reqpb)) != NULL ) {
        if (!strncmp("refresh", refresh, 7)) {
             refresh_val = strchr(refresh, '=');
             if (refresh_val) {
                 refresh_val++;
                 pblock_nvinsert("refresh", refresh_val, rq->srvhdrs);
             }
        }
    }

    /* See if we got our cookie back */
    cookie_pblock = get_cookie_pblock(rq);
    if (cookie_pblock) {
        char *timelast_str = pblock_findval("timelast", cookie_pblock);
        char *countlast_str = pblock_findval("countlast", cookie_pblock);

        if (timelast_str)
            timelast = atoi(timelast_str);
        if (countlast_str)
            countlast = atoi(countlast_str);
    }
    
    timenow = time(NULL);
    countnow = cgiwatch_counter;

    /* Cookie may be stale if server was restarted */
    if ((timelast < cgiwatch_start_time) || (countlast > countnow)) {
        timelast = timenow;
        countlast = countnow;
    }

    if (timenow - cgiwatch_start_time > 0) 
        total_cgi_per_second = 
            (float)countnow / (float)(timenow - cgiwatch_start_time);

    if (timelast && (timenow - timelast)) {
        cgi_per_second = (float)(countnow-countlast)/(float)(timenow-timelast);
    }

    /* Cleanup the old cookie pblock and create a new one */
    if (cookie_pblock)
        pblock_free(cookie_pblock);

    cookie_pblock = pblock_create(5);
    util_sprintf(int_str, "%d", timenow);
    pblock_nvinsert("timelast", int_str, cookie_pblock);
    util_sprintf(int_str, "%d", countnow);
    pblock_nvinsert("countlast", int_str, cookie_pblock);
    set_cookie_pblock(cookie_pblock, rq);

    /* Set the content type */
    param_free(pblock_remove("content-type", rq->srvhdrs));
    pblock_nvinsert("content-type", "text/plain", rq->srvhdrs);

    output_buffer_len = util_snprintf(output_buffer, BUFFER_LENGTH, 
               "\n\n"
               "Cumulative Stats\n"
               "----------------\n"
               "Number of CGI requests:     %6d\n"
               "Elapsed Time:               %6d (sec)\n"
               "CGI requests per second:    %6.2f\n\n"
               "Interval Stats\n"
               "--------------\n"
               "Number of CGI requests:     %6d\n"
               "Elapsed Time:               %6d (sec)\n"
               "CGI requests per second:    %6.2f\n\n",
               countnow,
               timenow - cgiwatch_start_time,
               total_cgi_per_second,
               countnow - countlast,
               timenow - timelast,
               cgi_per_second);

    
    /* Set the content-length of the response */
    util_sprintf(int_str, "%d", output_buffer_len);
    pblock_nvinsert("content-length", int_str, rq->srvhdrs);

    /* Set the response status */
    protocol_status(sn, rq, PROTOCOL_OK, NULL);

    /* Now send the response */
    protocol_start_response(sn, rq);

    net_write(sn->csd, output_buffer, output_buffer_len);
    
    return REQ_PROCEED;
}


/*
 * cgiwatch_send()
 * This is the function which is now the cgi handler function.
 * Counts the number of cgi requests and then just calls the real handler.
 */
static int
cgiwatch_send(pblock *pb, Session *sn, Request *rq)
{
    crit_enter(cgiwatch_lock);
    cgiwatch_counter++;
    crit_exit(cgiwatch_lock);
  
    /* Call the server's CGI handler */
    return cgiwatch_cgisend(pb, sn, rq);
}
