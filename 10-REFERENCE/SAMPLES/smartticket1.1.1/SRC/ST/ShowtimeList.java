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
import javax.microedition.io.*;
import javax.microedition.lcdui.*;

import shared.MessageConstants;

/**
 * A list of showtimes.  
 */
public class ShowtimeList extends List {
    
    static Image clockImage;

    int[] ids;
    
    public ShowtimeList() {
        super(SmartTicket.getMsg(MessageConstants.SHOWTIMES), IMPLICIT);
        if (clockImage == null) {
            try {
                clockImage = Image.createImage("/st/icons/clock.png");
            }
            catch (IOException ioe) {
                // Ignore.
            }
        }
    }

    /**
     * Initialize this list to contain the given number of items.
     *
     * @param  the size of this list.
     */
    public void init(int size) {
        for (int i = size(); i > 0; i--) {
            delete(i-1);
        }
        ids = new int[size];
    }
    
    /**
     * Add the given showtime to the list, corresponding to the given ID.
     * 
     * @param  the ID of the showtime to add.
     * @param  the time of the show to add.
     */
    public void addShowtime(int id, String showtime) {
        ids[size()] = id;
        append(showtime, clockImage);
    }
    
    /**
     * Return the ID of the selected show.
     */
    public int getShowID() { return ids[getSelectedIndex()]; }

    /**
     * Return the time of the selected show.
     */
    public String getShowtime() { return getString(getSelectedIndex()); }
}
