<%
  String name = (String)request.getAttribute("name");
  String SSN = (String)request.getAttribute("SSN");
  String address = (String)request.getAttribute("address");
  String refreshBalance = (String)request.getAttribute("refreshBalance");
  String currentBalance = (String)request.getAttribute("currentBalance");
  String transactionalBalance = (String)request.getAttribute("transactionalBalance");
  String progRefreshBalance = (String)request.getAttribute("progRefreshBalance");
  String autoRefreshBalance = (String)request.getAttribute("autoRefreshBalance");
%>
        <html>
          <head>
            <meta http-equiv="Refresh" content="60;URL=/SimpleBank/SimpleBankServlet?SSN=<%=SSN%>&transactionalBalance=<%=transactionalBalance%>">
            <title>Simple Bank Application</title>
          </head>
          <body>
            <table border="1">
            <form method="post" action="/SimpleBank/SimpleBankServlet">
            <tr>
              <td>Social Security Number</td>
              <td>Name</td>
              <td>Address</td>
            </tr>
            <tr>
              <td>
                <%=SSN%>
              </td>
              <td>
                <%=name%>
              </td>
              <td>
                <%=address%>
              </td>
            </tr>
            <tr>
              <td>Current Balance</td>
              <td>Type of Operation</td>
              <td>Amount</td>
            </tr>
            <tr>
              <td rowspan=2>
                Transactional Refresh : $<%=transactionalBalance%><br>
                <input type=submit name="action" value="Get Trans. Balance"><br>
                <br>
                Time-based Refresh : $<%=refreshBalance%><br>
                <br>
                Programmatic Refresh : $<%=progRefreshBalance%><br>
                <input type="submit" name="action" value="Get Prog. Balance"><br>
                <br>
              </td>
              <td>
                <input type=radio checked name=operation value=credit>Credit<br>
                <input type=radio name=operation value=debit>Debit
              </td>
              <td>
                $<input type=text name=amount value=>
                <input type=submit name="action" value="Update">
              </td>
            </tr>
            <tr>
              <td colspan=2>
                Current Balance :
                <%=currentBalance%>
              </td>
            </tr>
            <input type="hidden" name="SSN" value="<%=SSN%>">
            <input type="hidden" name="transactionalBalance" value="<%=transactionalBalance%>">
          </form>
          </table>
          </body>
        </html>

                
         
