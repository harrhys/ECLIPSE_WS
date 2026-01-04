/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 */

package samples.lifecycle.jdbc;

import com.sun.appserv.server.*;

/**
 * Class  LCLWithResourceAccessInThread
 *
 * Lifecycle listener that handles <b> asychronous </b> tasks
 * STARTUP_EVENT spawns a thread from which Pointbase data resource 
 * is accessed. 
 * SHUTDOWN_EVENT is where the thread spawned during STARTUP_EVENT is
 * gracefully cleanup
 * 
 */
public class LCLWithResourceAccessInThread implements LifecycleListener {
    LifecycleEventContext ctx = null;
    Thread th1 = null;

    public void handleEvent(LifecycleEvent event) throws ServerLifecycleException {

        ctx = event.getLifecycleEventContext();

        switch (event.getEventType()) {
            case LifecycleEvent.INIT_EVENT:
                ctx.log("[samples.lifecyclemodules.resourceaccess.LCLWithResourceAccessInThread.INIT_EVENT] :: INIT_EVENT");
                // Do any initialization tasks
                break;
            case LifecycleEvent.STARTUP_EVENT:
                ctx.log("[samples.lifecyclemodules.resourceaccess.LCLWithResourceAccessInThread.STARTUP_EVENT] :: STARTUP_EVENT");
                // Do any tasks that need to be done just before the application
                // is started
                th1 = new ResourceAccessThread();
                ((ResourceAccessThread) th1).setInitialContext(ctx.getInitialContext());
                th1.start();
                break;
            case LifecycleEvent.READY_EVENT:
                ctx.log("[samples.lifecyclemodules.resourceaccess.LCLWithResourceAccessInThread.READY_EVENT] :: READY_EVENT");
                // Do any tasks that need to be done just after the application
                // is started
                break;
            case LifecycleEvent.SHUTDOWN_EVENT:
                ctx.log("[samples.lifecyclemodules.resourceaccess.LCLWithResourceAccessInThread.SHUTDOWN_EVENT] :: SHUTDOWN_EVENT");
                // Do any tasks that need to be done just before the application is 
                // brought down (OR stopped)
                ((ResourceAccessThread) th1).stopWork();
                try {
                    th1.join();
                } catch (InterruptedException intrEX) {
                    ctx.log("[samples.lifecyclemodules.resourceaccess.LCLWithResourceAccessInThread.SHUTDOWN_EVENT] :: "+intrEX);
                    intrEX.printStackTrace();
                }
                break;
            case LifecycleEvent.TERMINATION_EVENT:
                ctx.log("[samples.lifecyclemodules.resourceaccess.LCLWithResourceAccessInThread.TERMINATION_EVENT] :: TERMINATION_EVENT");
                // Do any tasks that need to be done just before the application server
                // is terminated (OR stopped)
                break;
        }
    }
}

