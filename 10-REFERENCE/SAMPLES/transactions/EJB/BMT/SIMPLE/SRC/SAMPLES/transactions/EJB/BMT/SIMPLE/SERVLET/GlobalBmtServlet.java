//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
package samples.transactions.ejb.bmt.simple.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import java.io.IOException;
import java.rmi.RemoteException;

import samples.transactions.ejb.bmt.simple.ejb.GlobalBankService;
import samples.transactions.ejb.bmt.simple.ejb.GlobalBankServiceHome;
 
/**
 *  This servlet is responsible for presenting the pages for the transactions-globalbmt
 *  application and using container Managed through an EJB to complete transactions 
 *  between the two datasources.
*/


public class GlobalBmtServlet extends HttpServlet {

    private InitialContext    m_context;
    private GlobalBankService m_globalBankService;

    private final String GLOBAL_BANK_SERVICE_EJBHOME = 
   "java:comp/env/ejb/GlobalBankServiceEJBbmt";

/*
 * The getServletInfo method of the servlet returns a String
 * that describes the function of this servlet.
 * @return String with information about servlet.
*/

    public String getServletInfo( ) {
        return "Servlet that calls the GlobalBankService bean.";
    }


  /**
    * The doGet method of the servlet. Handles all http GET request.
    * Required by the servlet specification.
    * will retrieve the from and to bank information as well as the amount
    * to be transferred.
    * It will also execute the global transaction across 2 different datasources
    * either from ForeignBankDB to LocalBankDB or vice versa by
    * calling the GlobalBankServiceEJB transferFunds method (bean managed).
    * @exception throws ServletException and IOException.
    * and will rollback the transaction when an exception occurs.
    */
    public void doGet( HttpServletRequest  p_request,
                       HttpServletResponse p_response )
    throws ServletException, IOException {

        // Get data from form

        String fromBank = null;
        String fromAcctId = null;
        String toBank = null;
        String toAcctId = null;
        double transferAmount = 0.0;

        try {
            fromBank = (String) p_request.getParameter( "from_bank" );
            fromAcctId= (String) p_request.getParameter( "from_account_id" );
            toBank = (String) p_request.getParameter( "to_bank" );
            toAcctId = (String) p_request.getParameter( "to_account_id" );

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


        // Use GlobalBankService to transfer funds using a 
        // global bean-managed transaction.

        try {

            // Get GlobalBankService bean

            if ( m_globalBankService == null ) {
                m_globalBankService = getGlobalBankService( );
            }


            // Transfer Funds: see this method for details on how to use
            // a bean-managed global transaction

            m_globalBankService.transferFunds( fromBank, fromAcctId,
                                               toBank, toAcctId,
                                               transferAmount );

            p_request.setAttribute( "message",
                "Transfer of funds was successfully completed. " );

            p_request.setAttribute( "from_acct_new_bal", ""+
                m_globalBankService.getFromAccountNewBalance( ) );
            p_request.setAttribute( "to_acct_new_bal", ""+
                m_globalBankService.getToAccountNewBalance( ) );
            p_request.setAttribute( "from_acct_amount", "-" + transferAmount );
            p_request.setAttribute( "to_acct_amount", "+" + transferAmount );
        }
        catch( RemoteException r ) {

            if ( r.detail != null ) {
                p_request.setAttribute( "message",
                "TRANSFER FAILED: "+r.detail.getMessage( ));
            }
            else {
                p_request.setAttribute( "message",
                "TRANSFER FAILED: "+r.getMessage( ) );
            }
        }
        catch( Throwable e ) {
            p_request.setAttribute( "message",
            "TRANSFER FAILED: "+e.getMessage( ) );
        }
        finally {

            // Set Old Account Balance

            p_request.setAttribute( "from_acct_old_bal", ""+
                m_globalBankService.getFromAccountOldBalance( ) );
            p_request.setAttribute( "to_acct_old_bal", ""+
                m_globalBankService.getToAccountOldBalance( ) );
        }

        displayPage( p_request, p_response, "/GlobalTransaction.jsp" );
    }

   /**
    * The doPost method of the servlet. Handles all http POST requests.
    * Required by the servlet specification.
   */

    public void doPost( HttpServletRequest pRequest,
                        HttpServletResponse pResponse )
    throws ServletException, IOException {

        doGet( pRequest, pResponse );
    }


//------------------------------ private methods -----------------------------//


    private GlobalBankService getGlobalBankService()
    throws ServletException {

        try {
            if ( m_context == null ) {
                m_context = new InitialContext();
            }

            // Locate EJB Home
            Object objref = m_context.lookup( GLOBAL_BANK_SERVICE_EJBHOME );
            GlobalBankServiceHome ejbHome = ( GlobalBankServiceHome )
                PortableRemoteObject.narrow ( objref,
                                              GlobalBankServiceHome.class );

            // Create bean
            return ejbHome.create();
        }
        catch( Throwable t ) {
            throw new ServletException(
            "GlobalBmtServlet.getGlobalBankService(): "+t );
        }
    }


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
