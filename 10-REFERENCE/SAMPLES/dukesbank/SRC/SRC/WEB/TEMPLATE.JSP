<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/tutorial-template" prefix="tt" %>
<%@ page errorPage="errorpage.jsp" %>
<%@ include file="screendefinitions.jsp" %>
<html>
<head>
<title>
  <tt:insert definition="bank" parameter="title"/>
</title>
</head>
<body bgcolor="#ffffff">
  <tt:insert definition="bank" parameter="banner"/>
  <tt:insert definition="bank" parameter="links"/>
  <tt:insert definition="bank" parameter="body"/>
</body>
</html>
