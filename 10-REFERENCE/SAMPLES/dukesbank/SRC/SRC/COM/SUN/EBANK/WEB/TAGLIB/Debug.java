/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */


package com.sun.ebank.web.taglib;

public final class Debug {

    public static final boolean debuggingOn = true;

    public static void print(String msg) {
        if (debuggingOn) {
            System.err.print(msg);
        }
    }

    public static void println(String msg) {
        if (debuggingOn) {
            System.err.println(msg);
        }
    }

    public static void print(Exception e, String msg) {
        print((Throwable)e, msg);
    }

    public static void print(Exception e) {
        print(e, null);
    }

    public static void print(Throwable t, String msg) {
        if (debuggingOn) {
            System.err.println("Received throwable with Message: "+t.getMessage());
            if (msg != null) 
                System.err.print(msg);
            t.printStackTrace();
        }
    }

    public static void print(Throwable t) {
        print(t, null);
    }
}
