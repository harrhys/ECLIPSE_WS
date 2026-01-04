//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
package samples.packaging.pkgingE.lib;

import java.util.*;
import javax.servlet.*;
import java.io.*;
import java.net.*;

public class Props {
 static Properties interestProperties = null;

 public static void loadProperties (String fileName) {
        interestProperties = new Properties();
        try {
            URL url = new URL(fileName); 
           interestProperties.load(url.openStream()); 

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        interestProperties.list(System.out);
 }

 public static Properties getProperties() {
        //System.out.println(interestProperties.propertyNames());
    return interestProperties;
 }

}
