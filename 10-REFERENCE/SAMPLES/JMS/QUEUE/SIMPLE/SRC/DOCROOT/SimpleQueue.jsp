<!--
 **************************************************************
 * Copyright  2002 Sun Microsystems, Inc. All rights reserved. *
 ***************************************************************
-->

<%@ page import="java.util.*" %>

<%! ListIterator li = null; %>
<% LinkedList[] links = (java.util.LinkedList[]) request.getAttribute("mesgLinks"); %>

<html>
<head>
   <title>A Simple Chat Application Using iMQ.</title>
</head>

<body>
   <h3><center>A Simple Chat Application Using iMQ.</center></h3>

   <form method="POST" action="/queueSample/queueSample" >

   <TABLE ALIGN="center" Border=2 BORDER WIDTH="20%"  bgcolor="#c0c0c0">
   <TR>
      <TD><CENTER>Messages For User: &nbsp;&nbsp;&nbsp;<B>A</B></CENTER></TD>
      <TD><CENTER>Messages For User: &nbsp;&nbsp;&nbsp;<B>B</B></CENTER></TD>
   </TR>
   <TR>
      <TD><CENTER>
      <TEXTAREA NAME="message_a" ROWS="5" COLS="55">
<% if (links != null) {
li = links[0].listIterator();
while(li.hasNext()) {
out.println ((String) li.next());
}
} %>
      </TEXTAREA>
      </CENTER></TD>
      <TD><CENTER>
      <TEXTAREA NAME="message_b" ROWS="5" COLS="55">
<% if (links != null) {
li = links[1].listIterator();
while(li.hasNext()) {
out.println ((String) li.next());
}
} %>
      </TEXTAREA>
      </CENTER></TD>
   </TR>
   <TR>
      <TD><CENTER>Messages For User: &nbsp;&nbsp;&nbsp;<B>C</B></CENTER></TD>
      <TD><CENTER>Messages For User: &nbsp;&nbsp;&nbsp;<B>D</B></CENTER></TD>
   </TR>
   <TR>
      <TD><CENTER>
      <TEXTAREA NAME="message_c" ROWS="5" COLS="55">
<% if (links != null) {
li = links[2].listIterator();
while(li.hasNext()) {
out.println ((String) li.next());
}
} %>     
      </TEXTAREA>
      </CENTER></TD>
      <TD><CENTER>
      <TEXTAREA NAME="message_d" ROWS="5" COLS="55">
<% if (links != null) {
li = links[3].listIterator();
while(li.hasNext()) {
out.println ((String) li.next());
}
} %>
      </TEXTAREA>
      </CENTER></TD>
   </TR>
   </TABLE><BR><BR>

   <TABLE ALIGN="center" Border=0 BORDER WIDTH="40%">
   <TR>
      <TD></TD>
      <TD><CENTER><B>A</B></CENTER></TD>
      <TD><CENTER><B>B</B></CENTER></TD>
      <TD><CENTER><B>C</B></CENTER></TD>
      <TD><CENTER><B>D</B></CENTER></TD>
   </TR>
   <TR>
      <TD><font size=+1>Message Sender :</font></TD>
      <TD><CENTER><input type=radio name="req" value="A" checked></CENTER></TD>
      <TD><CENTER><input type=radio name="req" value="B"></CENTER></TD>
      <TD><CENTER><input type=radio name="req" value="C"></CENTER></TD>
      <TD><CENTER><input type=radio name="req" value="D"></CENTER></TD>
   </TR>
   <TR>
      <TD><font size=+1>Message Receiver :</font></TD>
      <TD><CENTER><input type=radio name="res" value="A"></CENTER></TD>
      <TD><CENTER><input type=radio name="res" value="B" checked></CENTER></TD>
      <TD><CENTER><input type=radio name="res" value="C"></CENTER></TD>
      <TD><CENTER><input type=radio name="res" value="D"></CENTER></TD>
   </TR>
   </TABLE>

   <BR><BR>
   <CENTER><font size=+1>Message :</font>
   <input type="text" name="mesg" size="40" value="Please type your message here." style="font-family: sans-serif">
   &nbsp;&nbsp;
   <input type="submit" value="Process" name="Process"></CENTER>

   </form>
</body>
</html>