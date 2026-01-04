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
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;

import shared.MessageConstants;

/**
 * A list of movies.
 */
public class MovieList extends List {

    int[] ids;
    String[] posterURLs;
   
    public MovieList() {
        super(SmartTicket.getMsg(MessageConstants.MOVIES), 
	      Choice.IMPLICIT);
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
        posterURLs = new String[size];
    }
    
    /**
     * Add the movie to this list.
     *
     * @param  id  the ID of the movie.
     * @param  title  the title of the movie.
     * @param  rating  the movie's rating.
     * @param  posterURL  the URL of the movie's poster.
     */
    public void addMovie(int id, String title, String rating, 
                         String posterURL) {
        ids[size()] = id;
        posterURLs[size()] = posterURL;
        try {
            append(title, Image.createImage("/st/icons/" + rating + ".png"));
        }
        catch (IOException ioe) {
            // Do nothing.
        }
    }
    
    /**
     * Get the ID of the selected movie.
     */
    public int getMovieID() { return ids[getSelectedIndex()]; }

    /**
     * Get the title of the selected movie.
     */
    public String getMovieTitle() { return getString(getSelectedIndex()); }

    /**
     * Get the URL of the poster for the selected movie.
     */
    public String getPosterUrl() { return posterURLs[getSelectedIndex()]; }
}
