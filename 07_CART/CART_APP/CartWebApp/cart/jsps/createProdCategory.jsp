<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ProductCategory</title>
</head>
<body>
<s:if test="hasActionErrors()">
   <div class="errors">
      <s:actionerror/>
   </div>
</s:if>

<b>Hi..<s:property value="name"/>, Welcome to Farbig Cart</b>


<a href="<s:url value="searchproducts"/>" />Search Products</a>

	<s:form action="createProdCategory">
		<s:textfield  label="Product Category Name" key="name"></s:textfield>
		<s:textfield  label="Product Category Description Password" key="desc" ></s:textfield>
		<s:submit value="Create"/>
		<br></s:form>

<%-- <s:updownselect
list="#{'KFC':'KFC', 'McDonald':'McDonald', 'Burger King':'Burger King',
'Pizza Hut':'Pizza Hut', 'Fat Boy King':'Fat Boy King'}"
name="favFastFood"
headerKey="-1"
headerValue="--- Please Order ---" 
size="7"
/> --%>
</body>
</html>