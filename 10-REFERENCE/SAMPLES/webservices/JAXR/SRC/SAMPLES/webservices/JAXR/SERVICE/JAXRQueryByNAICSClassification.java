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
import java.util.*;

/**
 * The JAXRQueryByNAICSClassification class consists of a main 
 * method, a makeConnection method, an executeQuery method, and 
 * some private helper methods. It searches a registry for 
 * information about organizations using an NAICS classification.
 * 
 * To run this program, use the command 
 * 
 *     ant run-query-naics
 *
 * after starting Tomcat.
 */
public class JAXRQueryByNAICSClassification {
    Connection connection = null;

    public JAXRQueryByNAICSClassification() {}

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
        
        JAXRQueryByNAICSClassification jq = 
            new JAXRQueryByNAICSClassification();

        jq.makeConnection(queryURL, publishURL);
        
        jq.executeQuery();
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
         * Edit to provide your own proxy information.
         * Host format: "host.subdomain.domain.com"
         * Port is usually 8080
         */
        String httpProxyHost = "";
        String httpProxyPort = "8080";
        String httpsProxyHost = "";
        String httpsProxyPort = "8080";

        /* 
         * Define connection configuration properties. 
         * For simple queries, you need the query URL.
         * To use a life cycle manager, you need the publish URL.
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
     * Searches for organizations corresponding to an NAICS
     * classification and displays data about them.
     */
    public void executeQuery() {
        RegistryService rs = null;
        BusinessQueryManager bqm = null;
        BusinessLifeCycleManager blcm = null;

        try {
            // Get registry service and managers
            rs = connection.getRegistryService();
            bqm = rs.getBusinessQueryManager();
            blcm = rs.getBusinessLifeCycleManager();
            System.out.println("Got registry service, query " +
                "manager, and lifecycle manager");

            // Find using an NAICS classification
            // Set classification scheme to NAICS
            ClassificationScheme cScheme = 
                bqm.findClassificationSchemeByName(null, 
                    "ntis-gov:naics");

            // Create and add classification
            Classification classification = (Classification)
                blcm.createClassification(cScheme, 
                    "Snack and Nonalcoholic Beverage Bars", "722213");
            Collection classifications = new ArrayList();
            classifications.add(classification);
            BulkResponse response = bqm.findOrganizations(null, 
                null, classifications, null, null, null);
            Collection orgs = response.getCollection();

            // Display information about the organizations found
            Iterator orgIter = orgs.iterator();
            while (orgIter.hasNext()) {
                Organization org = 
                    (Organization) orgIter.next();
                System.out.println("Org name: " + getName(org));
                System.out.println("Org description: " + 
                    getDescription(org));
                System.out.println("Org key id: " + getKey(org));

                // Display primary contact information
                User pc = org.getPrimaryContact();
                if (pc != null) {
                    PersonName pcName = pc.getPersonName();
                    System.out.println(" Contact name: " + 
                        pcName.getFullName());
                    Collection phNums = 
                        pc.getTelephoneNumbers(null);
                    Iterator phIter = phNums.iterator();
                    while (phIter.hasNext()) {
                        TelephoneNumber num = 
                            (TelephoneNumber) phIter.next();
                        System.out.println("  Phone number: " + 
                            num.getNumber());
                    }
                    Collection eAddrs = pc.getEmailAddresses();
                    Iterator eaIter = eAddrs.iterator();
                    while (eaIter.hasNext()) {
                        EmailAddress eAd = 
                            (EmailAddress) eaIter.next();
                        System.out.println("  Email Address: " + 
                            eAd.getAddress());
                    }
                }

                // Display service and binding information
                Collection services = org.getServices();
                Iterator svcIter = services.iterator();
                while (svcIter.hasNext()) {
                    Service svc = (Service) svcIter.next();
                    System.out.println(" Service name: " + 
                        getName(svc));
                    System.out.println(" Service description: " +
                        getDescription(svc));
                    Collection serviceBindings = 
                        svc.getServiceBindings();
                    Iterator sbIter = serviceBindings.iterator();
                    while (sbIter.hasNext()) {
                        ServiceBinding sb = 
                            (ServiceBinding) sbIter.next();
                        System.out.println("  Binding " +
                            "Description: " + 
                            getDescription(sb));
                        System.out.println("  Access URI: " + 
                            sb.getAccessURI());
                    }
                }
                // Print spacer between organizations
                System.out.println(" --- "); 
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

    /**
     * Returns the name value for a registry object.
     *
     * @param ro        a RegistryObject
     * @return          the String value
     */
    private String getName(RegistryObject ro) 
        throws JAXRException {

        try {
            return ro.getName().getValue();
        } catch (NullPointerException npe) {
            return "No Name";
        }
    }
    
    /**
     * Returns the description value for a registry object.
     *
     * @param ro        a RegistryObject
     * @return          the String value
     */
    private String getDescription(RegistryObject ro) 
        throws JAXRException {

        try {
            return ro.getDescription().getValue();
        } catch (NullPointerException npe) {
            return "No Description";
        }
    }
    
    /**
     * Returns the key id value for a registry object.
     *
     * @param ro        a RegistryObject
     * @return          the String value
     */
    private String getKey(RegistryObject ro) 
        throws JAXRException {

        try {
            return ro.getKey().getId();
        } catch (NullPointerException npe) {
            return "No Key";
        }
    }
}
