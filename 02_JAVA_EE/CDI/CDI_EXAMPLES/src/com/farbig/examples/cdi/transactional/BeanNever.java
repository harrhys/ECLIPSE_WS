package com.farbig.examples.cdi.transactional;

import javax.transaction.Transactional;

/**
 * @author Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
 */
public class BeanNever extends BeanBase {

  public String getName() {
    return "Transactional.TxType NEVER";
  }

  public String inside() {
    return "If called inside a transaction context, a TransactionalException with a nested " +
        "InvalidTransactionException must be thrown.";
  }

  public String outside() {
    return "If called outside a transaction context, managed bean method execution must then continue outside a " +
        "transaction context.";
  }

  @Transactional(value = Transactional.TxType.NEVER)
  public void run(StringBuilder builder) {
    super.run(builder);
  }

}
