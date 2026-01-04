/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;


import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;

public class Debug {
    private static PrintWriter pwriter  = null;
    private String      fileName = "demo_connector.log";
    private static boolean debug  = true;

    public Debug () {
        try {
            this.pwriter = new PrintWriter(System.out); 
            Date date=new Date();
            pwriter.println(" * * * * * * * * * * S t a r t  :  "+date.toString()+"* * * * * * * ");
        } catch (Exception e) {
        }
    }
 

    public static void printDebug(String msg) {
       if (debug) {
          printMessage(msg);
       }
    }


    public static void printMessage(String msg) {
       if (pwriter != null) {
          pwriter.println(msg);
          pwriter.flush();
       }
       else {
          System.out.println("Debug session is not initialized");
       }
    }

    public void close() {
        if (pwriter != null) {
            pwriter.close();
            pwriter = null;
        }
    }
}
