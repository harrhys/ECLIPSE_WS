package com.farbig.examples.cdi.transactional;

/**
 * @author Copyright (c) 2013, 2020, Oracle and/or its affiliates. All rights reserved.
 */
public abstract class BeanBase {

  public abstract String getName();

  public abstract String inside();

  public abstract String outside();

  public void run(StringBuilder builder) {
    builder.append("annotated method run successfully.");
  }

}
