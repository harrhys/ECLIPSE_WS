<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>


<body  bgcolor="#FFFFFF"> 
<center> 
<hr><br>&nbsp;
<h1> 
<font size="+3" color="#CC0066">Duke's</font> 
<img src="images/duke.books.gif">
<font size="+3" color="black">Bookstore</font> 
</h1>
<jsp:plugin type="applet" code="DigitalClock.class" codebase="applet" jreversion="1.3" align="center" height="25" width="300"
nspluginurl="http://java.sun.com/products/plugin/1.3/plugin-install.html" 
iepluginurl="http://java.sun.com/products/plugin/1.3/jinstall-13-win32.cab#Version=1,3,0,0" >
  <jsp:params>
    <jsp:param name="language" value="<%=request.getLocale().getLanguage()%>" />
    <jsp:param name="country" value="<%=request.getLocale().getCountry()%>" />
    <jsp:param name="bgcolor" value="FFFFFF" />
    <jsp:param name="fgcolor" value="CC0066" />
  </jsp:params>
  <jsp:fallback>
    <p>Unable to start plugin.</p>
  </jsp:fallback>
</jsp:plugin>
</center>
<br>&nbsp;<hr><br>
