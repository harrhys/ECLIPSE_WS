<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Enumeration" %>

<%@ page import="com.sun.xml.messaging.jaxm.provider.Config" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.PersistenceDescriptor" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.ErrorDescriptor" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.ProfileDescriptor" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.TransportDescriptor" %>
<%@ page import="javax.xml.messaging.URLEndpoint" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.MessagingProvider" %>
<%@ page import="provideradmin.util.MessageUtil" %>

<% 

  Config pc = (Config) application.getAttribute("theConfig");
  if (pc == null) {
     MessageUtil util = new MessageUtil();  
     pc = util.initializeConfig(request);
     application.setAttribute("theConfig", pc);        
   }

  ProfileDescriptor pr = pc.getProfileDescriptor(profile);
  PersistenceDescriptor pd = new PersistenceDescriptor();
  ErrorDescriptor ed = new ErrorDescriptor();
  
  TransportDescriptor td = pr.getTransportDescriptor(protocol);
  if (td!= null) {     
       pd = td.getPersistenceDescriptor();
       ed = td.getErrorDescriptor();
  }

%>

<html:html locale="true">

<%@ include file="header.jsp" %>

<body background="UI/images/jmenu/PaperTexture.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br>

<html:errors />

<html:form method="get" action="editMapping.do">

  <input type=hidden name=type value="PersErrorForProfile">
  <input type=hidden name=action value="modify">
  <input type=hidden name=profile value="<%= profile %>" >
  <input type=hidden name=protocol value="<%= protocol%>" >

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="7171A5">
      <td width="56%" > 
        <div class="page-title-text" align="left"><%= title %></div>
      </td>
      <td width="44%"> 
        <div align="right">
          <select name="select" onChange="MM_jumpMenu('parent.frames[\'mainFrame\']',this,0)">
            <option value="<%= profile %>_<%= protocol %>.jsp" selected>---- <bean:message key="actions.available"/> ----</option>
            <option value="<%= profile %>_<%= protocol %>.jsp">-------------------------------------</option>
            <option value="<%= profile %>_<%= protocol %>_newMapping.jsp">&nbsp <bean:message key="actions.endpoint.create"/> </option>
            <option value="<%= profile %>_<%= protocol %>_deleteMapping.jsp">&nbsp <bean:message key="actions.endpoint.delete"/></option>
          </select>
        </div>
      </td>
    </tr>
  </table>
  <br>
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr> 
      <td> 
        <div class="table-title-text"> 
          <p><bean:message key="provider.properties"/></p>
        </div>
      </td>
    </tr>
  </table>
  <table class="back-table" border="0" cellspacing="0" cellpadding="1" width="100%">
    <tr> 
      <td> 
        <table class="front-table" border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr class="header-row"> 
            <td width="40%"> 
              <div class="table-header-text" align="left">            
                <bean:message key="default.property"/>
              </div>
            </td>
            <td width="60%"> 
              <div class="table-header-text" align="left">            
                <bean:message key="default.value"/>
              </div>
            </td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="2"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>
          <tr> 
            <td width="40%" valign="top"> 
              <div class="table-label-text">            
                <bean:message key="num.retries"/>
              </div>
            </td>
            <td valign="bottom"> 
              <html:text property="maxretries" size="5"
                value="<%= String.valueOf(ed.getMaxRetries()) %>" /> 
            </td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="2"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>
          <tr> 
            <td width="40%" valign="top"> 
              <div class="table-label-text">
                <bean:message key="retry.interval"/>
              </div>
            </td>
            <td width="60%" valign="bottom"> 
              <html:text property="interval" size="5"
                value="<%= String.valueOf(ed.getRetryInterval()) %>" /> 
            </td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="2" valign="top"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>
          <tr> 
            <td width="40%" valign="top"> 
              <div class="table-label-text">
                <bean:message key="num.messages.log"/>
              </div>
            </td>
            <td width="60%" valign="bottom"> 
              <html:text property="records" size="5"
                value="<%= String.valueOf(pd.getRecordsPerFile()) %>" /> 
            </td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="2" valign="top"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>
          <tr> 
            <td width="40%" valign="top"> 
              <div class="table-label-text">
                <bean:message key="logfile.dir"/>
              </div>
            </td>
            <td width="60%" valign="bottom"> 
             <html:text property="directory" size="27"
                value="<%=  pd.getDirectory() %>" /> 
            </td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="2"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
 <br>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td width="73%">
        <input type=hidden name="submit" value="Save">
        <div align="center">
        <input type=image src="UI/images/buttons/jaxm_saveToProfile.gif" width="173" 
            height="21"  name="Save"></div>  
      </td>
    </tr>
  </table>
  <br>

</html:form>

  <!-- Table that displays the Endpoint mappings -->
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr> 
      <td> 
        <div class="table-title-text"> 
          <p><bean:message key="endpoint.mappings"/></p>
        </div>
      </td>
    </tr>
  </table>
  <table class="back-table" border="0" cellspacing="0" cellpadding="1" width="100%">
    <tr> 
      <td> 
        <table class="front-table" border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr class="header-row"> 
            <td width="50%"> 
              <div class="table-header-text" align="left">         
                <bean:message key="label.uri"/>
              </div>
            </td>
            <td width="50%"> 
              <div class="table-header-text" align="left">
                <bean:message key="label.url"/>
              </div>
            </td>
          </tr>
          <tr height="1"> 
            <td class="line-row" colspan="2"><img src="UI/images/jmenu/dot.gif" alt="" width="1" height="1" border="0"></td>
          </tr>

<% 
   Hashtable endpoints = td.getEndpointMappings();
   Enumeration enum = endpoints.keys();
   while ( enum.hasMoreElements() ) {
        String myuri = (String) enum.nextElement();
        if (! "jaxm.dummy.uri".equalsIgnoreCase(myuri)) {
            URLEndpoint ept = (URLEndpoint) endpoints.get(myuri);
            String myurl = ept.toString();
%>
          <tr> 
             <td width="50%"> 
              <div class="table-normal-text">
                  <a href="<%= profile %>_<%= protocol %>_editMapping.jsp?uri=<%= myuri %>&url=<%= myurl %> "> 
                  <%= myuri %>     
                 </a>

                </div>
             </td>
            <td width="50%"> 
              <div class="table-normal-text"> <%= myurl %> </div>
            </td>
          </tr>
<% 
} } %>
       </table>

      </td>
    </tr>
  </table>
  <br>
<p>&nbsp;</p>
</body>
</html:html>
