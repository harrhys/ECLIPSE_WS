<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<%
java.util.logging.Logger.getLogger("freemarker.cache").setLevel(
				java.util.logging.Level.OFF);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
.errors {
	background-color:#FFCCCC;
	border:1px solid #CC0000;
	width:400px;
	margin-bottom:8px;
}
.errors li{ 
	list-style: none; 
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Farbig</title>
</head>
<body>
<s:if test="hasActionErrors()">
   <div class="errors">
      <s:actionerror/>
   </div>
</s:if>

<h1>Welcome to Farbig Cart!</h1>
	<s:form action="login">
		<s:textfield  label="Enter Username" key="username"></s:textfield>
		<s:password  label="Enter Password" key="password" ></s:password>
		<s:submit></s:submit>
		<br></s:form>

</body>
</html>