<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*" %>
<%
   ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
   // Payment received -- invalidate the session
   session.invalidate();
%>
<html> 
<head><title><%=messages.getString("TitleReceipt")%></title>
</head> 
<%@ include file="banner.jsp" %>

<h3><%=messages.getString("ThankYou")%><%=request.getParameter("cardname")%>.</h3><br>
<strong><a href="<%=request.getContextPath()%>/enter"><%=messages.getString("ContinueShopping")%></a>&nbsp;&nbsp;&nbsp;</strong> 
</body></html>



