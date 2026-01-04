/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * ntrans.c: Example NSAPI functions to perform name translation.
 *
 * The Server Application Functions in this file are NameTrans class
 * functions, and designed to demonstrate common uses of name translation
 * functions.
 * 
 * Rob McCool
 */

#ifdef XP_WIN32
#define NSAPI_PUBLIC __declspec(dllexport)
#else /* !XP_WIN32 */
#define NSAPI_PUBLIC
#endif /* !XP_WIN32 */

/* 
   The following three are standard headers for SAFs.  They're used to
   get the data structures and prototypes needed to declare and use SAFs.
 */


#include "nsapi.h"


/* -------------------------- explicit_pathinfo --------------------------- */


/*

   CGI programs can use a facility called extra path information to pass
   extra data from the URL to the CGI program. This allows extra parameters
   and allows those scripts to utilize the name translation facilities of
   the server. 

   Normally, the server is required to search for your script name because
   path information is implicit in the script name. That is, the script
   is accessed as

   /dir1/dir2/script/extra/path/information

   /dir1/dir2/script is the program name. 

   In contrast, explicit path information can be created by choosing a
   character which will never appear in your URLs except to delimit path
   information. Suppose we choose the comma to delimit path information.
   This URL would then be:

   /dir1/dir2/script,/extra/path/information

   This does have one side effect: The SCRIPT_NAME variable will have the
   separator character tacked on the end.

   Usage:
   At the end of init.conf:
      Init fn=load-modules shlib=examples.<ext> funcs=explicit-pathinfo
   Inside the default object in obj.conf:
      NameTrans fn=explicit-pathinfo separator=","

   <ext> = so on UNIX
   <ext> = dll on NT.

   This NameTrans should appear before other Nametrans directives in the
   default object.

   Note that you can load more than one function using one load-modules
   command. If you want to try another function from this file, you can load
   it by separating the names to the funcs parameter with commas.

 */

#include <string.h>           /* strchr */
#include "frame/log.h"        /* log_error */


#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int explicit_pathinfo(pblock *pb, Session *sn, Request *rq)
{
    /* Parameter: The character to split the path by */
    char *sep = pblock_findval("separator", pb);

    /* Server variables */
    char *ppath = pblock_findval("ppath", rq->vars);

    /* Temp var */
    char *t;

    /* Verify correct usage */
    if(!sep) {
        log_error(LOG_MISCONFIG, "explicit-pathinfo", sn, rq, 
                  "missing parameter (need root)");
        /* When we abort, the default status code is 500 Server Error */
        return REQ_ABORTED;
    }
    /* Check for separator. If not there, don't do anything */
    t = strchr(ppath, sep[0]);
    if(!t)
        return REQ_NOACTION;

    /* Truncate path at the separator */
    *t++ = '\0';
    /* Assign path information */
    pblock_nvinsert("path-info", t, rq->vars);

    /* Normally NameTrans functions return REQ_PROCEED when they change
       the path. However, we want name translation to continue after we're
       done. */
    return REQ_NOACTION;
}


/* ---------------------------- https-redirect ---------------------------- */


/* 

   A function similar to this one is provided by the server by
   default, but we provide annotated source here for purposes of
   illustration.

   This function takes a parameter specifying a pattern of URLs to
   check.  When a URL matching the pattern is requested, and the
   client is Mozilla (oh very well Netscape Navigator) version 0.96 or
   greater, we'll redirect them to the given URL. Usually this would
   be used to transparently switch the user to a secure
   URL. Otherwise, the function either lets the user access the URL
   anyway, or will give them an alternate document which tells them
   what happened.

   Usage:
   At the end of init.conf:
      Init fn=load-modules shlib=examples.so funcs=https-redirect
   Inside the default object in obj.conf:
      NameTrans fn=https-redirect from="/*" url=https://something/
                alt=/usr/ns-home/docs/nohttps.html

   These parameters would redirect a Mozilla accessing
   "http://something/*" to the URL https://something/ and would send
   other browsers the document /usr/ns-home/docs/nohttps.html

 */


#include "base/util.h"        /* is_mozilla */
#include "frame/protocol.h"   /* protocol_status */
#include "base/shexp.h"       /* shexp_cmp */


#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int https_redirect(pblock *pb, Session *sn, Request *rq)
{
    /* Server Variable */
    char *ppath = pblock_findval("ppath", rq->vars);
    /* Parameters */
    char *from = pblock_findval("from", pb);
    char *url = pblock_findval("url", pb);
    char *alt = pblock_findval("alt", pb);
    /* Work vars */
    char *ua;

    /* Check usage */
    if((!from) || (!url)) {
        log_error(LOG_MISCONFIG, "https-redirect", sn, rq,
                  "missing parameter (need from, url)");
        return REQ_ABORTED;
    }
    /* Use wildcard match to see if this path is one we should redirect */
    if(shexp_cmp(ppath, from) != 0)
        return REQ_NOACTION;   /* no match */

    /* Sigh. The only way to check for SSL capability is to check UA */
    if(request_header("user-agent", &ua, sn, rq) == REQ_ABORTED)
        return REQ_ABORTED;

    /* The is_mozilla function checks for Mozilla version 0.96 or greater */
    if(util_is_mozilla(ua, "0", "96")) {
        /* Set the return code to 302 Redirect */
        protocol_status(sn, rq, PROTOCOL_REDIRECT, NULL);
        /* The error handling functions use this to set the Location: */
        pblock_nvinsert("url", url, rq->vars);
        return REQ_ABORTED;            
    }
    /* No match. Old client. */
    /* If there is an alternate document specified, use it. */
    if(alt) {
        pb_param *pp = pblock_find("ppath", rq->vars);

        /* Trash the old value */
        FREE(pp->value);
        /* We must dup it because the library will later free this pblock */
        pp->value = STRDUP(alt);
        return REQ_PROCEED;
    }
    /* Else do nothing */
    return REQ_NOACTION;
}

