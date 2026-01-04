/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class Worker1 extends Thread
{

  Socket soc = null;

  public Worker1(Socket soc)
  {
     super();
     this.soc = soc;
  }

  public void run()
  {
      boolean notForEver = true;
      String str = null;
      Date d;
      ObjectInput objInput = null;

      try {
        System.out.println("socket: "+soc);
        InputStream inputStream = soc.getInputStream();
        objInput = new ObjectInputStream(inputStream);
        OutputStream outputStream = soc.getOutputStream();
        ObjectOutput objOutput = new ObjectOutputStream(outputStream);
        while(notForEver) {
          str = (String) objInput.readObject();
          d = new Date();
          System.out.println(d +" : "+ str );
          if( str.equals("stop")  )
            notForEver = false;
          else
          {
            objOutput.writeObject("Hello "+str);
            objOutput.flush();
          }
      }
      inputStream.close();
      outputStream.close();
      objInput.close();
      objOutput.close();
      soc.close();
      System.out.println("End of JAVA application.");
    }//try
    catch (Exception ex) {
      System.out.println(ex.toString()+"******* socket: "+soc);
    }//catch
  }
}










