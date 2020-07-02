<h3>Introduction</h3>
<p>The <code>javax.transaction.Transactional</code> annotation provides an application with the ability to declaratively control
  transaction boundaries on CDI managed beans, as well as classes defined as managed beans by the Java EE
  specification, at both the class and method level where method level annotations override those at the class
  level.</p>
<p>The values for the <code>@Transactional</code> annotation are <code>MANDATORY, NEVER, NOT_SUPPORTED, REQUIRED,
  REQUIRES_NEW and SUPPORTS</code>.</p>

<h3>Description of the Example</h3>
<p>This sample displays how to inject a bean with a method annotated with the <br/><code>@Transactional(value =
  Transactional.TxType.MANDATORY,NEVER,NOT_SUPPORTED,REQUIRED, REQUIRES_NEW and SUPPORTS)</code> annotation. <br/>When running the
  sample, the annotated method is invoked outside and within the transaction.</p>

<h3>Use This Example</h3>
<p>Choose a value for the <code>@Transactional</code> annotation:</p>
<form name="myForm" method="GET" action="transactional">
  <p>
    <input name="TransactionalInterceptor" type="radio" value="MANDATORY"/>MANDATORY<br/>
    <input name="TransactionalInterceptor" type="radio" value="NEVER"/>NEVER<br/>
    <input name="TransactionalInterceptor" type="radio" value="NOT_SUPPORTED"/>NOT_SUPPORTED<br/>
    <input name="TransactionalInterceptor" type="radio" value="REQUIRED"/>REQUIRED<br/>
    <input name="TransactionalInterceptor" type="radio" value="REQUIRES_NEW"/>REQUIRES_NEW<br/>
    <input name="TransactionalInterceptor" type="radio" value="SUPPORTS"/>SUPPORTS<br/><br>
    <input type="submit" name="submit" value="submit"/>
  </p>
</form>
