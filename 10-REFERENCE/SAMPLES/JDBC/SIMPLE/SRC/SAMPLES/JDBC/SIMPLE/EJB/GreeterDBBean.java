/**
  Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
  */

package samples.jdbc.simple.ejb;  

import java.util.*; 
import java.io.*; 

/**
 * A simple stateless session bean which generates the greeting string for jdbc-simple 
 * application. This bean implements the business method as declared by the remote interface.
 */

public class GreeterDBBean implements javax.ejb.SessionBean { 

  private javax.ejb.SessionContext m_ctx = null; 
 
  /** 
    * Sets the session context. Required by EJB spec.  
	* @param ctx A SessionContext object.  
	*/

  public void setSessionContext(javax.ejb.SessionContext ctx) { 
    m_ctx = ctx; 
  } 

  /** 
    * Creates a bean. Required by EJB spec.  
	*/

  public void ejbCreate() {
    System.out.println("ejbCreate() on obj " + this); 
  } 

  /** 
    * Removes a bean. Required by EJB spec.  
	*/

  public void ejbRemove() { 
    System.out.println("ejbRemove() on obj " + this); 
  } 

  /** 
    * Loads the state of the bean from secondary storage. Required by EJB spec.  
	*/
  public void ejbActivate() { 
    System.out.println("ejbActivate() on obj " + this); 
  } 

  /**
    * Keeps the state of the bean to secondary storage. Required by EJB spec.
	*/
  public void ejbPassivate() { 
    System.out.println("ejbPassivate() on obj " + this); 
  } 

  /** 
    * Required by EJB spec.  
	*/
  public void GreeterDBBean() { 
  } 


  /**
    * Returns the Greeting String based on the time
    * @return the Greeting String.
    */
  public String getGreeting() throws java.rmi.RemoteException {
    System.out.println("GreeterDB EJB is determining message..."); 
    String message = null; 
    Calendar calendar = new GregorianCalendar(); 
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY); 
    if(currentHour < 12) message = "morning"; 
    else { 
      if( (currentHour >= 12) && 
       (calendar.get(Calendar.HOUR_OF_DAY) < 18)) message = "afternoon"; 
      else message = "evening"; 
    } 
    System.out.println("- Message determined successfully");
    return message; 
  } 
} 
