package samples.ejb.bmp.order.ejb;
/**
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
import samples.ejb.bmp.order.util.*;

/**
 *
 *  This entity bean  represents the order business object and can possess LineItem objects.
 *  Here, the LineItem Object is not represented by not another entity bean but a java helper class.
 *
 *
 * @see Order
 * @see OrderHome
 */
public class OrderBean implements EntityBean {

   private String orderId;
   private ArrayList lineItems;
   private String customerId;
   private double totalPrice;
   private String status;
   private EntityContext context;
   private DataSource dataSource;


/**
 * Returns the LineItems that make up this particular Order.
 * @exception RemoteException
 */
   public ArrayList getLineItems() {
      return lineItems;
   }


/**
 * Returns the CustomerId for this particular Order.
 * @exception RemoteException
 */
   public String getCustomerId() {
      return customerId;
   }

   /**
    * Returns the Total Price for this particular Order.
    * @exception RemoteException
    */

   public double getTotalPrice() {

      return totalPrice;
   } public String getStatus() { return status;
   }

   public String ejbCreate(String orderId, String customerId,
       String status, double totalPrice, ArrayList lineItems)
       throws CreateException {

       try {
          insertOrder(orderId, customerId, status, totalPrice);
          for (int i = 0; i < lineItems.size(); i++) {
             LineItem item = (LineItem)lineItems.get(i);
             insertItem(item);
          }
       } catch (Exception ex) {
           System.out.println("Exception in EJBCreate!!!");
           ex.printStackTrace();
           throw new EJBException("ejbCreate: " +
              ex.getMessage());
       }

       this.orderId = orderId;
       this.customerId = customerId;
       this.status = status;
       this.totalPrice = totalPrice;
       this.lineItems = lineItems ;
       return orderId;
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

   public Collection ejbFindByProductId(String productId)
      throws FinderException {

      Collection result;

      try {
         result = selectByProductId(productId);
       } catch (Exception ex) {
           throw new EJBException("ejbFindByProductId " +
              ex.getMessage());
       }
       return result;
   }

   public void ejbRemove() {

      try {
         deleteOrder(orderId);
         deleteItems(orderId);
       } catch (Exception ex) {
           throw new EJBException("ejbRemove: " +
              ex.getMessage());
       }
   }

   public void setEntityContext(EntityContext context) {

      this.context = context;
      try {
        InitialContext ic = new InitialContext();
        dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/bmp-orderDB");
      } catch (Exception ex) {
          throw new EJBException("Unable to connect to database. " +
             ex.getMessage());
      }
   }

   public void unsetEntityContext() {
       this.context = null;
   }

   public void ejbActivate() {

      orderId = (String)context.getPrimaryKey();
   }

   public void ejbPassivate() {

      orderId = null;
   }

   public void ejbLoad() {

      try {
         loadOrder();
         loadItems();
       } catch (Exception ex) {
           throw new EJBException("ejbLoad: " +
              ex.getMessage());
       }
   }

   public void ejbStore() {

      try {
         storeOrder();
         for (int i = 0; i < lineItems.size(); i++) {
            LineItem item = (LineItem)lineItems.get(i);
            storeItem(item);
         }
       } catch (Exception ex) {
           throw new EJBException("ejbLoad: " +
              ex.getMessage());
       }
   }

   public void ejbPostCreate(String orderId, String customerId,
       String status, double totalPrice, ArrayList lineItems) { }

/*********************** Database Routines *************************/

   private void insertOrder (String orderId, String customerId,
       String status, double totalPrice) throws SQLException {

          Connection con = dataSource.getConnection();
          String insertStatement =
                "insert into orders values ( ? , ? , ? , ? )";
          PreparedStatement prepStmt =
                con.prepareStatement(insertStatement);

          prepStmt.setString(1, orderId);
          prepStmt.setString(2, customerId);
          prepStmt.setDouble(3, totalPrice);
          prepStmt.setString(4, status);

          prepStmt.executeUpdate();
          prepStmt.close();
          con.close();
   }

   private void insertItem(LineItem lineItem)
       throws SQLException {
          Connection con = dataSource.getConnection();
          String insertStatement =
                "insert into lineitems values ( ? , ? , ? , ? , ? )";
          PreparedStatement prepStmt =
                con.prepareStatement(insertStatement);

          prepStmt.setInt(1, lineItem.getItemNo());
          prepStmt.setString(2, lineItem.getOrderId());
          prepStmt.setString(3, lineItem.getProductId());
          prepStmt.setDouble(4, lineItem.getUnitPrice());
          prepStmt.setInt(5, lineItem.getQuantity());

          prepStmt.executeUpdate();
          prepStmt.close();
          con.close();
   }

   private boolean selectByPrimaryKey(String primaryKey)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select orderid " +
            "from orders where orderid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, primaryKey);

      ResultSet rs = prepStmt.executeQuery();
      boolean result = rs.next();
      prepStmt.close();
      con.close();
      return result;
   }

   private Collection selectByProductId(String productId)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select distinct orderid " +
            "from lineitems where productid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, productId);
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

   private void deleteItems(String orderId) throws SQLException {

      Connection con = dataSource.getConnection();
      String deleteStatement =
            "delete from lineitems  " +
            "where orderid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(deleteStatement);

      prepStmt.setString(1, orderId);
      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }

   private void deleteOrder(String orderId) throws SQLException {

      Connection con = dataSource.getConnection();
      String deleteStatement =
            "delete from orders  " +
            "where orderid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(deleteStatement);

      prepStmt.setString(1, orderId);
      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }

   private void loadOrder() throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select customerid, totalprice, status " +
            "from orders where orderid =?";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, orderId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         customerId = rs.getString(1);
         totalPrice = rs.getDouble(2);
         status = rs.getString(3);
         prepStmt.close();
      }
      else {
         prepStmt.close();
         throw new NoSuchEntityException("Row for orderId " + orderId +
            " not found in database.");
      }
      con.close();
   }

   private void loadItems() throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select itemno, productid, unitprice, quantity " +
            "from  lineitems  where orderid=? " +
            "order by itemno";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, orderId);
      ResultSet rs = prepStmt.executeQuery();

      lineItems.clear();

      int count = 0;
      while (rs.next()) {
         int itemNo = rs.getInt(1);
         String productId = rs.getString(2);
         double unitPrice = rs.getDouble(3);
         int quantity = rs.getInt(4);
         //DEBUGGING
          LineItem testLineItem = new LineItem(productId, quantity, unitPrice, itemNo, orderId);
         // original state lineItems.set(itemNo - 1, testLineItem);
         //LineItem disposed = (LineItem) lineItems.set(itemNo - 1, testLineItem);

          lineItems.add(testLineItem);

         /******************************************
         lineItems.set(itemNo - 1,
            new LineItem(productId, quantity, unitPrice, itemNo, orderId));
         ****************************************************/

         count++;
      }
      prepStmt.close();
      con.close();

      if (count == 0) {
         throw new NoSuchEntityException("No items for orderId " + orderId +
            " found in database.");
      }
   }

   private void storeOrder() throws SQLException {

      Connection con = dataSource.getConnection();
      String updateStatement =
            "update orders set customerid =  ? ," +
            "totalprice = ? , status = ? " +
            "where orderid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setString(1, customerId);
      prepStmt.setDouble(2, totalPrice);
      prepStmt.setString(3, status);
      prepStmt.setString(4, orderId);
      int rowCount = prepStmt.executeUpdate();
      prepStmt.close();
      con.close();

      if (rowCount == 0) {
         throw new EJBException("Storing row for orderId " +
            orderId + " failed.");
      }
   }

   private void storeItem(LineItem item) throws SQLException {

      Connection con = dataSource.getConnection();
      String updateStatement =
            "update lineitems set productid =  ? ," +
            "unitprice = ? , quantity = ? " +
            "where orderid = ? and itemno = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setString(1, item.getProductId());
      prepStmt.setDouble(2, item.getUnitPrice());
      prepStmt.setInt(3, item.getQuantity());
      prepStmt.setString(4, orderId);
      prepStmt.setInt(5, item.getItemNo());
      int rowCount = prepStmt.executeUpdate();
      prepStmt.close();
      con.close();

      if (rowCount == 0) {
         throw new EJBException("Storing itemNo " + item.getItemNo() +
            "for orderId " + orderId + " failed.");
      }
   }

} // OrderBean
