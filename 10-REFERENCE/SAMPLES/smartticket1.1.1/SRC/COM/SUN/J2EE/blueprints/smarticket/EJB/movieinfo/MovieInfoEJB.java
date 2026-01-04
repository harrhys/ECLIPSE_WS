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

package com.sun.j2ee.blueprints.smarticket.ejb.movieinfo;

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

import java.util.List;
import java.util.ArrayList;

/**
 * Implements the MovieInfo EJB component. Takes care of business methods
 * and database access. This is a stateless session bean.
 */
public class MovieInfoEJB implements SessionBean {

    protected static final String MOVIEINFO_DB 
        = "java:comp/env/jdbc/MovieInfoDataSource";

    protected DataSource dataSource;


    // Overridden SessionBean methods

    public void setSessionContext(SessionContext sc) {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(MOVIEINFO_DB);
        }
        catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public void ejbCreate() {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}


    // Business methods

    private List getMovies() {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            ps = c.prepareStatement("select movie_id, title," 
                                    + " rating, poster_url"
                                    + " from movies");
            rs = ps.executeQuery();
            List ret = new ArrayList();
            while (rs.next()) {
                ret.add(new Movie(rs.getInt(1),
                                  rs.getString(2),
                                  rs.getString(3),
                                  rs.getString(4)));
            }
            rs.close();
            ps.close();

            c.close();
            return ret;
        } 
        catch (SQLException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List getMovies(String zipCode) {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            ps = c.prepareStatement("select distinct m.movie_id, title," 
                                    + " rating, poster_url"
                                    + " from movies m,"
                                    + " locations l, shows s"
                                    + " where l.zipcode = ?"
                                    + " and s.location_id = l.location_id"
                                    + " and s.movie_id = m.movie_id");
            ps.setString(1, zipCode);
            rs = ps.executeQuery();
            List ret = new ArrayList();
            while (rs.next()) {
                ret.add(new Movie(rs.getInt(1),
                                  rs.getString(2),
                                  rs.getString(3),
                                  rs.getString(4)));
            }
            rs.close();
            ps.close();

            c.close();

            // If no movies for this zipcode, return all movies.
            if (ret.isEmpty()) {
                ret = getMovies();
            }
            return ret;

        } 
        catch (SQLException e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List getLocations(int movieID) {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            ps = c.prepareStatement("select distinct"
                                    + " l.location_id, l.location"
                                    + " from locations l, shows s"
                                    + " where s.movie_id = ?"
                                    + " and l.location_id ="
                                    + " s.location_id");
            ps.setInt(1, movieID);
            rs = ps.executeQuery();
            List ret = new ArrayList();
            while (rs.next()) {
                ret.add(new Location(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            ps.close();

            c.close();
            return ret;
        } 
        catch (SQLException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List getLocations(String zipCode, int movieID) {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            ps = c.prepareStatement("select distinct"
                                    + " l.location_id, l.location"
                                    + " from locations l, shows s"
                                    + " where s.movie_id = ?"
                                    + " and l.zipcode = ?"
                                    + " and l.location_id ="
                                    + " s.location_id");
            ps.setInt(1, movieID);
            ps.setString(2, zipCode);
            rs = ps.executeQuery();
            List ret = new ArrayList();
            while (rs.next()) {
                ret.add(new Location(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            ps.close();

            c.close();

            // If no locations for this zipcode, return all locations.
            if (ret.isEmpty()) {
                ret = getLocations(movieID);
            }
            return ret;
        } 
        catch (SQLException e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List getShowtimes(int movieID, int locationID) {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            ps = c.prepareStatement("select distinct"
                                    + " s.show_id, s.showtime"
                                    + " from shows s, locations l"
                                    + " where s.movie_id = ?"
                                    + " and s.location_id = ?");
            ps.setInt(1, movieID);
            ps.setInt(2, locationID);
            rs = ps.executeQuery();
            List ret = new ArrayList();
            while (rs.next()) {
                ret.add(new Showtime(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            ps.close();

            c.close();
            return ret;
        } 
        catch (SQLException e) {
            throw new EJBException(e.getMessage());
        }
    }
}
