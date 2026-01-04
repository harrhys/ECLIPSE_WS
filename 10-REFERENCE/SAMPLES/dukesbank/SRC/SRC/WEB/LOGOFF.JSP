<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*" %>
<% 
  ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
  request.getSession().invalidate();
  HttpSession newSession = request.getSession(false);
%>

<center>
<h3><%=messages.getString("Farewell")%></h3>
<h3><a href="<%=request.getContextPath()%>/main"><%=messages.getString("LogonAgain")%></a>
</center>





















