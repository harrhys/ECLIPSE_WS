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

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.io.*;
import java.util.Hashtable;

import shared.MessageConstants;

/**
 * Main application.
 */
public class SmartTicket extends MIDlet implements CommandListener {
    
    Session session;

    static String[] msgTab;
    
    String servlet;
    String posterURL;
    String locale;
    String splashURL;
    Display display;
    GaugeForm gaugeForm;
    
    ImageCanvas splashCanvas;
    AccountForm accountForm;
    Form signedInForm;
    MovieList movieList;
    ImageCanvas posterCanvas;
    LocationList locationList;
    ShowtimeList showtimeList;
    SeatingCanvas seatingCanvas;
    ConfirmForm confirmForm;
    LocaleList localeList;
    
    Alert alertScreen;
    Displayable nextScreen;
    Displayable previousScreen;
    
    Command backCmd;
    Command cancelCmd;
    Command confirmCmd;
    Command nextCmd;
    Command reserveCmd;
    Command saveCmd;
    Command signInCmd;
    Command startCmd;
    Command localeCmd;
    
    /* Overridden methods. */

    public void startApp() {

        session = new Session(this);
        session.open();

        servlet = getBaseServletURL();
        posterURL = getAppProperty("SMARTicket-Poster-URL");
	locale = getAppProperty("SMARTicket-Locale");

        if (msgTab == null) {
            initMsg();
        }

	backCmd = new Command(getMsg(MessageConstants.BACK), 
			      Command.BACK, 10);
	cancelCmd = new Command(getMsg(MessageConstants.CANCEL), 
				Command.CANCEL, 10);
	confirmCmd = new Command(getMsg(MessageConstants.CONFIRM), 
				 Command.SCREEN, 5);
	nextCmd = new Command(getMsg(MessageConstants.NEXT), 
			      Command.SCREEN, 5);
	reserveCmd = new Command(getMsg(MessageConstants.RESERVE), 
				 Command.SCREEN, 5);
	saveCmd = new Command(getMsg(MessageConstants.SAVE), 
			      Command.SCREEN, 5);
	signInCmd = new Command(getMsg(MessageConstants.SIGN_IN), 
				Command.SCREEN, 5);
	startCmd = new Command(getMsg(MessageConstants.START), 
			       Command.SCREEN, 5);
	localeCmd = new Command(getMsg(MessageConstants.LOCALE),
				Command.SCREEN, 5);

        display = Display.getDisplay(this);
       
        Image i = null;
        try {
            i = Image.createImage("/st/icons/smarticket.png");
        }
        catch (IOException e) {
        }
        
        splashCanvas = new ImageCanvas(i);
	splashCanvas.addCommand(localeCmd);
        splashCanvas.addCommand(signInCmd);
        splashCanvas.setCommandListener(this);

	localeList = new LocaleList(locale);
	localeList.addCommand(nextCmd);
	localeList.addCommand(backCmd);
	localeList.setCommandListener(this);
        
        signedInForm = new Form(getMsg(MessageConstants.SMART_TICKET));
        StringItem signedInMessage = 
            new StringItem(null, getMsg(MessageConstants.SIGNED_IN));
        signedInForm.append(signedInMessage);
        signedInForm.addCommand(startCmd);
        signedInForm.setCommandListener(this);
        
        accountForm = new AccountForm();
        accountForm.addCommand(cancelCmd);
        accountForm.addCommand(saveCmd);
        accountForm.setCommandListener(this);
        
        movieList = new MovieList();
        movieList.addCommand(backCmd);
        movieList.addCommand(nextCmd);
        movieList.setCommandListener(this);
        
        posterCanvas = new ImageCanvas();
        posterCanvas.addCommand(backCmd);
        posterCanvas.addCommand(nextCmd);
        posterCanvas.setCommandListener(this);
        
        locationList = new LocationList();
        locationList.addCommand(backCmd);
        locationList.addCommand(nextCmd);
        locationList.setCommandListener(this);
        
        showtimeList = new ShowtimeList();
        showtimeList.addCommand(backCmd);
        showtimeList.addCommand(nextCmd);
        showtimeList.setCommandListener(this);
        
        seatingCanvas = new SeatingCanvas(display, reserveCmd);
        seatingCanvas.addCommand(cancelCmd);
        seatingCanvas.addCommand(reserveCmd);
        seatingCanvas.setCommandListener(this);
        
        confirmForm = new ConfirmForm();
        confirmForm.addCommand(cancelCmd);
        confirmForm.addCommand(confirmCmd);
        confirmForm.setCommandListener(this);

        gaugeForm = new GaugeForm("", false);
        
        display.setCurrent(splashCanvas);
    }

    public void pauseApp() { 
	session.close(); 
    }

    public void destroyApp(boolean unconditional) {
	session.close();
    }
    
    public void commandAction(Command c, Displayable d) {
        if (d == splashCanvas) {
	    if (c == localeCmd) {
		// retrieve the list of supported locales
		session.message = MessageConstants.DISPLAY_LOCALES;
		nextScreen = localeList;
		previousScreen = splashCanvas;
		startLoading(true, getMsg(MessageConstants.LOADING_LOCALES));
	    } else {
		if (session.isNewUser()) {
		    Alert a = createAlert(getMsg(MessageConstants.NO_ACCOUNT));
		    display.setCurrent(a, accountForm);
		    return;
		}
		session.message = MessageConstants.LOGIN_USER;
		nextScreen = signedInForm;
		previousScreen = splashCanvas;
		startLoading(false, getMsg(MessageConstants.SIGNING_IN));
	    }
        } else if (d == localeList) {
	    if (c == nextCmd) {
		session.message = MessageConstants.LOAD_MESSAGES;
		nextScreen = splashCanvas;
		previousScreen = splashCanvas;
		alertScreen = 
		    createAlert(getMsg(MessageConstants.LOCALE_CHANGED));
		startLoading(false, getMsg(MessageConstants.MESSAGES));
	    } else {
		display.setCurrent(splashCanvas);
                return;
	    }
	} else if (d == signedInForm) {
	    session.message = MessageConstants.DISPLAY_MOVIES;
            nextScreen = movieList;
            previousScreen = signedInForm;
            startLoading(true, getMsg(MessageConstants.MOVIES));
        } else if (d == accountForm) {
            if (c == cancelCmd) {
                display.setCurrent(splashCanvas);
                return;
            }
            
            try {
                accountForm.validateAll();
            } catch (Exception e) {
                Alert a = createAlert(getMsg(MessageConstants.ERROR));
                a.setString(e.getMessage());
                display.setCurrent(a, accountForm);
                return;
            }
            
	    session.message = MessageConstants.CREATE_USER;
            nextScreen = splashCanvas;
            previousScreen = accountForm;
            alertScreen = createAlert(getMsg(MessageConstants.HAVE_ACCOUNT));
            startLoading(false, getMsg(MessageConstants.CREATING_USER));
        } else if (d == movieList) {
            if (c == backCmd) {
                display.setCurrent(signedInForm);
                return;
            }
            
            if (session.previewMode == AccountForm.PREVIEW_NONE) {
		session.message = MessageConstants.DISPLAY_LOCATIONS;
                nextScreen = locationList;
                previousScreen = movieList;
                startLoading(true, getMsg(MessageConstants.LOADING_LOCS));
            } else {
		session.message = MessageConstants.DISPLAY_POSTER;
                nextScreen = posterCanvas;
                previousScreen = movieList;
                startLoading(true, getMsg(MessageConstants.LOADING_POSTER));
            }
        } else if (d == posterCanvas) {
            if (c == backCmd) {
                display.setCurrent(movieList);
                return;
            }
	    session.message = MessageConstants.DISPLAY_LOCATIONS;
            nextScreen = locationList;
            previousScreen = movieList;
            startLoading(true, getMsg(MessageConstants.LOADING_LOCS));
        } else if (d == locationList) {
            if (c == backCmd) {
                display.setCurrent(movieList);
                return;
            }
            session.message = MessageConstants.DISPLAY_SHOWTIMES;
            nextScreen = showtimeList;
            previousScreen = locationList;
            startLoading(true, getMsg(MessageConstants.LOADING_SHOWTIMES));
        } else if (d == showtimeList) {
            if (c == backCmd) {
                display.setCurrent(locationList);
                return;
            }
            session.message = MessageConstants.DISPLAY_SEATINGPLAN;
            nextScreen = seatingCanvas;
            previousScreen = showtimeList;
            startLoading(true, getMsg(MessageConstants.LOADING_PLAN));
        } else if (d == seatingCanvas) {
            if (c == cancelCmd) {
                display.setCurrent(showtimeList);
                return;
            }
            session.message = MessageConstants.RESERVE_SEATS;
            nextScreen = confirmForm;
            previousScreen = seatingCanvas;
            startLoading(false, getMsg(MessageConstants.RESERVING_SEATS));
        } else if (d == confirmForm) {
            if (c == cancelCmd) {
		session.message = MessageConstants.CANCEL_SEATS;
                nextScreen = movieList;
                previousScreen = confirmForm;
                alertScreen = 
		    createAlert(getMsg(MessageConstants.PURCHASE_CANCELLED));
                startLoading(false, 
		    getMsg(MessageConstants.CANCELLING_PURCHASE));
                return;
            }

	    // check that we have the last 4 digits
	    if (confirmForm.getCreditCardCheck().length() < 4) {
		return;
	    }

            session.message = MessageConstants.CONFIRM_SEATS;
            nextScreen = splashCanvas;
            previousScreen = confirmForm;
            alertScreen = createAlert(getMsg(MessageConstants.THANK_YOU));
            startLoading(false, getMsg(MessageConstants.CONFIRMING_PURCHASE));
        }
    }

    /* Helper methods. */

    /**
     * Load the base servlet URL. This URL is concatenated with the
     * session id at login time. Subsequent logins use the base URL.
     */
    String getBaseServletURL() {
        return getAppProperty("SMARTicket-Servlet-URL");
    }

    /**
     * Start an operation that requires a separate thread.
     *
     * @param  stoppable  indicates whether the operation can be stopped
     *                    by the user.
     * @param  title  the title of the progress gauge to show to the user.
     * @see  stopLoading()
     */
    void startLoading(boolean stoppable, String title) {
        gaugeForm.init(title, stoppable);
        display.setCurrent(gaugeForm);
        new Thread(session).start();
    }
    
    /**
     * Finish the operation that required a separate thread.
     *
     * @see  startLoading(Runnable, boolean, String), stopLoading(Exception)
     */
    public void stopLoading() {
        if (alertScreen != null) {
            display.setCurrent(alertScreen, nextScreen);
            alertScreen = null;
        } else {
            display.setCurrent(nextScreen);
        }
        
        previousScreen = null;
        nextScreen = null;
    }

    /**
     * Finish the operation that required a separate thread, but ended
     * prematurely (either because it was stopped or encountered an exception.)
     *
     * @see  startLoading(Runnable, boolean, String), stopLoading()
     */
    public void stopLoading(Exception e) {
        if (gaugeForm.stopped) {
            display.setCurrent(previousScreen);
            return;
        }
        
        Alert alert = createAlert(e.getMessage() != null
                                  ? e.getMessage()
                                  : getMsg(MessageConstants.CANNOT_CONNECT));
        display.setCurrent(alert, previousScreen);
    }
    
    /**
     * Create an alert with the given message.
     */
    Alert createAlert(String message) {
        Alert result = new Alert(getMsg(MessageConstants.SMART_TICKET));
        result.setTimeout(Alert.FOREVER);
        result.setString(message);
        return result;
    }

    /**
     * Update the progress gauge with the given value.
     */
    public void updateGauge(int value) throws ApplicationException {
        gaugeForm.update(value);
    }
    
    /**
     * Initialize the table of localized messages.
     */
    private void initMsg() {
        msgTab = new String[MessageConstants.NUM_MSG];
	DataInputStream isr = null;

        try {
	    RecordStore rs = session.openMessageStore(false);
	    if (rs != null && 
		locale.equals(new String(rs.getRecord(1))) == false) {
		isr = new DataInputStream(
		    new ByteArrayInputStream(rs.getRecord(2)));
	    } else {
		InputStream is = 
		    getClass().getResourceAsStream("/default.properties");
		isr = new DataInputStream(is);
	    }

            if (isr != null) {
                int c, idx;
                int i = 0, msgid = 0;
                byte cb[] = new byte[1];
                byte buf[] = new byte[100];
                String l;
                while ((c = isr.read(cb, 0, 1)) != -1) {

                    // Consider both \n and \r to be line terminators.
                    // Hopefully that covers all platforms.  Note that
                    // we ignore any line not containing an "=", and
                    // that includes empty lines.
                    if (cb[0] == (byte)'\n' || cb[0] == '\r') {
                        l = new String(buf, 0, i);
                        i = 0;
                        idx = l.indexOf('=');
                        if (idx > 0) {
                            msgTab[msgid++] = l.substring(idx+1);
                        }
                    } else {
                        buf[i++] = cb[0];
                    }
                }
            }
        } catch (Exception e) {
	    // do nothing
        }
    }

    /**
     * Return the localized message for the given key.
     */
    protected static String getMsg(int key) {
        return msgTab[key] != null ? msgTab[key] : "General application error";
    }
}
