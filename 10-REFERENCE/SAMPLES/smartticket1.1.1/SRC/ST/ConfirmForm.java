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

import javax.microedition.lcdui.*;
import java.util.*;

import shared.MessageConstants;

/**
 * Form displayed to users when they need to confirm their purchase.
 */
public class ConfirmForm extends Form {

    TextField creditCardCheck;
    StringItem movieItem;
    StringItem showtimeItem;
    StringItem seatsItem;
    StringItem totalItem;
    
    public ConfirmForm() {
	super("");
        
        creditCardCheck = new TextField(
            SmartTicket.getMsg(MessageConstants.LAST_4_DIGITS), 
	    "", 4, TextField.PASSWORD|TextField.NUMERIC);
        movieItem = new StringItem(
            SmartTicket.getMsg(MessageConstants.MOVIE) + " ", "");
        showtimeItem = new StringItem(
            SmartTicket.getMsg(MessageConstants.TIME) + " ", "");
        seatsItem = new StringItem(
	    SmartTicket.getMsg(MessageConstants.SEATS) + " ", "");
        totalItem = new StringItem(
	    SmartTicket.getMsg(MessageConstants.TOTAL) + " ", "");

        append(creditCardCheck);
        append(movieItem);
        append(showtimeItem);
        append(seatsItem);
        append(totalItem);
    }

    public void init(String movie, String showtime, int seats) {
	creditCardCheck.setString("");
        movieItem.setText(movie);
        showtimeItem.setText(showtime);
        seatsItem.setText(String.valueOf(seats));
        totalItem.setText(
	   SmartTicket.getMsg(MessageConstants.CURRENCY_SYMBOL) + 
	   seats*9);
    }
    
    public String getCreditCardCheck() {
        return creditCardCheck.getString();
    }
}
