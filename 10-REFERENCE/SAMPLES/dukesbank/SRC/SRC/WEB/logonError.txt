<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*" %>
<% ResourceBundle messages = (ResourceBundle)session.getAttribute("messages"); %>

<h2>Login Error</h2>
<%=messages.getString("LogonError")%>
<p><a href="<%=request.getContextPath()%>/logon"><%=messages.getString("LogonAgain")%></a></p>


