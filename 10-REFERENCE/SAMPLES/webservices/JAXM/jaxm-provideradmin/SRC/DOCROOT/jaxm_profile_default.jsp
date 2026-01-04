<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="com.sun.xml.messaging.jaxm.provider.Config" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.PersistenceDescriptor" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.ErrorDescriptor" %>
<%@ page import="com.sun.xml.messaging.jaxm.provider.MessagingProvider" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="provideradmin.util.MessageUtil" %>

<% 

  Config pc = (Config) application.getAttribute("theConfig");
  if (pc == null) {
     MessageUtil util = new MessageUtil();  
     pc = util.initializeConfig(request);
     application.setAttribute("theConfig", pc);        
   }
  
  PersistenceDescriptor pd = pc.getPersistenceDescriptor();
  ErrorDescriptor ed = pc.getErrorDescriptor();

%>

<html:html locale="true">

<%@ include file="header.jsp" %>

<body background="UI/images/jmenu/PaperTexture.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<html:errors/>

<html:form method="post" action="saveTopLevel.do">

  <%-- Values needed for determining what action to take --%>
  <input type=hidden name=type value="TopLevel">
  <input type=hidden name=action value="modify">
  <input type=hidden name=profile value="ebxml">
  <input type=hidden name=protocol value="http">

 <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="7171A5"> 
      <td width="65%" > 
        <div class="page-title-text" align="left">
         <bean:message key="default.properties"/>
        </div>
      </td>
    </tr>
  </table>
  <br>
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
        <div align="center"><input type=image 
        src="UI/images/buttons/jaxm_saveDefaultProperties.gif" 
        width="173" height="21"> </div>
      </td> 
    </tr>
  </table>
  <br>
</html:form>
<p>&nbsp;</p>
</body>
</html:html>
