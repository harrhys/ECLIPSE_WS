<h4>Transaction 1</h4>
<p>The two ExampleBean objects are in the same transaction and should be the same.</p>
<p><b>Output:</b></p>
<p><% out.println(request.getAttribute("transaction1")); %></p>

<h4>Transaction 2</h4>
<p>In a new transaction, so the printed bean should be different from the ones in the first transaction.</p>
<p><b>Output:</b></p>
<p><% out.println(request.getAttribute("transaction2")); %></p>

<h4>No Transaction</h4>
<p>Printing the TransactionScoped bean fails because it is out of scope.</p>
<p><b>Output:</b></p>
<p><% out.println(request.getAttribute("no-transaction")); %></p>