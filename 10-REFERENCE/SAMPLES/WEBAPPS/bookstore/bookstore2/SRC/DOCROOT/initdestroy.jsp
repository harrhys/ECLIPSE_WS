<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="samples.webapps.bookstore.bookstore2.ejb.database.*" %>
<%@ page errorPage="errorpage.jsp" %>
<%@ page import="javax.ejb.*, javax.naming.*, javax.rmi.PortableRemoteObject, java.rmi.RemoteException, samples.webapps.bookstore.bookstore2.ejb.database.BookDB, samples.webapps.bookstore.bookstore2.ejb.database.BookDBEJB, samples.webapps.bookstore.bookstore2.ejb.database.BookDBEJBHome" %>
<%!
    private BookDBEJB bookDBEJB;

    public void jspInit() {	

      bookDBEJB =
          (BookDBEJB)getServletContext().getAttribute("bookDBEJB");

      if (bookDBEJB == null) {
        try {
          InitialContext ic = new InitialContext();
          Object objRef = ic.lookup("java:comp/env/ejb/BookDBEJB");
          BookDBEJBHome home = (BookDBEJBHome)PortableRemoteObject.narrow(objRef, BookDBEJBHome.class);
          bookDBEJB = home.create();
          getServletContext().setAttribute("bookDBEJB", bookDBEJB);
        } catch (RemoteException ex) {
           System.out.println("Couldn't create database bean." + ex.getMessage());
        } catch (CreateException ex) {
            System.out.println("Couldn't create database bean." + ex.getMessage());
        } catch (NamingException ex) {
            System.out.println("Unable to lookup home: "+ "java:comp/env/ejb/BookDBEJB."+ ex.getMessage());
        }
      }	
    }

   public void jspDestroy() {      
      bookDBEJB = null;
 	 }
%>
