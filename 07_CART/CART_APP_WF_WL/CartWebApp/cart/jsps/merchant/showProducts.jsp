<%@ taglib uri="/struts-tags" prefix="s"%>
<table border="1">
	<tr>
		<th>Product Name</th>
		<th>Product Code</th>
		<th>Product Price</th>
		<th>Product Category</th>
		<th>Store</th>
		<th>Description</th>
	</tr>
	<s:iterator value="products" var="product">
		<tr>
			<td><s:property value="%{#product.name}" /></td>
			<td><s:property value="%{#product.productCode}" /></td>
			<td><s:property value="Rs.%{#product.price}" /></td>
			<td><s:property value="%{#product.productCategory.name}" /></td>
			<td><s:property value="%{#product.store.storeName}" /></td>
			<td><s:property value="%{#product.description}" /></td>
		</tr>
	</s:iterator>
</table>


