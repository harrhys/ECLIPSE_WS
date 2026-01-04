<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ include file="initdestroy.jsp" %>
<%@ page import="java.util.*" %>
<%
     ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
     if (messages == null) {
         Locale locale=request.getLocale();

      messages = ResourceBundle.getBundle("samples.webapps.bookstore.bookstore2.web.messages.BookstoreMessages", locale); 
      session.setAttribute("messages", messages);
   }
%>
<html>
<head><title>Duke's Bookstore</title></head>
<%@ include file="banner.jsp" %>
<jsp:useBean id="bookDB" class="samples.webapps.bookstore.bookstore2.ejb.database.BookDB" scope="page" >
  <jsp:setProperty name="bookDB" property="database" value="<%=bookDBEJB%>" />
</jsp:useBean>


<p><b><%=messages.getString("What")%></b></p>
<% 
   bookDB.setBookId("203");
    BookDetails bd = bookDB.getBookDetails();
%>
<blockquote><p><em><a href="<%=request.getContextPath()%>/bookdetails?bookId=203"><%=bd.getTitle()%></a></em> 
<%=messages.getString("Talk")%></blockquote>
     

<p><b><a href="<%=request.getContextPath()%>/catalog"><%=messages.getString("Start")%></a></b>
                    
                    
<br>&nbsp;<br>&nbsp;<br>&nbsp;

<center><em>Copyright &copy; 2001 Sun Microsystems, Inc. </em></center>

</body>
</html>
