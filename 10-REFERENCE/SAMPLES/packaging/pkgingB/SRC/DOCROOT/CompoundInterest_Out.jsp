<% String principal = request.getParameter("ci_principal"); %> 
<% String time = request.getParameter("ci_time"); %> 
<% String rate = request.getParameter("ci_rate"); %> 
<% String si_interest = (String) request.getAttribute("si_interest"); %> 
<% String ci_interest = (String) request.getAttribute("ci_interest"); %> 
<% String noofdays = (String) request.getAttribute("noofdays"); %> 
<HTML> 
  <HEAD><TITLE>Compound Interest</TITLE></HEAD> 
  <BODY BGCOLOR=#FFFFFF> 
    <H2>Compound Interest!</H2> 
    <p> 
      The Compound Interest derived on $<%= principal%>, for a period of <%= time%> months at a rate of <%= rate%> is $<%= ci_interest%><br>
      The Simple Interest on the same parameters is $<%= si_interest%>
    </p> 
    <hr>
    No of days left till tax day, 2003 is <%=noofdays%>
    <hr>
    <center> Session Info (ci=compound interest) (si=simple interest)</center>
    <hr>
    <%
    // get and print all session values
    java.util.Enumeration enum = session.getAttributeNames();
    String name= null;
    String value = null;
    while (enum.hasMoreElements()) {
        name = (String)enum.nextElement();
        if (name != null) {
            value = (String)session.getAttribute(name);
            out.println(name+":"+value+"<br>");
        }
    }
    %>
    
    
  </BODY> 
</HTML> 
