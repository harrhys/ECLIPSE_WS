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

import shared.MessageConstants;

/**
 * Form used to enter customer account information and user
 * preferences.  
 */
public class AccountForm extends Form {
   
    TextField username;
    TextField password;
    TextField zipCode;
    TextField creditCard;
    ChoiceGroup previewGroup;

    /** Preview mode to show nothing. */
    public static final byte PREVIEW_NONE = 0;

    /** Preview mode to show a poster. */
    public static final byte PREVIEW_POSTER = 1;

    public AccountForm() {
        super(SmartTicket.getMsg(MessageConstants.YOUR_ACCOUNT));

        username = new TextField(
            SmartTicket.getMsg(MessageConstants.USER_NAME), 
	    "", 6, TextField.ANY);
        password = new TextField(
            SmartTicket.getMsg(MessageConstants.PASSWORD), 
	    "", 6, TextField.PASSWORD);
        zipCode = new TextField(
            SmartTicket.getMsg(MessageConstants.ZIP_CODE), 	    
            "", 5, TextField.NUMERIC);
        creditCard = new TextField(
            SmartTicket.getMsg(MessageConstants.CREDIT_CARD), 
	    "", 12, TextField.NUMERIC);
        previewGroup = new ChoiceGroup(
	    SmartTicket.getMsg(MessageConstants.PREVIEW_MODE), 
	    Choice.EXCLUSIVE);
        previewGroup.append(
	    SmartTicket.getMsg(MessageConstants.NONE), 
	    null);
        previewGroup.append(
	    SmartTicket.getMsg(MessageConstants.POSTER), 
	    null);

        append(username);
        append(password);
        append(zipCode);
        append(creditCard);
        append(previewGroup);
    }

    /**
     * Validate all fields.
     *
     * @throws  ApplicationException  when a field is invalid.
     */
    public void validateAll() throws ApplicationException { 
        if (username.size() < 4) {
	    throw new ApplicationException(MessageConstants.INVALID_ID);
	}
        if (password.size() != password.getMaxSize()) {
	    throw new ApplicationException(MessageConstants.INVALID_PASSWD);
	}
        if (zipCode.size() != zipCode.getMaxSize()) {
	    throw new ApplicationException(MessageConstants.INVALID_ZIP);
	}
        if (creditCard.size() != creditCard.getMaxSize()) {
	    throw new ApplicationException(MessageConstants.INVALID_CC);
	}
    }
    
    public String getUsername() { return username.getString(); }
    
    public String getPassword() { return password.getString(); }
    
    public byte getPreviewMode() { 
        return (byte)previewGroup.getSelectedIndex(); 
    }
    
    public int getZipCode() { return Integer.parseInt(zipCode.getString()); }
    
    public String getCreditCard() { return creditCard.getString(); }
}
