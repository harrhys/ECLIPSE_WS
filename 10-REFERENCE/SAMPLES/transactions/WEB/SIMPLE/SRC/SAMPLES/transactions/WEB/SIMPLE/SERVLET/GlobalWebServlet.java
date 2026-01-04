//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
package samples.transactions.web.simple.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This servlet is responsible for presenting the pages for the transactions-global web
 * application and using javax.transaction.UserTransaction to complete transactions  between the two datasources.
*/
public class GlobalWebServlet extends HttpServlet {


    private final String USER_TRANSACTION_JNDI_NAME =
    "java:comp/UserTransaction";

/*
 * The getServletInfo method of the servlet returns a String 
 * that describes the function of this servlet.
 * @return String with information about servlet.
*/
    public String getServletInfo( ) {
        return "Servlet that performs a Global Transaction without using EJB.";
    }


   /**
    * The doGet method of the servlet. Handles all http GET request.
    * Required by the servlet specification.
    * will retrieve the from and to bank information as well as the amount 
    * to be transferred.
    * It will also execute the global transaction across 2 different datasources
    * either from ForeignBankDB to LocalBankDB or vice versa.
    * @exception throws ServletException and IOException.
    * and will rollback the transaction when an exception occurs.
    */

    public void doGet( HttpServletRequest  p_request,
                       HttpServletResponse p_response )
    throws ServletException, IOException {

        // Get data from form

        String fromBank = null;
        String fromAccountId = null;
        String toBank = null;
        String toAccountId = null;
        double transferAmount = 0.0;

        try {
            fromBank = (String) p_request.getParameter( "from_bank" );
            fromAccountId= (String) p_request.getParameter( "from_account_id" );

            toBank = (String) p_request.getParameter( "to_bank" );
            toAccountId = (String) p_request.getParameter( "to_account_id" );

            String value = (String) p_request.getParameter( "transfer_amount" );
            if ( value == null ) value = "0.0";
            transferAmount = Double.parseDouble( value );
        }
        catch( Throwable t ) {
            p_request.setAttribute( "message",
                "Please correct your entries: " + t.getMessage( ) );
            displayPage( p_request, p_response, "/GlobalTransaction.jsp" );
            return;
        }


        // Transfer Funds: Execute a Global Transaction that spans across
        // different Data Sources.

        double fromAcctOldBal = 0.0;
        double toAcctOldBal = 0.0;
        double fromAcctNewBal = 0.0;
        double toAcctNewBal = 0.0;
        BankAccount fromBankAccount = null;
        BankAccount toBankAccount = null;
        UserTransaction globalTransaction = null;

        try {

            // Create Global Transaction 

            InitialContext ic = new InitialContext( );
            globalTransaction = (UserTransaction) ic.lookup(
                                                  USER_TRANSACTION_JNDI_NAME );
            	
            globalTransaction.begin( );


            // Get Source Account

            if ( fromBank.equals( "Local Bank" ) ) {
                fromBankAccount = new LocalBankAccount( fromAccountId );
            }
            else {
                fromBankAccount = new ForeignBankAccount( fromAccountId );
            }
            fromAcctOldBal = fromBankAccount.getBalance( );


            // Get Destination Account

            if ( toBank.equals( "Local Bank" ) ) {
                toBankAccount = new LocalBankAccount( toAccountId );
            }
            else {
                toBankAccount = new ForeignBankAccount( toAccountId );
            }
            toAcctOldBal = toBankAccount.getBalance( );


            // Transfer Funds

            fromBankAccount.withdraw( transferAmount );
            toBankAccount.deposit( transferAmount );

            fromAcctNewBal = fromBankAccount.getBalance( );
            toAcctNewBal = toBankAccount.getBalance( );

            if ( fromAcctNewBal < 0 ) {
                throw new Exception( "Source Account has insufficient funds." );
            }

            globalTransaction.commit( );

            p_request.setAttribute( "message",
                "Transfer of funds was successfully completed." );

            p_request.setAttribute( "from_acct_new_bal",""+fromAcctNewBal );
            p_request.setAttribute( "to_acct_new_bal",""+toAcctNewBal );
            p_request.setAttribute( "from_acct_amount","-"+transferAmount );
            p_request.setAttribute( "to_acct_amount","+"+transferAmount );
            p_request.setAttribute( "from_bank",fromBank);
            p_request.setAttribute( "to_bank",toBank);
        }
        catch( RemoteException r ) {
            try {
                globalTransaction.rollback( );
            }
            catch( Exception e2 ) { }

            if ( r.detail != null && r.detail.getMessage( ) != null ) {
                p_request.setAttribute( "message",
                    "TRANSFER FAILED: "+r.detail.getMessage( ) );
            }
            else {
                p_request.setAttribute( "message",
                    "TRANSFER FAILED: "+r.getMessage( ) );
            }
        }
        catch( Throwable t ) {
            try {
                globalTransaction.rollback( );
            }
            catch( Exception t2 ) { }

            p_request.setAttribute( "message",
                "TRANSFER FAILED: "+t.getMessage( ) );
        }
        finally {

            // Set Old Account Balances

            p_request.setAttribute( "from_acct_old_bal",""+fromAcctOldBal );
            p_request.setAttribute( "to_acct_old_bal", ""+toAcctOldBal );

            // Release resources

            try {
                fromBankAccount.cleanup( );
                toBankAccount.cleanup( );
            }
            catch ( Exception e2 ) { }
        }

        displayPage( p_request, p_response, "/GlobalTransaction.jsp" );
    }


//------------------------------ private methods ----------------------------//


    private void displayPage( HttpServletRequest p_request,
                              HttpServletResponse p_response,
                              String p_url )
    throws ServletException, IOException {
        p_response.setContentType( "text/html" );
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                                       p_url );
        dispatcher.include( p_request, p_response );
    }
}
