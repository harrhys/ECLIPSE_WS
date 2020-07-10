<%@ taglib uri="/struts-tags" prefix="s"%>
<table border="1">
	<tr>
		<th>Product Category</th>
		<th>Description</th>
	</tr>
	<s:iterator value="categories" var="category">
		<tr>
			<td><s:property value="%{#category.name}" /></td>
			<td><s:property value="%{#category.description}" /></td>
		</tr>
	</s:iterator>
</table>


