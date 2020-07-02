package com.farbig.examples.cdi.transactional;

import javax.transaction.Transactional;

/**
 * @author Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
 */
public class BeanRequired extends BeanBase {
  public String getName() {
    return "Transactional.TxType REQUIRED";
  }

  public String inside() {
    return "If called inside a transaction context, the current transaction context must be suspended, " +
        "a new JTA transaction will begin, the managed bean method execution must then continue inside this " +
        "transaction context, the transaction must be completed, and the previously suspended transaction must be " +
        "resumed.";
  }

  public String outside() {
    return "If called outside a transaction context, the interceptor must begin a new JTA transaction, " +
        "the managed bean method execution must then continue inside this transaction context, " +
        "and the transaction must be completed by the interceptor.";
  }

  @Transactional(value = Transactional.TxType.REQUIRED)
  public void run(StringBuilder builder) {
    super.run(builder);
  }

}
