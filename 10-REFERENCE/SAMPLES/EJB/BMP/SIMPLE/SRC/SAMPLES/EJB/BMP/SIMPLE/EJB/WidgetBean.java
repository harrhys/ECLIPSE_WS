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

public class WidgetBean implements EntityBean {

   private String widgetId;
   private String description;
   private double price;
   private EntityContext context;
   private DataSource dataSource;


   public String getDescription() {

      return description;
   }

   public double getPrice() {

      return price;
   }

   public String ejbCreate(String widgetId, String description,
      double price)
      throws CreateException {

      try {
         insertRow(widgetId, description, price);
      } catch (Exception ex) {
          throw new EJBException("ejbCreate: " +
             ex.getMessage());
      }

      this.widgetId = widgetId;
      this.description = description;
      this.price = price;

      return widgetId;
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

   public void ejbRemove() {

      try {
         deleteRow(widgetId);
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

      widgetId = (String)context.getPrimaryKey();
   }

   public void ejbPassivate() {

      widgetId = null;
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


   public void ejbPostCreate(String widgetId, String description,
      double price) { }


/*********************** Database Routines *************************/

   private void insertRow (String widgetId, String description,
                           double price) throws SQLException {

      Connection con = dataSource.getConnection();
          String insertStatement =
                "insert into widget values ( ? , ? , ? )";
          PreparedStatement prepStmt =
                con.prepareStatement(insertStatement);

          prepStmt.setString(1, widgetId);
          prepStmt.setString(2, description);
          prepStmt.setDouble(3, price);

          prepStmt.executeUpdate();
          prepStmt.close();
          con.close();
   }

   private void deleteRow(String widgetId) throws SQLException {

      Connection con = dataSource.getConnection();
      String deleteStatement =
            "delete from widget where widgetid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(deleteStatement);

      prepStmt.setString(1, widgetId);
      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }

   private boolean selectByPrimaryKey(String primaryKey)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select widgetid " +
            "from widget where widgetid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, primaryKey);

      ResultSet rs = prepStmt.executeQuery();
      boolean result = rs.next();
      prepStmt.close();
      con.close();
     return result;
   }

   private void loadRow() throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select description, price " +
            "from widget where widgetid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, this.widgetId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         this.description = rs.getString(1);
         this.price = rs.getDouble(2);
         prepStmt.close();
      }
      else {
         prepStmt.close();
         throw new NoSuchEntityException("Row for widgetId " + widgetId +
            " not found in database.");
      }
      con.close();
   }


   private void storeRow() throws SQLException {

      Connection con = dataSource.getConnection();
      String updateStatement =
            "update widget set description =  ? , " +
            "price = ? " +
            "where widgetid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setString(1, description);
      prepStmt.setDouble(2, price);
      prepStmt.setString(3, widgetId);
      int rowCount = prepStmt.executeUpdate();
      prepStmt.close();
      con.close();

      if (rowCount == 0) {
         throw new EJBException("Storing row for widgetId " +
            widgetId + " failed.");
      }
   }

} // WidgetBean
