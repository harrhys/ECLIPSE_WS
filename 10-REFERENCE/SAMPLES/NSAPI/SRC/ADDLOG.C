/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * Copyright (c) 1994, 1995.  Netscape Communications Corporation.  All
 * rights reserved.
 * 
 * Use of this software is governed by the terms of the license agreement for
 * the Netscape Enterprise or Netscape Personal Server between the
 * parties.
 */


/* ------------------------------------------------------------------------ */


/*
 * addlog.c: Example NSAPI functions to log requests
 *
 * The Server Application Functions in this file are AddLog class
 * functions, and designed to demonstrate in general how to record 
 * your own information about a request
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

/* ------------------------------ brief-log ------------------------------- */


/* 

   The Common Log format gives a lot of information, but it can be
   rather large and take up a lot of space. This custom function logs
   only three things:

   IP-address method URI

   That is, a sample from this log would be:

   198.93.95.99 GET /foo/bar/baz

   Usage:
   At the end of init.conf:
      Init fn=load-modules shlib=example.<ext> funcs=brief-init,brief-log
      Init fn=brief-init file=/foo/bar/baz
   Inside an object in obj.conf:
      AddLog fn=brief-log

   <ext> = so on UNIX
   <ext> = dll on NT.

 */


#include "base/daemon.h" /* daemon_atrestart */
#include "base/file.h"   /* system_fopenWA, system_fclose */
#include "base/util.h"   /* sprintf */


/* File descriptor to be shared between the processes */
static SYS_FILE logfd = SYS_ERROR_FD;

#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC void brief_terminate(void *parameter)
{
    system_fclose(logfd);
    logfd = SYS_ERROR_FD;
}

#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int brief_init(pblock *pb, Session *sn, Request *rq)
{
    /* Parameter */
    char *fn = pblock_findval("file", pb);

    if(!fn) {
        pblock_nvinsert("error", "brief-init: please supply a file name", pb);
        return REQ_ABORTED;
    }
    logfd = system_fopenWA(fn);
    if(logfd == SYS_ERROR_FD) {
        pblock_nvinsert("error", "brief-init: please supply a file name", pb);
        return REQ_ABORTED;
    }
    /* Close log file when server is restarted */
    daemon_atrestart(brief_terminate, NULL);
    return REQ_PROCEED;
}

#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int brief_log(pblock *pb, Session *sn, Request *rq)
{
    /* No parameters */

    /* Server data */
    char *method = pblock_findval("method", rq->reqpb);
    char *uri = pblock_findval("uri", rq->reqpb);
    char *ip = pblock_findval("ip", sn->client);

    /* Temp vars */
    char *logmsg;
    int len;

    logmsg = (char *) 
        MALLOC(strlen(ip) + 1 + strlen(method) + 1 + strlen(uri) + 1 + 1);
    len = util_sprintf(logmsg, "%s %s %s\n", ip, method, uri);
    /* The atomic version uses locking to prevent interference */
    system_fwrite_atomic(logfd, logmsg, len);
    FREE(logmsg);

    return REQ_PROCEED;
}
