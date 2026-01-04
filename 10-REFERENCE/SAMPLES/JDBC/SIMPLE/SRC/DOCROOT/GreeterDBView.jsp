<!--
   Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-->
<%
  System.out.println("\nGreeterDBView JSP is executing..."); 
  String nameString = request.getParameter("name");  
  String messageString = (String) request.getAttribute("message"); 
  String hostString = request.getServerName()+":"+request.getServerPort();
  String linkString = "<a href=\"GreeterDBLogDisplayServlet\">here</a>";
%>

<HTML> 
  <HEAD><TITLE>JDBC-Simple Sample Application</TITLE></HEAD> 
  <BODY BGCOLOR=#FFFFFF> 
    <H2>JDBC-Simple Sample Application</H2> 
    <p> 
      Good <%= messageString%>, <%= nameString%>.  Enjoy your <%= messageString%>. 
    </p> 
    This greeting has been recorded in the data base.  To see all the recorded greetings, click&nbsp<%=linkString%>.
  </BODY>
</HTML> 
<%
  System.out.println("\nGreeterDBView JSP is all done\n"); 
%>
