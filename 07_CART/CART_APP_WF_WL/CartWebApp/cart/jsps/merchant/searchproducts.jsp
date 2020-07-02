<%@include file="../includes/header.jsp"%>

<s:form action="searchproducts">

	<s:if test="%{#request.display=='categories'}">
		<s:select list="categories" headerKey="-1"
			headerValue="Select Product Category" name="categoryId" listKey="id"
			listValue="name" />
		<s:hidden name="selectType" value="categoryId" /> 
		<s:submit value="Search Products" />
	</s:if>
	
	<s:elseif test="%{#request.display=='products'}">
		<s:select list="products" headerKey="-1" headerValue="Select Product"
			name="productId" listKey="id" listValue="name" />
		<s:hidden name="selectType" value="productId" />
		<s:hidden name="categoryId" value="%{#request.categoryId}" /> <!-- To handle error  -->
		<s:submit value="Get Product Details" />
	</s:elseif>
	
	<s:elseif test="%{#request.display=='product'}">
		<s:push value="product">
			Product ID:<s:property value="id" />
			<br>
			Product Name:<s:property value="name" />
			<br>
			Product Code:<s:property value="productCode" />
			<br>
		</s:push>
		<s:hidden name="selectType" value="" />
		<s:submit value="Exit" />
	</s:elseif>
</s:form>

<%@include file="../includes/footer.jsp"%>