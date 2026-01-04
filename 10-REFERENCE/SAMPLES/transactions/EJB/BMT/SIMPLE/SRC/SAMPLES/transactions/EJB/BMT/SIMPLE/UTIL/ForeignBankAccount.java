//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.

package samples.transactions.ejb.bmt.simple.util;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * This class implements the interface BankAccount and represents a ForeignBankAccount class.
 * It has its own datasource on a different database server.
*/
public class ForeignBankAccount implements BankAccount {
    private String  m_id;
    private double  m_balance;

    private static final String FOREIGN_BANK_DATASOURCE =
    "java:comp/env/jdbc/transactions-globalbmt/ForeignBankDB";


    private DataSource m_dataSource;
    private Connection m_connection;

/*
 * Constructor to create a ForeignBank Account.  Takes a String ID for a primary key.
*/
    public ForeignBankAccount( String p_accountId )
    throws Exception {
        m_id = p_accountId;
    }

/*
 *  Implementation of getBalance() of the BankAccount interface.  It will return the balance of the bank account.
 *  @return returns a the balance as a double.
 *  @exception throws a General Exception when the account with this primary key does not exist.
 *
 */
    public double getBalance( )
    throws Exception {
        findByPrimaryKey( m_id );
        return m_balance;
    }

/*
 *  Implementation of deposit(double amount) of the BankAccount interface.  
*   It will create a DB connection to the ForeignBankDB and update the balance of the bank account.
 *  @exception throws a General Exception when the amount deposited is <=0 or if unable to obtain a DB connection.
 *
 */

    public void deposit( double p_amount )
    throws Exception {

        Statement stmt = null;
        
        try {
            // Amount must be > 0

            if ( p_amount <= 0 ) {
                throw new Exception( "Amount must be more than 0." );
            }

            // Deposit to account

            m_connection = getDBConnection( );
            stmt = m_connection.createStatement( );

            stmt.execute(
                "UPDATE account SET balance = balance + " + p_amount +
                          " WHERE id = '" + m_id + "'" );
        }
        catch( Throwable t ) {
			t.printStackTrace();
            throw new Exception(
            "Cannot deposit to Foreign Account " + m_id + ": " + t.getMessage( ) );
        }
        finally {
            if ( stmt != null ) stmt.close( );
        }
    }

/*
 *  Implementation of withdraw(double amount) of the BankAccount interface.  
 *  It will create a DB connection to the ForeignBankDB and withdraw the balance of the bank account.
 *  @exception throws a General Exception when the amount deposited is <=0 or if unable to obtain a DB connection.
*/
    public void withdraw( double p_amount )
    throws Exception {

        Statement stmt = null;
        
        try {
            // Amount must be > 0

            if ( p_amount <= 0 ) {
                throw new Exception( "Amount must be more than 0." );
            }

            // Withdraw from account

            m_connection = getDBConnection( );
            stmt = m_connection.createStatement( );

            stmt.execute(
                "UPDATE account SET balance = balance - " + p_amount +
                          " WHERE id = '" + m_id + "'" );
        }
        catch( Throwable t ) {
			t.printStackTrace();
            throw new Exception(
            "Cannot withdraw from Foreign Account " +m_id+ ": " + t.getMessage( ) );
        }
        finally {
            if ( stmt != null ) stmt.close( );
        }
    }

/*
 *  Implementation of cleanup() of the BankAccount interface.  
 *  This method is called to close the DB connection.
 *  @exception throws a General Exception if it is unable to close the DB connection.
*/

    public void cleanup( )
    throws Exception {
        if ( m_connection != null ) m_connection.close();
    }


//------------------------------- private methods ----------------------------//


    private Connection getDBConnection( )
    throws Exception {

        if ( m_connection == null || m_connection.isClosed( ) ) {

            if ( m_dataSource == null ) {
                m_dataSource = (DataSource) new InitialContext( ).lookup(
                                                    FOREIGN_BANK_DATASOURCE );
            }

            m_connection = m_dataSource.getConnection( );
        }

        return m_connection;
    }


    private void findByPrimaryKey( String p_accountId )
    throws Exception {

        if ( p_accountId == null || p_accountId.trim( ).length( ) == 0 ) {
            throw new Exception( "Please specify the Foreign Account ID." );
        }

        Statement stmt = null;
        ResultSet rs = null;

        try {
            m_connection = getDBConnection( );
            stmt = m_connection.createStatement( );
            stmt.execute(
                "SELECT id, balance FROM account WHERE id = '" +
                          p_accountId + "'" );
            rs = stmt.getResultSet( ); 
            if ( rs.next( ) ) {
                m_id = rs.getString( 1 );
                m_balance = rs.getDouble( 2 );
            }
            else {
                throw new Exception(
                "Foreign Account ID "+p_accountId+" does not exist. " );
            }
        }
        catch( Throwable t ) {
			t.printStackTrace();
            throw new Exception(
                "Cannot find Foreign Account information: " + t.getMessage( ) );
        }
        finally {
            if ( stmt != null ) stmt.close( );
            if ( rs != null ) rs.close( );
        }
    }
}
