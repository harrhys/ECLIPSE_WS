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

package com.sun.j2ee.blueprints.smarticket.ejb.ticketsales;

import com.sun.j2ee.blueprints.smarticket.ejb.*;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import javax.ejb.EJBException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.*;

/**
 * Implements the TicketSales EJB component. Takes care of business
 * methods and resource access.
 */
public class TicketSalesEJB implements SessionBean {

    // EJB-specific variables

    protected static final String TICKETSALES_DB 
        = "java:comp/env/jdbc/TicketSalesDataSource";

    protected DataSource dataSource;


    // Business variables

    protected String customerID;
    protected int showID;
    protected Set seats;


    // Overridden SessionBean methods

    public void ejbCreate(String c, int s) {
        initDataSources(); 
        customerID = c;
        showID = s;
        seats = Collections.EMPTY_SET;
    }

    public void ejbRemove() { 
        dataSource = null; 
    }

    public void ejbActivate() { initDataSources(); }

    public void ejbPassivate() { dataSource = null; }

    public void setSessionContext(SessionContext sc) {}

    protected void initDataSources() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(TICKETSALES_DB);
        }
        catch (Exception e) {
            throw new EJBException(e);
        }
    }


    // Business methods

    public SeatingPlan getSeatingPlan() {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            // Load seating plan from database.
            // The data comes in the form of strings.
            SeatingPlan seatingPlan;
            ps = c.prepareStatement("select t.s_row, t.seats"
                                    + " from seating t"
                                    + " where t.show_id = ?"
                                    + " order by t.s_row");
            ps.setInt(1, showID);
            rs = ps.executeQuery();
            List rowList = new ArrayList();
            while (rs.next()) {
                rowList.add(rs.getString(2));
            }
            rs.close();
            ps.close();
            String[] rowStrings 
                = (String[]) rowList.toArray(new String[] {});
	    int rowLength = (rowStrings.length > 0) 
                ? rowStrings[0].length() : 0;
	    seatingPlan = new SeatingPlan(rowStrings.length, rowLength);
	    for (int r = 0; r < rowStrings.length; r++) {
		seatingPlan.setRow(r, rowStrings[r]);
	    }

            c.close();
            return seatingPlan;
        }
        catch (SQLException e) {
            throw new EJBException("Error accessing data: " 
                                   + e.getMessage());
        }
    }

    public boolean reserveSeats(Set s) throws UnavailableSeatsException {

        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            seats = s;

            // Load seating plan from database.
            // The data comes in the form of strings.
            SeatingPlan seatingPlan;
            ps = c.prepareStatement("select t.s_row, t.seats"
                                    + " from seating t"
                                    + " where t.show_id = ?"
                                    + " order by t.s_row");
            ps.setInt(1, showID);
            rs = ps.executeQuery();
            List rowList = new ArrayList();
            while (rs.next()) {
                rowList.add(rs.getString(2));
            }
            rs.close();
            ps.close();
            String[] rowStrings 
                = (String[]) rowList.toArray(new String[] {});
	    int rowLength = (rowStrings.length > 0) 
                ? rowStrings[0].length() : 0;
	    seatingPlan = new SeatingPlan(rowStrings.length, rowLength);
	    for (int r = 0; r < rowStrings.length; r++) {
		seatingPlan.setRow(r, rowStrings[r]);
	    }

            // Check that seats are available.
            for (Iterator it = seats.iterator(); it.hasNext(); ) {
                Seat x = (Seat) it.next();
                if (!seatingPlan.isAvailable(x.row, x.seat)) {
                    seats = Collections.EMPTY_SET;
                    throw new UnavailableSeatsException();
                }
            }

            // Update seating plan.
            for (Iterator it = seats.iterator(); it.hasNext(); ) {
                Seat x = (Seat) it.next();
                seatingPlan.setBooked(x.row, x.seat);
            }
            ps = c.prepareStatement("update seating set seats = ?"
                                    + " where show_id = ? and s_row = ?");
            for (int r = 0; r < seatingPlan.getRowCount(); r++) {
                if (seatingPlan.hasBooking(r)) {
                    for (int t = 0; t < seatingPlan.getRowLength(); t++) {
                        if (seatingPlan.isBooked(r, t)) {
                            seatingPlan.setUnavailable(r, t);
                        }
                    }
                    ps.setString(1, seatingPlan.getRow(r));
                    ps.setInt(2, showID);
                    ps.setInt(3, r);
                    ps.executeUpdate();
                }
            }
            ps.close();

            // Update reservations.
            ps = c.prepareStatement("insert into reservations"
                                    + " (show_id, s_row, seat, username)"
                                    + " values (?, ?, ?, ?)");
            for (Iterator it = seats.iterator(); it.hasNext(); ) {
                Seat x = (Seat) it.next();
                ps.setInt(1, showID);
                ps.setInt(2, x.row);
                ps.setInt(3, x.seat);
                ps.setString(4, customerID);
                ps.executeUpdate();
            }
            ps.close();

            c.close();
            return true;
        }
        catch (SQLException e) {
            throw new EJBException("Error accessing data: " 
                                   + e.getMessage());
        }
    }

    public boolean cancelSeats() {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            SeatingPlan seatingPlan;
            ps = c.prepareStatement("select t.s_row, t.seats"
                                    + " from seating t"
                                    + " where t.show_id = ?"
                                    + " order by t.s_row");
            ps.setInt(1, showID);
            rs = ps.executeQuery();
            List rowList = new ArrayList();
            while (rs.next()) {
                rowList.add(rs.getString(2));
            }
            rs.close();
            ps.close();
            String[] rowStrings 
                = (String[]) rowList.toArray(new String[] {});
	    int rowLength = (rowStrings.length > 0) 
                ? rowStrings[0].length() : 0;
	    seatingPlan = new SeatingPlan(rowStrings.length, rowLength);
	    for (int r = 0; r < rowStrings.length; r++) {
		seatingPlan.setRow(r, rowStrings[r]);
	    }

            // Update seating plan.
            for (Iterator it = seats.iterator(); it.hasNext(); ) {
                Seat x = (Seat) it.next();
                seatingPlan.setBooked(x.row, x.seat);
            }
            ps = c.prepareStatement("update seating set seats = ?"
                                    + " where show_id = ? and s_row = ?");
            for (int r = 0; r < seatingPlan.getRowCount(); r++) {
                if (seatingPlan.hasBooking(r)) {
                    for (int s = 0; s < seatingPlan.getRowLength(); s++) {
                        if (seatingPlan.isBooked(r, s)) {
                            seatingPlan.setAvailable(r, s);
                        }
                    }
                    ps.setString(1, seatingPlan.getRow(r));
                    ps.setInt(2, showID);
                    ps.setInt(3, r);
                    ps.executeUpdate();
                }
            }
            ps.close();

            // Update reservations.
            ps = c.prepareStatement("delete from reservations"
                                    + " where show_id = ?"
                                    + " and s_row = ?"
                                    + " and seat = ?");
            for (Iterator it = seats.iterator(); it.hasNext(); ) {
                Seat x = (Seat) it.next();
                ps.setInt(1, showID);
                ps.setInt(2, x.row);
                ps.setInt(3, x.seat);
                ps.executeUpdate();
            }
            ps.close();

            c.close();
            seats.clear();
            return true;
        }
        catch (SQLException e) {
            throw new EJBException("Error accessing data: " 
                                           + e.getMessage());
        }
    }

    public boolean confirmSeats() {
        try {
            Connection c = null;
            PreparedStatement ps = null;

            c = dataSource.getConnection();

            // Update reservations.
            ps = c.prepareStatement("delete from reservations"
                                    + " where show_id = ?"
                                    + " and s_row = ?"
                                    + " and seat = ?");
            for (Iterator it = seats.iterator(); it.hasNext(); ) {
                Seat x = (Seat) it.next();
                ps.setInt(1, showID);
                ps.setInt(2, x.row);
                ps.setInt(3, x.seat);
                ps.executeUpdate();
            }
            ps.close();

            c.close();
            seats.clear();
            return true;
        }
        catch (SQLException e) {
            throw new EJBException("Error accessing data: " 
                                           + e.getMessage());
        }
    }
}
