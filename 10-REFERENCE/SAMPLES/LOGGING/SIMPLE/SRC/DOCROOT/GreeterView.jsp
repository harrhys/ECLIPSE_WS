<% String nameString = request.getParameter("name"); %> 
<% String messageString = (String) request.getAttribute("message"); %> 
<HTML> 
  <HEAD><TITLE>Greeter</TITLE></HEAD> 
  <BODY BGCOLOR=#FFFFFF> 
    <H2>Hello World!</H2> 
    <p> 
      Good <%= messageString%>, <%= nameString%>.  Enjoy your <%= messageString%>. 
    </p> 
<hr>
<font color="#0000FF">
See <b>server.log</b> or the log file specified in <b>log.properties</b>, in case you selected 'Log to a file' option.
Default log file <b>helloworld.log</b>, in 'Log to a file' option, can be found at &lt;install_root&gt;/domains/domain1/server1/logs.
</font>
<hr>
  </BODY> 
</HTML> 
