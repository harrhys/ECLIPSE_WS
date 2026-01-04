<% String nameString = request.getParameter("name"); %> 
<% String messageString = (String) request.getAttribute("message"); %> 
<HTML> 
  <HEAD><TITLE>Greeter</TITLE></HEAD> 
  <BODY BGCOLOR=#FFFFFF> 
    <H2>Hello World!</H2> 
    <p> 
      Good <%= messageString%>, <%= nameString%>.  Enjoy your <%= messageString%>. 
    </p> 
  </BODY> 
</HTML> 
