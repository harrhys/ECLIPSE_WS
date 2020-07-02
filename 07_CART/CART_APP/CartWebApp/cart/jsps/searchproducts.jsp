<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Struts2</title>
</head>
<body>
	<s:if test="hasActionErrors()">
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>

	<s:set name="listtype" value="flag" />

<a href="<s:url value="home"/>" />Home</a> </br>

	<s:form action="searchproducts">
		<s:if test="%{#listtype=='cat'}">
			<s:select list="categories" headerKey=""
				headerValue="Select Product Category" name="categoryId" listKey="id"
				listValue="name" />
			<s:hidden name="flag" value="cat" />
			<s:submit value="Search Products" />
		</s:if>
		<s:elseif test="%{#listtype=='pro'}">
			<s:select list="products" headerKey="" name="productId" listKey="id"
				listValue="name" />
			<s:hidden name="flag" value="pro" />
			<s:submit value="Get Product Details" />
		</s:elseif>
		<s:elseif test="%{#listtype=='det'}">
			
			<s:push value="product">
			Product ID:<s:property value="id"/><br>
			Product Name:<s:property value="name"/><br>
			Product Code:<s:property value="productCode"/><br>
			</s:push>
			<s:hidden name="flag" value="" />
			<s:submit value="Exit" />
		</s:elseif>

		
	</s:form>


</body>
</html>