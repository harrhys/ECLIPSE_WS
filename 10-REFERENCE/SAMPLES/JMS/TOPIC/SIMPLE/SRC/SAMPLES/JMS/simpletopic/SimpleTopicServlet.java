/***************************************************************
 * Copyright  2002 Sun Microsystems, Inc. All rights reserved. *
 ***************************************************************/
package samples.jms.simpletopic;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.jms.*;
import javax.naming.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class SimpleTopicServlet extends HttpServlet {

  private int numOfUsers = 4;
  private TopicConnectionFactory TCFactory;
  private Topic topic;
  private LinkedList[] links = new LinkedList[numOfUsers];
  
  private static final String LOOKUP_STRING_FACTORY = "java:comp/env/jms/sampleTCF";
  private static final String LOOKUP_STRING_TOPIC   = "java:comp/env/jms/sampleTopic";
  private static final String topicTemplate = "/SimpleTopic.jsp";  


  /**
   * The init method looks up the TopicConectionFactory and Topic objects 
   * via JNDI service names and initializes variables 
   *
   * @exception ServletException
   */

  public void init(ServletConfig conf) throws ServletException {
    
    super.init(conf);
    
    try {
      
      // create the intial contex
      Context initialContext = new InitialContext();
      
      // Look up the topic connection factory from JNDI
      TCFactory = (TopicConnectionFactory) initialContext.lookup(LOOKUP_STRING_FACTORY);
      
      // Look up the topic from JNDI
      topic = (Topic) initialContext.lookup(LOOKUP_STRING_TOPIC);
      //out(LOOKUP_STRING_TOPIC + ": " + topic);

      // initialize links array
      for (int i = 0; i < numOfUsers; i++ )
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
      RequestDispatcher dispatcher = context.getRequestDispatcher(topicTemplate);  
      dispatcher.include(req, res); 
      return;
  }

  /**
   * The doPost method handles http POST request. The method does the followings to 
   * to process a comming request:<br>
   * - create a topicConnection<br>
   * - create a topicSession<br> 
   * - create a publisher<br>
   * - create one or more subcribers depending on the request<br>
   * - Publish the message to the topic and deliver the message to the subcribers<br>
   * - close the connection<br>
   * - finally, send the output to the jsp template<b>
   
   * @param req an HttpServletRequest that contains the request the client has made of the servlet
   * @param res an HttpServletResponse object that contains the response the servlet sends to the client  
   * @exception  ServletException
   * @exception  IOException
   */
  public void doPost (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
		
    TopicSession	tSession     = null;
    TopicConnection	tConnection  = null;
    TopicPublisher	tPublisher   = null;
    TopicSubscriber[]	tSubscribers  = new TopicSubscriber[numOfUsers] ;
    TextMessage	        msgReceived  = null;
    String[]            users  = new String[numOfUsers];  
    int i = 0;
    
    String  mesg = (String) req.getParameter("mesg");    
    for (i=0; i < numOfUsers; i++) {
      String name = (String) req.getParameter("subcriber" + (i+1));     
      if (name != null)
	name = name.toLowerCase();     
      users[i] = name;
    }
    
    try {	
      /*
       * Build and send a JMS message
       */
      
      // Creating TopicConnection using the topic connection factory;
      tConnection = TCFactory.createTopicConnection();
      
      // Creating the TopicSession using the Connection
      out("Creating the TopicSession using the Connection");
      tSession = tConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
      
      //"Creating a TopicPublisher using the TopicSession
      out("Creating a TopicPublisher using the TopicSession");
      tPublisher = tSession.createPublisher(topic);
      
      // Creating one or more TopicSubscriber using the same TopicSession
      out("Creating one or more  TopicSubscriber using the TopicSession");
      for (i=0; i < numOfUsers; i++ ) {
	if ( users[i] != null) {
	  tSubscribers[i] = tSession.createSubscriber(topic);
	}
      }

      // Starting the Connection
      out("Starting the Connection");
      tConnection.start();
      
      // Building a message text
      TextMessage msgPublised = tSession.createTextMessage();
      msgPublised.setText("[" + new Date()+"] : " + mesg);
      
      // publishing the message to the topic destination
      out("Sending message to " + topic.getTopicName() );
      tPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      tPublisher.publish(msgPublised);
      
      // getting the message from the topic
      String msgTxt;
      out("getting the message from the topic");
      for (i=0; i < numOfUsers; i++ ) {
	if ( users[i] != null) {
	  msgReceived = (TextMessage) tSubscribers[i].receive(2000);
	 
	  if (msgReceived != null) {
	    msgTxt =  msgReceived.getText();
	    synchronized (links) {
	      links[i].addFirst(msgTxt);
	    }
	  }
	}
      }    
    } 
    catch (JMSException e) {
      out("An exception occurred: " + e.toString());
      
      Exception linkedException = e.getLinkedException();
      if (linkedException != null) {
	out("The linked exception is: " + linkedException.toString());
      }
      e.printStackTrace();   
    } 
    finally {  
      /*
       * Close all of the JMS resources
       */

      if (tConnection != null) {
	try {
	  out("Closing TopicConnection");
	  tConnection.close();
	}
	catch (JMSException e) {
	  out("There was an error closing the TopicConnection.\n");
	  e.printStackTrace();
	}
      }
    }

    // display the message to the user
    sendUserResponse(req, res, links); 
  }

 
  /**
   * The sendUserResponse method handles http GET request. The method sends reponse
   * page to the user via a JSP template.
   *  
   * @param req  an HttpServletRequest that contains the request the client has made of the servlet
   * @param res  an HttpServletResponse object that contains the response the servlet sends to the client
   * @param links  an array of LinkedList object that contains received message for each subscibers.
   * @exception ServletException
   * @exception IOException
   */
  private void sendUserResponse(HttpServletRequest req, HttpServletResponse res, LinkedList[] links) 
    throws ServletException, IOException {
            
    req.setAttribute("mesgLinks", links);
    out("Dispatching JSP for output"); 
    ServletContext context = getServletContext();
    res.setContentType("text/html");  
    RequestDispatcher dispatcher = context.getRequestDispatcher(topicTemplate);  
    dispatcher.include(req, res);  
    return;
    
  }


  public void destroy() {
    
    super.destroy();
    topic     = null;
    TCFactory = null;    
  }
  
  private void out(String message) {
    System.out.println(message);
  }
}
