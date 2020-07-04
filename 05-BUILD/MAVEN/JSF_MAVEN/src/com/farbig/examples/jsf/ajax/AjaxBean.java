package com.farbig.examples.jsf.ajax;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class AjaxBean {
  private String time;

  public String getTime() {
    return time;
  }

  public void showTime() {
    time = (new Date(System.currentTimeMillis())).toString();
  }
}
