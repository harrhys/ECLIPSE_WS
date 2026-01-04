<%--

  Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.

  This software is the proprietary information of Sun Microsystems, Inc.
  Use is subject to license terms.

--%>

<% String principal = request.getParameter("si_principal"); %> 
<% String time = request.getParameter("si_time"); %> 
<% String rate = request.getParameter("si_rate"); %> 
<% String si_interest = (String) request.getAttribute("si_interest"); %> 
<% String ci_interest = (String) request.getAttribute("ci_interest"); %> 
<% String noofdays = (String) request.getAttribute("noofdays"); %> 
<% String suggestedValues = (String) request.getAttribute("strValues"); %> 
<HTML> 
  <HEAD><TITLE>Simple Interest</TITLE></HEAD> 
  <BODY BGCOLOR=#FFFFFF> 
    <H2>Simple Interest!</H2> 
    <p> 
      The Simple Interest derived on $<%= principal%>, for a period of <%= time%> months at a rate of <%= rate%> percent is <%= si_interest%><br>
      The Compound Interest on the same parameters is <%= ci_interest%>
    </p> 
    <hr>
    No of days left till tax day, 2001 is <%=noofdays%>
    <hr>
    <i>Suggested Values that you could use next time :<br>
    <%=suggestedValues%>
    </i>
    <hr>
  </BODY> 
</HTML> 
