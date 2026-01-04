//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.

package samples.transactions.ejb.cmt.simple.ejb;

import javax.ejb.EJBObject;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.transaction.UserTransaction;
import java.rmi.RemoteException;
import samples.transactions.ejb.cmt.simple.util.*;

/**
 * A simple stateless session bean for the Converter application. 
 * This bean implements all business method as declared by the remote 
 * interface, <code>Converter</code>.
 *
 * @see GlobalBankService
 * @see GlobalBankServiceEJB
 */

public class GlobalBankServiceEJB implements SessionBean {


    private transient SessionContext m_sessionContext;

    private static double m_fromAcctOldBal;
    private static double m_toAcctOldBal;
    private static double m_fromAcctNewBal;
    private static double m_toAcctNewBal;
    private static String m_message;

 /* Transfer the funds from one bank account to another.
 * @param fromBank String for the Source account
 * @param FromAccountID String for the Source account ID
 * @param toBank String for the Destination account
 * @param toAccountID String for the Destination Account ID
 * @param transferAmount double amount to be transferred.
 * @exception RemoteException
 */
    public void transferFunds( String p_fromBank,
                               String p_fromAcctId,
                               String p_toBank,
                               String p_toAcctId,
                               double p_transferAmount )
    throws RemoteException {

        if ( p_fromBank == null || p_fromBank.trim().length() == 0 ) {
            throw new RemoteException(
   "Please specify if the Source Account is from a Local or Foreign Bank." );
        }
        if ( p_toBank == null || p_toBank.trim().length() == 0 ) {
            throw new RemoteException(
   "Please specify if the Recipient Account is from a Local or Foreign Bank." );
        }

        initResults( );

        // Use a global transaction to transfer funds

        BankAccount fromAcct = null;
        BankAccount toAcct = null;
        UserTransaction txn = null;

        try {
            if ( p_fromBank.equals( "Local Bank" ) ) {
                fromAcct = new LocalBankAccount( p_fromAcctId );
            }
            else {
                fromAcct = new ForeignBankAccount( p_fromAcctId );
            }
            m_fromAcctOldBal = fromAcct.getBalance( );

            if ( p_toBank.equals( "Local Bank" ) ) {
                toAcct = new LocalBankAccount( p_toAcctId );
            }
            else {
                toAcct = new ForeignBankAccount( p_toAcctId );
            }
            m_toAcctOldBal = toAcct.getBalance( );

            fromAcct.withdraw( p_transferAmount );
            toAcct.deposit( p_transferAmount );

            m_fromAcctNewBal = fromAcct.getBalance( ); 
            m_toAcctNewBal = toAcct.getBalance( ); 

            if ( m_fromAcctNewBal < 0 ) {
                throw new Exception( "Source Account has insufficient funds." );
            }
        }
        catch( Throwable t ) {
            m_message = t.getMessage( );
            throw new RemoteException( t.getMessage( ) );
        }
        finally {
            try {
                fromAcct.cleanup( );
                toAcct.cleanup( );
            }
            catch( Exception e2 ) { }
        }
    }

   /**
   * Returns the old balance from the source account
   * @exception RemoteException
   */
    public double getFromAccountOldBalance( ) { return m_fromAcctOldBal; }


   /**
   * Returns the new balance from the source account.
   * @exception RemoteException
   */
    public double getFromAccountNewBalance( ) { return m_fromAcctNewBal; }

    /**
    * Returns the old balance from the destination account
    * @exception RemoteException
    */
    public double getToAccountOldBalance( ) { return m_toAcctOldBal; }

    /**
    * Returns the new balance from the destination account
    * @exception RemoteException
    */
    public double getToAccountNewBalance( ) { return m_toAcctNewBal; }

    /**
    * Returns the message to be displayed after transaction.
    * @exception RemoteException
    */

    public String getMessage( ) { return m_message; }


    /**
     * Creates a bean. Required by EJB spec.
     * @exception throws CreateException.
     * @exception throws RemoteException.
     */
    public void ejbCreate()
    throws RemoteException, CreateException { }

    /**
     * Loads the state of the bean from secondary storage. Required by EJB spec.
     */
    public void ejbActivate() { }
    
    /**
     * Keeps the state of the bean to secondary storage. Required by EJB spec.
     */

    public void ejbPassivate() { }

    /**
     * Removes the bean. Required by EJB spec.
     */
    public void ejbRemove() { }
    
    /**
     * Sets the session context. Required by EJB spec.
     * @param ctx A SessionContext object.
     */

    public void setSessionContext( SessionContext pContext ) {
        m_sessionContext = pContext;
    }

//---------------------------- private methods ------------------------------//

    private void initResults( ) {
        m_fromAcctOldBal = 0.0;
        m_toAcctOldBal = 0.0;
        m_fromAcctNewBal = 0.0;
        m_toAcctNewBal = 0.0;
        m_message = null;
    }
}
