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

package com.sun.j2ee.blueprints.smarticket.web;

import shared.*;

import com.sun.j2ee.blueprints.smarticket.ejb.customer.*;
import com.sun.j2ee.blueprints.smarticket.ejb.movieinfo.*;
import com.sun.j2ee.blueprints.smarticket.ejb.ticketsales.*;
import com.sun.j2ee.blueprints.smarticket.ejb.localeinfo.*;

import java.io.*;
import java.util.*;

import java.rmi.*;
import javax.ejb.*;
import javax.naming.*;
import javax.rmi.*;

/**
 * A service that handles MIDP client requests for this application.
 */
public class MIDPService implements MessageConstants {

    /** Version of data format sent to the client */
    static final byte CODEC_VERSION = 1;

    MovieInfo movieInfo = null;
    CustomerInformation customerInfo = null;
    TicketSales ticketSales = null;

    String sessionURL = null;

    public MIDPService() throws MIDPException {
        try {
            movieInfo = createMovieInfoEJB();
        }
        catch (Exception e) {
            serverError(e);
        }
    }

    public void removeMovieInfo() throws MIDPException {
	if (movieInfo != null) {
	    try {
		movieInfo.remove();
		movieInfo = null;
	    } catch (RemoteException rem) {
		serverError(rem);
	    } catch (RemoveException rev) {
		serverError(rev);
	    }
	}
    }
	  

    /**
     * <p>Processes the given request, which is passed as a string.
     * Returns a string which contains the data requested.</p>
     *
     * <p>This assumes the message is formatted as:</p>
     *
     * <p><code>command^param1,param2,...</code></p>
     *
     * <p>where command is a constant from 
     * <code>MessageConstants</code>.</p>
     *
     * @param req request represented as a string
     * @return the requested data
     * @see MessageConstants
     */
    public boolean processRequest(DataOutputStream out, 
				  String req, String sessionURL)
        throws MIDPException
    {
	this.sessionURL = sessionURL;
        StringTokenizer params;
        int cmd;
	boolean invalidate = false;

        try {
            // Split the request string into its parts.
            // Interpret the command and deal with it appropriately.
            int i = req.indexOf("^");
            cmd = Integer.parseInt(req.substring(0, i));
            params = new StringTokenizer(req.substring(i+1), ",");
        }
        catch (Exception e) {
            throw new MIDPException(MESSAGE_ERROR);
        }

        switch (cmd) {
        case CREATE_USER:
            createUser(out, params);
            break;
        case LOGIN_USER:
            loginUser(out, params);
            break;
	case DISPLAY_LOCALES:
	    displayLocales(out, params);
	    break;
	case LOAD_MESSAGES:
	    loadMessages(out, params);
	    break;
        case DISPLAY_MOVIES:
            displayMovies(out, params);
            break;
        case DISPLAY_LOCATIONS:
            displayLocations(out, params);
            break;
        case DISPLAY_SHOWTIMES:
            displayShowtimes(out, params);
            break;
        case DISPLAY_SEATINGPLAN:
            displaySeatingPlan(out, params);
            break;
        case RESERVE_SEATS:
            reserveSeats(out, params);
            break;
        case CONFIRM_SEATS:
            confirmSeats(out, params);
	    invalidate = true;
            break;
        case CANCEL_SEATS:
            cancelSeats(out, params);
            break;
        default:
            // We shouldn't get to this point.
	    throw new MIDPException(MESSAGE_ERROR);
        }
	return invalidate;
    }

    // Parameters: username, password, zipCode, creditCard
    void createUser(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

	Customer c = null;

        try {
            String username = params.nextToken();
            String password = params.nextToken();
            String zipCode = params.nextToken();
            String creditCard = params.nextToken();

            c = createCustomerEJB(username, password, 
                                           zipCode, creditCard);
            customerInfo = c.getInformation();
            out.writeUTF(sessionURL);
        } catch (DuplicateKeyException dke) {
            throw new MIDPException(USER_ALREADY_EXISTS_ERROR);
        } catch (Exception e) {
            serverError(e);
        } finally {
	    if (c != null) {
		try {
		    c.remove();
		} catch (RemoteException rem) {
		    serverError(rem);
		} catch (RemoveException rev) {
		    serverError(rev);
		} 
	    }
	}
    }

    // Parameters: <none>
    void displayLocales(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

	LocaleInfo li = null;

	try {
	    li = createLocaleInfoEJB();
	    List locales = li.getLocales();
	    out.writeInt(locales.size());
            for (int i = 0; i < locales.size(); i++) {
                MessageLocale locale = 
		    (MessageLocale)locales.get(i);
                out.writeInt(locale.getID());
                out.writeUTF(locale.getLocale());
            }
        } catch (Exception e) {
            serverError(e);
        } finally {
	    if (li != null) {
		try {
		    li.remove();
		} catch (RemoteException rem) {
		    serverError(rem);
		} catch (RemoveException rev) {
		    serverError(rev);
		} 
	    }
	}	    
    }

    // Parameters: localeID
    void loadMessages(DataOutputStream out, StringTokenizer params) 
	throws MIDPException {

	LocaleInfo li = null;
	try {
	    int localeID = Integer.parseInt(params.nextToken());
	    li = createLocaleInfoEJB();
	    List messages = li.getMessages(localeID);
	    StringBuffer sb = new StringBuffer(256);
            for (int i = 0; i < messages.size(); i++) {
                Message message = (Message) messages.get(i);
		sb.append(Integer.toString(message.getID()));
		sb.append("=");
		sb.append(message.getMessage());
		sb.append("\n");
            }
	    out.writeUTF(sb.toString());
        } catch (Exception e) {
            serverError(e);
        } finally {
	    if (li != null) {
		try {
		    li.remove();
		} catch (RemoteException rem) {
		    serverError(rem);
		} catch (RemoveException rev) {
		    serverError(rev);
		} 
	    }
	}	    
	
    }

    // Parameters: username, password
    void loginUser(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

        try {
            String username = params.nextToken();
            String password = params.nextToken();

            Customer c = findCustomerEJB(username);
            CustomerInformation ci = c.getInformation();
            if (!password.equals(ci.getPassword())) {
                throw new MIDPException(PASSWORD_INCORRECT_ERROR);
            }
            customerInfo = ci;

            out.writeUTF(sessionURL);
        } catch (FinderException fe) {
            throw new MIDPException(USER_NOT_FOUND_ERROR);
        } catch (Exception e) {
            serverError(e);
        }
    }

    // Parameters: <none>
    void displayMovies(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

        try {
            List movies = movieInfo.getMovies(customerInfo.getZipCode());
            out.writeInt(movies.size());
            for (int i = 0; i < movies.size(); i++) {
                Movie movie = (Movie) movies.get(i);
                out.writeInt(movie.getID());
                out.writeUTF(movie.getTitle());
                out.writeUTF(movie.getRating());
                out.writeUTF(movie.getPosterURL());
            }
        } catch (Exception e) {
            serverError(e);
        }
    }
    
    // Parameters: movieID
    void displayLocations(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

        try {
            int movieID = Integer.parseInt(params.nextToken());
            List locations = 
                movieInfo.getLocations(customerInfo.getZipCode(), movieID);
            out.writeInt(locations.size());
            for (int i = 0; i < locations.size(); i++) {
                Location location = (Location) locations.get(i);
                out.writeInt(location.getID());
                out.writeUTF(location.getLocation());
            }
        } catch (Exception e) {
            serverError(e);
        }
    }

    // Parameters: movieID, locationID
    void displayShowtimes(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

        try {
            int movieID = Integer.parseInt(params.nextToken());
            int locationID = Integer.parseInt(params.nextToken());

            List showtimes = movieInfo.getShowtimes(movieID, locationID);
            out.writeInt(showtimes.size());
            for (int i = 0; i < showtimes.size(); i++) {
                Showtime showtime = (Showtime) showtimes.get(i);
                out.writeInt(showtime.getID());
                out.writeUTF(showtime.getShowtime());
            }
        } catch (Exception e) {
            serverError(e);
        }
    }

    // Parameters: showID
    void displaySeatingPlan(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

        try {
            int showID = Integer.parseInt(params.nextToken());

            if (ticketSales != null) {
                ticketSales.cancelSeats();
            }
            ticketSales 
                = createTicketSalesEJB(customerInfo.getUsername(), showID);
            SeatingPlan sp = ticketSales.getSeatingPlan();

            out.write((byte)sp.getRowCount());
            out.write((byte)sp.getRowLength());
            byte[] seats = sp.getSeatData();
            out.write(seats);
        } catch (Exception e) {
            serverError(e);
        }
    }

    // Parameters: showID, row1, seat1, ..., rowN, seatN
    void reserveSeats(DataOutputStream out, StringTokenizer params)
        throws MIDPException {

        try {
            Set seats = new HashSet();
            while (params.hasMoreTokens()) {
                seats.add(new Seat(Integer.parseInt(params.nextToken()), 
                                   Integer.parseInt(params.nextToken())));
            }
            ticketSales.reserveSeats(seats);
        } catch (UnavailableSeatsException use) {
            throw new MIDPException(UNAVAILABLE_SEATS_ERROR);
        } catch (Exception e) {
            serverError(e);
        }
	
    }

    // Parameters: creditCard
    void confirmSeats(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

	int creditCard = Integer.parseInt(params.nextToken());

        try {
            ticketSales.confirmSeats();
        } catch (Exception e) {
            serverError(e);
        } finally {
	    if (ticketSales != null) {
		try {
		    ticketSales.remove();
		    ticketSales = null;
		} catch (RemoteException rem) {
		    serverError(rem);
		} catch (RemoveException rev) {
		    serverError(rev);
		}
	    }
	}
    }

    // Parameters: <none>
    void cancelSeats(DataOutputStream out, StringTokenizer params) 
        throws MIDPException {

        try { 
            ticketSales.cancelSeats();
        } catch (Exception e) {
            serverError(e);
        } finally {
	    if (ticketSales != null) {
		try {
		    ticketSales.remove();
		    ticketSales = null;
		} catch (RemoteException rem) {
		    serverError(rem);
		} catch (RemoveException rev) {
		    serverError(rev);
		}
	    }
	}
    }


    // EJB helper methods

    /*
     * Helper method to instantiate a MovieInfoEJB.
     */
    MovieInfo createMovieInfoEJB() 
        throws CreateException, NamingException, RemoteException {
        MovieInfoHome home = 
            (MovieInfoHome) PortableRemoteObject
            .narrow(new InitialContext()
                .lookup("java:comp/env/ejb/MovieInfo"), 
                    MovieInfoHome.class);
        return (MovieInfo) home.create();
    }

    /*
     * Helper method to instantiate a LocaleInfoEJB.
     */
    LocaleInfo createLocaleInfoEJB() 
        throws CreateException, NamingException, RemoteException {
        LocaleInfoHome home = 
            (LocaleInfoHome) PortableRemoteObject
            .narrow(new InitialContext()
                .lookup("java:comp/env/ejb/LocaleInfo"), 
                    LocaleInfoHome.class);
        return (LocaleInfo) home.create();
    }


    /*
     * Helper method to find TicketSales for the given show.
     */
    TicketSales createTicketSalesEJB(String customerID, int showID) 
        throws CreateException, NamingException, RemoteException {
        TicketSalesHome home = 
            (TicketSalesHome) PortableRemoteObject
            .narrow(new InitialContext()
                .lookup("java:comp/env/ejb/TicketSales"), 
                    TicketSalesHome.class);
        return (TicketSales) home.create(customerID, showID);
    }

    /*
     * Helper method to create a new Customer.
     */
    Customer createCustomerEJB(String username, String password, 
                               String zipCode, String creditCard)
        throws CreateException, DuplicateKeyException, 
               NamingException, RemoteException {
        CustomerHome home = 
            (CustomerHome) PortableRemoteObject
            .narrow(new InitialContext()
                .lookup("java:comp/env/ejb/Customer"), 
                    CustomerHome.class);
        return (Customer) home.create(username, password, 
                                      zipCode, creditCard);
    }

    /*
     * Helper method to find a Customer by his or her username.
     */
    Customer findCustomerEJB(String username) 
        throws FinderException, NamingException, RemoteException {
        CustomerHome home = 
            (CustomerHome) PortableRemoteObject
            .narrow(new InitialContext()
                .lookup("java:comp/env/ejb/Customer"), 
                    CustomerHome.class);
        return (Customer) home.findByPrimaryKey(username);
    }

    /*
     * Some unexpected server error has occurred.
     */
    private void serverError(Exception e) throws MIDPException {
        System.err.println("[TRACE]: Server error: " + e);
        e.printStackTrace();
        throw new MIDPException(SERVER_ERROR);
    }
}
