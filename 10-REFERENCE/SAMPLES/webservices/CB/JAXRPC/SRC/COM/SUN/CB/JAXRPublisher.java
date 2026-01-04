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


package com.sun.cb;

import javax.xml.registry.*; 
import javax.xml.registry.infomodel.*; 
import java.net.*;
import java.security.*;
import java.util.*;

/**
 * The JAXRPublisher class consists of a makeConnection
 * method and an executePublish method. The makeConnection
 * method establishes a connection to the Registry Server.
 * The executePublish method creates an organization and 
 * publishes it to the registry.
 */
public class JAXRPublisher {

    Connection connection = null;

    public JAXRPublisher() {}

    /**
     * Establishes a connection to a registry.
     *
     * @param queryUrl	the URL of the query registry
     * @param publishUrl	the URL of the publish registry
     */
    public void makeConnection(String queryUrl, 
        String publishUrl) {

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
    public String executePublish(String username, 
        String password, String endpoint) {

        String id = null;
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

            // Get hardcoded strings from a ResourceBundle
            ResourceBundle bundle =
                ResourceBundle.getBundle("com.sun.cb.CoffeeRegistry");

            // Create organization name and description
            Organization org = 
                blcm.createOrganization(bundle.getString("org.name"));
            InternationalString s = 
                blcm.createInternationalString
                (bundle.getString("org.description"));
            org.setDescription(s);

            // Create primary contact, set name
            User primaryContact = blcm.createUser();
            PersonName pName = 
                blcm.createPersonName(bundle.getString("person.name"));
            primaryContact.setPersonName(pName);

            // Set primary contact phone number
            TelephoneNumber tNum = blcm.createTelephoneNumber();
            tNum.setNumber(bundle.getString("phone.number"));
            Collection phoneNums = new ArrayList();
            phoneNums.add(tNum);
            primaryContact.setTelephoneNumbers(phoneNums);

            // Set primary contact email address
            EmailAddress emailAddress = 
                blcm.createEmailAddress(bundle.getString("email.address"));
            Collection emailAddresses = new ArrayList();
            emailAddresses.add(emailAddress);
            primaryContact.setEmailAddresses(emailAddresses);

            // Set primary contact for organization
            org.setPrimaryContact(primaryContact);

            // Set classification scheme to NAICS
            ClassificationScheme cScheme = 
                bqm.findClassificationSchemeByName
                    (null, bundle.getString("classification.scheme"));

            // Create and add classification
            Classification classification = (Classification)
                blcm.createClassification(cScheme, 
                    bundle.getString("classification.name"), 	
                    bundle.getString("classification.value"));
            Collection classifications = new ArrayList();
            classifications.add(classification);
            org.addClassifications(classifications);

            // Create services and service
            Collection services = new ArrayList();
            Service service = 
                blcm.createService(bundle.getString("service.name"));
            InternationalString is = 
                blcm.createInternationalString
                (bundle.getString("service.description"));
            service.setDescription(is);

            // Create service bindings
            Collection serviceBindings = new ArrayList();
            ServiceBinding binding = blcm.createServiceBinding();
            is = blcm.createInternationalString
                (bundle.getString("service.binding"));
            binding.setDescription(is);
            try {
                binding.setAccessURI(endpoint);
            } catch (JAXRException je) {
                throw new JAXRException("Error: Publishing this " +
                    "service in the registry has failed because " +
                    "the service has not been installed on Tomcat.");
            }
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
                    id = orgKey.getId();
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {
                    System.err.println("Connection close failed");
                }
            }
        }
    return id;
    }
}
