//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
package samples.transactions.ejb.bmt.simple.util;

/**
 * This interface defines the methods necessary for bank account transactions
*/

public interface BankAccount {

/**
 * Returns the balance remaining in the bank account.
 * @exception throws Exception.
 */
    public double getBalance( )
    throws Exception;

/**
 * Deposits the amount in the bank account.
 * @exception throws Exception.
 */
    public void deposit( double amount )
    throws Exception;

/**
 * Withdraws the amount in the bank account.
 * @exception throws Exception.
 */
 
    public void withdraw( double amount )
    throws Exception;

/**
 * Closes the Database connection.
 * @exception throws Exception.
 */
    public void cleanup( )
    throws Exception;
}
