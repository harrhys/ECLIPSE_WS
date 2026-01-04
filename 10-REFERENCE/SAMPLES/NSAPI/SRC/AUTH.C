/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * auth.c: Example NSAPI functions to perform user authorization
 *
 * The Server Application Functions in this file are AuthTrans class
 * functions, and designed to demonstrate in general how to use the 
 * server's authorization facilities.
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

/* ---------------------------- hardcoded_auth ---------------------------- */


/*

   This function is kind of silly, but is used to demonstrate how to
   use your own custom ways of verifying that the username and
   password that a remote client provided is accurate. This program
   uses a hard coded table of usernames and passwords and checks a
   given user's password against the one in the static data array.

   Note that this function doesn't actually enforce authorization
   requirements, it only takes given information and tells the server
   if it's correct or not. The PathCheck function require-auth
   performs the enforcement.

   The userdb parameter is not used by this function, but your
   function can use it as an opaque string to tell you which database
   to use.

   Usage:
   At the end of init.conf:
      Init fn=load-modules shlib=example.<ext> funcs=hardcoded-auth
   Inside an object in obj.conf:
      AuthTrans fn=basic-auth auth-type="basic" userdb=garbage
                userfn=hardcoded-auth
      PathCheck fn=require-auth realm="test realm" auth-type="basic"
   <ext> = so on UNIX
   <ext> = dll on NT.


 */


typedef struct {
    char *name;
    char *pw;
} user_s;

static user_s user_set[] = {
    {"joe", "shmoe"},
    {"suzy", "creamcheese"},
    {NULL, NULL}
};

#include "frame/log.h"


#ifdef __cplusplus
extern "C"
#endif
NSAPI_PUBLIC int hardcoded_auth(pblock *param, Session *sn, Request *rq)
{
    /* Parameters given to us by auth-basic */
    char *pwfile = pblock_findval("userdb", param);
    char *user = pblock_findval("user", param);
    char *pw = pblock_findval("pw", param);

    /* Temp variables */
    register int x;

    for(x = 0; user_set[x].name != NULL; ++x) {
        /* If this isn't the user we want, keep going */
        if(strcmp(user, user_set[x].name) != 0)
            continue;
        /* Verify password */
        if(strcmp(pw, user_set[x].pw)) {
            log_error(LOG_SECURITY, "hardcoded-auth", sn, rq, 
                  "user %s entered wrong password", user);
            /* This will cause the enforcement function to ask user again */
            return REQ_NOACTION;
        }
        /* If we return REQ_PROCEED, the username will be accepted */
        return REQ_PROCEED;
    }
    /* No match, have it ask them again */
    log_error(LOG_SECURITY, "hardcoded-auth", sn, rq, 
              "unknown user %s", user);
    return REQ_NOACTION;
}
