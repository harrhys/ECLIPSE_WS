<%@include file="../includes/header.jsp"%>
<b>Hi..<s:property value="%{#session.name}" />, Welcome to Farbig
	Cart
</b>

<br>
<br>

<a href="<s:url value="admin/createAdminUser"/>" >
Create Admin User
</a>

<br>
<br>

<a href="<s:url value="admin/createMerchant"/>" >
Create Merchant
</a>

<br>
<br>

<a href="<s:url value="admin/createProdCategoryInput"/>" >
Create Merchant
</a>

<br>
<br>
<%@include file="../includes/footer.jsp"%>
<a href="<s:url value="createCustomer"/>" >
Create Customer
</a>
<br>
<br>
