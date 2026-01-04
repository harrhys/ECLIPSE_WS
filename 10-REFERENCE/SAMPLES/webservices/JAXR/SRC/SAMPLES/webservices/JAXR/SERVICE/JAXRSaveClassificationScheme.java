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
 * The JAXRSaveClassificationScheme class consists of a main method, a
 * makeConnection method, and an executePublish method.  
 * It creates a classification scheme and publishes it to a registry.
 * 
 * To run this program, use the command 
 * 
 *     ant run-save-scheme
 */
public class JAXRSaveClassificationScheme {
    Connection connection = null;
                
    public JAXRSaveClassificationScheme() {}
        
    public static void main(String[] args) {       
        String queryURL = 
            "http://www-3.ibm.com/services/uddi/v2beta/inquiryapi";
            //"http://uddi.rte.microsoft.com/inquire";
            // For Registry Server, replace localhost with fully
            //  qualified host name if necessary
            //"http://localhost:8080/registry-server/RegistryServerServlet";
        String publishURL = 
            "https://www-3.ibm.com/services/uddi/v2beta/protect/publishapi";
            //"https://uddi.rte.microsoft.com/publish";
            // For Registry Server, replace localhost with fully
            //  qualified host name if necessary
            //"http://localhost:8080/registry-server/RegistryServerServlet";
        // Edit to provide your own username and password
        String username = "";
        String password = "";
        
        JAXRSaveClassificationScheme jscs = new JAXRSaveClassificationScheme();
        
        jscs.makeConnection(queryURL, publishURL);
        
        jscs.executePublish(username, password);                                                                     
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
     * Creates a classification scheme and saves it to the
     * registry.
     *
     * @param username  the username for the registry
     * @param password  the password for the registry
     */
    public void executePublish(String username, 
        String password) {

        RegistryService rs = null;
        BusinessLifeCycleManager blcm = null;
        BusinessQueryManager bqm = null;

        try 
        {
            rs = connection.getRegistryService();
            blcm = rs.getBusinessLifeCycleManager();
            bqm = rs.getBusinessQueryManager();
            System.out.println("Got registry service, query " +
                "manager, and life cycle manager");

            // Get authorization from the registry
            PasswordAuthentication passwdAuth =
                new PasswordAuthentication(username, 
                    password.toCharArray());

            Set creds = new HashSet();
            creds.add(passwdAuth);
            connection.setCredentials(creds);
            System.out.println("Established security credentials");

            // Create classification scheme
            ClassificationScheme postalScheme =
                blcm.createClassificationScheme("MyPostalAddressScheme", 
                "A ClassificationScheme for My PostalAddressMappings");

            ClassificationScheme uddiOrgTypes = 
                bqm.findClassificationSchemeByName(null, "uddi-org:types");

            if (uddiOrgTypes != null) {
                // 3rd argument should be postalAddress, not 
                // categorization - IBM registry not accepting this yet
                Classification classification = 
                    blcm.createClassification(uddiOrgTypes,
                         "postalAddress", "categorization");

                postalScheme.addClassification(classification);
                
                // Set link to location of postal scheme (fictitious)
                //   so others can look it up
                /*ExternalLink externalLink = 
                    (ExternalLink) 
         blcm.createExternalLink("http://www.mycompany.com/mypostalscheme.html",
                        "My PostalAddress Scheme");
                postalScheme.addExternalLink(externalLink);
                */
                // Add scheme and save it to registry
                // Retrieve key if successful
                Collection schemes = new ArrayList();
                schemes.add(postalScheme);
                BulkResponse br = blcm.saveClassificationSchemes(schemes);
                if (br.getStatus() == JAXRResponse.STATUS_SUCCESS) {
                    System.out.println("Saved PostalAddress " +
                        "ClassificationScheme");
                    Collection schemeKeys = br.getCollection();
                    Iterator keysIter = schemeKeys.iterator();
                    while (keysIter.hasNext()) {
                        javax.xml.registry.infomodel.Key key =
                            (javax.xml.registry.infomodel.Key)keysIter.next();
                        System.out.println("The postalScheme key is " + 
                            key.getId());
                        System.out.println("Use this key as the scheme uuid " +
                            "in the postalconcepts.xml file\n  and as the " +
                            "argument to run-publish-postal and " +
                            "run-query-postal");                        
                    }                 
                } else {
                    Collection exceptions = br.getExceptions();
                    Iterator excIter = exceptions.iterator();
                    Exception exception = null;
                    while (excIter.hasNext()) {
                        exception = (Exception) excIter.next();
                        System.err.println("Exception on save: " + 
                        exception.toString());
                    }
                }
            } else {
                System.out.println("uddi-org:types not found. Unable to " +
                    "save PostalAddress scheme");
            }                                                                                                                                                                                   
        } catch (JAXRException jaxe) {
            jaxe.printStackTrace();
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
