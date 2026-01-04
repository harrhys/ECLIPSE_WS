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
import javax.microedition.rms.*;

import shared.MessageConstants;

/**
 * Helper for managing the shopping session with the server.
 */
public class Session implements Runnable {

    /*
     * Note: Each method that connects with the server calls back to 
     * the SmartTicket MIDlet:
     *
     * 1) updateGauge() as needed to update the progress bar for the user.
     * 2) stopLoading() or stopLoading(Exception) to indicate the operation
     *    is done.
     */
    
    byte message;

    // The name of the MIDlet's record store.
    static final String RECORD_STORE_NAME = "SmartTicket";
    static final String MESSAGE_STORE_NAME = "l10n_data";

    
    SmartTicket smartTicket;
    RecordStore recordStore;
    
    // Cache variable for preview mode.
    byte previewMode;
    
    // Cache variable for size of rows in seating plan.
    int rowSize;

    public Session(SmartTicket st) {
        smartTicket = st;
    }

    /**
     * run method for this session - from here we call the individual
     * methods that service the requests
     */
    public void run() {
	switch (message) {
	case MessageConstants.CREATE_USER:
	    createUser(smartTicket.accountForm.getUsername(),
		       smartTicket.accountForm.getPassword(),
		       smartTicket.accountForm.getZipCode(),
		       smartTicket.accountForm.getCreditCard(),
		       smartTicket.accountForm.getPreviewMode());
	    break;
	case MessageConstants.LOGIN_USER:
	    login();
	    break;
	case MessageConstants.DISPLAY_LOCALES:
	    loadLocales(smartTicket.localeList);
	    break;
	case MessageConstants.LOAD_MESSAGES:
	    loadMessages(smartTicket.localeList.getLocaleID());
	    break;
	case MessageConstants.DISPLAY_MOVIES:
	    loadMovies(smartTicket.movieList);
	    break;
	case MessageConstants.DISPLAY_LOCATIONS:
	    loadLocations(smartTicket.locationList, 
			  smartTicket.movieList.getMovieID()); 
	    break;
	case MessageConstants.DISPLAY_SHOWTIMES:
           loadShowtimes(smartTicket.showtimeList,
			 smartTicket.movieList.getMovieID(),
			 smartTicket.locationList.getLocationID());
	   
	    break;
	case MessageConstants.DISPLAY_SEATINGPLAN:
	    loadSeatingPlan(smartTicket.seatingCanvas,
			    smartTicket.showtimeList.getShowID(),
			    smartTicket.movieList.getMovieTitle(),
			    smartTicket.showtimeList.getShowtime());
	    break;
	case MessageConstants.DISPLAY_POSTER:
	    loadPoster(smartTicket.posterCanvas, 
		       smartTicket.posterURL + 
		       smartTicket.movieList.getPosterUrl()); 
	    break;
	case MessageConstants.RESERVE_SEATS:
	    reserveSeats(smartTicket.confirmForm, 
			 smartTicket.seatingCanvas.getSelectedSeats(),
			 smartTicket.movieList.getMovieTitle(),
			 smartTicket.showtimeList.getShowtime());
	    break;
	case MessageConstants.CONFIRM_SEATS:
	    confirmSeats(smartTicket.confirmForm.getCreditCardCheck());
	    break;
	case MessageConstants.CANCEL_SEATS:
	    cancelSeats();
	    break;
	default:
	}
    }

    /**
     * Open the session.
     *
     * Remember to call close() when you are done.
     */
    public void open() {
        try {
            recordStore = RecordStore.openRecordStore(RECORD_STORE_NAME, true);
        }
        catch (RecordStoreException rse) {
            // Do nothing.
        }
    }
    
    /**
     * Indicate whether the session is for a new user (one who doesn't have
     * an account).
     */
    public boolean isNewUser() {
        try {
            return (recordStore.getNumRecords() == 0);
        }
        catch (RecordStoreException rse) {
            return true;
        }
    }

    /**
     * Close the session.
     *
     * The session should have been opened using open().
     */
    public void close() {
        try {
            recordStore.closeRecordStore();
        }
        catch (RecordStoreException rse) {
            // Do nothing.
        }
    }

    /* Messages to server. */
    
    /**
     * Login to server.
     */
    public void login() {
        HttpConnection conn = null;
        DataInputStream in = null;
        
        try {
            if (recordStore.getNumRecords() == 0) {
                throw new ApplicationException(
		   MessageConstants.USER_NOT_FOUND_ERROR);
            }
            
            // Load the account from local store.
            byte[] record = recordStore.getRecord(1);
            in = new DataInputStream(new ByteArrayInputStream(record));
            String userID = in.readUTF();
            String password = in.readUTF();
            int zipCode = in.readInt();
            previewMode = (byte)in.read();

            // Send the login information to the server.
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
            String message = MessageConstants.LOGIN_USER + 
		"^" + userID + "," + password;
            conn = open(smartTicket.getBaseServletURL(), message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);
            smartTicket.servlet = in.readUTF();
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } 
        catch (Exception e) {
            smartTicket.stopLoading(e);
        }
        finally {
            close(conn, in);
        }
    }

    /**
     * Load the list of locales available for this application.
     *
     * @param  ll  the list to populate with locale data.
     */
    public void loadLocales(LocaleList ll) {
	HttpConnection conn = null;
	DataInputStream in = null;
	
	try {
	    smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
	    String message = MessageConstants.DISPLAY_LOCALES + "^";
	    conn = open(smartTicket.servlet, message);
	    in = conn.openDataInputStream();
	    smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);
	    int count = in.readInt();
	    ll.init(count);
            for (int i = 0; i != count; i++) {
                ll.addLocale(in.readInt(), in.readUTF());
            }

	    smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();   
	} catch (Exception e) {
	    smartTicket.stopLoading(e);
	} finally {
	    close(conn, in);
	}
	
    }

    /**
     * Load into the record store the localized messages for the
     * locale with the given ID.  
     */
    public void loadMessages(int localeID) {
	HttpConnection conn = null;
	DataInputStream in = null;
	RecordStore rs = null;

	try {
	    rs = openMessageStore(true);
	    byte [] record = 
		smartTicket.localeList.getLocale().getBytes();
	    if (rs.getNumRecords() > 0) {
		if (smartTicket.localeList.getLocale().equals(
		    new String(rs.getRecord(1)))) {
		    smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
		    smartTicket.stopLoading();
		    return;
		} else {
		    rs.setRecord(1, record, 0, record.length);
		}
	    } else {
		rs.addRecord(record, 0, record.length);
	    }
	    
	    /*
	     * If the selected localed is not the default and not
	     * the current locale download the messages
	     */
	    if (localeID > 0) {
		
		String message = MessageConstants.LOAD_MESSAGES + 
		    "^" + localeID;
		conn = open(smartTicket.servlet, message);
		in = conn.openDataInputStream();
		smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);
		
		// read the messages
		record = in.readUTF().getBytes();
		if (rs.getNumRecords() > 1) {
		    rs.setRecord(2, record, 0, record.length);
		} else {
		    rs.addRecord(record, 0, record.length);
		}
	    }
	    smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
	    smartTicket.stopLoading();
		
	} catch (Exception e) {
	    smartTicket.stopLoading(e);
	} finally {
	    close(conn, in);
	    if (rs != null) {
		try {
		    rs.closeRecordStore();
		} catch (RecordStoreException rse) {
		    // ignore
		}
	    }
	}
    }
    
    /**
     * Create a new user account.
     *
     * This method saves some information locally and some information on the
     * server.
     *
     * @param  userID  the user name.
     * @param  password  the user's password.
     * @param  zipCode  the user's zipCode.
     * @param  creditCard  the user's credit card number.
     * @param  previewMode  the user's preview mode preference.
     */
    public void createUser(String userID, String password, int zipCode,
                           String creditCard, byte previewMode) 
    {
        HttpConnection conn = null;
        DataInputStream in = null;
        
        try {
            // Send the account information to the server.
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
            String message = MessageConstants.CREATE_USER + 
		"^" + userID + "," + password
                + "," + zipCode + "," + creditCard;
            conn = open(smartTicket.servlet, message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);
            smartTicket.servlet = in.readUTF();

            // Save the account to the local store.
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bout);
            out.writeUTF(userID);
            out.writeUTF(password);
            out.writeInt(zipCode);
            out.write((byte)previewMode);
            byte[] record = bout.toByteArray();
            if (recordStore.getNumRecords() > 0) {
                recordStore.setRecord(1, record, 0, record.length);
            } else {
                recordStore.addRecord(record, 0, record.length);
            }

            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
            close(conn, in);
        }
    }

    /**
     * Load movie data.
     *
     * @param  ml  the movie list to populate with the data.
     */
    public void loadMovies(MovieList ml) {
	HttpConnection conn = null;
	DataInputStream in = null;

	try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
            String message = MessageConstants.DISPLAY_MOVIES + "^";
	    conn = open(smartTicket.servlet, message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);

            int count = in.readInt();
            ml.init(count);
            for (int i = 0; i != count; i++) {
                ml.addMovie(in.readInt(), in.readUTF(), in.readUTF(), 
                            in.readUTF());
            }
            
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, in);
        }
    }
    
    /**
     * Load a poster.
     *
     * @param  ic  the ImageCanvas on which to display the poster.
     * @param  posterURL  the URL of the poster.
     */    
    public void loadPoster(ImageCanvas ic, String posterURL) {
        HttpConnection conn = null;
        DataInputStream is = null;

        try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
            conn = open(posterURL);
            is = conn.openDataInputStream();
            smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);

            int size = (int)(conn.getLength());
            if (size < 0) {
                size = 12000;   // Make a guess
            }
            byte[] buf = new byte[size];
            int offset = 0;
            int remaining = 0;
            while ((remaining = size - offset) > 0) {
                int len = is.read(buf, offset, remaining);
                if (len < 0)
                    break;
                offset += len;
            }
            if (remaining < 0) {
                throw new IOException("image not read completely");
            }
            ic.setImage(Image.createImage(buf, 0, offset));
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, is);
        }
    }

    /**
     * Load the locations for the given movie.
     *
     * @param  ll  the location list to populate.
     * @param  movieID  the ID of the movie.
     */
    public void loadLocations(LocationList ll, int movieID) {
	HttpConnection conn = null;
	DataInputStream in = null;

	try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
            String message = MessageConstants.DISPLAY_LOCATIONS + 
		"^" + movieID;
	    conn = open(smartTicket.servlet, message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);
            
            int count = in.readInt();
            ll.init(count);
            for (int i = 0; i != count; i++) {
                ll.addLocation(in.readInt(), in.readUTF());
            }

            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, in);
        }
    }

    /**
     * Load showtimes for the given movie at the given location.
     *
     * @param  sl  the list to populate with showtime data.
     * @param  movieID  the ID of the movie.
     * @param  locationID  the ID of the location.
     */
    public void loadShowtimes(ShowtimeList sl, int movieID, 
			      int locationID) {
	HttpConnection conn = null;
	DataInputStream in = null;

	try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
            String message = MessageConstants.DISPLAY_SHOWTIMES + 
		"^" + movieID + "," + locationID;
	    conn = open(smartTicket.servlet, message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);

            int count = in.readInt();
            sl.init(count);
            for (int i = 0; i != count; i++) {
                sl.addShowtime(in.readInt(), in.readUTF());
            }

            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, in);
        }
    }

    /**
     * Load the given seating canvas with the data of the seating
     * plan for the given show.
     *
     * @param  sc  the seating canvas which needs the data.
     * @param  showID  the ID of the show.
     * @param  movieTitle  the title of the movie (for display).
     * @param  showtime  the time of the movie (for display).
     */
    public void loadSeatingPlan(SeatingCanvas sc, int showID,
                                String movieTitle, String showtime) {
	HttpConnection conn = null;
	DataInputStream in = null;

	try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/3);
            String message = MessageConstants.DISPLAY_SEATINGPLAN + 
		"^" + showID;
	    conn = open(smartTicket.servlet, message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(2*GaugeForm.GAUGE_MAX/3);
            
            int rows = in.read();
            rowSize = in.read();
            byte[] seats = new byte[rows*rowSize];
            in.readFully(seats);
            sc.init(seats, rowSize, movieTitle, showtime);

            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, in);
        }
    }
    
    /**
     * Reserve the selected seats.
     *
     * @param cf the confirmation form into which the confirmation
     * data should be loaded.
     * @param selectedSeats the indices of the selected seats in the
     * byte array representing the seating plan.
     * @param movieTitle the title of the movie (for display).
     * @param showtime the time of the showing (for display).
     */
    public void reserveSeats(ConfirmForm cf, int[] selectedSeats,
                             String movieTitle, String showtime) {
	HttpConnection conn = null;
	DataInputStream in = null;

        try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/2);

            StringBuffer messageBuf = new StringBuffer(30);
            messageBuf.append(MessageConstants.RESERVE_SEATS);
            messageBuf.append('^');
            if (selectedSeats.length > 0) {
                messageBuf.append(selectedSeats[0] / rowSize);
                messageBuf.append(',');
                messageBuf.append(selectedSeats[0] % rowSize);
                for (int i = 1; i != selectedSeats.length; i++) {
                    messageBuf.append(',');
                    messageBuf.append(selectedSeats[i] / rowSize);
                    messageBuf.append(',');
                    messageBuf.append(selectedSeats[i] % rowSize);
                }
            }
	    conn = open(smartTicket.servlet, 
                                         messageBuf.toString());
            in = conn.openDataInputStream();
            cf.init(movieTitle, showtime, selectedSeats.length);
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, in);
        }
    }

    /**
     * Confirm the purchase.
     *
     * @param creditCardCheck the last four digits of the credit card
     * number, as a security check.
     */
    public void confirmSeats(String creditCardCheck) {
	HttpConnection conn = null;
	DataInputStream in = null;

        try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/2);
            String message = MessageConstants.CONFIRM_SEATS + 
		"^" + creditCardCheck;
	    conn = open(smartTicket.servlet, message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, in);
        }
    }

    /**
     * Cancel the purchase.
     */
    public void cancelSeats() {
	HttpConnection conn = null;
	DataInputStream in = null;

        try {
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX/2);
            String message = MessageConstants.CANCEL_SEATS + "^";
	    conn = open(smartTicket.servlet, message);
            in = conn.openDataInputStream();
            smartTicket.updateGauge(GaugeForm.GAUGE_MAX);
            smartTicket.stopLoading();
        } catch (Exception e) {
            smartTicket.stopLoading(e);
        } finally {
	    close(conn, in);
        }
    }

    /* Helper methods. */

    /**
     * Open the record store for storing localized messages.
     */
    public RecordStore openMessageStore(boolean create) 
	throws RecordStoreException {
	RecordStore rs;
	try {
	    rs = RecordStore.openRecordStore(MESSAGE_STORE_NAME, create);
	} catch (RecordStoreNotFoundException rsnfe) {
	    return null;
	}
	return rs;
    }

    static HttpConnection open(String url) 
        throws IOException
    {
        HttpConnection conn = (HttpConnection) Connector.open(url);
        conn.setRequestProperty("User-Agent", 
	    System.getProperty("microedition.profiles"));
        return conn;
    }

    /**
     * Open a connection, post a command, and check the response.
     *
     * The URL is opened and the command is written to the output
     * stream and the OutputStream is closed.  If there is a cookie
     * in the response, it is saved.
     *
     * @param url to open a connection for
     * @param command to POST to the stream
     * @returns an open HttpConnection
     * @exception IOException if any exception occurs
     * @exception ApplicationException if servlet reported an error
     */
    static HttpConnection open(String url, String message)
        throws IOException, ApplicationException
    {
        HttpConnection conn = open(url);
        conn.setRequestMethod(HttpConnection.POST);
        DataOutputStream os = null;
        message = message + '\n';
        try {
            os = conn.openDataOutputStream();
            os.write(message.getBytes());
        } finally {
            if (os != null) {
                os.close();
            }
        }

        // If the server reports an error, let's report it.
        if (conn.getResponseCode() == HttpConnection.HTTP_INTERNAL_ERROR) {
            String msg = conn.getHeaderField("Reason-Phrase");
            if (msg == null) {
                // This shouldn't happen (?).
                msg = "Internal Server Error";
            }
            throw new ApplicationException(Integer.parseInt(msg));
        }
        checkResponseCode(conn);
        return conn;
    }

    /**
     * Close the connection and streams.
     */
    static void close(HttpConnection conn, InputStream is) {
	if (is != null) {
	    try {
		is.close();
	    } catch (IOException ignore) {
		// ignore
	    }
	}
	if (conn != null) {
	    try {
		conn.close();
	    } catch (IOException ignore) {
		// ignore
	    }
	}
    }

    /**
     * Get the response code out and throw an error if it is not HTTP_OK.
     */
    static void checkResponseCode(HttpConnection conn) throws IOException {
        int code = conn.getResponseCode();
        if (code != HttpConnection.HTTP_OK) {
            throw new IOException(code + "; " + conn.getResponseMessage());
        }
    }
}
