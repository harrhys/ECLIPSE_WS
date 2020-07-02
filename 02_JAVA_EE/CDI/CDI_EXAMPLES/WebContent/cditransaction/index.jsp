<h3>TransactionScoped Annotation Introduction</h3>

<p>The <code>javax.transaction.TransactionScoped</code> annotation provides the ability to specify a standard CDI scope to
  define bean instances whose lifecycle is scoped to the currently active JTA transaction. This annotation has no
  effect on classes which have non-contextual references, such those defined as managed beans by the Java EE
  specification.</p>

<h3>Description of the Example</h3>
<p>ExampleBean is annotated with the TransactionScoped annotation and injected to bean1 and bean2 in the servlet. There are three scenarios to
  demonstrate the new scope:</p>
  <ul>
    <li>Begin a UserTransaction and print bean1 and bean2 (both are ExampleBean). They should show the same output. </li>
    <li>Begin another UserTransaction and print bean1. This instance should differ from the first scenario since the beans are in different transactions. </li>
    <li>No transaction. Printing bean1 causes an exception because it is out of scope.</li>
  </ul>
<p>When a transaction is committed or rolled back, the beans are destroyed. You can view the command prompt output to see the related messages.</p>

<h3>Use This Example</h3>
<form name="myForm" method="GET" action="transaction">
  <p>Click the button to run the three scenarios mentioned above.</p>
  <p><input type="submit" name="submit" value="submit"/></p>
<form>