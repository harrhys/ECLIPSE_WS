<!--
   Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 -->

<%@ page import="samples.comet.ejb.*,javax.resource.ResourceException,javax.ejb.*, javax.naming.*, javax.rmi.PortableRemoteObject, java.rmi.RemoteException" %>
<%!

   public void jspInit() { 

   }

   public void jspDestroy() {

   }
    private String getFString(int length,String val){
        int diff=length-val.length();
        if(diff>0){
           byte[] tmp=new byte[diff];
           for(int i=0;i<diff;i++)
              tmp[i]=(byte)' ';
           return (val+new String(tmp));
        } else{
            if(diff<0)
               return (val.substring(0,length));
            else
               return (val);
        }
    }

%>




<HTML>
<HEAD>
	<TITLE>Comet Welcome Sample</TITLE>
</HEAD>
<BODY>
<center>
<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 WIDTH="600" > 
	<tr> 
		<td VALIGN=TOP WIDTH="420" >
			<form method=get>
			<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=6 WIDTH="100%">
			<tr>
				<td BGCOLOR="#8979C8">
				<b><font face="sans-serif,arial,helvetica" color=white>
					Comet Welcome Sample Start Form</font>
				</b>
				</td>
			</tr>
			<tr VALIGN=TOP>
				<td BGCOLOR="#666699">
				<p>
				<center>
				<TABLE BORDER=0 cellspacing=0 cellpadding=0>

				<tr>
					<td><font face="arial, helvetica, sans-serif" color=white size="-1">Please enter your name:</font></td>
					<td><font face="arial, helvetica, sans-serif">
						<input type="text" name="NAME"  size=20 maxsize=50 value="" ></font>
					</td>
				</tr>
				</TABLE>
				<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 WIDTH="100%" >
				<tr>
					<td BGCOLOR="#666699" >
					<TABLE>
					<tr>
						<td valign=top ><input type="submit" name="execute" value="EXECUTE">
						</td>
					</tr>
					</TABLE>
					</td>
				</tr>
				</TABLE>
				</td>
			</tr>
			</TABLE>
			</form>
		</td>
	</tr>
</TABLE>
</center>
<%
  Welcome _welcome=null;
  try{


    String name=request.getParameter("NAME");
    if(name!=null && name.length()>0){

       InitialContext ic = new InitialContext();
       Object objRef = ic.lookup("java:comp/env/ejb/welcome");
       WelcomeHome home = (WelcomeHome)PortableRemoteObject.narrow(objRef, WelcomeHome.class);
       _welcome = home.create();
       System.out.println("before execute");
       _welcome.setName(getFString(10,name));
       boolean ok=_welcome.execute();
       System.out.println("after execute");
       if(ok){
	     String msg=_welcome.getMessage();
 %>
          <p>
          Message: <%= msg %> 

<%

       }else{
%>
         <p>
         Failed to execute
<%
       }

    }
  if(_welcome!=null){
       _welcome.remove();
       _welcome=null;
  }


  }catch(Exception e){
%>
      Exception: <%= e.getMessage() %>
<%
  }
%>
</BODY>
</HTML>
