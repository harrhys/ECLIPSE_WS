<%@page contentType="text/html;charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
	<H1>Result</H1>

	<jsp:useBean id="sampleUserWSProxyid" scope="session"
		class="com.farbig.services.ws.user.UserWSProxy" />
	<%
		if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
		sampleUserWSProxyid.setEndpoint(request.getParameter("endpoint"));
	%>

	<%
		String method = request.getParameter("method");
	int methodID = 0;
	if (method == null)
		methodID = -1;

	if (methodID != -1)
		methodID = Integer.parseInt(method);
	boolean gotMethod = false;

	try {
		switch (methodID) {
		case 2:
			gotMethod = true;
			java.lang.String getEndpoint2mtemp = sampleUserWSProxyid.getEndpoint();
			if (getEndpoint2mtemp == null) {
	%>
	<%=getEndpoint2mtemp%>
	<%
		} else {
		String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
	%>
	<%=tempResultreturnp3%>
	<%
		}
	break;
	case 5:
		gotMethod = true;
		String endpoint_0id = request.getParameter("endpoint8");
		java.lang.String endpoint_0idTemp = null;
		if (!endpoint_0id.equals("")) {
			endpoint_0idTemp = endpoint_0id;
		}
		sampleUserWSProxyid.setEndpoint(endpoint_0idTemp);
		break;
	case 10:
		gotMethod = true;
		com.farbig.services.ws.user.UserWS getUserWS10mtemp = sampleUserWSProxyid.getUserWS();
		if (getUserWS10mtemp == null) {
	%>
	<%=getUserWS10mtemp%>
	<%
		} else {
		if (getUserWS10mtemp != null) {
			String tempreturnp11 = getUserWS10mtemp.toString();
	%>
	<%=tempreturnp11%>
	<%
		}
	}
	break;
	case 13:
		gotMethod = true;
		java.lang.String createBaseUser13mtemp = sampleUserWSProxyid.createBaseUser();
		if (createBaseUser13mtemp == null) {
	%>
	<%=createBaseUser13mtemp%>
	<%
		} else {
		String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(createBaseUser13mtemp));
	%>
	<%=tempResultreturnp14%>
	<%
		}
	break;
	}
	} catch (Exception e) {
	%>
	Exception:
	<%=org.eclipse.jst.ws.util.JspUtils.markup(e.toString())%>
	Message:
	<%=org.eclipse.jst.ws.util.JspUtils.markup(e.getMessage())%>
	<%
		return;
	}
	if (!gotMethod) {
	%>
	result: N/A
	<%
		}
	%>
</BODY>
</HTML>