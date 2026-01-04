package samples.transactions.ejb.cmt.bank.ejb;
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

/**
  *
 *  This session bean demonstrates container-managed transaction,
 *  synchronizing a session's instance variables with the database values.
 *  The EJB container sets the boundaries of the transactions, and 
 *  simplify development because the EJB does not explicitly mark the 
 *  transaction's boundaries.
 *  
 * @see Bank
 * @see BankHome
 */

public class BankBean implements SessionBean, SessionSynchronization {

   private String customerId;
   private double checkingBalance;
   private double savingBalance;
   private SessionContext context;
   private Connection con;
   private String dbName = "java:comp/env/jdbc/transactions-bank/BankDB";

   /**
    **
    * Transfers the given amount to the Savings Account.
    * Uses container managed transactions.
    * @param amount
    * @exception InsufficientBalanceException
    */

   public void transferToSaving(double amount) throws 
      InsufficientBalanceException  {

      checkingBalance -= amount;
      savingBalance += amount;

      try {
         updateChecking(checkingBalance);
         if (checkingBalance < 0.00) {
            context.setRollbackOnly();
            throw new InsufficientBalanceException();
         }
         updateSaving(savingBalance);
      } catch (SQLException ex) {
          throw new EJBException 
             ("Transaction failed due to SQLException: " 
             + ex.getMessage());
      }
   }

/** 
 * Returns the balance of the checking Account.
 */
   public double getCheckingBalance() {

      return checkingBalance;
   }

/** 
 *
 * Returns the balance of the savings Account.
 *
 */

   public double getSavingBalance() {

      return savingBalance;
   }

   public void ejbCreate(String id) throws CreateException {

      customerId = id;

      try {
         makeConnection();
         checkingBalance = selectChecking();
         savingBalance = selectSaving();
      } catch (Exception ex) {
          throw new CreateException(ex.getMessage());
      }
     
   }


   public void ejbRemove() {

      try {
          con.close();
      } catch (SQLException ex) {
          throw new EJBException("ejbRemove SQLException: " + ex.getMessage());
      }
   }

   public void ejbActivate() {

      try {
         makeConnection();
      } catch (Exception ex) {
          throw new EJBException("ejbActivate Exception: " + ex.getMessage());
      }
   }

   public void ejbPassivate() {

      try {
        con.close();
      } catch (SQLException ex) {
          throw new EJBException("ejbPassivate Exception: " + ex.getMessage());
      }
   }


   public void setSessionContext(SessionContext context) {
      this.context = context;
   }

   public void afterBegin() {

      System.out.println("afterBegin()");
      try {
         checkingBalance = selectChecking();
         savingBalance = selectSaving();
      } catch (SQLException ex) {
          throw new EJBException("afterBegin Exception: " + ex.getMessage());
      }
         
   }

   public void beforeCompletion() {

      System.out.println("beforeCompletion()");
   }

   public void afterCompletion(boolean committed) {

      System.out.println("afterCompletion: " + committed);
      if (committed == false) {
         try {
            checkingBalance = selectChecking();
            savingBalance = selectSaving();
         } catch (SQLException ex) {
             throw new EJBException("afterCompletion SQLException: " +
                ex.getMessage());
         }
      }
   }

   public BankBean() {}


   private void updateChecking(double amount) throws SQLException {

      String updateStatement =
            "update checking set balance =  ? " +
            "where id = ?";

      PreparedStatement prepStmt = 
            con.prepareStatement(updateStatement);

      prepStmt.setDouble(1, amount);
      prepStmt.setString(2, customerId);
      prepStmt.executeUpdate();
      prepStmt.close();
   }

   private void updateSaving(double amount) throws SQLException {

      String updateStatement =
            "update saving set balance =  ? " +
            "where id = ?";

      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setDouble(1, amount);
      prepStmt.setString(2, customerId);
      prepStmt.executeUpdate();
      prepStmt.close();
   }

   private double selectChecking() throws SQLException {

      String selectStatement =
            "select balance " +
            "from checking where id = ? ";
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


   private double selectSaving() throws SQLException {

      String selectStatement =
            "select balance " +
            "from saving where id = ? ";
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

   private void makeConnection() 
      throws NamingException, SQLException {

      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup(dbName);
      con =  ds.getConnection();
   }

} // BankBean 
