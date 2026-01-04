/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 * 
 */
package samples.webservices.jaxr.service;
import javax.xml.registry.*; 
import javax.xml.registry.infomodel.*; 
import java.net.*;
import java.security.*;
import java.util.*;

/**
 * The JAXRDeleteScheme class consists of a main method, a 
 * makeConnection method, a createSchemeKey method, and an executeRemove 
 * method. It finds and deletes the classification scheme that the 
 * JAXRSaveClassificationScheme program created.  Specify the string key 
 * ID value returned by the JAXRSaveClassificationScheme program.
 * 
 * To run this program, use the command 
 * 
 *     ant run-delete-scheme -Dkey-string=<value>
 */
public class JAXRDeleteScheme {
    Connection connection = null;
    RegistryService rs = null;

    public JAXRDeleteScheme() {}

    public static void main(String[] args) {
        String queryURL = 
            "http://www-3.ibm.com/services/uddi/v2beta/inquiryapi";
            //"http://uddi.rte.microsoft.com:/inquire";
            // For Registry Server, replace localhost with fully
            //  qualified host name if necessary
            //"http://localhost:8080/registry-server/RegistryServerServlet";
        String publishURL = 
            "https://www-3.ibm.com/services/uddi/v2beta/protect/publishapi";
            //"https://uddi.rte.microsoft.com/publish";
            // For Registry Server, replace localhost with fully
            //  qualified host name if necessary
            //"http://localhost:8080/registry-server/RegistryServerServlet";
        javax.xml.registry.infomodel.Key key = null;
        // Edit to provide your own username and password
        // Defaults for Registry Server are testuser/testuser
        String username = "";
        String password = "";

        if (args.length < 1) {
            System.out.println("Usage: ant " +
                "run-delete-scheme -Dkey-string=<value>");
            System.exit(1);
        }

        String keyString = new String(args[0]);
        System.out.println("Key string is " + keyString);

        JAXRDeleteScheme jd = new JAXRDeleteScheme();

        jd.makeConnection(queryURL, publishURL);
        
        key = jd.createSchemeKey(keyString);

        if (key != null) {
            jd.executeRemove(key, username, password);
        } else {
            System.out.println("Key not found, nothing to remove");
        }
    }
    
    /**
     * Establishes a connection to a registry.
     *
     * @param queryUrl  the URL of the query registry
     * @param publishUrl        the URL of the publish registry
     */
    public void makeConnection(String queryUrl, 
        String publishUrl) {

        /*
         * Edit to provide your own proxy information
         *  if you are going beyond your firewall.
         * Host format: "host.subdomain.domain.com".
         * Port is usually 8080.
         * Registry Server ignores these values.
         */
        String httpProxyHost = "";
        String httpProxyPort = "8080";
        String httpsProxyHost = "";
        String httpsProxyPort = "8080";

        /*
         * Define connection configuration properties. 
         * To delete, you need both the query URL and the 
         * publish URL.
         */
        Properties props = new Properties();
        props.setProperty("javax.xml.registry.queryManagerURL",
            queryUrl);
        props.setProperty("javax.xml.registry.lifeCycleManagerURL", 
            publishUrl);
        props.setProperty("com.sun.xml.registry.http.proxyHost", 
            httpProxyHost);
        props.setProperty("com.sun.xml.registry.http.proxyPort", 
            httpProxyPort);
        props.setProperty("com.sun.xml.registry.https.proxyHost", 
            httpsProxyHost);
        props.setProperty("com.sun.xml.registry.https.proxyPort", 
            httpsProxyPort);

        try {
            // Create the connection, passing it the 
            // configuration properties
            ConnectionFactory factory = 
                ConnectionFactory.newInstance();
            factory.setProperties(props);
            connection = factory.createConnection();
            System.out.println("Created connection to registry");
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {}
            }
        }
    }

    /**
     * Creates a Key object from the user-supplied string.
     *
     * @param keyStr    the key of the published organization
     *
     * @return  the key of the organization found
     */
    public javax.xml.registry.infomodel.Key createSchemeKey(String keyStr) {

        BusinessLifeCycleManager blcm = null;
        javax.xml.registry.infomodel.Key schemeKey = null;

        try {
            rs = connection.getRegistryService();
            blcm = rs.getBusinessLifeCycleManager();
            System.out.println("Got registry service and " +
                "life cycle manager");
            
            schemeKey = blcm.createKey(keyStr);
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {
                    System.err.println("Connection close failed");
                }
            }
        }
        return schemeKey;
    }

    /**
     * Removes the classification scheme with the specified key value.
     *
     * @param key       the Key of the organization
     * @param username  the username for the registry
     * @param password  the password for the registry
     */
    public void executeRemove(javax.xml.registry.infomodel.Key key,
        String username, String password) {

        BusinessLifeCycleManager blcm = null;
    
        try {
            blcm = rs.getBusinessLifeCycleManager();

            // Get authorization from the registry
            PasswordAuthentication passwdAuth =
                new PasswordAuthentication(username, 
                    password.toCharArray());

            Set creds = new HashSet();
            creds.add(passwdAuth);
            connection.setCredentials(creds);
            System.out.println("Established security credentials");

            String id = key.getId();
            System.out.println("Deleting classification scheme with id " + id);
            Collection keys = new ArrayList();
            keys.add(key);
            BulkResponse response = blcm.deleteClassificationSchemes(keys);
            Collection exceptions = response.getExceptions();
            if (exceptions == null) {
                System.out.println("Classification scheme deleted");
                Collection retKeys = response.getCollection();
                Iterator keyIter = retKeys.iterator();
                javax.xml.registry.infomodel.Key schemeKey = null;
                if (keyIter.hasNext()) {
                    schemeKey = 
                        (javax.xml.registry.infomodel.Key) keyIter.next();
                    id = schemeKey.getId();
                    System.out.println("Classification scheme key was " + id);
                }
            } else {
                Iterator excIter = exceptions.iterator();
                Exception exception = null;
                while (excIter.hasNext()) {
                    exception = (Exception) excIter.next();
                    System.err.println("Exception on delete: " + 
                    exception.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally  {
            // At end, close connection to registry
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {}
            }
        }
    }
}
