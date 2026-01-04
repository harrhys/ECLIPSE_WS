package samples.ejb.bmp.salesrep.ejb;
/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
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
import javax.rmi.PortableRemoteObject;

/**
 *
  *  This entity bean  represents the SalesRep business object and can possess multiple customers.  However,
  *  each customer can only have one sales representative.  This example shows
  *  the mapping of a one-to-many relationship between database tables to a pair of entity beans with
  *  bean-managed persistence.
  *
  * @see SalesRep
  * @see SalesRepHome
  */
public class SalesRepBean implements EntityBean {

   private String salesRepId;
   private String name;
   private ArrayList customerIds;
   private EntityContext context;
   private CustomerHome customerHome;
   private DataSource dataSource;
   /**
    * Returns an ArrayList of CustomersIds associated with this SalesRep
    * @exception RemoteException
    *
    */

   public ArrayList getCustomerIds() {

      return customerIds;
   }

   /**
    * Returns the name of this SalesRep
    * @exception RemoteException
    *
    */

   public String getName() {

      return name;
   }

   /**
    * Sets the name of this SalesRep
    * @param name
    * @exception RemoteException
    *
    */

   public void setName(String name) {

      this.name = name;
   }

   public String ejbCreate(String salesRepId, String name)
       throws CreateException {

       try {
          insertSalesRep(salesRepId, name);
       } catch (Exception ex) {
           throw new EJBException("ejbCreate: " +
              ex.getMessage());
       }

       this.salesRepId = salesRepId;
       this.name = name;
       return salesRepId;
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
         deleteSalesRep(salesRepId);
       } catch (Exception ex) {
           throw new EJBException("ejbRemove: " +
              ex.getMessage());
       }
   }

   public void setEntityContext(EntityContext context) {

      this.context = context;
      customerIds = new ArrayList();

      try {
         InitialContext ic = new InitialContext();
         dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/bmp-salesrepDB");
         Context initial = new InitialContext();
         Object objref = initial.lookup("java:comp/env/ejb/Customer");

         customerHome = (CustomerHome)PortableRemoteObject.narrow(objref,
                                           CustomerHome.class);
      } catch (Exception ex) {
          throw new EJBException("setEntityContext: " +
             ex.getMessage());
      }
   }

   public void unsetEntityContext() {
   }

   public void ejbActivate() {

      salesRepId = (String)context.getPrimaryKey();
   }

   public void ejbPassivate() {

      salesRepId = null;
   }

   public void ejbLoad() {

      try {
         loadSalesRep();
         loadCustomerIds();
       } catch (Exception ex) {
           throw new EJBException("ejbLoad: " +
              ex.getMessage());
       }
   }

   private void loadCustomerIds() {

      customerIds.clear();

      try {
         Collection c = customerHome.findBySalesRep(salesRepId);
         Iterator i=c.iterator();
         while (i.hasNext()) {
            Customer customer = (Customer)i.next();
            String id = (String)customer.getPrimaryKey();
            customerIds.add(id);
         }
      } catch (Exception ex) {
          throw new EJBException("Exception in loadCustomerIds: " +
             ex.getMessage());
       }
   }

   public void ejbStore() {

      try {
         storeSalesRep();
       } catch (Exception ex) {
           throw new EJBException("ejbStore: " +
              ex.getMessage());
       }
   }

   public void ejbPostCreate(String salesRepId, String name) { }

/*********************** Database Routines *************************/

   private void insertSalesRep (String salesRepId, String name)
       throws SQLException {

          Connection con = dataSource.getConnection();
          String insertStatement =
                "insert into salesrep values ( ? , ? )";
          PreparedStatement prepStmt =
                con.prepareStatement(insertStatement);

          prepStmt.setString(1, salesRepId);
          prepStmt.setString(2, name);

          prepStmt.executeUpdate();
          prepStmt.close();
          con.close();
   }

   private boolean selectByPrimaryKey(String primaryKey)
      throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select salesrepid " +
            "from salesrep where salesrepid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);
      prepStmt.setString(1, primaryKey);

      ResultSet rs = prepStmt.executeQuery();
      boolean result = rs.next();
      prepStmt.close();
      con.close();
      return result;
   }

   private void deleteSalesRep(String salesRepId) throws SQLException {

      Connection con = dataSource.getConnection();
      String deleteStatement =
            "delete from salesrep  " +
            "where salesrepid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(deleteStatement);

      prepStmt.setString(1, salesRepId);
      prepStmt.executeUpdate();
      prepStmt.close();
      con.close();
   }

   private void loadSalesRep() throws SQLException {

      Connection con = dataSource.getConnection();
      String selectStatement =
            "select name " +
            "from salesRep where salesrepid = ? ";
      PreparedStatement prepStmt =
            con.prepareStatement(selectStatement);

      prepStmt.setString(1, salesRepId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
         name = rs.getString(1);
         prepStmt.close();
      }
      else {
         prepStmt.close();
         throw new NoSuchEntityException("Row for salesRepId " + salesRepId +
            " not found in database.");
      }
      con.close();
   }

   private void storeSalesRep() throws SQLException {

      Connection con = dataSource.getConnection();
      String updateStatement =
            "update salesrep set name =  ? " +
            "where salesrepid = ?";
      PreparedStatement prepStmt =
            con.prepareStatement(updateStatement);

      prepStmt.setString(1, name);
      prepStmt.setString(2, salesRepId);
      int rowCount = prepStmt.executeUpdate();
      prepStmt.close();
      con.close();

      if (rowCount == 0) {
         throw new EJBException("Storing row for salesRepId " +
            salesRepId + " failed.");
      }
   }


} // SalesRepBean
