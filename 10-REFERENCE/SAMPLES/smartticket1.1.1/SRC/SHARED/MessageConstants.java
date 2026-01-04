/*
 * Copyright 1999-2002 Sun Microsystems, Inc. ALL RIGHTS RESERVED
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package shared;

/**
 * Defines the constants used by messages sent from the MIDP client.
 */
public interface MessageConstants {

    public static final byte CREATE_USER = 1;
    public static final byte LOGIN_USER = 2;
    public static final byte DISPLAY_LOCALES = 3;
    public static final byte LOAD_MESSAGES = 4;
    public static final byte DISPLAY_MOVIES = 11;
    public static final byte DISPLAY_LOCATIONS = 12;
    public static final byte DISPLAY_SHOWTIMES = 13;
    public static final byte DISPLAY_SEATINGPLAN = 14;
    public static final byte DISPLAY_POSTER = 15;
    public static final byte RESERVE_SEATS = 21;
    public static final byte CONFIRM_SEATS = 22;
    public static final byte CANCEL_SEATS = 23;

    public static final byte VERSION = 1;

    // Application message constants

    public static final int YOUR_ACCOUNT = 0;
    public static final int USER_NAME = 1;
    public static final int PASSWORD = 2;
    public static final int ZIP_CODE = 3;
    public static final int CREDIT_CARD = 4;
    public static final int PREVIEW_MODE = 5;
    public static final int NONE = 6;
    public static final int POSTER = 7;
    public static final int BACK = 8;
    public static final int CANCEL = 9;
    public static final int CONFIRM = 10;
    public static final int NEXT = 11;
    public static final int RESERVE = 12;
    public static final int SAVE = 13;
    public static final int SIGN_IN = 14;
    public static final int START = 15;
    public static final int SMART_TICKET = 16;
    public static final int SIGNED_IN = 17;
    public static final int NO_ACCOUNT = 18;
    public static final int SIGNING_IN = 19;
    public static final int LOADING_MOVIES = 20;
    public static final int ERROR = 21;
    public static final int HAVE_ACCOUNT = 22;
    public static final int CREATING_USER = 23;
    public static final int LOADING_LOCS = 24;
    public static final int LOADING_POSTER = 25;
    public static final int LOADING_SHOWTIMES = 26;
    public static final int LOADING_PLAN = 27;
    public static final int RESERVING_SEATS = 28;
    public static final int PURCHASE_CANCELLED = 29;
    public static final int CANCELLING_PURCHASE = 30;
    public static final int THANK_YOU = 31;
    public static final int CONFIRMING_PURCHASE = 32;
    public static final int CANNOT_CONNECT = 33;
    public static final int SHOWTIMES = 34;
    public static final int SCREEN = 35;
    public static final int MOVIES = 36;
    public static final int LOCATIONS = 37;
    public static final int STOP = 38;
    public static final int LAST_4_DIGITS = 39;
    public static final int MOVIE = 40;
    public static final int TIME = 41;
    public static final int SEATS = 42;
    public static final int TOTAL = 43;
    public static final int CURRENCY_SYMBOL = 44;
    public static final int LOCALE = 45;
    public static final int LOADING_LOCALES = 46;
    public static final int MESSAGES = 47;
    public static final int LOCALE_CHANGED = 48;
	
    // Error message Constants

    public static final int MESSAGE_ERROR = 49;
    public static final int SERVER_ERROR = 50;
    public static final int USER_ALREADY_EXISTS_ERROR = 51;
    public static final int PASSWORD_INCORRECT_ERROR = 52;
    public static final int USER_NOT_FOUND_ERROR = 53;
    public static final int UNAVAILABLE_SEATS_ERROR = 54;
    public static final int APP_ERROR = 55;
    public static final int INVALID_ID = 56;
    public static final int INVALID_PASSWD = 57;
    public static final int INVALID_ZIP = 58;
    public static final int INVALID_CC = 59;
    public static final int INVALID_DATE = 60;
    public static final int NO_IMAGE = 61;

    // this must be kept in sync with highest message id plus one

    public static final int NUM_MSG = 62;
}
