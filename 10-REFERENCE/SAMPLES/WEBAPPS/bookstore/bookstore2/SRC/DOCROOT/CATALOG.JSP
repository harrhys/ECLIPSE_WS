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


<jsp:useBean id="cart" scope="session" class="samples.webapps.bookstore.bookstore2.web.cart.ShoppingCart"/>
<jsp:useBean id="currency" class="samples.webapps.bookstore.bookstore2.web.util.Currency" scope="session">
  <jsp:setProperty name="currency" property="locale" value="<%=request.getLocale()%>"/>
</jsp:useBean>

<html> 
<head><title><%=messages.getString("TitleBookCatalog")%></title></head> 
<%@ include file="banner.jsp" %>

<%
  // Additions to the shopping cart
  String bookId = request.getParameter("Add");
  if (bookId != null) {
    bookDB.setBookId(bookId);
    BookDetails book = bookDB.getBookDetails();
    cart.add(bookId, book);
%>
<p><h3> 
<font color="red"> 
<%=messages.getString("CartAdded1")%> <i><%=book.getTitle()%></i> <%=messages.getString("CartAdded2")%></font></h3>
<%
}
if (cart.getNumberOfItems() > 0) {
%>

<p><strong><a href="<%=request.getContextPath()%>/showcart"><%=messages.getString("CartCheck")%></a>&nbsp;&nbsp;&nbsp;
<a href="<%=request.getContextPath()%>/cashier"><%=messages.getString("Buy")%></a></p></strong>

<%
}
%>
<br>&nbsp;
<br>&nbsp;
<h3><%=messages.getString("Choose")%></h3> 
<center>
<table>
<%
  Collection c = bookDB.getBooks();
  Iterator i = c.iterator();
  while (i.hasNext()) {
    BookDetails book = (BookDetails)i.next();
    bookId = book.getBookId();
%>

<tr> 
<td bgcolor="#ffffaa"> 
<a href="<%=request.getContextPath()%>/bookdetails?bookId=<%=bookId%>"><strong>
<%=book.getTitle()%>&nbsp;</strong></a></td> 

<td bgcolor="#ffffaa" rowspan=2> 
<jsp:setProperty name="currency" property="amount" value="<%=book.getPrice()%>"/>
<%=currency.getFormat()%>  

<td bgcolor="#ffffaa" rowspan=2> 
<a href="<%=request.getContextPath()%>/catalog?Add=<%=bookId%>">&nbsp;<%=messages.getString("CartAdd")%>&nbsp;</a></td></tr> 

<tr> 
<td bgcolor="#ffffff"> 
&nbsp;&nbsp;<%=messages.getString("By")%> <em><%=book.getFirstName()%>&nbsp;
<%=book.getSurname()%></em></td></tr>

<% } %>

</table>
</center>
</body>
</html>
