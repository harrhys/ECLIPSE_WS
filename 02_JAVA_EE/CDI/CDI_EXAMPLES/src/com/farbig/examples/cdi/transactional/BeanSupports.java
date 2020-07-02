package com.farbig.examples.cdi.transactional;

import javax.transaction.Transactional;

/**
 * @author Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
 */
public class BeanSupports extends BeanBase {

  public String getName() {
    return "Transactional.TxType SUPPORTS";
  }

  public String inside() {
    return "If called inside a transaction context, the managed bean method execution must then continue inside this " +
        "transaction context.";
  }

  public String outside() {
    return "If called outside a transaction context, managed bean method execution must then continue outside a " +
        "transaction context.";
  }

  @Transactional(value = Transactional.TxType.SUPPORTS)
  public void run(StringBuilder builder) {
    super.run(builder);
  }

}
