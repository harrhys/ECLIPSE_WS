<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
java.util.logging.Logger.getLogger("freemarker.cache").setLevel(
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
	<header>
		<h3 align="left">Farbig Cart</h3>
		Hi <s:property value="%{#session.user.name}" /> <s:if test="%{#session.user.userType=='MERCHANT'}"> - <s:property value="%{#session.user.businessName}"/></s:if> 
		<br><br>
		<s:if test="hasActionErrors()">
				<s:actionerror />
		</s:if>
	</header>