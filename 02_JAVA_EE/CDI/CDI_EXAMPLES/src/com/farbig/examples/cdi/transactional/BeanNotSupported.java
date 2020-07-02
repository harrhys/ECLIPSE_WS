package com.farbig.examples.cdi.transactional;

import javax.transaction.Transactional;

/**
 * @author Copyright (c) 2015, 2020, Oracle and/or its affiliates. All rights reserved.
 */
public class BeanNotSupported extends BeanBase {

  public String getName() {
    return "Transactional.TxType NOT_SUPPORTED";
  }

  public String inside() {
    return "If called inside a transaction context, the current transaction context must be suspended, " +
        "the managed bean method execution must then continue outside a transaction context, " +
        "and the previously suspended transaction must be resumed by the interceptor that suspended it after the " +
        "method execution has completed.";
  }

  public String outside() {
    return "If called outside a transaction context, managed bean method execution must then continue outside a " +
        "transaction context.";
  }

  @Transactional(value = Transactional.TxType.NOT_SUPPORTED)
  public void run(StringBuilder builder) {
    super.run(builder);
  }

}
