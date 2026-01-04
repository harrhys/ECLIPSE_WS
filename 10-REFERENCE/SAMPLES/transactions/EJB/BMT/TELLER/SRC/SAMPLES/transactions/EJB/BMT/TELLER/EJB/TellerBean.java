package samples.transactions.ejb.bmt.teller.ejb;
/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.util.*;
import javax.ejb.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import javax.transaction.*;

/**
 *
 *  This session bean demonstrates bean-managed transactions with the javax.transaction.UserTransaction 
 *  interface.  To demarcate a JTA (Java Transaction API) trasaction, you invoke the begin, commit
 *  and rollback methods of the interface.  This session bean uses the UserTransaction methods
 *  using begin and commit invocations to delimit the updates to the database.
 *  If the updates fail, the code invokes the rollback method and throws an EJBException.
 *  
 *
 * @see Teller
 * @see TellerHome
 */

public class TellerBean implements SessionBean {

   private String customerId;
   private double machineBalance;
   private SessionContext context;
   private Connection con;
   private String dbName = "java:comp/env/jdbc/transactions-teller/TellerDB";

   /**
    * Withdraws the specified amount from the bank account
    * Note how if the call to updateChecking(amount) fails, the code invokes the rollback method
    * and throws an EJBException.
    * This API demonstrates the UserTransaction API.
    * @param amount
    * @exception RemoteException
    */

   public void withdrawCash(double amount) {

      UserTransaction ut = context.getUserTransaction();

      try {
         ut.begin();
         updateChecking(amount);
         machineBalance -= amount;
         insertMachine(machineBalance);
         ut.commit();
      } catch (Exception ex) {
          try {
             ut.rollback();
          } catch (SystemException syex) {
              throw new EJBException
                 ("Rollback failed: " + syex.getMessage());
          }
          throw new EJBException 
             ("Transaction failed: " + ex.getMessage());
     }
   }

 /**
  * Returns the checking balance from the bank account
  * @exception RemoteException
  */

   public double getCheckingBalance() {

      try {
         return selectChecking();
      } catch (SQLException ex) {
          throw new EJBException
             ("Unable to get balance: "
             + ex.getMessage());
      }
   }

 /**
  * As per EJB specification. 
  * @exception CreateException
  */
   public void ejbCreate(String id) throws CreateException {

      customerId = id;

      try {
         makeConnection();
         machineBalance = selectMachine();
      } catch (Exception ex) {
          throw new CreateException(ex.getMessage());
      }
     
   }

 /**
  * As per EJB specification. 
  * 
  */

   public void ejbRemove()  {

      try {
         con.close();
      } catch (SQLException ex) {
          throw new EJBException(ex.getMessage());
      }
   }

 /**
  * As per EJB specification. 
  * 
  */
   public void ejbActivate()  {

      try {
         makeConnection();
      } catch (Exception ex) {
          throw new EJBException(ex.getMessage());
      }
   }

 /**
  * As per EJB specification. 
  * 
  */
   public void ejbPassivate()  {

      try {
         con.close();
      } catch (SQLException ex) {
          throw new EJBException(ex.getMessage());
      }
   }
 /*
  * As per EJB specification. 
  * 
  */

   public void setSessionContext(SessionContext context) {
      this.context = context;
   }

   public TellerBean() {}


   private void makeConnection() 
      throws NamingException, SQLException {

      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup(dbName);
      con =  ds.getConnection();
   }

   private void updateChecking(double amount) 
      throws SQLException {

      String updateStatement =
            "update checking set balance =  balance - ? " +
            "where id = ?";

      PreparedStatement prepStmt = 
            con.prepareStatement(updateStatement);

      prepStmt.setDouble(1, amount);
      prepStmt.setString(2, customerId);
      prepStmt.executeUpdate();
      prepStmt.close();
   }

   private void insertMachine(double amount)
      throws SQLException {

      String insertStatement =
            "insert into cash_in_machine values " +
            "( ? , current_date )";

      PreparedStatement prepStmt =
            con.prepareStatement(insertStatement);

      prepStmt.setDouble(1, amount);
      prepStmt.executeUpdate();
      prepStmt.close();
   }

   private double selectMachine() throws SQLException  {

      String selectStatement =
            "select amount " +
            "from cash_in_machine " +
            "where time_stamp = " +
            "(select max(time_stamp) from cash_in_machine)";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         double result = rs.getDouble(1);
         prepStmt.close();
         return result;
      }
      else {
         prepStmt.close();
         throw new EJBException
           ("Row for id " + customerId + " not found.");
      } 
   }

   private double selectChecking() throws SQLException  {

      String selectStatement =
            "select balance " +
            "from checking " +
            "where id = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, customerId);
      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         double result = rs.getDouble(1);
         prepStmt.close();
         return result;
      }
      else {
         prepStmt.close();
         throw new EJBException
           ("Row for id " + customerId + " not found.");
      } 
   }

} // TellerBean
