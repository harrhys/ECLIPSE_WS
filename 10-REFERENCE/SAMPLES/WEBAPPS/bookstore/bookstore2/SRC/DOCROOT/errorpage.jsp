<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page isErrorPage="true" %>
<%@ page import="java.util.*" %>
<%
   ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
   if (messages == null) {
      Locale locale=null;
      String language = request.getParameter("language");

      if (language != null) { 
         if (language.equals("English")) { 
          locale=new Locale("en", ""); 
         } else { 
          locale=new Locale("es", ""); 
         }
      } else 
         locale=new Locale("en", "");

      messages = ResourceBundle.getBundle("samples.webapps.bookstore.bookstore2.web.messages.BookstoreMessages", locale); 
      session.setAttribute("messages", messages);
   }
%>
<html>
<head>
<title><%=messages.getString("ServerError")%></title>
</head>
<body bgcolor="white">
<h3>
<%=messages.getString("ServerError")%>
</h3>
<p>
<%= exception.getMessage() %>
</body>
</html>

