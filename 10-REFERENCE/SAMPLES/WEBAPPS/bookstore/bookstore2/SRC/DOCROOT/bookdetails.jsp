<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ include file="initdestroy.jsp" %>
<%@ page import="java.util.*" %>
<%
   ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
%>

<jsp:useBean id="bookDB" class="samples.webapps.bookstore.bookstore2.ejb.database.BookDB" scope="page" >
  <jsp:setProperty name="bookDB" property="database" value="<%=bookDBEJB%>" />
</jsp:useBean>

<jsp:useBean id="currency" class="samples.webapps.bookstore.bookstore2.web.util.Currency" scope="session">
  <jsp:setProperty name="currency" property="locale" value="<%=request.getLocale()%>"/>
</jsp:useBean>

<html>
<head><title><%=messages.getString("TitleBookDescription")%></title></head>

<%@ include file="banner.jsp" %>
<br>&nbsp;
<%
    //Get the identifier of the book to display
    String bookId = request.getParameter("bookId");
    bookDB.setBookId(bookId);
    BookDetails book = bookDB.getBookDetails();
%>

<h2><%=book.getTitle()%></h2>

&nbsp;<%=messages.getString("By")%> <em><%=book.getFirstName()%> <%=book.getSurname()%></em>&nbsp;&nbsp;
(<%=book.getYear()%>)<br> &nbsp; <br>

<h4><%=messages.getString("Critics")%></h4>
<blockquote><%=book.getDescription()%>
</blockquote>
<jsp:setProperty name="currency" property="amount" value="<%=book.getPrice()%>"/>
<h4><%=messages.getString("Price")%><%=currency.getFormat()%></h4>

<p><strong><a href="<%=request.getContextPath()%>/catalog?Add=<%=bookId%>"><%=messages.getString("CartAdd")%></a>&nbsp; &nbsp; &nbsp;
<a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("ContinueShopping")%></a></p></strong>
