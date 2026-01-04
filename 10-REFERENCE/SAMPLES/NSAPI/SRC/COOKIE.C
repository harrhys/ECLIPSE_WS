/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * cookie.c
 *
 */

#include "nsapi.h"
#include "cookie.h"

pblock *get_cookie_pblock(Request *rq)
{
    char *cookie = pblock_findval("cookie", rq->headers);
    char *name, *value;
    char *ptr;
    pblock *cookie_pblock;
    int fInValue = 0;
    int done = 0;

    if (!cookie)
        return NULL;

    cookie_pblock = pblock_create(5);

    ptr = cookie;
    while(*ptr && isspace(*ptr))
        ptr++;
    name = ptr;
    while(!done) {
        switch(*ptr) {
            case '=':
                if (!fInValue) {
                    *ptr = '\0';
                    ptr++;
                    while(*ptr && isspace(*ptr)) 
                        ptr++;
                    value = ptr;
                    fInValue = 1;
                }
                break;
            case '\0':
            case ';':
                if (fInValue) {
                    if (*ptr) {
                        *ptr = '\0';
                        ptr++;
                        while(*ptr && isspace(*ptr)) 
                            ptr++;
                        if (!ptr)    /* don't go past edge */
                            ptr--;
                    } else  
                        done = 1;
                    pblock_nvinsert(name, value, cookie_pblock);
                    name = ptr;
                    fInValue = 0;
                }
                break;
        }
        ptr++;
    }

    return cookie_pblock;
}

int set_cookie_pblock(pblock *pb, Request *rq)
{
    char cookie[MAX_COOKIE_STRING_LEN];
    int cookie_items = 0;
    int bucket;
    struct pb_entry *bucket_ptr;

    /* enumerate all the entries in the pblock */
    for (bucket = 0; bucket < pb->hsize; bucket++) {
        for (bucket_ptr=pb->ht[bucket]; bucket_ptr; bucket_ptr=bucket_ptr->next) {
            char *domain, *secure, *expires, *path;
            int length;
            char *escaped_value;

            domain = pblock_findval("domain", pb);
            expires = pblock_findval("expires", pb);
            path = pblock_findval("path", pb);
            secure = pblock_findval("secure", pb);
            escaped_value = util_uri_escape(NULL, bucket_ptr->param->value);

            length = util_snprintf(cookie, MAX_COOKIE_STRING_LEN, "%s=%s",
                bucket_ptr->param->name, escaped_value);

            if (domain) {
                if (length + strlen(domain) + 1 > MAX_COOKIE_STRING_LEN)
                     return -1;
                strcat(cookie, "; ");
                strcat(cookie, domain);
            }

            if (expires) {
                if (length + strlen(expires) + 1 > MAX_COOKIE_STRING_LEN)
                     return -1;
                strcat(cookie, "; ");
                strcat(cookie, expires);
            }

            if (path) {
                if (length + strlen(path) + 1 > MAX_COOKIE_STRING_LEN)
                     return -1;
                strcat(cookie, "; ");
                strcat(cookie, path);
            }

            if (secure) {
                if (length + strlen(secure) + 1 > MAX_COOKIE_STRING_LEN)
                     return -1;
                strcat(cookie, "; ");
                strcat(cookie, secure);
            }

            /* Insert the cookie into the response headers pblock */
            pblock_nvinsert("Set-cookie", cookie, rq->srvhdrs);

            FREE(escaped_value);

            cookie_items++;
        }
    }

    return cookie_items;
}
