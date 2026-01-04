<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<head>
<meta http-equiv="refresh" content="5">
<title> Cache Tag demo </title>
</head>
<html>
<%@ page session="false"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="sunone" uri="Sun ONE Application Server Tags" %>

<% java.util.Date date = new Date(); %>

<hr />
<h3> This segment is not cached </h3>
Time now is : <%= date %>

<hr />
<%
  boolean dontcache = false;
  String nc = request.getParameter("nocache");
  if (nc != null)
      dontcache = true;
%>
       
<sunone:cache nocache="<%= dontcache %>" timeout="20">
<h3>Cached segment 1 with cache timeout = 20sec </h3>
  
Time here is : <%= date %>
<p>
<a href="JSPCache.jsp?nocache=1">Here</a> is how I would like if I was not cached. 

</sunone:cache>

<hr />
<%
  boolean refresh = false;
  String ref = request.getParameter("refresh");
  if (ref != null)
      refresh = true;
%>
       
<sunone:cache key="foo" refresh="<%= refresh %>" timeout="120">
<h3>Cached segment 2 with timeout = 120sec </h3>
Time here is : <%= date %>
<p>
<a href="JSPCache.jsp?refresh=1">This</a> will refresh me.
</sunone:cache>

<hr />
<sunone:cache timeout="-1">
<h3>Cached segment 3 which will never expire</h3>
Time here is : <%= date %>
</sunone:cache>

<hr />
</html>
