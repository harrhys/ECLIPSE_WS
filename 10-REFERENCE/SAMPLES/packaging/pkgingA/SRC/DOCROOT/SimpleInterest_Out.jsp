<% String principal = request.getParameter("si_principal"); %> 
<% String time = request.getParameter("si_time"); %> 
<% String rate = request.getParameter("si_rate"); %> 
<% String si_interest = (String) request.getAttribute("si_interest"); %> 
<% String ci_interest = (String) request.getAttribute("ci_interest"); %> 
<% String noofdays = (String) request.getAttribute("noofdays"); %> 
<HTML> 
  <HEAD><TITLE>Simple Interest</TITLE></HEAD> 
  <BODY BGCOLOR=#FFFFFF> 
    <H2>Simple Interest!</H2> 
    <p> 
      The Simple Interest derived on $<%= principal%>, for a period of <%= time%> months at a rate of <%= rate%> percent is <%= si_interest%><br>
      The Compound Interest on the same parameters is <%= ci_interest%>
    </p> 
    <hr>
    No of days left till tax day, 2003 is <%=noofdays%>
  </BODY> 
</HTML> 
