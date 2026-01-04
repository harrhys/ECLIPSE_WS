/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.webapps.bookstore.bookstore2.ejb.database;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import javax.ejb.*;
import samples.webapps.bookstore.bookstore2.ejb.exception.*;


public class BookDBEJBImpl implements SessionBean {   		
   private ArrayList books = null;
   private Connection con = null;
   private String dbName = "java:comp/env/jdbc/BookDB";

   // implementation of create and remove remote methods

  public void ejbCreate() throws CreateException {
    try  {               
      InitialContext ic = new InitialContext();
      DataSource ds = (DataSource) ic.lookup(dbName);
      con =  ds.getConnection();     
    } catch (Exception ex) {
      throw new CreateException("Couldn't create bean: " + ex.getMessage());
    }
    books = new ArrayList();
  }

  public void ejbRemove() throws EJBException {
    try {
      con.close();
    } catch (SQLException ex) {
      throw new EJBException("unsetEntityContext: " + ex.getMessage());
    }
    con = null;
    books = null;
  }
   
  public BookDBEJBImpl() {}
  public void ejbActivate() {}
  public void ejbPassivate() {}
  public void setSessionContext(SessionContext sc) {}

    // remote methods
 
  public int getNumberOfBooks() throws BooksNotFoundException {
    books = new ArrayList();
    try {
      String selectStatement = "select * " + "from books";
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
          BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
              rs.getFloat(5), rs.getInt(6), rs.getString(7));
              books.add(bd);
      }
      prepStmt.close();
    } catch (SQLException ex) {
      throw new BooksNotFoundException("Couldn't find books: " +  ex.getMessage());
    }
    return books.size();
  }
    
  public Collection getBooks() throws BooksNotFoundException {
    books = new ArrayList();
    try {
      String selectStatement = "select * " + "from books";
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);
      ResultSet rs = prepStmt.executeQuery();
      while (rs.next()) {
        BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
          rs.getFloat(5), rs.getInt(6), rs.getString(7));
        books.add(bd);
      }
      prepStmt.close();
    } catch (SQLException ex) {
      throw new BooksNotFoundException("Couldn't find books: " +  ex.getMessage());
    }
    
    Collections.sort(books);
    return books;
  }
    
  public BookDetails getBookDetails(String bookId) throws BookNotFoundException {
    try {
      String selectStatement = "select * " + "from books where id = ? ";
      PreparedStatement prepStmt = con.prepareStatement(selectStatement);

      prepStmt.setString(1, bookId);

      ResultSet rs = prepStmt.executeQuery();

      if (rs.next()) {
        BookDetails bd = new BookDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
          rs.getFloat(5), rs.getInt(6), rs.getString(7));
        prepStmt.close();
        return bd;

      } else {					
        prepStmt.close();
        throw new BookNotFoundException("Couldn't find book: " + bookId);
      }
    } catch (SQLException ex) {
      throw new BookNotFoundException("Couldn't find book: " + bookId + ex.getMessage());
    }
  }
}



