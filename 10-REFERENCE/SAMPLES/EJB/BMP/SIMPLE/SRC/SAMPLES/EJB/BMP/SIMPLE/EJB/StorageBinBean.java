/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.ejb.bmp.simple.ejb;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;

public class StorageBinBean implements EntityBean {

   private String storageBinId;
   private String widgetId;
   private int quantity;
   private EntityContext context;
   private DataSource dataSource;


   public String getWidgetId() {

      return widgetId;
   }

   public int getQuantity() {

      return quantity;
   }

   public String ejbCreate(String storageBinId, String widgetId,
      int quantity)
      throws CreateException {

      try {
         insertRow(storageBinId, widgetId, quantity);
      } catch (Exception ex) {
          throw new EJBException("ejbCreate: " +
             ex.getMessage());
      }

      this.storageBinId = storageBinId;
      this.widgetId = widgetId;
      this.quantity = quantity;

      return storageBinId;
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

   public String ejbFindByWidgetId(String widgetId)
      throws FinderException {

      String storageBinId;

      try {
         storageBinId = selectByWidgetId(widgetId);
       } catch (Exception ex) {
           throw new EJBException("ejbFindByWidgetId: " +
              ex.getMessage());
       }
       return storageBinId;
   }

   public void ejbRemove() {

      try {
         deleteRow(storageBinId);
       } catch (Exception ex) {
           throw new EJBException("ejbRemove: " +
              ex.getMessage());
       }
   }

   public void setEntityContext(EntityContext context) {

      this.context = context;
      try {
        InitialContext ic = new InitialContext();
        dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/bmp-simple");
      } catch (Exception ex) {
          throw new EJBException("Unable to connect to database. " +
             ex.getMessage());
      }
   }

   public void unsetEntityContext() {
      this.context = null;
   }

   public void ejbActivate() {

      storageBinId = (String)context.getPrimaryKey();
   }

   public void ejbPassivate() {

      storageBinId = null;
   }

   public void ejbLoad() {

      try {
         loadRow();
       } catch (Exception ex) {
           throw new EJBException("ejbLoad: " +
              ex.getMessage());
       }
   }

   public void ejbStore() {

      try {
         storeRow();
       } catch (Exception ex) {
           throw new EJBException("ejbLoad: " +
              ex.getMessage());
       }
   }


   public void ejbPostCreate(String storageBinId, String widgetId,
      int quantity) { }


/*********************** Database Routines *************************/
   private void insertRow (String storageBinId, String widgetId,
                           int quantity) throws SQLException {

      Connection con = dataSource.getConnection();
      String insertStatement =
            "insert into storagebin values ( ? , ? , ? )";
      PreparedStatement prepStmt =
            con.prepareStatement(insertStatement);

      prepStmt.setString(1, storageBinId);
      prepStmt.setString(2, widgetId);
      prepStmt.setInt(3, quantity);

      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }

   private void deleteRow(String storageBinId) throws SQLException {

      Connection con = dataSource.getConnection();
      String deleteStatement =
            "delete from storagebin where storagebinid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(deleteStatement);

      prepStmt.setString(1, storageBinId);
      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }

   private boolean selectByPrimaryKey(String primaryKey)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select storagebinid " +
            "from storagebin where storagebinid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, primaryKey);

      ResultSet rs = prepStmt.executeQuery();
      boolean result = rs.next();
      prepStmt.close();
      con.close();
      return result;
   }


   private String selectByWidgetId(String widgetId)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String storageBinId;

      String selectStatement =
            "select storagebinid " +
            "from storagebin where widgetid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, widgetId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         storageBinId = rs.getString(1);
      }
      else {
         storageBinId = null;
      }

      prepStmt.close();
      con.close();
      return storageBinId;
   }

   private void loadRow() throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select widgetId, quantity " +
            "from storagebin where storagebinid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, this.storageBinId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         this.widgetId = rs.getString(1);
         this.quantity = rs.getInt(2);
         prepStmt.close();
      }
      else {
         prepStmt.close();
         throw new NoSuchEntityException("Row for storageBinId " + storageBinId +
            " not found in database.");
      }
      con.close();
   }


   private void storeRow() throws SQLException {

      Connection con = dataSource.getConnection();
      String updateStatement =
            "update storagebin set widgetId =  ? , " +
            "quantity = ? " +
            "where storagebinid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setString(1, widgetId);
      prepStmt.setInt(2, quantity);
      prepStmt.setString(3, storageBinId);
      int rowCount = prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
      if (rowCount == 0) {
         throw new EJBException("Storing row for storageBinId " +
            storageBinId + " failed.");
      }
   }

} // StorageBinBean
