<h3><% out.println(request.getAttribute("name")); %></h3>

<h4>Inside a transaction</h4>
<p><% out.println(request.getAttribute("inside")); %></p>
<p><b>Invoke method</b>: <% out.println(request.getAttribute("inside-result")); %></p>

<h4>Out of transaction</h4>
<p><% out.println(request.getAttribute("outside")); %></p>
<p><b>Invoke method</b>: <% out.println(request.getAttribute("outside-result")); %></p>

<br/>
<p><a href="index.jsp">Return to main page</a></p>
