/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 */

package samples.lifecycle.jdbc;

import com.sun.appserv.server.*;

import javax.naming.NamingException;

/**
 * Class - LCLWithResourceAccess
 *
 * this class is a lifecycle listener registered with LifecycleModule
 * method of interest <b> handleEvent </b> that does most of the work
 * at the STARTUP_EVENT - access PointBase (database) resource for
 * insert/update DMLs
 * 
 */
public class LCLWithResourceAccess implements LifecycleListener {
    LifecycleEventContext ctx = null;

    public void handleEvent(LifecycleEvent event) throws ServerLifecycleException {

        ctx = event.getLifecycleEventContext();

        switch (event.getEventType()) {
            case LifecycleEvent.INIT_EVENT:
                ctx.log("[samples.lifecycle.jdbc.LCLWithResourceAccess.INIT_EVENT] :: INIT_EVENT");
                // Do any initialization tasks
                break;
            case LifecycleEvent.STARTUP_EVENT:
                ctx.log("[samples.lifecycle.jdbc.LCLWithResourceAccess.STARTUP_EVENT] :: STARTUP_EVENT");
                // Do any tasks that need to be done just before the application
                // is started
                ResourceAccess ra = null;
                ra = new ResourceAccess();
                ra.setInitialContext(ctx.getInitialContext());
                ra.doWork();
                break;
            case LifecycleEvent.READY_EVENT:
                ctx.log("[samples.lifecycle.jdbc.LCLWithResourceAccess.READY_EVENT] :: READY_EVENT");
                // Do any tasks that need to be done just after the application
                // is started
                break;
            case LifecycleEvent.SHUTDOWN_EVENT:
                ctx.log("[samples.lifecycle.jdbc.LCLWithResourceAccess.SHUTDOWN_EVENT] :: SHUTDOWN_EVENT");
                // Do any tasks that need to be done just before the application is 
                // brought down (OR stopped)
                break;
            case LifecycleEvent.TERMINATION_EVENT:
                ctx.log("[samples.lifecycle.jdbc.LCLWithResourceAccess.TERMINATION_EVENT] :: TERMINATION_EVENT");
                // Do any tasks that need to be done just before the application server
                // is terminated (OR stopped)
                break;
        }
    }
}

