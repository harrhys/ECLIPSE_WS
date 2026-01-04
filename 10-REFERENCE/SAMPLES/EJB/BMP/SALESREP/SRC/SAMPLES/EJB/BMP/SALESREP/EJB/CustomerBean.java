package samples.ejb.bmp.salesrep.ejb;
/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;

/**
 **
 *  This entity bean  represents the Customer business object and can only have one sales representative.
 *  However, each sales representative can have multiple customers. This example shows mapping between
 *  a one-to-many relationship between database tables and pair of entity beans (Customer, Salesrep) with
 *  bean managed persistence.
 *
 *
 * @see Customer
 * @see CustomerHome
 */

public class CustomerBean implements EntityBean {

   private String customerId;
   private String salesRepId;
   private String name;
   private EntityContext context;
   private DataSource dataSource;


   /**
    * Returns a SalesRepId for this Customer
    * @exception RemoteException
    */

   public String getSalesRepId() {

      return salesRepId;
   }

    /**
     * Returns a Name for this Customer
     * @exception RemoteException
     */

   public String getName() {

      return name;
   }

   /**
    * Sets a SalesRepId for this Customer
    * @param salesRepId
    * @exception RemoteException
    */

   public void setSalesRepId(String salesRepId) {

      this.salesRepId = salesRepId;
   }

    /**
     * Sets the name for this Customer
     * @param name
     * @exception RemoteException
     */

   public void setName(String name) {

      this.name = name;
   }


   public String ejbCreate(String customerId, String salesRepId,
       String name) throws CreateException {

       try {
          insertCustomer(customerId, salesRepId, name);
       } catch (Exception ex) {
           throw new EJBException("ejbCreate: " +
              ex.getMessage());
       }

       this.customerId = customerId;
       this.salesRepId = salesRepId;
       this.name = name;

       return customerId;
   }

   public String ejbFindByPrimaryKey(String primaryKey)
      throws FinderException {

      boolean result;

      try {
         result = selectByPrimaryKey(primaryKey);
       } catch (Exception ex) {
           throw new EJBException("ejbFindByPrimaryKey: " +
              ex.getMessage());
       }

      if (result) {
         return primaryKey;
      }
      else {
         throw new ObjectNotFoundException
            ("Row for id " + primaryKey + " not found.");
      }
   }

   public Collection ejbFindBySalesRep(String salesRepId)
      throws FinderException {

      Collection result;

      try {
         result = selectBySalesRep(salesRepId);
       } catch (Exception ex) {
           throw new EJBException("ejbFindBySalesRep: " +
              ex.getMessage());
       }
       return result;
   }

   public void ejbRemove() {

      try {
         deleteCustomer(customerId);
       } catch (Exception ex) {
           throw new EJBException("ejbRemove: " +
              ex.getMessage());
       }
   }

   public void setEntityContext(EntityContext context) {

      this.context = context;
      try {
        InitialContext ic = new InitialContext();
        dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/bmp-salesrepDB");
      } catch (Exception ex) {
          throw new EJBException("Unable to connect to database. " +
             ex.getMessage());
      }
   }

   public void unsetEntityContext() {
      this.context = null;
   }

   public void ejbActivate() {

      customerId = (String)context.getPrimaryKey();
   }

   public void ejbPassivate() {

      customerId = null;
   }

   public void ejbLoad() {

      try {
         loadCustomer();
       } catch (Exception ex) {
           throw new EJBException("ejbLoad: " +
              ex.getMessage());
       }
   }

   public void ejbStore() {

      try {
         storeCustomer();
       } catch (Exception ex) {
           throw new EJBException("ejbStore: " +
              ex.getMessage());
       }
   }

   public void ejbPostCreate(String customerId, String salesRepId,
        String name) { }

/*********************** Database Routines *************************/

   private void insertCustomer (String customerId, String salesRepId,
       String name) throws SQLException {

          Connection con = dataSource.getConnection();
          String insertStatement =
                "insert into customer values ( ? , ? , ? )";
          PreparedStatement prepStmt =
                con.prepareStatement(insertStatement);

          prepStmt.setString(1, customerId);
          prepStmt.setString(2, salesRepId);
          prepStmt.setString(3, name);

          prepStmt.executeUpdate();
          prepStmt.close();
          con.close();
   }

   private boolean selectByPrimaryKey(String primaryKey)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select customerid " +
            "from customer where customerid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, primaryKey);

      ResultSet rs = prepStmt.executeQuery();
      boolean result = rs.next();
      prepStmt.close();
      con.close();
      return result;
   }

   private Collection selectBySalesRep(String salesRepId)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select customerid " +
            "from customer where salesrepid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, salesRepId);
      ResultSet rs = prepStmt.executeQuery();
      ArrayList a = new ArrayList();

      while (rs.next()) {
         String id = rs.getString(1);
         a.add(id);
      }

      prepStmt.close();
      con.close();
      return a;
   }

   private void deleteCustomer(String customerId) throws SQLException {

      Connection con = dataSource.getConnection();
      String deleteStatement =
            "delete from customer  " +
            "where customerid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(deleteStatement);

      prepStmt.setString(1, customerId);
      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }

   private void loadCustomer() throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select customerid, salesRepid, name " +
            "from customer where customerid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, customerId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         customerId = rs.getString(1);
         salesRepId = rs.getString(2);
         name = rs.getString(3);
         prepStmt.close();
      }
      else {
         prepStmt.close();
         throw new NoSuchEntityException("Row for customerId " + customerId +
            " not found in database.");
      }
      con.close();
   }

   private void storeCustomer() throws SQLException {

      Connection con = dataSource.getConnection();
      String updateStatement =
            "update customer " +
            "set salesRepid = ? , name = ? " +
            "where customerid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setString(1, salesRepId);
      prepStmt.setString(2, name);
      prepStmt.setString(3, customerId);
      int rowCount = prepStmt.executeUpdate();
      prepStmt.close();
      con.close();

      if (rowCount == 0) {
         throw new EJBException("Storing row for customerId " +
            customerId + " failed.");
      }
   }

} // CustomerBean
