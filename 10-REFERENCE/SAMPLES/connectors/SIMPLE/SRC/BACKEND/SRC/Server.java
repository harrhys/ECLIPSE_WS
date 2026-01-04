/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;


public class Server {

  public static void main(String args[]) {
      ServerSocket ser = null;
      Socket soc = null;
      if( args.length != 1 )
      {
        System.out.println(" USAGE : java Server port");
        System.exit(1);
      }

      try {
        ser = new ServerSocket(Integer.parseInt(args[0]));
        System.out.println(">>>>ServerSocket: "+ser);
        Worker1 worker1 = null;
        System.out.println("waiting for client... ");
        while(true)
        {
             System.out.println(">>>>before accept.");
             soc = ser.accept();
             soc.setSoTimeout(0);
             worker1 = new Worker1(soc);
             worker1.start();

        }
      }
      catch(Exception ex) {
      System.out.println(ex.toString());
      System.exit(1);
    }//catch
  }//main
}//class
