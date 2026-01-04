<% String fromBank = (String) request.getParameter( "from_bank" ); %>
<% String fromAcct = (String) request.getParameter( "from_account_id" ); %>
<% String toBank = (String) request.getParameter( "to_bank" ); %>
<% String toAcct = (String) request.getParameter( "to_account_id" ); %>
<% String amount = (String) request.getParameter( "transfer_amount" ); %>
<% String message = (String) request.getAttribute( "message" ); %>
<% String fromAcctOldBal = (String) request.getAttribute( "from_acct_old_bal" ); %>
<% String fromAcctNewBal = (String) request.getAttribute( "from_acct_new_bal" ); %>
<% String fromAcctAmt = (String) request.getAttribute( "from_acct_amount" ); %>
<% String toAcctAmt = (String) request.getAttribute( "to_acct_amount" ); %>
<% String toAcctOldBal = (String) request.getAttribute( "to_acct_old_bal" ); %>
<% String toAcctNewBal = (String) request.getAttribute( "to_acct_new_bal" ); %>

<HTML>

  <HEAD>
  <TITLE>Global Container-Managed Transaction</TITLE>
  </HEAD>

  <BODY BGCOLOR=#BBCCDD>

    <HR WIDTH=100%>

    <CENTER><H1>Global Container-Managed Transaction</H1></CENTER>

    <HR WIDTH=100%>

    <CENTER><H2>Transfer Funds</H2></CENTER>

    <FORM NAME=GlobalTransaction METHOD=get ACTION="/transactions-globalcmt/GlobalCmtServlet">

    <CENTER>

    <TABLE>
      <TR>
        <TD><FONT SIZE=3 COLOR=blue>Source Bank:</FONT></TD>
        <TD><FONT SIZE=3>
          <SELECT NAME=from_bank SIZE=1>
            <OPTION SELECTED VALUE="Local Bank">Local Bank</OPTION>
            <OPTION VALUE="Foreign Bank">Foreign Bank</OPTION>
          </SELECT></FONT></TD>
      </TR>
      <TR>
        <TD><FONT SIZE=3 COLOR=blue>Source Account ID:</FONT></TD>
        <TD><FONT SIZE=3><INPUT TYPE=text SIZE=25 NAME=from_account_id VALUE=<%= fromAcct%> ></FONT></TD>
        <TD><FONT SIZE=2 COLOR=red>Required</FONT></TD>
      </TR>
      <TR>
        <TD><FONT SIZE=3 COLOR=blue>Recipient Bank:</FONT></TD>
        <TD><FONT SIZE=3>
          <SELECT NAME=to_bank SIZE=1>
            <OPTION SELECTED VALUE="Foreign Bank">Foreign Bank</OPTION>
            <OPTION VALUE="Local Bank">Local Bank</OPTION>
          </SELECT></FONT></TD>
      </TR>
      <TR>
        <TD><FONT SIZE=3 COLOR=blue>Recipient Account ID:</FONT></TD>
        <TD><FONT SIZE=3><INPUT TYPE=text SIZE=25 NAME=to_account_id VALUE=<%= toAcct%> ></FONT></TD>
        <TD><FONT SIZE=2 COLOR=red>Required</FONT></TD>
      </TR>
      <TR>
        <TD><FONT SIZE=3 COLOR=blue>Transfer Amount:</FONT></TD>
        <TD><FONT SIZE=3><INPUT TYPE=text SIZE=10 NAME=transfer_amount VALUE=<%= amount%> ></FONT></TD>
        <TD><FONT SIZE=2 COLOR=red>Required</FONT></TD>
      </TR>
    </TABLE>

    <HR WIDTH="100%">

    <FONT SIZE=3 COLOR=red><%= message%></FONT>

    <HR WIDTH="100%">

    <FONT SIZE=3>
      <INPUT TYPE=submit VALUE="Transfer Funds">
    </FONT>

    <HR WIDTH="100%">

    <TABLE BORDER=2 WIDTH=100%>
      <TR>
        <TH ALIGN=center BGCOLOR=#C0C0C0>Account ID</TH>
        <TH ALIGN=center BGCOLOR=#C0C0C0>Old Balance</TH>
        <TH ALIGN=center BGCOLOR=#C0C0C0>Transfer Amount</TH>
        <TH ALIGN=center BGCOLOR=#C0C0C0>New Balance</TH>
      </TR>
      <TR>
        <TD><%= fromAcct%></TD>
        <TD><%= fromAcctOldBal%></TD>
        <TD><%= fromAcctAmt%></TD>
        <TD><%= fromAcctNewBal%></TD>
      </TR>
      <TR>
        <TD><%= toAcct%></FONT></TD>
        <TD><%= toAcctOldBal%></TD>
        <TD><%= toAcctAmt%></TD>
        <TD><%= toAcctNewBal%></TD>
      </TR>
    </TABLE>

    <HR WIDTH="100%">

    </CENTER>

    </FORM>

  </BODY>

</HTML>
