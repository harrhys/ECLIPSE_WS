/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 */

package samples.lifecycle.multithreaded;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import javax.transaction.SystemException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class ResourceAccess
 *
 * accessed from Lifecycle listeners during Lifecycle Events either
 * asynchronously by LCLWithResourceAccessInThread 
 */
public class ResourceAccess {
    Connection _connection = null;
    Context _namingCtx = null;
    UserTransaction _userTrans = null;

    private static long serialNumber=0;

    private String qrySQL = "SELECT SERIALNUMBER, NAME, MARKS from LCMLIFECYCLE";
    private String updSQL = "UPDATE LCMLIFECYCLE SET MARKS=MARKS+1";
    private String insSQL = "INSERT INTO LCMLIFECYCLE (SERIALNUMBER, NAME, MARKS) VALUES (?, ?, 1.0)";

    public ResourceAccess() {
    }

    public void setInitialContext(Context initCtx) {
        _namingCtx = initCtx;
    }

    /**
     * does the JNDI lookup for UserTransaction reference
     */
    public void initialize() {
        try {
            if (_namingCtx == null)
                _namingCtx = new InitialContext();
            _userTrans = (UserTransaction) _namingCtx.lookup("java:comp/UserTransaction");
        } catch (NamingException nameEX) {
            System.out.println("[samples.lifecycle.multithreaded.getNamingContext()]:: "+nameEX);
            nameEX.printStackTrace();
        }
    }

    /**
     * does the JNDI lookup for Pointbase datasource
     */
    public void openConnection() {
        try {
            DataSource ds = (DataSource) _namingCtx.lookup("multithreaded/lifecycle-multithreaded");
            _connection = ds.getConnection();
        } catch (Exception ex) {
            System.out.println("[samples.lifecycle.multithreaded.openConnection()]:: "+ex);
            ex.printStackTrace();
        }
    }

    /**
     * closes pending open connection to Pointbase datasource
     */
    public void closeConnection() {
        try {
            if (_connection != null) {
                _connection.close();
            }
        } catch (Exception ex) {
            System.out.println("[samples.lifecycle.multithreaded.closeConnection()]:: "+ex);
            ex.printStackTrace();
        }
    }

    /**
     *
     *
     * inserts a record into LCMLIFECYCLE of Pointbase datasource
     */
    public void insert() {
        if (_connection == null) {
            System.out.println("[samples.lifecycle.multithreaded.insert()]:: Connection lost OR not initialized");
            return;
        }

        PreparedStatement stmt = null;
        try {
            stmt = _connection.prepareStatement(insSQL);
            synchronized (this) {
                stmt.setLong(1, serialNumber++);
            }
            stmt.setString(2, "abc"+serialNumber);
            stmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("[samples.lifecycle.multithreaded.insert()]:: "+ex);
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlEX) {
                System.out.println("[samples.lifecycle.multithreaded.insert()]:: "+sqlEX);
                sqlEX.printStackTrace();
            }
        }
    }

    /**
     *
     *
     * updates an existing record by incrementing 'MARKS' numeric field
     * of the record
     */
    public void update() {
        if (_connection == null) {
            System.out.println("[samples.lifecycle.multithreaded.update()]:: Connection lost OR not initialized");
            return;
        }

        Statement stmt = null;
        try {
            stmt = _connection.createStatement();
            stmt.executeUpdate(updSQL);
        } catch (Exception ex) {
            System.out.println("[samples.lifecycle.multithreaded.update()]:: "+ex);
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlEX) {
                System.out.println("[samples.lifecycle.multithreaded.insert()]:: "+sqlEX);
                sqlEX.printStackTrace();
            }
        }
    }

    /**
     * 
     *
     * executes a Query to fetch all records in the LCMLIFECYCLE
     *
     *@return ArrayList of records where each element in the returned list
     *        is itself a list containing all the read fields
     */
    public ArrayList query() {
        if (_connection == null) {
            System.out.println("[samples.lifecycle.multithreaded.query()]:: Connection lost OR not initialized");
            return null;
        }

        Statement stmt = null;
        ArrayList ret = null;
        ResultSet rs = null;
        try {
            stmt = _connection.createStatement();
            rs = stmt.executeQuery(qrySQL);
            ret = new ArrayList();
            while (rs.next()) {
                ArrayList col = new ArrayList();
                col.add(Long.toString(rs.getLong(1)));
                col.add(rs.getString(2));
                col.add(Double.toString(rs.getDouble(3)));
                ret.add(col);
            }
        } catch (SQLException sqlEX) {
            System.out.println("[samples.lifecycle.multithreaded.query()]:: "+sqlEX);
            sqlEX.printStackTrace();
        } catch (Exception ex) {
            System.out.println("[samples.lifecycle.multithreaded.query()]:: "+ex);
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlEX) {
                System.out.println("[samples.lifecycle.multithreaded.query()]:: "+sqlEX);
                sqlEX.printStackTrace();
            }
        }

        return ret;
    }

    /**
     *     
     *     -  initialize javax.transaction.UserTransaction
     *     -  create java.sql.Connection reference to a open Pointbase connection resource
     *     -  query the contents of LCMLIFECYCLE
     *     -  insert a record into LCMLIFECYCLE
     *     -  query after the insert
     *     -  update all records by incrementing the 'MARKS' numeric field
     *     -  query again
     *     -  close the open connection to Pointbase
     *      all exception are handled by the methods that execute each of
     *      of the above operation
     */
    public void doWork() {
        initialize();
        openConnection();
        print(query());
        insert();
        print(query());
        update();
        print(query());
        closeConnection();
    }

    /**
     * 
     *@param cols  list of records to be displayed
     */
    private void print(ArrayList cols) {
       	if (cols == null)
            return;

        System.out.println("The Existing records in the LCMLIFECYCLE are:");
        for (int i=0, len=cols.size(); i < len; i++) {
            System.out.println("serial no. ="+(String) ((ArrayList) cols.get(i)).get(0));
            System.out.println("name       ="+(String) ((ArrayList) cols.get(i)).get(1));
            System.out.println("marks      ="+(String) ((ArrayList) cols.get(i)).get(2));
            System.out.println("");
        }
    }
}

