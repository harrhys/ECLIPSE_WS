<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Struts2</title>
</head>
<body>
<s:if test="hasActionErrors()">
   <div class="errors">
      <s:actionerror/>
   </div>
</s:if>

<b>Hi..<s:property value="name"/>, Welcome to Farbig Cart</b>

</br></br>

<a href="<s:url value="searchproducts"/>" />Search Products</a>

</br></br>

<a href="<s:url value="prodcat"/>" />Create Product Category</a>

</br></br>

<a href="<s:url value="stores"/>" />Create Store</a>
</br></br>

<a href="<s:url value="createProduct"/>" />Create Product</a>




</body>
</html>