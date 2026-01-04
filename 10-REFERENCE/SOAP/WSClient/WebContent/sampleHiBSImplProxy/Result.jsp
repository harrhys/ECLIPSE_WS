<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleHiBSImplProxyid" scope="session" class="com.test.HiBSImplProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
sampleHiBSImplProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = sampleHiBSImplProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
            java.lang.String endpoint_0idTemp = null;
        if(!endpoint_0id.equals("")){
         endpoint_0idTemp  = endpoint_0id;
        }
        sampleHiBSImplProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        com.test.HiBSImpl getHiBSImpl10mtemp = sampleHiBSImplProxyid.getHiBSImpl();
if(getHiBSImpl10mtemp == null){
%>
<%=getHiBSImpl10mtemp %>
<%
}else{
        if(getHiBSImpl10mtemp!= null){
        String tempreturnp11 = getHiBSImpl10mtemp.toString();
        %>
        <%=tempreturnp11%>
        <%
        }}
break;
case 13:
        gotMethod = true;
        String name_1id=  request.getParameter("name16");
            java.lang.String name_1idTemp = null;
        if(!name_1id.equals("")){
         name_1idTemp  = name_1id;
        }
        java.lang.String sayHi13mtemp = sampleHiBSImplProxyid.sayHi(name_1idTemp);
if(sayHi13mtemp == null){
%>
<%=sayHi13mtemp %>
<%
}else{
        String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(sayHi13mtemp));
        %>
        <%= tempResultreturnp14 %>
        <%
}
break;
}
} catch (Exception e) { 
%>
Exception: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.toString()) %>
Message: <%= org.eclipse.jst.ws.util.JspUtils.markup(e.getMessage()) %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>