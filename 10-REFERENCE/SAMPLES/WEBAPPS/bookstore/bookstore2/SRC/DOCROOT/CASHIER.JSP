<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*" %>
<%
   ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
%>
<jsp:useBean id="cart" scope="session" class="samples.webapps.bookstore.bookstore2.web.cart.ShoppingCart"/>
<jsp:useBean id="currency" class="samples.webapps.bookstore.bookstore2.web.util.Currency" scope="session">
  <jsp:setProperty name="currency" property="locale" value="<%=request.getLocale()%>"/>
</jsp:useBean>
<jsp:setProperty name="currency" property="amount" value="<%=cart.getTotal()%>"/>

<html>
<head><title><%=messages.getString("TitleCashier")%></title></head>
<%@ include file="banner.jsp" %>
<p><%=messages.getString("Amount")%>
<strong><%=currency.getFormat()%></strong>
<p><%=messages.getString("Purchase")%>
<form action="<%=request.getContextPath()%>/receipt" method="post">
<table>
<tr>
<td><strong><%=messages.getString("Name")%></strong></td>
<td><input type="text" name="cardname" value="Gwen Canigetit" size="19"></td>
</tr>
<tr>
<td><strong><%=messages.getString("CCNumber")%></strong></td>
<td><input type="text" name="cardnum" value="xxxx xxxx xxxx xxxx" size="19"></td>
</tr>
<tr>
<td></td>
<td><input type="submit" value="<%=messages.getString("Submit")%>"></td>
</tr>
</table>
</form>
</body>
</html>
