/***************************************************************
 * Copyright  2002 Sun Microsystems, Inc. All rights reserved. *
 ***************************************************************/
package samples.jms.simplequeue;

import java.io.*;
import java.text.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.jms.*;
import javax.naming.*;
import java.io.IOException;


public class SimpleQueueServlet extends HttpServlet {

  private static int numOfQ = 4;
  private QueueConnectionFactory targetQCF;
  private QueueConnectionFactory[] QCFactories = new QueueConnectionFactory[numOfQ];
  private Queue targetQ;
  private Queue[] queues = new Queue[numOfQ] ;
  private LinkedList[] links = new LinkedList[numOfQ];
  private String users[]={"A","B","C","D"};
  
  /**
   * The init method looks up the QueueConectionFactory and Queue objects 
   * via JNDI service names and initializes variables 
   *
   * @exception ServletException
   */
  public void init(ServletConfig conf) throws ServletException {
		
    super.init(conf);

    try {
      // create the intial context
      Context initialContext = new InitialContext();

      // look up the connection factory from the object store 
      out("Looking up the queue connection factory from JNDI");
      QCFactories[0] = (QueueConnectionFactory) initialContext.lookup("java:comp/env/jms/sampleQCF");
      QCFactories[1] = (QueueConnectionFactory) initialContext.lookup("java:comp/env/jms/sampleQCF1");
      QCFactories[2] = (QueueConnectionFactory) initialContext.lookup("java:comp/env/jms/sampleQCF2");
      QCFactories[3] = (QueueConnectionFactory) initialContext.lookup("java:comp/env/jms/sampleQCF3");
      
      // look up queue from the object store
      out("Looking up the queue from JNDI");
      queues[0] = (Queue) initialContext.lookup("java:comp/env/jms/sampleQ");
      queues[1] = (Queue) initialContext.lookup("java:comp/env/jms/sampleQ1");
      queues[2] = (Queue) initialContext.lookup("java:comp/env/jms/sampleQ2");
      queues[3] = (Queue) initialContext.lookup("java:comp/env/jms/sampleQ3");
      
      // initialize links array
      for (int i = 0; i < numOfQ; i++ )
	links[i] = new LinkedList ();

    } 
    catch (NamingException e) {
      String msg = "An error was encountered trying to lookup an object from JNDI";
      out(msg);
      e.printStackTrace();
      throw new ServletException(msg, e);
    }
  }

  /**
   * The doGet method handles http GET request. The method displays the 
   * the JSP template when the servlet first gets invoked.
   *  
   * @param req  an HttpServletRequest that contains the request the client has made of the servlet
   * @param res  an HttpServletResponse object that contains the response the servlet sends to the client
   * @exception  ServletException
   * @exception  IOException
   */

  public void doGet (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
      // the 1st time the servlet is invoked, just display the form
      ServletContext context = getServletContext();
      res.setContentType("text/html");  
      RequestDispatcher dispatcher = context.getRequestDispatcher("/SimpleQueue.jsp");  
      dispatcher.include(req, res); 
      return;
  }

  /**
   * The doPost method handles http POST request. The method does the followings to 
   * to process a comming request:<br>
   * - create a queueConnection<br>
   * - create a queueSession<br> 
   * - create a message sender<br>
   * - create a message receiver<br>
   * - send the message to the queue and deliver the message to the receiver<br>
   * - close the connection<br>
   * - finally, send the output to the jsp template<b>
   
   * @param req an HttpServletRequest that contains the request the client has made of the servlet
   * @param res an HttpServletResponse object that contains the response the servlet sends to the client  
   * @exception  ServletException
   * @exception  IOException
   */
  public void doPost (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
      
      QueueSession	session		= null;
      QueueConnection	connection	= null;
      QueueSender	queueSender	= null;
      QueueReceiver	queueReceiver	= null;
      String		successText	= "SUCCESSFUL";
      TextMessage	msgReceived	= null;
      
      String  from = (String) req.getParameter("req");
      String  to   = (String) req.getParameter("res");
      String  mesg = (String) req.getParameter("mesg");
      
      //out("from: " + from); out("to: " + to); out("mesg: " + mesg);

      from = from.toUpperCase();
      to = to.toUpperCase(); 

      boolean found = false;
      for(int i=0; i<users.length; i++){
	if(from.equals(users[i])){
	  found=true;
          break;
	}
      }
      if(!found){
	out("Request type object does not exist");
	return;
      }      
      found = false;
      for (int i=0; i<users.length; i++) {
	if (to.equals(users[i])){
          targetQCF = QCFactories[i];
          targetQ   = queues[i];
	  found = true;
          break;
	}
      }

      if (!found) {
	out("Reponsetype object does not exit");
	return;
      }   

      try{	

	// Creating a QueueConnection to the Message service");
	connection = targetQCF.createQueueConnection();
	
	// Starting the Connection
	connection.start();

        // Creating a session within the connection
	session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

	// Creating a QueueSender 
	queueSender = session.createSender(targetQ);
	
	// Creating a QueueReceiver
	queueReceiver = session.createReceiver(targetQ);
	
	// Building a message text
	TextMessage msgSent = session.createTextMessage();
	msgSent.setText( to + ":" + from + " ["+new Date()+"]: " + mesg);

	// Sending message to the target queue 
	queueSender.send(msgSent);
	
	/* comment the following line to leave the message on the queue. 
	   then use the message queue product's admin tools to verify that
	   the message was placed on the queue. 
	*/
        // Retrieving the next message that arrives within the timeout interval of 2000 miliseconds
	msgReceived = (TextMessage) queueReceiver.receive(2000);
	
	if (msgReceived == null) {
	  out("An error has occurred.  The return message was not received.");
	  successText = "UNSUCCESSFUL";
          return;
	} 
        else {
	  //Retreive the contents of the message.
	  if (msgReceived instanceof TextMessage) {
	    TextMessage txtMsg = (TextMessage) msgReceived;
	    out("\nMessage received: " + txtMsg.getText());
	  }
	}
      } 
      catch (JMSException e) {
	out("An unexpected exception occurred: " + e);
	
	Exception linkedException = e.getLinkedException();
	if (linkedException != null) {
	  out("The linked exception is: " + linkedException);
	}
	e.printStackTrace();
	successText = "UNSUCCESSFUL";
	
      } finally {
	
	/*
	 * Close all of the JMS resources
	 */
	if (queueReceiver != null) {
	  try {
	    out("Closing QueueReceiver");
	    queueReceiver.close();
	  } catch (JMSException e) {
	    out("There was an error closing the receiver");
	    e.printStackTrace();
	  }
	}
	
	if (queueSender != null) {
	  try {
	    out("Closing QueueSender");
	    queueSender.close();
	  } catch (JMSException e) {
	    out("There was an error closing the sender");
	    e.printStackTrace();
	  }
	}
	
	if (session != null) {
	  try {
	    out("Closing session");
	    session.close();
	  } catch (JMSException e) {
	    out("There was an error closing the session");
	    e.printStackTrace();
	  }	
	}
	
	if (connection != null) {
	  try {
	    out("Closing connection");
	    connection.close();
	  } catch (JMSException e) {
	    out("There was an error closing the connection");
	    e.printStackTrace();
	  }
	}
      }
      
      // Send the results of the test to the user
      sendUserResponse(req, res, (TextMessage) msgReceived, successText);
  }
  

  /**
   * The sendUserResponse method sends a reponse page to the user via a JSP template.
   *  
   * @param req  an HttpServletRequest that contains the request the client has made of the servlet
   * @param res  an HttpServletResponse object that contains the response the servlet sends to the client
   * @param msgReceived a TextMessage object that contains a delivered message.
   * @param successText a String object that either contains "SUCCESSFUL" or UNSUCCESSFUL"
   * @exception ServletException
   * @exception IOException
   */
  private void sendUserResponse(HttpServletRequest req, HttpServletResponse res,
				TextMessage msgReceived, String successText) 
    throws ServletException, IOException {
    
      // Get the string containing the message data 
      String msgStr = null;
      try{ 
        if (msgReceived != null)
	   msgStr = msgReceived.getText();
        else { 
           out ("error: Message received is null"); 
           return;
        }
      }
      catch(JMSException j){
	out("exception while getting text from JMS message");
	return;	
      }

      int index = msgStr.indexOf(":");
      if(index == -1){
	out("error : could not find the receiver name in the message received");
	return;
      }

      String to = msgStr.substring(0,index);
      String mesg = msgStr.substring(index+1);
      
      // out("the message recieved was sent to " + to + ". Messg content: " + mesg);
       
      req.setAttribute("successMessage", successText);
      
      /* 
       * Uncomment this block of code if you want to display these info to the user.
       * The SimpleQueue.jsp need to be modified to display these data 
       */ 

      /*
      if (msgReceived != null)  {
	Map attributes = new HashMap();
	try {
	  attributes.put("Text:", msgReceived.getText());
	  attributes.put("JMSCorrelationID:", msgReceived.getJMSCorrelationID());
	  attributes.put("JMSDeliveryMode:", "" + msgReceived.getJMSDeliveryMode());
	  attributes.put("JMSDestination:", "" + msgReceived.getJMSDestination());
	  attributes.put("JMSExpiration:", "" + msgReceived.getJMSExpiration());
	  attributes.put("JMSMessageID:", msgReceived.getJMSMessageID());
	  attributes.put("JMSPriority:", "" + msgReceived.getJMSPriority());
	  attributes.put("JMSRedelivered:", "" + msgReceived.getJMSRedelivered());
	  attributes.put("JMSReplyTo:", "" + msgReceived.getJMSReplyTo());
	  attributes.put("JMSTimestamp:", "" + msgReceived.getJMSTimestamp());
	  attributes.put("JMSType:", msgReceived.getJMSType());

	  java.util.Enumeration enum = msgReceived.getPropertyNames();
	  while (enum.hasMoreElements()) {
	    String name = (String)enum.nextElement();
	    attributes.put(name + ": ", "" + msgReceived.getObjectProperty(name));
	  }
	} 
        catch (JMSException e) {
	  out("An error has occurred while getting Message information");
	  e.printStackTrace();
	}
	
	req.setAttribute("messageAttributes", attributes);
      }
      */

      for (int i=0; i<users.length; i++) {
	if (to.equals(users[i])){
          synchronized (links) {
	    links[i].addFirst(mesg);
	  }
          break;
	}
      }

      req.setAttribute("mesgLinks", links);
     
      out("Dispatching JSP for output"); 
      ServletContext context = getServletContext();
      res.setContentType("text/html");  
      RequestDispatcher dispatcher = context.getRequestDispatcher("/SimpleQueue.jsp");  
      dispatcher.include(req, res);  
      return;
  }

  public void destroy() {
    //out ("calling destroy method");
    super.destroy(); 
    targetQCF = null;
    targetQ   = null;
    for (int i = 0; i < numOfQ; i++ )
	links[i] = null;
    
  }
  
  private void out(String message) {
    System.out.println(message);
  }

}

