/*
    PROPRIETARY/CONFIDENTIAL.  Use of this product is subject to license terms.
    Copyright  1999 Sun Microsystems, Inc. Some preexisting portions Copyright
    1999 Netscape Communications Corp. All rights reserved.
*/

/*
 * cookie.h
 *
 * This NSAPI module helps with programmers trying to use cookies.  It provides
 * a couple of basic functions for manipulating cookies without having to
 * parse the cookie header returned by the client manually.  This module 
 * creates a "cookie pblock" which is just a process of converting the cookie
 * returned by the client into a pblock, and vice versa.  pblocks are indexed
 * and searchable, and this makes using cookies a little easier.
 *
 * A pblock is just a set of name/value pairs.  To send a cookie with many
 * name value pairs back to the client, just add several names to your 
 * cookie pblock.
 *
 * For example, the following code will send this cookie header:
 *     Set-cookie: CUSTOMER=WILE_E_COYOTE; PART_NUMBER=ROCKET_LAUNCHER_001
 *
 *     cookie_pblock = pblock_create(5);
 *     pblock_nvinsert("CUSTOMER", "WILE_E_COYOTE", cookie_pblock);
 *     pblock_nvinsert("PART_NUMBER", "ROCKET_LAUNCHER_001", cookie_pblock);
 *     set_cookie_pblock(cookie_pblock);
 *     protocol_start_response(...);
 *
 * Keep in mind that the cookie specification defines four "special" names 
 * for the name/value pairs.  They are:
 *     domain
 *     expires
 *     path
 *     secure
 *
 * The original cookie specification can be found at:
 *      http://home.netscape.com/newsref/std/cookie_spec.html
 *
 */

#ifndef _NSAPI_COOKIE_H_
#define _NSAPI_COOKIE_H_

#include "nsapi.h"

/*
 * The maximum cookie size 
 */
#define MAX_COOKIE_STRING_LEN	1024

/*
 * get_cookie_pblock()
 * Retrieves the "cookie" header sent by the client and converts it into a 
 * pblock.  If the client did not send a cookie header, returns NULL.
 */
pblock *get_cookie_pblock(Request *rq);

/*
 * set_cookie_pblock()
 * Converts a pblock into a cookie header.  
 * 
 * This call must be made prior to calling protocol_start_response().
 * On success, returns a positive number indicating the number of name-value 
 * pairs found in the pblock.   Returns -1 on failure.  Failure most likely
 * means that the size of the cookie was greater than MAX_COOKIE_STRING_LEN.
 */
int set_cookie_pblock(pblock *pb, Request *rq);

#endif /* _NSAPI_COOKIE_H_ */
