<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.MessagingProvider" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.Config" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.ProfileDescriptor" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.TransportDescriptor" %>
<%@ page import="javax.xml.messaging.URLEndpoint" %>
<%@ page import="provideradmin.util.MessageUtil" %>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<% 

  Config pc = (Config) application.getAttribute("theConfig");
  if (pc == null) {
     MessageUtil util = new MessageUtil();  
     pc = util.initializeConfig(request);
     application.setAttribute("theConfig", pc);        
   }

  ProfileDescriptor pr = pc.getProfileDescriptor(profile);
  TransportDescriptor td = pr.getTransportDescriptor(protocol);
%>

<html:html locale="true">

<%@ include file="header.jsp" %>

<body background="UI/images/jmenu/PaperTexture.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<html:form method="get" action="editMapping.do">

<html:errors />

  <input type=hidden name=type value="Endpoint">
  <input type=hidden name=action value="remove">
  <input type=hidden name=profile value="<%= profile %>" >
  <input type=hidden name=protocol value="<%= protocol %>" >

  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr> 
      <td> 
        <div class="table-title-text"> 
          <p><bean:message key="actions.endpoint.delete"/></p>
        </div>
      </td>
    </tr>
  </table>
  <table class="back-table" border="0" cellspacing="0" cellpadding="1" width="100%">
    <tr> 
      <td> 
        <table class="front-table" border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr class="header-row"> 
            <td width="10%" align="center"> 
              <div class="table-header-text" align="left"><bean:message key="label.delete"/></div>
            </td>
            <td width="30%"> 
              <div class="table-header-text" align="left"><bean:message key="label.uri"/></div>
            </td>
            <td width="60%"> 
              <div class="table-header-text" align="left"><bean:message key="label.url"/></div>
            </td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="3" align="center"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>


<%   Hashtable endpoints = td.getEndpointMappings();
     int count = 0;
     Enumeration enum = endpoints.keys();     
        while ( enum.hasMoreElements() ) {
        String uri = (String) enum.nextElement();
        if (! "jaxm.dummy.uri".equalsIgnoreCase(uri)) {
            URLEndpoint ept = (URLEndpoint) endpoints.get(uri);
            String url = ept.toString();
            count ++;
    %>
          <tr> 
            <td width="10%" align="center"> 
              <div class="table-normal-text"> 
                <input type="checkbox" name="checkbox" value="<%= uri %>" >
              </div>
            </td>
            <td width="30%"> 
              <div class="table-normal-text"> <%= uri %> </div>
            </td>
            <td width="60%"> 
              <div class="table-normal-text"> <%= url %></div>
            </td>
          </tr>
<% 
}} 
%>
          <input type="hidden" name="TotalEndpoints" value="<%= count %>" >
          <tr height="1"> 
            <td class="line-row" colspan="3" align="center"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="3"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

<%@ include file="buttons.jsp" %>

</html:form>
<p>&nbsp;</p>
</body>
</html:html>
