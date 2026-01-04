<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>
<%@ page import="java.util.*" %>
<%
  ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
  if (messages == null) { 
      messages = ResourceBundle.getBundle("WebMessages", Locale.getDefault());
      session.setAttribute("messages", messages);
  }

%>
<tt:definition name="bank" screen="<%= (String)request.getAttribute(\"selectedScreen\") %>">
       <tt:screen id="/main">
                <tt:parameter name="title" value="Duke's Bank" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/main.jsp" direct="false"/>
        </tt:screen>
       <tt:screen id="/error">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleError\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/error.jsp" direct="false"/>
        </tt:screen>
       <tt:screen id="/transferAck">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleTransferSucceeded\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/transferAck.jsp" direct="false"/>
        </tt:screen>
       <tt:screen id="/transferFunds">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleTransferFunds\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/transferFunds.jsp" direct="false"/>
        </tt:screen>
       <tt:screen id="/atmAck">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleWDSucceeded\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/atmAck.jsp" direct="false"/>
        </tt:screen>
       <tt:screen id="/atm">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleWD\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/atm.jsp" direct="false"/>
        </tt:screen>
        <tt:screen id="/accountHist">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleAccountHistory\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/accountHist.jsp" direct="false"/>
        </tt:screen>
        <tt:screen id="/accountList">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleAccountList\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/links.jsp" direct="false"/>
                <tt:parameter name="body" value="/accountList.jsp" direct="false"/>
        </tt:screen>
        <tt:screen id="/logoff">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleLogoff\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/nolinks.jsp" direct="false"/>
                <tt:parameter name="body" value="/logoff.jsp" direct="false"/>
        </tt:screen>
        <tt:screen id="/logon">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleLogon\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/nolinks.jsp" direct="false"/>
                <tt:parameter name="body" value="/logon.jsp" direct="false"/>
        </tt:screen>
        <tt:screen id="/logonError">
                <tt:parameter name="title" value="<%=messages.getString(\"TitleLogonError\")%>" direct="true"/>
                <tt:parameter name="banner" value="/banner.jsp" direct="false"/>
                <tt:parameter name="links" value="/nolinks.jsp" direct="false"/>
                <tt:parameter name="body" value="/logonError.jsp" direct="false"/>
        </tt:screen>
</tt:definition>
