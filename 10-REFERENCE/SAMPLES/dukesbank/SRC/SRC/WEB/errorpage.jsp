<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page isErrorPage="true" %>

<html>
  <head>
    <title> Server Error</title>
  </head>
<body> 
<h2>Server Error</h2>
   
<h3>
Your request cannot be completed.  The server got the following error: </h3>
<p>
<%= exception.getMessage() %>
</body>
</html>




















