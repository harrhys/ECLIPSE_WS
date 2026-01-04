<!-- 
   Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
-->
<%
  System.out.println("\nGreeterDBLogView JSP is executing..."); 
  String nameString = request.getParameter("name");
  String messageString = (String) request.getAttribute("message");  
  String hostString = request.getServerName()+":"+request.getServerPort();
  String linkString = "<a href=\"index.html\">here</a>";
%>
<HTML> 
  <HEAD><TITLE>JDBC-SIMPLE Sample Application</TITLE></HEAD> 
  <BODY BGCOLOR=#FFFFFF> 
    <CENTER>
    <H2>JDBC-Simple Database Log</H2> 
    <HR>
    <%
      int count = 0;
      java.sql.ResultSet rs = (java.sql.ResultSet) request.getAttribute("dbResults");
      try {  
	  if (rs == null) {
          out.println("<TR>Result set is null</TR>");
        }
        else {
          out.println("<TABLE BORDER CELLSPACING=3>");
          out.println("<TR>");
          out.println("<TH>Timestamp</TH>");
          out.println("<TH>Name</TH>");
          out.println("<TH>Message</TH>");
          out.println("</TR>");
	    while (rs.next()) {
	      String timeStamp = rs.getString("timeStamp");
            String name = rs.getString("name");
            String message = rs.getString("message");
            out.println("<TR>");
            out.println("<TD>" + timeStamp + "</TD>");
            out.println("<TD>" + name + "</TD>");
            out.println("<TD>" + message + "</TD>");
            out.println("</TR>");
            count++;
          }
        out.println("</TABLE>");
        }
      }
      catch (Exception ex) {
        out.println("<TR>Exception thrown</TR>");
      }
      //rs.close(); 
      out.println(count + " records listed" + "<BR>" + "<HR>");
    %> 
    To enter another greeting, click&nbsp<%=linkString%>.
    </CENTER>
  </BODY> 
</HTML> 
<%
  System.out.println("\nGreeterDBLogView JSP is all done\n"); 
%>
