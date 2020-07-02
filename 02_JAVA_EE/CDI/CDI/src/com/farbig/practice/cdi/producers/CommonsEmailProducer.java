package com.farbig.practice.cdi.producers;

import javax.enterprise.inject.Produces;

import org.apache.commons.mail.SimpleEmail;

public class CommonsEmailProducer {

  @Produces
  public SimpleEmail simpleEmailProducer(){
    return new SimpleEmail();
  }
  
}