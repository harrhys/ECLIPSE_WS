

 This is a sample of a Session EJB sending a message to JMS Queue  and prints 
 it to system out.  There is also a mini-app with some jsp pages and a bean 
 and all the deployment descriptors to build an ear file.

1) To build the component::
   -from the src/ directory, type build.sh or build.bat 
    which will create an EJB jar file

2) To build an ear file for the mini-application
   - do step 1) to build the EJB jar, 
     then from the src/test directory do a build.sh or build.bat 
    which will create the asyncSender.ear file in the src/test directory.

3) To DEPLOY the mini app,
   -go to J2ee RI bin/ directory and  add the queue to the RI 
    using the j2ee admin tool

   -to list all the queues and destinations currently run

    j2eeadmin -listJmsDestination    	//will list all current queues
   
   -to creste the queue for the jndi name you bind in the deloyment 
    descriptor, for example if the jndi name is jms/JPS_ORDER_QUEUE run

    j2eeadmin -addJmsDestination jms/JPS_ORDER_QUEUE   queue    //will add the queue used by this application
   
    - Now open deploytool, and deploy the asyncSender.ear 
      inside src/test/ directory

4) to run the mini-app
   point your browser to http://localhost:8000/sendmsg
   and just put in some message into the text field when prompted and 
   it will be passed to the queue and printed out in the terminal running the RI.
