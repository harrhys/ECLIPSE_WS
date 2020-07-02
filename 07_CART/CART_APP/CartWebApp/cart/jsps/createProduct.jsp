<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
java.util.logging.Logger.getLogger("freemarker.cache").setLevel(
				java.util.logging.Level.OFF);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Store</title>
</head>
<body>
	<s:if test="hasActionErrors()">
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>

	<b>Hi..<s:property value="name" />, Welcome to Farbig Cart
	</b>

<a href="<s:url value="home"/>" />Home</a></br>


	<s:form action="createProduct">
		<s:textfield label="Product Name" key="name" />
		<s:textfield label="Product Code" key="code" />
		<s:textfield label="Product Description" key="desc" />
		<s:select list="categories" headerKey=""
			headerValue="Select Product Category" name="categoryId" listKey="id"
			listValue="name" />
		<s:select list="stores" headerKey=""
			headerValue="Select Store Category" name="storeId" listKey="id"
			listValue="storeName" />
		<s:submit value="Create" />
		<br>
	</s:form>


</body>
</html>