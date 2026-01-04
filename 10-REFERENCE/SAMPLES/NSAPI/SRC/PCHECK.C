/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * pcheck.c: Example NSAPI functions to perform path checks
 *
 * The Server Application Functions in this file are PathCheck class
 * functions, and designed to demonstrate in general how to use this
 * class of functions.
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

/* --------------------------- restrict-by-acf ---------------------------- */


/* 

   When managing access control lists, it is often easier to manage
   simple flat text files which specify allowed hosts than it is to
   manage the wildcard expressions the administrative functions need.

   This function loads an access control list from a custom file upon
   server startup. This information is loaded into static data
   structures where it is then consulted by a PathCheck function to
   verify that the given user is from an allowed host.

   The file is simply a list of IP addresses, one per line, which are
   allowed. All others are denied access. For simplicity, the stdio
   library is used to scan the IP addresses from the
   file. Alternatively we could have used filebufs and util_getline.

   Note that since this function loads a static data structure and
   doesn't make provision for more than one ACF, you may only have one
   ACF per server. An interesting modification to this function would
   be to have it assign names to each ACF, and have the PathCheck
   function check only a named ACF. Another interesting modification
   would be to use session_dns to allow DNS based access lists.

   Usage:
   At the end of init.conf:
      Init fn=load-modules shlib=example.<ext> funcs=acf-init,restrict-by-acf
      Init fn=acf-init file=/foo/bar/baz
   Inside an object in obj.conf:
      PathCheck fn=restrict-by-acf

   <ext> = so on UNIX
   <ext> = dll on NT.

   Note: The load-modules Init function must be called before acf-init
   is called.

 */


/* Set to NULL to prevent problems with people not calling acf-init */
static char **hosts = NULL;

#include <stdio.h>
#include "base/daemon.h"
#include "base/util.h"      /* util_sprintf */
#include "frame/log.h"      /* log_error */
#include "frame/protocol.h" /* protocol_status */

/* The longest line we'll allow in an access control file */
#define MAX_ACF_LINE 256


/* Used to free static array on restart */
#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC void acf_free(void *unused)
{
    register int x;

    for(x = 0; hosts[x]; ++x)
        FREE(hosts[x]);
    FREE(hosts);
    hosts = NULL;
}

#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int acf_init(pblock *pb, Session *sn, Request *rq)
{
    /* Parameter */
    char *acf_file = pblock_findval("file", pb);

    /* Working variables */
    int num_hosts;
    FILE *f;
    char err[MAGNUS_ERROR_LEN];
    char buf[MAX_ACF_LINE];

    /* Check usage. Note that Init functions have special error logging */
    if(!acf_file) {
        util_sprintf(err, "missing parameter to acf_init (need file)");
        pblock_nvinsert("error", err, pb);
        return REQ_ABORTED;
    }
    f = fopen(acf_file, "r");

    /* Did we open it? */
    if(!f) {
        util_sprintf(err, "can't open access control file %s (%s)",
                     acf_file, system_errmsg());
        pblock_nvinsert("error", err, pb);
        return REQ_ABORTED;
    }
    /* Initialize hosts array */
    num_hosts = 0;
    hosts = (char **) MALLOC(1 * sizeof(char *));
    hosts[0] = NULL;

    while(fgets(buf, MAX_ACF_LINE, f)) {
        /* Blast linefeed that stdio helpfully leaves on there */
        buf[strlen(buf) - 1] = '\0';
        hosts = (char **) REALLOC(hosts, (num_hosts + 2) * sizeof(char *));
        hosts[num_hosts++] = STRDUP(buf);
        hosts[num_hosts] = NULL;
    }
    fclose(f);

    /* At restart, free hosts array */
    daemon_atrestart(acf_free, NULL);

    return REQ_PROCEED;
}


#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int restrict_by_acf(pblock *pb, Session *sn, Request *rq)
{
    /* No parameters */

    /* Working variables */
    char *remip = pblock_findval("ip", sn->client);
    register int x;

    if(!hosts) {
        log_error(LOG_MISCONFIG, "restrict-by-acf", sn, rq, 
                  "restrict-by-acf called without call to acf-init");
        /* When we abort, the default status code is 500 Server Error */
        return REQ_ABORTED;
    }
    for(x = 0; hosts[x] != NULL; ++x) {
        /* If they're on the list, they're allowed */
        if(!strcmp(remip, hosts[x]))
            return REQ_NOACTION;
    }
    /* Set response code to forbidden and return an error. */
    protocol_status(sn, rq, PROTOCOL_FORBIDDEN, NULL);
    return REQ_ABORTED;
}
