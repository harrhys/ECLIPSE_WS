<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
java.util.logging.Logger.getLogger("freemarker").setLevel(
		java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("freemarker.beans").setLevel(
				java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("freemarker.cache").setLevel(
		java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("freemarker.runtime").setLevel(
		java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("freemarker.runtime.attempt").setLevel(
		java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("freemarker.servlet").setLevel(
		java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("freemarker.jsp").setLevel(
		java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("freemarker.configuration").setLevel(
		java.util.logging.Level.WARNING);
java.util.logging.Logger.getLogger("com.opensymphony").setLevel(
		java.util.logging.Level.WARNING);


%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Farbig Cart</title>
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