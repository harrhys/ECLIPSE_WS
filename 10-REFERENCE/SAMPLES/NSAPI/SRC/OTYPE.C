/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * otype.c: Example NSAPI functions to assign object types
 *
 * The Server Application Functions in this file are ObjectType class
 * functions, and designed to demonstrate in general how to assign
 * content metainformation like type to data objects.
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

/* ----------------------------- shtml-search ----------------------------- */


/*

   The server gives you many options for identifying which HTML files
   you want parsed, and which you don't. This is yet another way to do
   this.

   This function will look at the given file name. If it finds that it
   ends with .html, it will look for a file with the same base name,
   but with the extension .shtml instead of .html. If it finds one, it
   will use that path and tell the rest of the server that the file is
   parsed HTML instead of regular HTML. Note that this requires an 
   extra stat call for every HTML file accessed.

   Usage:
   At the end of init.conf:
      Init fn=load-modules shlib=example.<ext> funcs=html2shtml
   Inside an object in obj.conf:
      ObjectType fn=html2shtml

   <ext> = so on UNIX
   <ext> = dll on NT.

   The ObjectType directive must appear before any call to type-by-extension
   in obj.conf

 */

#include <string.h>    /* strncpy */
#include "base/util.h"

#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int html2shtml(pblock *pb, Session *sn, Request *rq)
{
    /* No parameters */

    /* Work variables */
    pb_param *path = pblock_find("path", rq->vars);
    struct stat finfo;
    char *npath;
    int baselen;

    /* If the type has already been set, don't do anything */
    if(pblock_findval("content-type", rq->srvhdrs))
        return REQ_NOACTION;

    /* If path does not end in .html, let normal object types do their job */
    baselen = strlen(path->value) - 5;
    if(strcasecmp(&path->value[baselen], ".html") != 0)
        return REQ_NOACTION;

    /* 1 = Room to convert html to shtml */
    npath = (char *) MALLOC((baselen + 5) + 1 + 1);
    strncpy(npath, path->value, baselen);
    strcpy(&npath[baselen], ".shtml");

    /* If it's not there, don't do anything */
    if(stat(npath, &finfo) == -1) {
        FREE(npath);
        return REQ_NOACTION;
    }
    /* Got it, do the switch */
    FREE(path->value);
    path->value = npath;

    /* The server caches the stat() of the current path. Update it. */
    (void) request_stat_path(NULL, rq);

    pblock_nvinsert("content-type", "magnus-internal/parsed-html", 
                    rq->srvhdrs);
    return REQ_PROCEED;
}
