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
 * service.c: Example NSAPI functions to complete responses to requests
 *
 * The Server Application Functions in this file are Service class
 * functions, and designed to demonstrate in general how to finish the
 * server's response to a request.
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

/* ----------------------------- send-images ------------------------------ */


/*

   Users of Netscape 1.1 may be familiar with what those wacky client
   guys are calling the "server push" version of dynamic document
   refresh. One problem with doing this operation through CGI is that
   it causes two processes, your CGI program and its corresponding
   server process, to run. This can be wasteful. Further, your program
   does not have direct access to the client socket and thus can't be
   absolutely sure of when the client aborts a connection.

   This service class function is a replacement for the doit.cgi
   demonstration available on the Netscape home pages. When accessed
   as /dir1/dir2/foo.picgroup, the function will check if it's being
   accessed by Mozilla/1.1. If not, it will send a short error
   message. 

   foo.picgroup should be a simple file which lists on every line a
   filename followed by a content-type. The filenames will be filtered
   such that they may only refer to files in the current directory.

   Example .picgroup file:
      one.gif image/gif
      two.jpg image/jpeg
      three.gif image/gif

   Usage:
   At the end of init.conf:
      Init fn=load-modules shlib=example.<ext> funcs=send-images
   In mime.types:
      type=magnus-internal/picgroup exts=picgroup
   Inside an object in obj.conf:
      Service method=(GET|HEAD) type=magnus-internal/picgroup fn=send-images 

   <ext> = so on UNIX
   <ext> = dll on NT.

   send-images also takes an optional parameter, delay, which specifies 
   the number of seconds to pause between images.

 */


#include "base/util.h"       /* is_mozilla, getline */
#include "frame/protocol.h"  /* protocol_start_response */
#include "base/file.h"       /* system_fopenRO */
#include "base/buffer.h"     /* filebuf */
#include "frame/log.h"       /* log_error */

#include <stdio.h>
#ifndef XP_WIN32
#include <unistd.h>  /* sleep */
#else /* XP_WIN32 */
#include <windows.h>
#endif /* XP_WIN32 */

#define PICGROUP_MAXLINE 300
#define DEFAULT_DELAY 1

#define FIRSTMSG "--AnUnlikelyOccurrence\n"
#define NEWMSG "\n--AnUnlikelyOccurrence\n"
#define MULTICT "multipart/x-mixed-replace; boundary=AnUnlikelyOccurrence"
#define ERRMSG \
"This feature requires <a href=\"ftp://ftp.netscape.com\">Netscape 1.1</a><p>"


/*
   This function sends the given file to Netscape
 */

#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int _picgroup_send_file(char *dir, char *fn, char *ct, int delay,
                        Session *sn, Request *rq)
{
    struct stat finfo;
    filebuffer *buf;
    SYS_FILE fd;
    int len, ret = REQ_PROCEED;
    char ctbuf[PICGROUP_MAXLINE + sizeof("Content-type: ") + 2 + 1];

    char *pathname = (char *) MALLOC(strlen(dir) + strlen(fn) + 1);

    util_sprintf(pathname, "%s%s", dir, fn);

    /* If any errors, just skip it. The gotos are mostly for convenience. */
    if(stat(pathname, &finfo) == -1)
        goto done;

    fd = system_fopenRO(pathname);
    if(fd == SYS_ERROR_FD)
        goto done;

    buf = filebuf_open_nostat(fd, FILE_BUFFERSIZE, &finfo);
    if(!buf) {
        system_fclose(fd);
        goto done;
    }
    len = util_sprintf(ctbuf, "Content-type: %s\nContent-length: %d\n\n", 
                       ct, finfo.st_size);
    if(net_write(sn->csd, ctbuf, len) == IO_ERROR)
        ret = REQ_EXIT;

    if(filebuf_buf2sd(buf, sn->csd) == IO_ERROR)
        ret = REQ_EXIT;
    filebuf_close(buf);

    if(net_write(sn->csd, NEWMSG, strlen(NEWMSG)) == IO_ERROR)
        ret = REQ_EXIT;
    /* Pause to display */
#ifdef XP_WIN32
    Sleep(delay*1000);
#else /* !XP_WIN32 */
    sleep(delay);
#endif /* !XP_WIN32 */

  done:
    FREE(pathname);
    return ret;
}


#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int send_images(pblock *pb, Session *sn, Request *rq)
{
    char *delaystr = pblock_findval("delay", pb);

    /* Server variables */
    char *path = pblock_findval("path", rq->vars);

    /* Work variables */
    filebuffer *groupbuf;
    SYS_FILE fd;
    char *ua, *t, *base;
    char line[PICGROUP_MAXLINE], *ct;
    struct stat *fi;
    int lnum, ret, delay;

    delay = (delaystr ? atoi(delaystr) : DEFAULT_DELAY);

    /* We need to get rid of the internal content type. */
    param_free(pblock_remove("content-type", rq->srvhdrs));

    /* 
       There's a chance somebody accidentally sent us path info.
       If they did, send not found. 
     */
    if( (t = pblock_findval("path-info", rq->vars)) ) {
        log_error(LOG_WARN, "send-images", sn, rq, "%s%s not found", path, t);
        protocol_status(sn, rq, PROTOCOL_NOT_FOUND, NULL);
        return REQ_ABORTED;
    }

    /* 
       Get the file's cached stat information, and open it. 
       Errors mean it doesn't exist, or it can't be read
     */
    if( (!(fi = request_stat_path(path, rq)) ) || 
        ( (fd = system_fopenRO(path)) == SYS_ERROR_FD) )
    {
        int notfound = file_notfound();
        log_error(LOG_WARN, "send-images", sn, rq, "error opening %s (%s)", 
                  path, system_errmsg());
        protocol_status(sn, rq, (notfound ? PROTOCOL_NOT_FOUND : 
                                 PROTOCOL_FORBIDDEN), NULL);
        return REQ_ABORTED;
    }

    /* Use server native buffered I/O on the file */
    groupbuf = filebuf_open_nostat(fd, FILE_BUFFERSIZE, fi);
    if(!groupbuf) {
        log_error(LOG_WARN,"send-file", sn, rq, 
                  "error opening buffer from %s (%s)", path, 
                  system_errmsg());
        protocol_status(sn, rq, PROTOCOL_FORBIDDEN, NULL);
        system_fclose(fd);
        return REQ_ABORTED;
    }

    protocol_status(sn, rq, PROTOCOL_OK, NULL);

    /* Every time I have to check this variable I cringe. How revolting. */
    if(request_header("user-agent", &ua, sn, rq) == REQ_ABORTED)
        return REQ_ABORTED;
    /* Check for Mozilla 1.1 or better */
    if(!util_is_mozilla(ua, "1", "1")) {
        /* Return a short error message */
        pblock_nvinsert("content-type", "text/html", rq->srvhdrs);
        if(protocol_start_response(sn, rq) != REQ_NOACTION)
            (void) net_write(sn->csd, ERRMSG, strlen(ERRMSG));
        return REQ_PROCEED;
    }

    /* 
       Normally, a call to stat followed by protocol_set_finfo would
       be used here to handle conditional GET requests from the
       browser. However, caching is not used for dynamic documents.
     */

    /* Set content-type and begin response */
    /* Technically, I should generate a random string here. */
    pblock_nvinsert("content-type", MULTICT, rq->srvhdrs);

    /* A noaction response from this function means the request was HEAD */
    if(protocol_start_response(sn, rq) == REQ_NOACTION) {
        filebuf_close(groupbuf);  /* this also closes fd */
        return REQ_PROCEED;
    }

    /* Start response by giving boundary string */
    if(net_write(sn->csd, FIRSTMSG, strlen(FIRSTMSG)) == IO_ERROR)
        return REQ_EXIT;

    /* Get the base directory name */
    base = STRDUP(path);
    t = strrchr(base, FILE_PATHSEP);
    *(t + 1) = '\0';

    for(ret = 0, lnum = 1; ret == 0; ++lnum) {
        ret = util_getline(groupbuf, lnum, PICGROUP_MAXLINE, line);
        if(ret != -1) {
            /* If no space, invalid format. But just skip. */
            if(!(ct = strchr(line, ' ')))
                continue;
            *ct++ = '\0';

            /* Only files in this directory are allowed. */
            if(strchr(line, FILE_PATHSEP))
                continue;

            /* Send a page break followed by the image */
            if(_picgroup_send_file(base, line, ct, delay, sn, rq) == REQ_EXIT)
                ret = -1;

            /* Check for EOF */
            if(ret == 1)
                ret = -1;
        }
    }
    filebuf_close(groupbuf);
    return REQ_PROCEED;
}
