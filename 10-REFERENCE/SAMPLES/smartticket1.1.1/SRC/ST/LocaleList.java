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

package st;

import java.io.*;
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;

import shared.MessageConstants;

/**
 * A list of locales for which localized messages are available.
 */
public class LocaleList extends List {
    
    int[] ids;

    // If no locale is chosen, then the application uses this
    // locale by default.
    String defaultLocale;
    
    public LocaleList(String locale) {
        super(SmartTicket.getMsg(MessageConstants.LOCALE), 
	      Choice.IMPLICIT);
	defaultLocale = locale;
    }
    
    /**
     * Initialize this list to contain the given number of locales,
     * on top of the default locale.
     *
     * @param  the size of this list.
     */
    public void init(int size) {
        for (int i = size(); i > 0; i--) {
            delete(i-1);
        }
        ids = new int[size + 1];

	// Add the default locale.
	ids[size()] = 0;
	append(defaultLocale, null);
    }
    
    /**
     * Add a locale to this list, and map it to the given ID.
     *
     * @param  id  the ID of the locale.
     * @param  locale  the name of the locale.
     */
    public void addLocale(int id, String locale) {
        ids[size()] = id;
        append(locale, null);
    }

    public int getLocaleID() { return ids[getSelectedIndex()]; }

    public String getLocale() { return getString(getSelectedIndex()); }

}
