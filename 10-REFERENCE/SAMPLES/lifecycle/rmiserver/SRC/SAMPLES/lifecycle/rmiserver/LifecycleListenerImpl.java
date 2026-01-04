/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * 
 */

package samples.lifecycle.rmiserver;

import java.util.Properties;
import java.util.Enumeration;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.*;
import java.io.*;

import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.ServerLifecycleException;
import com.sun.appserv.server.LifecycleEventContext;

/**
 *  LifecycleListenerImpl is an implementation for the LifecycleListener interface.
 *  <p>
 *  Sun ONE Application Server emits five events during its lifecycle -
 *  1. INIT_EVENT: Server is initializing subsystems and setting up the runtime environment.
 *  2. STARTUP_EVENT: Server is starting up applications
 *  3. READY_EVENT: Server started up applications and is ready to service requests
 *  4. SHUTDOWN_EVENT: Server is shutting down applications
 *  5. TERMINATION_EVENT: Server is terminating the subsystems and the runtime environment.
 * 
 *  In this sample, on STARTUP_EVENT, rmiserver is started and 
 *  on SHUTDOWN_EVENT, this rmiserver is stopped.
 *  </p>
 */

public class LifecycleListenerImpl implements LifecycleListener {

    /**
     *  Life cycle event context
     */
    LifecycleEventContext ctx;

    private Process proc;  // process handle for RMI server
    private String policyFile = "nopolicy";  //policy file
    private SampleRemoteInterface lci;

    /** 
     *  Receives a server lifecycle event 
     *  @param event associated event
     *  @throws <code>ServerLifecycleException</code> for exceptional condition.
     */
    public void handleEvent(LifecycleEvent event) 
                         throws ServerLifecycleException {

        ctx = event.getLifecycleEventContext();

	switch(event.getEventType()) {
	    case LifecycleEvent.INIT_EVENT:
                // read the policy.file
                Properties props= (Properties) event.getData();
                try {
                    policyFile=props.getProperty ("policy.file");
                } catch (Exception e) {
                    e.printStackTrace();
                }
		onInitTask();
	  	break;

	    case LifecycleEvent.STARTUP_EVENT:
		onStartTask();
	  	break;

            case LifecycleEvent.READY_EVENT:
                onReadyTask();
                break;

	    case LifecycleEvent.SHUTDOWN_EVENT:
		onShutdownTask();
	  	break;

	    case LifecycleEvent.TERMINATION_EVENT:
		onTerminationTask();
	  	break;
	}

    }

    /**
     *  Task to be carried out in the INIT_EVENT.
     *  Logs a message.
     */
    private void onInitTask() {
        ctx.log("Lifecycle rmiserver sample: INIT_EVENT");
    }

    /**
     *  Tasks to be carried out in the STARTUP_EVENT.
     *  Calls remote message start.
     */
    private void onStartTask() {
        ctx.log("Lifecycle rmiserver sample: STARTUP_EVENT");

        String inputLine;
        InputStream input;
        BufferedReader in;

        try {

          // start a seperate process .. exec
          String javaPath = System.getProperty ("JAVA_HOME")+File.separator+"bin"+File.separator;
          String javaCmd=javaPath;

          if (isWindows())
             javaCmd += "java.exe";
          else 
             javaCmd += "java";

          String classpath=System.getProperty ("java.class.path");
          javaCmd += " -Djava.security.policy="+policyFile+" -classpath "+classpath+ " samples.lifecycle.rmiserver.SampleRMIServer";
          ctx.log("Creating SampleRMIServer");
          proc=Runtime.getRuntime().exec (javaCmd);

          input = proc.getInputStream();
          in = new BufferedReader(new InputStreamReader(input));

          while ((inputLine = in.readLine()) != null) {
             ctx.log(inputLine);
             break;
          }

          String name = "//localhost/SampleRMIServer";
          lci = (SampleRemoteInterface) Naming.lookup(name);
          lci.start();
          while ((inputLine = in.readLine()) != null) {
               ctx.log(inputLine);
               break;
          }
        } catch (Exception e) {
          System.err.println("Client exception: " + e.getMessage());
          e.printStackTrace();
       }
    }

    private boolean isWindows() {
       String osname = System.getProperty("os.name");

       if(osname == null || osname.length() <= 0)
          return false;

       osname  = osname.toLowerCase();
       if ((osname.indexOf("windows") >= 0)  && (File.separatorChar == '\\'))
          return true;

       return false;
    }

    /**
     *  Tasks to be carried out in the READY_EVENT. 
     *  Logs a message.
     */
    private void onReadyTask() {
        ctx.log("Lifecycle rmiserver sample: READY_EVENT");
    }

    /**
     *  Tasks to be carried out in the SHUTDOWN_EVENT. 
     *  Calls remote method stop.
     */
    private void onShutdownTask() {
        ctx.log("Lifecycle rmiserver sample: SHUTDOWN_EVENT");

        try {

            String name = "//localhost/SampleRMIServer";
            lci.stop();

            InputStream input = proc.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
               ctx.log(inputLine);
               break;
            }
            in.close();

            ctx.log("Destroying SampleRMIServer");
            proc.destroy();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  Tasks to be carried out in the TERMINATION_EVENT. 
     *  Log a message.
     */
    private void onTerminationTask() {
        ctx.log("Lifecycle rmiserver sample: TERMINATION_EVENT");
    }
}
