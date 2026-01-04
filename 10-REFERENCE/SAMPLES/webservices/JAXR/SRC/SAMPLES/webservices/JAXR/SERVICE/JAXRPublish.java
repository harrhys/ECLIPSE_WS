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
 * The JAXRPublish class consists of a main method, a
 * makeConnection method, and an executePublish method.
 * It creates an organization and publishes it to a registry.
 *
 * To run this program, use the command
 *
 *     ant run-publish
 */
public class JAXRPublish {
    Connection connection = null;

    public JAXRPublish() {}

    public static void main(String[] args) {
        String queryURL =
		"http://www-3.ibm.com/services/uddi/v2beta/inquiryapi";
		//"http://uddi.rte.microsoft.com:/inquire";
		// For Registry Server, replace <YourHost> with fully
		//  qualified host name or localhost
		//"http://<YourHost>/registry-server/RegistryServerServlet";
		//For iRS replace <YourHost> with fully qualified hostname
		//"http://<YourHost>/uddi/api/inquiry";
        String publishURL =
		"https://www-3.ibm.com/services/uddi/v2beta/protect/publishapi";
		//"https://uddi.rte.microsoft.com/publish";
		// For Registry Server, replace <YourHost> with fully
		//  qualified host name or localhost
		//"http://<YouHost>/registry-server/RegistryServerServlet";
		//For iRS replace <YourHost> with fully qualified hostname
                //"http://<YourHost>/uddi/api/publish";
        // Edit to provide your own username and password
        // Defaults for Registry Server are testuser/testuser
        String username = "";
        String password = "";

        JAXRPublish jp = new JAXRPublish();

        jp.makeConnection(queryURL, publishURL);

        jp.executePublish(username, password);
    }

    /**
     * Establishes a connection to a registry.
     *
     * @param queryUrl	the URL of the query registry
     * @param publishUrl	the URL of the publish registry
     */
    public void makeConnection(String queryUrl,
        String publishUrl) {

        /*
         * Edit to provide your own proxy information
         *  if you are going beyond your firewall.
         * Host format: "host.subdomain.domain.com".
         * Port is usually 8080.
         * Leave strings empty to use Registry Server.
         */
        String httpProxyHost = "";
        String httpProxyPort = "";
        String httpsProxyHost = "";
        String httpsProxyPort = "";

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
        props.setProperty("javax.xml.registry.http.proxyHost",
            httpProxyHost);
        props.setProperty("javax.xml.registry.http.proxyPort",
            httpProxyPort);
        props.setProperty("javax.xml.registry.https.proxyHost",
            httpsProxyHost);
        props.setProperty("javax.xml.registry.https.proxyPort",
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
     * Creates an organization, its classification, and its
     * services, and saves it to the registry.
     */
    public void executePublish(String username,
        String password) {

        RegistryService rs = null;
        BusinessLifeCycleManager blcm = null;
        BusinessQueryManager bqm = null;

        String orgName = "The Coffee Break";
        String orgDesc = "Purveyor of the finest coffees. Established 1895";
        String contactName = "Jane Doe";
        String contactPhone = "(800) 555-1212";
        String contactEmail = "jane.doe@TheCoffeeBreak.com";
        String serviceName = "My Service Name";
        String serviceDesc = "My Service Description";
        String serviceBindingDesc = "My Service Binding Description";
        String serviceBindingURI = "http://localhost:1024";
        String scheme = "ntis-gov:naics";
        String conceptName = "Snack and Nonalcoholic Beverage Bars";
        String conceptCode = "722213";
        try {
            java.io.BufferedInputStream bfInput = null;
            Properties propTemp = new Properties();
            bfInput =  new java.io.BufferedInputStream
                (new java.io.FileInputStream("jaxr.properties"));
            propTemp.load(bfInput);
            bfInput.close();
            orgName = propTemp.getProperty("org-name");
            orgDesc = propTemp.getProperty("org-desc");
            contactName = propTemp.getProperty("contact-name");
            contactPhone = propTemp.getProperty("contact-phone");
            contactEmail = propTemp.getProperty("contact-email");
            serviceName = propTemp.getProperty("service-name");
            serviceDesc = propTemp.getProperty("service-desc");
            serviceBindingDesc = propTemp.getProperty("service-binding-desc");
            serviceBindingURI = propTemp.getProperty("service-binding-uri");
            scheme = propTemp.getProperty("scheme");
            conceptName = propTemp.getProperty("concept");
            conceptCode = propTemp.getProperty("concept-code");
        } catch (Exception e) {
            System.out.println("Error loading properties: "+e.getMessage());
        }

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
                blcm.createOrganization(orgName);
            InternationalString s =
                blcm.createInternationalString(orgDesc);
            org.setDescription(s);

            // Create primary contact, set name
            User primaryContact = blcm.createUser();
            PersonName pName = blcm.createPersonName(contactName);
            primaryContact.setPersonName(pName);

            // Set primary contact phone number
            TelephoneNumber tNum = blcm.createTelephoneNumber();
            tNum.setNumber(contactPhone);
            Collection phoneNums = new ArrayList();
            phoneNums.add(tNum);
            primaryContact.setTelephoneNumbers(phoneNums);

            // Set primary contact email address
            EmailAddress emailAddress =
                blcm.createEmailAddress(contactEmail);
            Collection emailAddresses = new ArrayList();
            emailAddresses.add(emailAddress);
            primaryContact.setEmailAddresses(emailAddresses);

            // Set primary contact for organization
            org.setPrimaryContact(primaryContact);

            // Set classification scheme to NAICS
            ClassificationScheme cScheme =
                bqm.findClassificationSchemeByName(null,scheme);
            if (cScheme != null) {
            // Create and add classification
            Classification classification = (Classification)
                blcm.createClassification(cScheme,
                    conceptName, conceptCode);
            Collection classifications = new ArrayList();
            classifications.add(classification);
            org.addClassifications(classifications);
            }
            // Create services and service
            Collection services = new ArrayList();
            Service service =
                blcm.createService(serviceName);
            InternationalString is =
                blcm.createInternationalString(serviceDesc);
            service.setDescription(is);

            // Create service bindings
            Collection serviceBindings = new ArrayList();
            ServiceBinding binding = blcm.createServiceBinding();
            is =
                blcm.createInternationalString(serviceBindingDesc);
            binding.setDescription(is);
            // allow us to publish a bogus URL without an error
            binding.setValidateURI(false);
            binding.setAccessURI(serviceBindingURI);
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
                    org.setKey(orgKey);
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
            //e.printStackTrace();
            System.out.println("Exception in executePublish. The message is:");
            System.out.println(e.getMessage());
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {
                    System.err.println("Connection close failed");
                }
            }
        }
    }
}
