/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * 
 */

package samples.lifecycle.simple;
import java.util.Properties;
import java.util.Date;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.TextMessage;
import javax.jms.TopicConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicPublisher;
import javax.jms.TopicSubscriber;
import javax.jms.JMSException;
import javax.jms.DeliveryMode;

import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.ServerLifecycleException;
import com.sun.appserv.server.LifecycleEventContext;

/**
 *  LifecycleTopic is an implementation for the LifecycleListener interface.
 *  <p>
 *  Sun ONE Application Server emits five events during its lifecycle -
 *  1. INIT_EVENT: Server is initializing subsystems and setting up the runtime environment.
 *  2. STARTUP_EVENT: Server is starting up applications
 *  3. READY_EVENT: Server started up applications and is ready to service requests
 *  4. SHUTDOWN_EVENT: Server is shutting down applications
 *  5. TERMINATION_EVENT: Server is terminating the subsystems and the runtime environment.
 * 
 *  In this sample, on STARTUP_EVENT, a thread is started which sends a simple JMS message to 
 *  sampleTopic every minute. On SHUTDOWN_EVENT, this thread is stopped.
 *  </p>
 */

public class LifecycleListenerImpl implements LifecycleListener {

    /**
     *  Life cycle event context
     */
    LifecycleEventContext ctx;

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
        ctx.log("LifecycleTopic: INIT_EVENT");
    }

    /**
     *  Tasks to be carried out in the STARTUP_EVENT.
     *  Logs a message
     */
    private void onStartTask() {
        ctx.log("LifecycleTopic: STARTUP_EVENT");
    }

    /**
     *  Tasks to be carried out in the READY_EVENT. 
     *  Logs a message.
     */
    private void onReadyTask() {
        ctx.log("LifecycleTopic: READY_EVENT");
    }

    /**
     *  Tasks to be carried out in the SHUTDOWN_EVENT. 
     *  Logs a message
     */
    private void onShutdownTask() {
        ctx.log("LifecycleTopic: SHUTDOWN_EVENT");
    }

    /**
     *  Tasks to be carried out in the TERMINATION_EVENT. 
     *  Log a message.
     */
    private void onTerminationTask() {
        ctx.log("LifecycleTopic: TERMINATION_EVENT");
    }

}
