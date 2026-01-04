/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package samples.jdbc.transactions.ejb;
import java.util.*;
import javax.ejb.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/** 
 * A simple session bean for the jdbc-transactions application. This bean implements all 
 * business method as declared by the remote interface.  
 */

public class WarehouseBean implements SessionBean {

   private SessionContext context;
   private Connection con;
   private String dbName = "java:comp/env/jdbc/jdbc-transactions";

   /** 
     * Updates the received for particular product inside the particular order.  
	 * sets the status to shipped 
	 * @param productID.  
	 * @param orderID 
	 * @param quantity the received quantity 
	 */
   public void ship (String productId, String orderId, int quantity) {

      try {
         con.setAutoCommit(false);
         updateOrderItem(productId, orderId);
         updateInventory(productId, quantity);
         con.commit();
      } catch (Exception ex) {
          try {
             con.rollback();
             throw new EJBException("Transaction failed: " + ex.getMessage());
          } catch (SQLException sqx) {
              throw new EJBException("Rollback failed: " + sqx.getMessage());
          }
      }
   } 

  /** 
    * Sets the get the status for the particular product inside the particular order.  
	* @param productID.  
	* @param orderID 
	* @return the status 
	*/

   public String getStatus(String productId, String orderId) {

      try {
         return selectStatus(productId, orderId);
      } catch (SQLException ex) {
          throw new EJBException 
             ("Unable to fetch status due to SQLException: " 
             + ex.getMessage());
      }
   }

  /** 
    * creates a bean, required by EJB spec
	* @exception throws CreateException.  
	*/

   public void ejbCreate() throws CreateException {

      try {
         makeConnection();
      } catch (Exception ex) {
          throw new CreateException(ex.getMessage());
      }
     
   }

  /** 
    * Removes a bean. Required by EJB spec.  
	* @exception throws CreateException.  
	*/
   public void ejbRemove()  {

      try {
         con.close();
      } catch (SQLException ex) {
          throw new EJBException(ex.getMessage());
      }
   }

  /** 
    * The activate method is called when the instance is activated from its "passive" state. The instance 
	* should acquire any resource that it has released earlier in the ejbPassivate() method.
	*/
   public void ejbActivate()  {

      try {
         makeConnection();
      } catch (Exception ex) {
          throw new EJBException(ex.getMessage());
      }
   }

   /**
     * The passivate method is called before the instance enters the "passive" state. 
	 * The instance should release any resources that it can re-acquire later in the ejbActivate() 
	 * method. 
	 */
   public void ejbPassivate()  {

      try {
         con.close();
      } catch (SQLException ex) {
          throw new EJBException(ex.getMessage());
      }
   }

   /** 
     * Set the associated session context. The container calls this method after the instance 
	 * creation. 
	 * @param context SessionContex
	 */
   public void setSessionContext(SessionContext context) {

      this.context = context;
   }

   /** 
     * Required by EJB spec
	 * creation. 
	 * @param context SessionContex
	 */

   public WarehouseBean() {}


/********************* Database Routines ***********************/

   private String selectStatus(String productId, String orderId)
      throws SQLException {

      String result;

      String selectStatement =
            "select status " +
            "from order_item where product_id = ? " +
            "and order_id = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, productId);
      prepStmt.setString(2, orderId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         result = rs.getString(1);
      }
      else {
         result = "No rows found.";
      }

      prepStmt.close();
      return result;
   }

   private void updateOrderItem(String productId, String orderId)
      throws SQLException {

      String updateStatement =
            "update order_item set status =  'shipped' " +
            "where product_id = ? " +
            "and order_id = ?";

      PreparedStatement prepStmt = 
            con.prepareStatement(updateStatement);

      prepStmt.setString(1, productId);
      prepStmt.setString(2, orderId);
      prepStmt.executeUpdate();
      prepStmt.close();
   }

   private void updateInventory(String productId, int quantity)
      throws SQLException {

      String updateStatement =
            "update inventory " + 
            "set quantity = quantity - ? " +
            "where product_id = ?";

      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setInt(1, quantity);
      prepStmt.setString(2, productId);
      prepStmt.executeUpdate();
      prepStmt.close();
   }

   private void makeConnection() 
      throws NamingException, SQLException {

      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup(dbName);
      con =  ds.getConnection();
   }

} // WarehouseBean 
