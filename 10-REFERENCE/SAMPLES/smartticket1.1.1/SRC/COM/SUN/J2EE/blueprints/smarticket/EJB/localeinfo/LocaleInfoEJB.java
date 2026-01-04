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

package com.sun.j2ee.blueprints.smarticket.ejb.localeinfo;

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
 * Implements the LocaleInfo EJB component. Takes care of business methods
 * and database access. This is a stateless session bean.
 */
public class LocaleInfoEJB implements SessionBean {

    protected static final String LOCALEINFO_DB 
        = "java:comp/env/jdbc/LocaleInfoDataSource";

    protected DataSource dataSource;


    // Overridden SessionBean methods

    public void setSessionContext(SessionContext sc) {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(LOCALEINFO_DB);
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

    /**
     * Get a list of the available locales for this application.
     */
    public List getLocales() {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            ps = c.prepareStatement("select locale_id, locale" 
                                    + " from locales");
            rs = ps.executeQuery();
            List ret = new ArrayList();
            while (rs.next()) {
                ret.add(new MessageLocale(rs.getInt(1),
				   rs.getString(2)));

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

    /**
     * Return a list of localized messages for the given locale.
     *
     * @param locale the ID of the locale.
     */
    public List getMessages(int locale) {

        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();

            ps = c.prepareStatement("select message_id, message_text" 
                                    + " from messages"
				    + " where locale_id = ?");
	    ps.setInt(1, locale);
            rs = ps.executeQuery();
            List ret = new ArrayList();
            while (rs.next()) {
                ret.add(new Message(rs.getInt(1),
				   rs.getString(2)));

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
