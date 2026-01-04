<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ include file="initdestroy.jsp" %>
<%@ page import="java.util.*, samples.webapps.bookstore.bookstore2.web.cart.*" %>
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
<head><title><%=messages.getString("TitleShoppingCart")%></title></head>
<%@ include file="banner.jsp" %>
<%
  String bookId = request.getParameter("Remove");
  if (bookId != null) {
    cart.remove(bookId);
    bookDB.setBookId(bookId);
      BookDetails book = bookDB.getBookDetails();
%>

<font color="red" size="+2"><%=messages.getString("CartRemoved")%><em><%=book.getTitle()%>
</em> 
<br>&nbsp;<br> 
</font>

<%
  } 

if (request.getParameter("Clear") != null) {
  cart.clear();
%>

<font color="red" size="+2"><strong> 
<%=messages.getString("CartCleared")%>
</strong><br>&nbsp;<br></font>

<%
  }
  // Print a summary of the shopping cart
  int num = cart.getNumberOfItems();
  if (num > 0) {
%>


<font size="+2"><%=messages.getString("CartContents")%><%=num%> <%=(num==1 ? messages.getString("CartItem") : messages.getString("CartItems"))%>
</font><br>&nbsp;

<table> 
<tr> 
<th align=left><%=messages.getString("ItemQuantity")%></TH> 
<th align=left><%=messages.getString("ItemTitle")%></TH> 
<th align=left><%=messages.getString("ItemPrice")%></TH> 
</tr>

<% 
    Iterator i = cart.getItems().iterator();
    while (i.hasNext()) {
      ShoppingCartItem item = (ShoppingCartItem)i.next();
      BookDetails book = (BookDetails)item.getItem();
%>

<tr> 
<td align="right" bgcolor="#ffffff"> 
<%=item.getQuantity()%>
</td> 

<td bgcolor="#ffffaa"> 
<strong><a href="<%=request.getContextPath()%>/bookdetails?bookId=<%=book.getBookId()%>">
<%=book.getTitle()%></a></strong> 
</td> 

<td bgcolor="#ffffaa" align="right"> 
<jsp:setProperty name="currency" property="amount" value="<%=book.getPrice()%>"/>
<jsp:getProperty name="currency" property="format"/>&nbsp;</td>  

<td bgcolor="#ffffaa"> 
<strong> 
<a href="<%=request.getContextPath()%>/showcart?Remove=<%=book.getBookId()%>"><%=messages.getString("RemoveItem")%></a></strong> 
</td></tr>

<%
    // End of while
      }
%>

<tr><td colspan="5" bgcolor="#ffffff"> 
<br></td></tr> 

<tr> 
<td colspan="2" align="right" "bgcolor="#ffffff"> 
<%=messages.getString("Subtotal")%></td> 
<td bgcolor="#ffffaa" align="right"> 
<jsp:setProperty name="currency" property="amount" value="<%=cart.getTotal()%>"/>
<jsp:getProperty name="currency" property="format"/>
</td> 
</td><td><br></td></tr></table> 

<p>&nbsp;<p>
<strong><a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("ContinueShopping")%></a>&nbsp;&nbsp;&nbsp;  
<a href="<%=request.getContextPath()%>/cashier"><%=messages.getString("Checkout")%></a>&nbsp;&nbsp;&nbsp; 
<a href="<%=request.getContextPath()%>/showcart?Clear=clear"><%=messages.getString("ClearCart")%></a></strong>
<% 
} else { 
%>

<font size="+2"><%=messages.getString("CartEmpty")%></font> 
<br>&nbsp;<br> 
<center><a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("Catalog")%></a> </center>

<%
  // End of if
  }
%>

</body>
</html>
