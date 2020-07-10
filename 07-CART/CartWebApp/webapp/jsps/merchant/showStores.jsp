<%@ taglib uri="/struts-tags" prefix="s"%>
<table border="1">
	<tr>
		<th>Store Name</th>
		<th>Description</th>
	</tr>
	<s:iterator value="stores" var="store">
		<tr>
			<td><s:property value="%{#store.storeName}" /></td>
			<td><s:property value="%{#store.description}" /></td>
		</tr>
	</s:iterator>
</table>


