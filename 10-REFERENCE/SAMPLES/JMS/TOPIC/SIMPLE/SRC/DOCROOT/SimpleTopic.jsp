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
   <title>A Simple Pubish-and-Subscribe Application.</title>

   <script Language="JavaScript">
   function process () {
     if (document.forms[0].mesg.value == "") {
        alert("There is no message to publish! Please enter a message.");
        document.forms[0].mesg.focus(); 
        return false; 
     }
     return true;
   } 
   </script>	    

</head>

<body>
<h3><center>A Simple Publish-and-Subscribe Application.</center></h3>

<form method="POST" action="/topicSample/topicSample">

   <TABLE ALIGN="center" Border=2 BORDER WIDTH="20%" bgcolor="#c0c0c0">
   <TR>
      <TD><CENTER>Messages For Subsciber: &nbsp;&nbsp;&nbsp;<B>A</B></CENTER></TD>
      <TD><CENTER>Messages For Subsciber: &nbsp;&nbsp;&nbsp;<B>B</B></CENTER></TD>
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
<% if (links != null ) {
li = links[1].listIterator();
while(li.hasNext()) {
out.println ((String) li.next());
}
} %>
         </TEXTAREA>
      </CENTER></TD>
   </TR>
   <TR>
      <TD><CENTER>Messages For Subsciber: &nbsp;&nbsp;&nbsp;<B>C</B></CENTER></TD>
      <TD><CENTER>Messages For Subsciber: &nbsp;&nbsp;&nbsp;<B>D</B></CENTER></TD>
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

   <TABLE ALIGN="center" Border=0>
   <TR ALIGN="left">
      <TD COLSPAN="5"><font size=1>1. Please select zero ore more subscribers.</font><br></TD>
   </TR>
   <TR ALIGN="left">
      <TD><font size=+1>Subscribers:</font></TD>
      <TD><b>A</b><input type="checkbox" name="subcriber1" value="a"></TD>
      <TD><b>B</b><input type="checkbox" name="subcriber2" value="b"></TD>
      <TD><b>C</b><input type="checkbox" name="subcriber3" value="c"></TD>
      <TD><b>D</b><input type="checkbox" name="subcriber4" value="d"></TD>
   </TR>
   <TR ALIGN="left">
      <TD COLSPAN="5"><br>
         <font size=1>2. Please enter a message you want to publish and click the Publish button.</font>
      </TD>
   </TR>
   <TR ALIGN="left">
      <TD COLSPAN="5"><font size=+1>Message:</font>&nbsp;
         <input type="text" name="mesg" size="40" value="" style="font-family: sans-serif">&nbsp;&nbsp;
         <input type="submit" value="Publish" name="Publish" onClick="return process();">
      </TD>
   </TR>
   </TABLE>

</form>
</body>
</html>
