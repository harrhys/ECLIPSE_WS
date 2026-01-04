/*
 * Copyright 2001, 2002 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 */

//package samples.security.auth.login;
package samples.security.jdbcrealm;

import java.util.*;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Driver;
import java.sql.DriverManager;

import javax.security.auth.login.LoginException;
//import javax.security.jdbcrealm.LoginException;

//import samples.security.auth.realm.JDBCRealm;
import samples.security.jdbcrealm.JDBCRealm;
import com.iplanet.ias.security.auth.login.PasswordLoginModule;

import com.sun.enterprise.security.auth.Credentials;
import com.sun.enterprise.security.auth.AuthenticationStatus;
import com.sun.enterprise.security.auth.realm.Realm;


/**
 * JDBCRealm login module.
 * sample setting in server.xml for JDBCLoginModule
 *    <auth-realm name="jdbc" classname="samples.security.auth.realm.JDBCRealm">
 *	<property name="dbdrivername" value="com.pointbase.jdbc.jdbcUniversalDriver"/>
 *	<property name="dburl"        value="jdbc:pointbase:server://localhost:9092/sample"/>
 *	<property name="dbusername"   value="public"/>
 *	<property name="dbpasswd"     value="public"/>
 *	<property name="usertable"     value="user_tbl"/>
 *	<property name="usernamecol"   value="uid"/>
 *	<property name="userpasswdcol" value="passwd"/>
 *	<property name="usergroupcol"  value="groups"/>        
 *       <property name="jaas-context"  value="jdbcRealm"/>
 *    </auth-realm>
 */
public class JDBCLoginModule extends PasswordLoginModule
{
    static final String PARAMS_DBDRIVERNAME= "dbdrivername";
    static final String PARAMS_DBURL       = "dburl";
    static final String PARAMS_DBUSERNAME  = "dbusername";
    static final String PARAMS_DBPASSWD    = "dbpasswd";

    static final String PARAMS_USERTABLE    = "usertable";
    static final String PARAMS_USERNAMECOL  = "usernamecol";
    static final String PARAMS_USERPASSWDCOL= "userpasswdcol";
    static final String PARAMS_USERGROUPCOL = "usergroupcol";

    static Driver     _dbdriver = null;
    static Connection _dbConnection = null;

    /**
     * Return AuthenticationStatus object
     * credentials, if there is one; otherwise return <code>null</code>.
     */
    protected AuthenticationStatus authenticate()
        throws LoginException
    {
        if (!(_currentRealm instanceof JDBCRealm)) {
            throw new LoginException("JDBCLoginModule requires JDBCRealm.");
        }
        String[] grpList = this.authenticate(_username, _password);
        if (grpList == null) {  // JAAS behavior
            throw new LoginException("Failed JDBC login: " + _username);
        }        
        System.out.println("JDBCRealm login succeeded.");
        return commitAuthentication(_username, _password,
                                    _currentRealm, grpList);
    }


    /**
     * Return the user group associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username the user's id
     * @param passwd   the user's clear password
     */
    private String[] authenticate(String username,String passwd)
    {
        // Look up the user's credentials
        String dbCredential = null;
        String dbgroups = null;

	JDBCRealm jdbcRealm = (JDBCRealm)_currentRealm;
        String usertable    = jdbcRealm.getRealmProperty(PARAMS_USERTABLE);
        String usernamecol  = jdbcRealm.getRealmProperty(PARAMS_USERNAMECOL);
        String userpasswdcol= jdbcRealm.getRealmProperty(PARAMS_USERPASSWDCOL);
        String usergroupcol = jdbcRealm.getRealmProperty(PARAMS_USERGROUPCOL);

        String sql = "SELECT " + userpasswdcol + "," + usergroupcol + 
                     " FROM " + usertable + 
                     " WHERE " + usernamecol + " =?";
        PreparedStatement ps = null;
        try {
	  Connection dbcon = getConnection();
          ps = dbcon.prepareStatement(sql);
	  ps.setString(1, username);
	  ResultSet rs = ps.executeQuery();
	  if (rs.next()) {
            dbCredential = rs.getString(1).trim();
            dbgroups = rs.getString(2).trim();
	  }
        } catch (SQLException e) {
          e.printStackTrace();
	} finally {
	  try { 
             ps.close(); 
          } catch (SQLException ignore) {
	    ignore.printStackTrace();
          }
	}

	String[] g = null;
        if ( (dbCredential!= null) && (dbCredential.equals(passwd)) ) {
          Vector membership = new Vector();
	  if (dbgroups != null) {
            StringTokenizer gst = new StringTokenizer(dbgroups,",;");
            while (gst.hasMoreTokens()) {
                membership.add(gst.nextToken() );
            }
	  }
          if (membership.size()>0) {
            g = new String[membership.size()];
            membership.toArray(g);
            jdbcRealm.setGroupNames(username,g);
          }
	}
	return g;
    }

    /**
     * get jdbc connection.
     */
    private Connection getConnection() throws SQLException 
    {
        if (_dbConnection != null)
            return (_dbConnection);

        JDBCRealm jdbcRealm = (JDBCRealm)_currentRealm;
        String dbdrivername = jdbcRealm.getRealmProperty(PARAMS_DBDRIVERNAME);
        String dburl        = jdbcRealm.getRealmProperty(PARAMS_DBURL);
        String dbusername   = jdbcRealm.getRealmProperty(PARAMS_DBUSERNAME);
        String dbpasswd     = jdbcRealm.getRealmProperty(PARAMS_DBPASSWD);

        if (_dbdriver == null) {
            try {
                Class clazz = Class.forName(dbdrivername);
                _dbdriver = (Driver) clazz.newInstance();
            } catch (Throwable e) {
                throw new SQLException(e.getMessage());
            }
        }

        // Open a new connection
        Properties props = new Properties();
        props.put("user",dbusername);
        props.put("password",dbpasswd);
        Connection dbcon = _dbdriver.connect(dburl,props);
        dbcon.setAutoCommit(false);
        return (dbcon);
    }
}

