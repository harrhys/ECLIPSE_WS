/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import java.util.*;

/**
 *This class handle the error messages.
 *The messages are read from Messages.properties file(According to i18n)
 */

 public class Messages {
  public static Hashtable messages;

  //message id
  public  static final String  NO_TRANSACTION="1";
  public  static final String  NO_RESULT_SET="2";
  public  static final String  INVALID_CONNECTION="3";
  public  static final String  DESTROYED_CONNECTION="4";
  public  static final String  INVALID_INTERACTION_SPEC="5";
  public  static final String  ILLEGAL_EVENT_TYPE="6";
  public  static final String  PRINCIPAL_DOES_NOT_MATCH="7";
  public  static final String  NO_XATRANSACTION="8";
  public  static final String  NO_PASSWORD_CREDENTIAL="9";
  public  static final String  CONNECTION_FAILED="10";
  public  static final String  SEND_DATA_FAILED="11";
  public  static final String  GET_DATA_FAILED="11";  
  //set message value

  static {
    messages=new Hashtable();
    ResourceBundle rb=ResourceBundle.getBundle("samples.connectors.simple.Messages");
    try {
     messages.put(NO_TRANSACTION,rb.getString("NO_TRANSACTION"));
     messages.put(NO_RESULT_SET,rb.getString("NO_RESULT_SET"));
     messages.put(INVALID_CONNECTION,rb.getString("INVALID_CONNECTION"));
     messages.put(DESTROYED_CONNECTION,rb.getString("DESTROYED_CONNECTION"));
     messages.put(INVALID_INTERACTION_SPEC,rb.getString("INVALID_INTERACTION_SPEC"));
     messages.put(ILLEGAL_EVENT_TYPE,rb.getString("ILLEGAL_EVENT_TYPE"));
     messages.put(PRINCIPAL_DOES_NOT_MATCH,rb.getString("PRINCIPAL_DOES_NOT_MATCH"));
     messages.put(NO_XATRANSACTION,rb.getString("NO_XATRANSACTION"));
     messages.put(NO_PASSWORD_CREDENTIAL,rb.getString("NO_PASSWORD_CREDENTIAL"));
     messages.put(CONNECTION_FAILED,rb.getString("CONNECTION_FAILED"));
     messages.put(SEND_DATA_FAILED,rb.getString("SEND_DATA_FAILED"));
     messages.put(GET_DATA_FAILED,rb.getString("GET_DATA_FAILED"));
    } catch (MissingResourceException mre) {
      System.err.println("Missing parameter in Messages.properties: "+mre);
    }
  }


  /**
   *Get the error message text.
   *@param id  Error code
   *@return String - Error message text
   */

  public static String getMessage(String id) {
       return (String)messages.get(id);
  }
}

