/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.bmp.simple.client;
import samples.ejb.bmp.simple.ejb.*;

import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class StorageBinClient {

   public static void main(String[] args) {

       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/SimpleStorageBin");

           StorageBinHome storageBinHome = 
               (StorageBinHome)PortableRemoteObject.narrow(objref, 
                                            StorageBinHome.class);

           objref = initial.lookup("java:comp/env/ejb/SimpleWidget");

           WidgetHome widgetHome = 
               (WidgetHome)PortableRemoteObject.narrow(objref, 
                                            WidgetHome.class);

           String widgetId = "777";
           StorageBin storageBin = storageBinHome.findByWidgetId(widgetId);
           String storageBinId = (String)storageBin.getPrimaryKey();
           int quantity = storageBin.getQuantity();

           Widget widget = widgetHome.findByPrimaryKey(widgetId);
           double price =  widget.getPrice();
           String description = widget.getDescription();

           System.out.println(widgetId  + " " +
              storageBinId + " " +
              quantity + " " +
              price + " " +
              description);

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an exception." );
           ex.printStackTrace();
       }
   } 
} 
