/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import javax.resource.spi.*;
import java.lang.Object;

// CustomCodeBegin globalScope

// CustomCodeEnd

/**
 *The ConnectionRequestInfo interface enables a resource adapter to pass its own request
 *specific data structure across the connection request flow.
 */
public class CometConnectionRequestInfo implements ConnectionRequestInfo {

  private String user=null;
  private String password=null;

  // CustomCodeBegin classScope

  /*example code:
  private language=null;

  public void setLanguage(String language){
       this.language=language;
  }

  public String getLanguage(){
       return this.language;
  }
  */
  // CustomCodeEnd

  /**
   *Constructor
   *@param user user name
   *@param password  user password
   */
  public CometConnectionRequestInfo(String user, String password) {
	System.out.println("In CometConnectionRequestInfo ctor");
        this.user = user;
        this.password = password;
  }

  /**
   *get user
   *@return String - get user
   */
  public String getUser() {
        return user;
  }

  /**
   *get password
   *@return String - password
   */
  public String getPassword() {
        return password;
  }

  /**
   *Checks whether this instance is equal to another.
   *@param obj other object
   *@return boolean - True if the two instances are equal.
   */
  public boolean equals(Object obj){
        boolean equal=false;

        if (obj != null) {
          if (obj instanceof CometConnectionRequestInfo) {
              CometConnectionRequestInfo other = (CometConnectionRequestInfo) obj;
              
              // ToDo: Add service specific code here
          	  // CustomCodeBegin hashCode
              equal=Util.isEqual(this.user, other.user) &&
                    Util.isEqual(this.password, other.password);
		          // CustomCodeEnd

          }
        }
        return equal;

  }

  /**
   *Returns the hashCode of the ConnectionRequestInfo.
   *@return int - hash code os this instance
   */
  public int hashCode(){
     int hashcode=(new String("")).hashCode();
     if(user!=null)
        hashcode=hashcode+user.hashCode();
     if(password!=null)
        hashcode=hashcode+password.hashCode();

     // ToDo: Add service specific code here
		 // CustomCodeBegin hashCode

		 // CustomCodeEnd
     
     return hashcode;
  }

}
