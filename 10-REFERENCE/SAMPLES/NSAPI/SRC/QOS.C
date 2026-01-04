/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/* qos.c

sample NSAPI handlers for Quality of Service

This code is meant as an example of how to build your own Quality of Service
functions. The primary audience for this is ISPs who want to limit service
for their hosting customers based on bandwidth and connections limits.

In order to enable Quality of Service, you need to :
a) enable QOS in the admin under monitoring. This will set up your
   connections & bandwidth limit and store them in the server.xml
   configuration file
b) load your QOS shared library . This is done in init.conf via
   an Init statement such as :
   Init fn="load-modules" shlib="D:/netscape/server6/plugins/nsapi/examples/qos.dll" funcs="qos_error_sample,qos_handler_sample" shlib_flags="(global|now)"
c) enable the QOS in obj.conf in the default object by adding AuthTrans and Error statements such as :

<Object name=default>
AuthTrans fn="qos_handler_sample"
Error fn="qos_error_sample" code="503"
... # rest of your objects definition

After you do these changes and restart the server, the qos_handler_sample
will check the QOS limits, and the qos_error_sample will return the error
page if the limits are exceeded. You will probably want to show a nicer HTML
page than in this sample, such as one containing an explanation for the user telling him
the server is overloaded and to please come back later.
You may also take any other related action, such as taking note of the
high bandwidth or connection usage of your webhosting customer in order to
remind him to purchase a higher service contract.

*/

#include "base/pblock.h"
#include "frame/log.h"
#include "frame/http.h"     

/*-----------------------------------------------------------------------------
 decode : internal function used for parsing of QOS values in pblock
-----------------------------------------------------------------------------*/

void decode(const char* val, PRInt32* var, pblock* pb)
{
    char* pbval;
    if ( (!var) || (!val) || (!pb) )
        return;
    pbval = pblock_findval(val, pb);
    if (!pbval)
        return;

    *var = atoi(pbval);
}

/*-----------------------------------------------------------------------------
 qos_error_sample

 This function is meant to be an error handler for an HTTP 503 error code,
 which is returned by qos_handler when QOS limits are exceeded and enforced

 This sample function just prints out a message about which limits were exceeded.

-----------------------------------------------------------------------------*/

NSAPI_PUBLIC int qos_error_sample(pblock *pb, Session *sn, Request *rq)
{
    char error[1024] = "";
	char* err_header = "<HTML><HEAD><TITLE>Unable to service request</TITLE></HEAD><BODY>";
	char* err_footer = "</BODY></HTML>";

    PRBool ours = PR_FALSE;

    PRInt32 vs_bw = 0, vs_bwlim = 0, vs_bw_ef = 0,
            vs_conn = 0, vs_connlim = 0, vs_conn_ef = 0,
            vsc_bw = 0, vsc_bwlim = 0, vsc_bw_ef = 0,
            vsc_conn = 0, vsc_connlim = 0, vsc_conn_ef = 0,
            srv_bw = 0, srv_bwlim = 0, srv_bw_ef = 0,
            srv_conn = 0, srv_connlim = 0, srv_conn_ef = 0;

    pblock* apb = rq->vars;

    decode("vs_bandwidth", &vs_bw, apb);
    decode("vs_connections", &vs_conn, apb);

    decode("vs_bandwidth_limit", &vs_bwlim, apb);
    decode("vs_bandwidth_enforced", &vs_bw_ef, apb);

    decode("vs_connections_limit", &vs_connlim, apb);
    decode("vs_connections_enforced", &vs_conn_ef, apb);
    
    decode("vsclass_bandwidth", &vsc_bw, apb);
    decode("vsclass_connections", &vsc_conn, apb);
    
    decode("vsclass_bandwidth_limit", &vsc_bwlim, apb);
    decode("vsclass_bandwidth_enforced", &vsc_bw_ef, apb);

    decode("vsclass_connections_limit", &vsc_connlim, apb);
    decode("vsclass_connections_enforced", &vsc_conn_ef, apb);
    
    decode("server_bandwidth", &srv_bw, apb);
    decode("server_connections", &srv_conn, apb);
    
    decode("server_bandwidth_limit", &srv_bwlim, apb);
    decode("server_bandwidth_enforced", &srv_bw_ef, apb);

    decode("server_connections_limit", &srv_connlim, apb);
    decode("server_connections_enforced", &srv_conn_ef, apb);

    if ((vs_bwlim) && (vs_bw>vs_bwlim))
    {
        /* VS bandwidth limit was exceeded, display it */
        ours = PR_TRUE;
        sprintf(error, "<P>Virtual server bandwidth limit of %d . Current VS bandwidth : %d . <P>",
				vs_bwlim, vs_bw);
    };

    if ((vs_connlim) && (vs_conn>vs_connlim))
    {
        /* VS connection limit was exceeded, display it */
        ours = PR_TRUE;
        sprintf(error, "<P>Virtual server connection limit of %d . Current VS connections : %d . <P>",
				vs_connlim, vs_conn);
    };

    if ((vsc_bwlim) && (vsc_bw>vsc_bwlim))
    {
        /* VSCLASS bandwidth limit was exceeded, display it */
        ours = PR_TRUE;
        sprintf(error, "<P>Virtual server class bandwidth limit of %d . Current VSCLASS bandwidth : %d . <P>",
				vsc_bwlim, vsc_bw);
    };

    if ((vsc_connlim) && (vsc_conn>vsc_connlim))
    {
        /* VSCLASS connection limit was exceeded, display it */
        ours = PR_TRUE;
        sprintf(error, "<P>Virtual server class connection limit of %d . Current VSCLASS connections : %d . <P>",
				vsc_connlim, vsc_conn);
    };

    if ((srv_bwlim) && (srv_bw>srv_bwlim))
    {
        /* SERVER bandwidth limit was exceeded, display it */
        ours = PR_TRUE;
        sprintf(error, "<P>Global bandwidth limit of %d . Current bandwidth : %d . <P>",
				srv_bwlim, srv_bw);
    };

    if ((srv_connlim) && (srv_conn>srv_connlim))
    {
        /* SERVER connection limit was exceeded, display it */
        ours = PR_TRUE;
        sprintf(error, "<P>Global connection limit of %d . Current connections : %d . <P>",
				srv_connlim, srv_conn);
    };

    if (ours)
    {
        /* this was really a QOS failure, therefore send the error page */
        pb_param *pp = pblock_remove ("content-type", rq->srvhdrs);

        if (pp != NULL)
        	param_free (pp);

        pblock_nvinsert ("content-type", "text/html", rq->srvhdrs);

        protocol_start_response(sn, rq);
		net_write(sn->csd, err_header, strlen(err_header));
        net_write(sn->csd, error, strlen(error));
		net_write(sn->csd, err_footer, strlen(err_footer));
        return REQ_PROCEED;
    }
    else
    {
        /* this 503 didn't come from a QOS SAF failure, let someone else handle it */
        return REQ_PROCEED;
    };
}

/*-----------------------------------------------------------------------------
 qos_handler_sample

 This is an NSAPI AuthTrans function

 It examines the QOS values in the request and compare them to the QOS limits.

 It does several things :
 1) It will log errors if the QOS limits are exceeded. 
 2) It will return REQ_ABORTED with a 503 error code if the QOS limits are exceeded,
    and the QOS limits are set to be enforced. Otherwise it will return REQ_PROCEED

-----------------------------------------------------------------------------*/

NSAPI_PUBLIC int qos_handler_sample(pblock *pb, Session *sn, Request *rq)
{
    PRBool ok = PR_TRUE;

    PRInt32 vs_bw = 0, vs_bwlim = 0, vs_bw_ef = 0,
            vs_conn = 0, vs_connlim = 0, vs_conn_ef = 0,
            vsc_bw = 0, vsc_bwlim = 0, vsc_bw_ef = 0,
            vsc_conn = 0, vsc_connlim = 0, vsc_conn_ef = 0,
            srv_bw = 0, srv_bwlim = 0, srv_bw_ef = 0,
            srv_conn = 0, srv_connlim = 0, srv_conn_ef = 0;

    pblock* apb = rq->vars;

    decode("vs_bandwidth", &vs_bw, apb);
    decode("vs_connections", &vs_conn, apb);

    decode("vs_bandwidth_limit", &vs_bwlim, apb);
    decode("vs_bandwidth_enforced", &vs_bw_ef, apb);

    decode("vs_connections_limit", &vs_connlim, apb);
    decode("vs_connections_enforced", &vs_conn_ef, apb);
    
    decode("vsclass_bandwidth", &vsc_bw, apb);
    decode("vsclass_connections", &vsc_conn, apb);
    
    decode("vsclass_bandwidth_limit", &vsc_bwlim, apb);
    decode("vsclass_bandwidth_enforced", &vsc_bw_ef, apb);

    decode("vsclass_connections_limit", &vsc_connlim, apb);
    decode("vsclass_connections_enforced", &vsc_conn_ef, apb);
    
    decode("server_bandwidth", &srv_bw, apb);
    decode("server_connections", &srv_conn, apb);
    
    decode("server_bandwidth_limit", &srv_bwlim, apb);
    decode("server_bandwidth_enforced", &srv_bw_ef, apb);

    decode("server_connections_limit", &srv_connlim, apb);
    decode("server_connections_enforced", &srv_conn_ef, apb);

    if ((vs_bwlim) && (vs_bw>vs_bwlim))
    {
        /* bandwidth limit was exceeded, log it */
        ereport(LOG_FAILURE, "Virtual server bandwidth limit of %d exceeded. Current VS bandwidth : %d", &vs_bwlim, vs_bw);

        if (vs_bw_ef)
        {
            /* and enforce it */
            ok = PR_FALSE;
        };
    };

    if ((vs_connlim) && (vs_conn>vs_connlim))
    {
        /* connection limit was exceeded, log it */
        ereport(LOG_FAILURE, "Virtual server connection limit of %d exceeded. Current VS connections : %d", &vs_connlim, vs_conn);

        if (vs_conn_ef)
        {
            /* and enforce it */
            ok = PR_FALSE;
        };
    };

    if ((vsc_bwlim) && (vsc_bw>vsc_bwlim))
    {
        /* bandwidth limit was exceeded, log it */
        ereport(LOG_FAILURE, "Virtual server class bandwidth limit of %d exceeded. Current VSCLASS bandwidth : %d", &vsc_bwlim, vsc_bw);

        if (vsc_bw_ef)
        {
            /* and enforce it */
            ok = PR_FALSE;
        };
    };

    if ((vsc_connlim) && (vsc_conn>vsc_connlim))
    {
        /* connection limit was exceeded, log it */
        ereport(LOG_FAILURE, "Virtual server class connection limit of %d exceeded. Current VSCLASS connections : %d", &vsc_connlim, vsc_conn);

        if (vsc_conn_ef)
        {
            /* and enforce it */
            ok = PR_FALSE;
        };
    };


    if ((srv_bwlim) && (srv_bw>srv_bwlim))
    {
        /* bandwidth limit was exceeded, log it */
        ereport(LOG_FAILURE, "Global bandwidth limit of %d exceeded. Current global bandwidth : %d", &srv_bwlim, srv_bw);

        if (srv_bw_ef)
        {
            /* and enforce it */
            ok = PR_FALSE;
        };
    };

    if ((srv_connlim) && (srv_conn>srv_connlim))
    {
        /* connection limit was exceeded, log it */
        ereport(LOG_FAILURE, "Global connection limit of %d exceeded. Current global connections : %d", &srv_connlim, srv_conn);

        if (srv_conn_ef)
        {
            /* and enforce it */
            ok = PR_FALSE;
        };
    };

    if (ok)
    {
        return REQ_PROCEED;
    }
    else
    {
        /* one of the limits was exceeded
           therefore, we set HTTP error 503 "server too busy" */
        protocol_status(sn, rq, PROTOCOL_SERVICE_UNAVAILABLE, NULL);
        return REQ_ABORTED;
    };
}

