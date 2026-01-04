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

package com.sun.j2ee.blueprints.smarticket.ejb.customer;

import com.sun.j2ee.blueprints.smarticket.ejb.*;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.rmi.RemoteException;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Implements the Customer EJB component. Takes care of business
 * methods and database access.
 */
public class CustomerEJB implements EntityBean {

    // EJB-specific variables

    protected static final String CUSTOMER_DB 
        = "java:comp/env/jdbc/CustomerDataSource";

    protected EntityContext entityContext;
    protected DataSource dataSource;


    // Business variables
    
    protected CustomerInformation information;


    // Overridden EntityBean methods

    public void setEntityContext(EntityContext ec) {
        entityContext = ec;
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(CUSTOMER_DB);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e);
        }
    }

    public void unsetEntityContext() {
        dataSource = null;
        entityContext = null;
    }

    public String ejbCreate(String u, String p, String zc, String cc) 
        throws EJBException, DuplicateKeyException, CreateException {
        String ret = dbInsertCustomer(u, p, zc, cc);
        if (ret != null) {
            return ret;
        } else {
            throw new CreateException(u);
        }
    }

    public void ejbLoad() {
        dbLoadCustomer();
    }

    public String ejbFindByPrimaryKey(String key) throws FinderException {
        String ret = dbFindCustomer(key);
        if (ret != null) {
            return ret;
        } else {
            throw new ObjectNotFoundException(key);
        }
    }

    public void ejbPostCreate(String u, String p, String zc, String cc) 
        throws EJBException, DuplicateKeyException, CreateException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbStore() {}
    public void ejbRemove() {}


    // Business methods

    public CustomerInformation getInformation() { return information; }


    // Helper methods

    String dbInsertCustomer(String u, String p, String zc, String cc) 
        throws DuplicateKeyException {

        // Check for duplicates.
        if (dbFindCustomer(u) != null) {
            throw new DuplicateKeyException();
        }

        // Insert the customer.
        try {
            Connection c = dataSource.getConnection();
            PreparedStatement ps = 
                c.prepareStatement("insert into userprefs"
                                   + " (username, password,"
                                   + " zipcode, creditcard)"
                                   + " values (?, ?, ?, ?)");
            ps.setString(1, u);
            ps.setString(2, p);
            ps.setString(3, zc);
            ps.setString(4, cc);
            ps.executeUpdate();
            ps.close();
            c.close();
            return u;
        }
        catch (SQLException e) {
            throw new EJBException(e);
        }        
    }

    void dbLoadCustomer() {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();
            ps = c.prepareStatement("select u.username, u.password,"
                                    + " u.zipcode, u.creditcard"
                                    + " from userprefs u"
                                    + " where username = ?");
            String username = (String) entityContext.getPrimaryKey();
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                information = 
                    new CustomerInformation(rs.getString(1), rs.getString(2),
                                            rs.getString(3), rs.getString(4));
            }
            rs.close();
            ps.close();
            c.close();
        }
        catch (SQLException e) {
            throw new EJBException(e);
        }
    }

    String dbFindCustomer(String username) {
        try {
            Connection c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            c = dataSource.getConnection();
            ps = c.prepareStatement("select username"
                                    + " from userprefs"
                                    + " where username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            String ret = rs.next() ? username : null;

            rs.close();
            ps.close();
            c.close();

            return ret;
        }
        catch (SQLException e) {
            throw new EJBException(e);
        }
    }
}
