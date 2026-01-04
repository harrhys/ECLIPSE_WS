<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
	<head>
		<title>Posts</title>
		<link href="<c:url value='main.css'/>" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="titleDiv">
			My Blog
		</div>
		<c:url var="url" scope="page" value="/postSetUp.do">
			<c:param name="dispatch" value="setUpForInsertOrUpdate" />
		</c:url>
		<br />
		<a href="${url}">Add New Post</a>
		<br />
		<br />
		<table class="borderAll">
			<tr>
				<th>
					ID
				</th>
				<th>
					Title
				</th>
				<th>
					Content
				</th>
				<th>
					Time
				</th>
				<th>
					&nbsp;
				</th>
			</tr>
			<c:forEach var="post" items="${posts}" varStatus="status">
				<tr class="${status.index%2==0?'even':'odd'}">
					<td class="nowrap">
						<c:out value="${post.id}" />
					</td>
					<td class="nowrap">
						<c:out value="${post.title}" />
					</td>
					<td class="nowrap">
						<c:out value="${post.content}" />
					</td>
					<td class="nowrap">
						<c:out value="${post.posttime}" />
					</td>
					<td class="nowrap">
						<c:url var="url" scope="page" value="/postSetUp.do">
							<c:param name="id" value="${post.id}" />
							<c:param name="dispatch" value="setUpForInsertOrUpdate" />
						</c:url>
						<a href="${url}">Edit</a> &nbsp;&nbsp;&nbsp;
						<c:url var="url" scope="page" value="/postProcess.do">
							<c:param name="id" value="${post.id}" />
							<c:param name="dispatch" value="delete" />
						</c:url>
						<a href="${url}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>