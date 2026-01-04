/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.webapps.bookstore.bookstore1.database;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

import samples.webapps.bookstore.bookstore1.exception.*;
import samples.webapps.bookstore.bookstore1.cart.*;

public class BookDB {

  private ArrayList books;
  Connection con;
  private boolean conFree = true;
  private String dbName = "java:comp/env/jdbc/BookDB";

  public BookDB () throws Exception {
    try  {               
      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup(dbName);
      con =  ds.getConnection();     
    } catch (Exception ex) {
      throw new Exception("Couldn't open connection to database: " + ex.getMessage());
    }       
  }

  public void remove () {
    try {
        con.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
  }

  protected synchronized Connection getConnection() {
    while (conFree == false) {
       try {
          wait();
       } catch (InterruptedException e) {
       }
    }
    conFree = false;
    notify();
    return con;
  }

  protected synchronized void releaseConnection() {
    while (conFree == true) {
       try {
          wait();
       } catch (InterruptedException e) {
       }
    }
    conFree = true;
    notify();
   }

  public int getNumberOfBooks() throws BooksNotFoundException {
    books = new ArrayList();
    try {
      String selectStatement = "select * " + "from books";
      getConnection();
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
        BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), 
          rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getInt(8));
        if (rs.getInt(8) > 0)
          books.add(bd);
      }
      prepStmt.close();
    } catch (SQLException ex) {
      throw new BooksNotFoundException(ex.getMessage());
    }
    releaseConnection();
    return books.size();
  }

  public Collection getBooks() throws BooksNotFoundException {
    books = new ArrayList();
    try {
      String selectStatement = "select * " + "from books";
      getConnection();
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
        BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3),
          rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getInt(8));
        if (rs.getInt(8) > 0)
          books.add(bd);
      }
      prepStmt.close();
    } catch (SQLException ex) {
      throw new BooksNotFoundException(ex.getMessage());
    }
	releaseConnection();
    Collections.sort(books);
    return books;
  }

  public BookDetails getBookDetails(String bookId) throws BookNotFoundException {
    try {
      String selectStatement = "select * " + "from books where id = ? ";
      getConnection();
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      prepStmt.setString(1, bookId);
      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
        BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), 
          rs.getString(4), rs.getFloat(5), rs.getInt(6), rs.getString(7), rs.getInt(8));
        prepStmt.close();
        releaseConnection();                
        return bd;
      }
      else {          
        prepStmt.close();
        releaseConnection();
        throw new BookNotFoundException("Couldn't find book: " + bookId);
      }
    } catch (SQLException ex) {
      releaseConnection();
      throw new BookNotFoundException("Couldn't find book: " + bookId + " " + ex.getMessage());
    }
  }

  public void buyBooks(ShoppingCart cart) throws OrderException{
    Collection items = cart.getItems();
    Iterator i = items.iterator();
    try {
      getConnection();        
      con.setAutoCommit(false); 
      while (i.hasNext()) {
        ShoppingCartItem sci = (ShoppingCartItem)i.next();
        BookDetails bd = (BookDetails)sci.getItem();
        String id = bd.getBookId();
        int quantity = sci.getQuantity();
        buyBook(id, quantity);
      }
      con.commit();
      con.setAutoCommit(true);
      releaseConnection();     
    } catch (Exception ex) {     
      try {    
        con.rollback();
        releaseConnection();       
        throw new OrderException("Transaction failed: " + ex.getMessage());  
      } catch (SQLException sqx) { 
        releaseConnection();    
        throw new OrderException("Rollback failed: " + sqx.getMessage());        
      }        
    }       
  }


  public void buyBook(String bookId, int quantity) throws OrderException {  
    try {
      String selectStatement = "select * " + "from books where id = ? ";
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      prepStmt.setString(1, bookId);
      ResultSet rs = prepStmt.executeQuery();
      if (rs.next()) {
        int inventory = rs.getInt(8);
        prepStmt.close();              
        if ((inventory - quantity) >= 0) {       
          String updateStatement =
                  "update books set inventory = inventory - ? where id = ?";
          prepStmt = con.prepareStatement(updateStatement);
          prepStmt.setInt(1, quantity);
          prepStmt.setString(2, bookId);
          prepStmt.executeUpdate();
          prepStmt.close();
        } else
          throw new OrderException("Not enough of " + bookId + " in stock to complete order.");
      }
    } catch (Exception ex) {
      throw new OrderException("Couldn't purchase book: " + bookId + ex.getMessage());
    }
  }
}
