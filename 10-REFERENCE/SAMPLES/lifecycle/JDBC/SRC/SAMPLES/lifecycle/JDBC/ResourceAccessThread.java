/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 */

package samples.lifecycle.jdbc;

import javax.naming.NamingException;
import javax.naming.Context;

/**
 * Class ResourceAccessThread
 *
 * thread that delegates the resource access to ResourceAccess instance
 * to do the actual work
 *
 * during Lifecycle STARTUP_EVENT, an instance of this is spawned to
 * perform datasource lookup and operations asynchronously.
 */
public class ResourceAccessThread extends Thread {
    private boolean _shutdown = false;
    private Context _namingCtx = null;

    public ResourceAccessThread() {
    }

    public void setInitialContext(Context initCtx) {
        _namingCtx = initCtx;
    }

    public void run() {
        ResourceAccess ra = null;
        ra = new ResourceAccess();
        ra.setInitialContext(_namingCtx);
        while (!_shutdown) {
            ra.doWork();
            try {
                sleep(60000);
            } catch (InterruptedException intrEX) {
            }
        }
    }

    public void stopWork() {
        _shutdown = true;
    }
}

