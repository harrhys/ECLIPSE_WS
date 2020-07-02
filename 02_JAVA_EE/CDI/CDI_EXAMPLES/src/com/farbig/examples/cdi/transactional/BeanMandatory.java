package com.farbig.examples.cdi.transactional;

import javax.transaction.Transactional;

/**
 * @author Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
 */
public class BeanMandatory extends BeanBase {

  public String getName() {
    return "Transactional.TxType MANDATORY";
  }

  public String inside() {
    return "If called inside a transaction context, managed bean method execution will then continue under that " +
        "context.";
  }

  public String outside() {
    return "If called outside a transaction context, a TransactionalException with a nested " +
        "TransactionRequiredException must be thrown.";
  }

  @Transactional(value = Transactional.TxType.MANDATORY)
  public void run(StringBuilder builder) {
    super.run(builder);
  }

}      
