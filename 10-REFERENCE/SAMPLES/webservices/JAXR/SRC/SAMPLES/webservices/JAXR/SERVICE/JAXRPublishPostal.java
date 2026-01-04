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
 * The JAXRPublishPostal class consists of a main method, a
 * makeConnection method, and an executePublish method.  
 * It creates an organization and publishes it to a registry.
 * The organization's primary contact has a postal address.
 *
 * Edit the file postalconcepts.xml before you run this 
 * program.
 * 
 * To run this program, use the command 
 * 
 *     ant run-publish-postal -Duuid-string=<value>
 *
 * where uuid-string is the classification scheme string that
 * you used in postalconcepts.xml.
 */
public class JAXRPublishPostal {
    Connection connection = null;

    public JAXRPublishPostal() {}

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
        // Defaults for Registry Server are testuser/testuser
        String username = "";
        String password = "";

        if (args.length < 1) {
            System.out.println("Usage: ant " +
                "run-publish-postal -Duuid-string=<value>");
            System.exit(1);
        }
        String uuidString = new String(args[0]);
        System.out.println("UUID string is " + uuidString);

        JAXRPublishPostal jp = new JAXRPublishPostal();

        jp.makeConnection(queryURL, publishURL, uuidString);

        jp.executePublish(username, password);
    }
    
    /**
     * Establishes a connection to a registry.
     *
     * @param queryUrl  the URL of the query registry
     * @param publishUrl        the URL of the publish registry
     */
    public void makeConnection(String queryUrl, 
        String publishUrl, String uuidString) {

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
         * To publish, you need both the query URL and the 
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
        
        // Set properties for postal address mapping using my scheme
        props.setProperty("javax.xml.registry.postalAddressScheme",
            uuidString);
        props.setProperty("javax.xml.registry.semanticEquivalences", 
            "urn:uuid:PostalAddressAttributes/StreetNumber," + 
            "urn:" + uuidString + "/MyStreetNumber|" +
            "urn:uuid:PostalAddressAttributes/Street," +
            "urn:" + uuidString + "/MyStreet|" +
            "urn:uuid:PostalAddressAttributes/City," + 
            "urn:" + uuidString + "/MyCity|" +
            "urn:uuid:PostalAddressAttributes/State," +                   
            "urn:" + uuidString + "/MyState|" +
            "urn:uuid:PostalAddressAttributes/PostalCode," +
            "urn:" + uuidString + "/MyPostalCode|" +
            "urn:uuid:PostalAddressAttributes/Country," +
            "urn:" + uuidString + "/MyCountry");

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
     * Creates an organization, its classification, and its
     * services, and saves it to the registry.  The primary
     * contact has a postal address.
     *
     * @param username  the username for the registry
     * @param password  the password for the registry
     */
    public void executePublish(String username, 
        String password) {

        RegistryService rs = null;
        BusinessLifeCycleManager blcm = null;
        BusinessQueryManager bqm = null;

        try {
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

            // Create organization name and description
            Organization org = 
                blcm.createOrganization("The PostalCoffee Break");
            InternationalString s = 
                blcm.createInternationalString("Purveyor of " +
                    "the finest coffees. Established 1895");
            org.setDescription(s);

            // Create primary contact, set name
            User primaryContact = blcm.createUser();
            PersonName pName = blcm.createPersonName("Jane Postal");
            primaryContact.setPersonName(pName);

            // Set primary contact phone number
            TelephoneNumber tNum = blcm.createTelephoneNumber();
            tNum.setNumber("(800) 555-1212");
            Collection phoneNums = new ArrayList();
            phoneNums.add(tNum);
            primaryContact.setTelephoneNumbers(phoneNums);

            // Set primary contact email address
            EmailAddress emailAddress = 
                blcm.createEmailAddress("jane.postal@TheCoffeeBreak.com");
            Collection emailAddresses = new ArrayList();
            emailAddresses.add(emailAddress);
            primaryContact.setEmailAddresses(emailAddresses);

            // create postal address for primary contact
            String streetNumber = "99";
            String street = "Imaginary Ave. Suite 33";
            String city = "Imaginary City";
            String state = "NY";
            String country = "USA";
            String postalCode = "00000";
            String type = "";
            PostalAddress postAddr = 
                blcm.createPostalAddress(streetNumber, street, city,
                    state, country, postalCode, type);
            Collection postalAddresses = new ArrayList();
            postalAddresses.add(postAddr);
            primaryContact.setPostalAddresses(postalAddresses);

            // Set primary contact for organization
            org.setPrimaryContact(primaryContact);

            // Set classification scheme to NAICS
            ClassificationScheme cScheme = 
                bqm.findClassificationSchemeByName(null, "ntis-gov:naics");

            // Create and add classification
            Classification classification = (Classification)
                blcm.createClassification(cScheme, 
                    "Snack and Nonalcoholic Beverage Bars", "722213");  
            Collection classifications = new ArrayList();
            classifications.add(classification);
            org.addClassifications(classifications);

            // Create services and service
            Collection services = new ArrayList();
            Service service = 
                blcm.createService("My Service Name");
            InternationalString is = 
                blcm.createInternationalString("My Service Description");
            service.setDescription(is);

            // Create service bindings; don't validate this fake URL
            Collection serviceBindings = new ArrayList();
            ServiceBinding binding = blcm.createServiceBinding();
            is = 
                blcm.createInternationalString("My Service Binding " +
                    "Description");
            binding.setDescription(is);
            binding.setValidateURI(false);
            binding.setAccessURI("http://TheCoffeeBreak.com:8080/sb/");
            serviceBindings.add(binding);

            // Add service bindings to service
            service.addServiceBindings(serviceBindings);

            // Add service to services, then add services to organization
            services.add(service);
            org.addServices(services);

            // Add organization and submit to registry
            // Retrieve key if successful
            Collection orgs = new ArrayList();
            orgs.add(org);
            BulkResponse response = blcm.saveOrganizations(orgs);
            Collection exceptions = response.getExceptions();
            if (exceptions == null) {
                System.out.println("Organization saved");

                Collection keys = response.getCollection();
                Iterator keyIter = keys.iterator();
                if (keyIter.hasNext()) {
                    javax.xml.registry.infomodel.Key orgKey = 
                        (javax.xml.registry.infomodel.Key) keyIter.next();
                    String id = orgKey.getId();
                    System.out.println("Organization key is " + id);
                }
            } else {
                Iterator excIter = exceptions.iterator();
                Exception exception = null;
                while (excIter.hasNext()) {
                    exception = (Exception) excIter.next();
                    System.err.println("Exception on save: " + 
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
