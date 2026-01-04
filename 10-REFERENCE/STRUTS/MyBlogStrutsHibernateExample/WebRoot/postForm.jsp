<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>

<c:set var="insertUpdateTitle"
	value="${!empty postForm.id && postForm.id != 0 ?'Update Post':'Add Post'}" />
	
<html>
	<head>
		<link href="<c:url value='main.css'/>" rel="stylesheet" type="text/css" />
		<style>
			td {
				white-space: nowrap;
			}
		</style>
		<title><c:out value="${insertUpdateTitle}" /></title>
	</head>
	<body>
		<html:form action="/postProcess">
			<table>
				<tr>
					<td class="tdLabel">
						ID
					</td>
					<td>
						<html:text disabled="true" property="id" size="20" />
						<html:errors property="id" />
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						Title
					</td>
					<td>
						<html:text property="title" size="40" />
						<html:errors property="title" />
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						Content
					</td>
					<td>
						<html:textarea property="content" rows="5" cols="30" />
						<html:errors property="content" />
					</td>
				</tr>
				<tr>
					<td class="tdLabel">
						Time
					</td>
					<td>
						<html:text property="posttime" size="24" />
						<html:errors property="posttime" />
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<html:hidden property="id" />
						<input type="hidden" name="dispatch" value="insertOrUpdate" />
						<br />
						<input type="submit" value="Submit" />
						&nbsp;&nbsp;&nbsp;
						<input type="submit" value="Cancel"
							onclick="document.postForm.dispatch.value='getPosts'" />
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>