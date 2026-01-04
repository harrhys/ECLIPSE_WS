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
 * The JAXRQueryPostal class consists of a main method, a 
 * makeConnection method, an executeQuery method, and some 
 * private helper methods. It searches a registry for 
 * information about organizations whose names contain a 
 * user-supplied string.  It displays the postal addresses for
 * the contacts of the organizations using the user-supplied
 * postal address classification scheme UUID.
 * 
 * To run this program, use the command 
 * 
 *     ant run-query -Dquery-string=<value> -Duuid-string=<value>
 */
public class JAXRQueryPostal {
    Connection connection = null;

    public JAXRQueryPostal() {}

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

        if (args.length < 2) {
            System.out.println("Usage: ant " +
                "run-query -Dquery-string=<value> -Duuid-string=<value>");
            System.exit(1);
        }
        String queryString = new String(args[0]);
        System.out.println("Query string is " + queryString);
        String uuidString = new String(args[1]);
        System.out.println("UUID string is " + uuidString);
        
        JAXRQueryPostal jq = new JAXRQueryPostal();

        jq.makeConnection(queryURL, publishURL, uuidString);

        jq.executeQuery(queryString);
    }

    /**
     * Establishes a connection to a registry.
     *
     * @param queryUrl  the URL of the query registry
     * @param publishUrl        the URL of the publish registry
     * @param uuidString        the UUID string of the postal address scheme
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

        /* 
         * Define connection configuration properties. 
         * For simple queries, you need the query URL.
         */
        Properties props = new Properties();
        props.setProperty("javax.xml.registry.queryManagerURL",
            queryUrl);
        props.setProperty("com.sun.xml.registry.http.proxyHost", 
            httpProxyHost);
        props.setProperty("com.sun.xml.registry.http.proxyPort", 
            httpProxyPort);

        // Set properties for postal address mapping: postal address
        //  classification scheme and mapping to JAXR scheme
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
     * Searches for organizations containing a string and 
     * displays data about them, including the postal address in
     * either the JAXR PostalAddress format or the Slot format.
     *
     * @param qString   the string argument
     */
    public void executeQuery(String qString) {
        RegistryService rs = null;
        BusinessQueryManager bqm = null;

        try {
            // Get registry service and query manager
            rs = connection.getRegistryService();
            bqm = rs.getBusinessQueryManager();
            System.out.println("Got registry service and " +
                "query manager");

            // Define find qualifiers and name patterns
            Collection findQualifiers = new ArrayList();
            findQualifiers.add(FindQualifier.SORT_BY_NAME_DESC);
            Collection namePatterns = new ArrayList();
            namePatterns.add("%" + qString + "%");

            // Find using the name
            BulkResponse response = 
                bqm.findOrganizations(findQualifiers, 
                    namePatterns, null, null, null, null);
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
                    
                    // Display postal addresses 
                    //   using PostalAddress methods
                    Collection pAddrs = pc.getPostalAddresses();
                    Iterator paIter = pAddrs.iterator();
                    while (paIter.hasNext()) {
                        PostalAddress pAd = 
                            (PostalAddress) paIter.next();
                        System.out.println("  Postal Address (PostalAddress methods):\n    " + 
                            pAd.getStreetNumber() + " " +
                            pAd.getStreet() + "\n    " +
                            pAd.getCity() + ", " +
                            pAd.getStateOrProvince() + " " +
                            pAd.getPostalCode() + "\n    " +
                            pAd.getCountry());
                    }

                    // Display postal addresses 
                    //   using Slot methods
                    Collection pAddrs2 = pc.getPostalAddresses();
                    Iterator paIter2 = pAddrs2.iterator();
                    while (paIter2.hasNext()) {
                        PostalAddress pAd = 
                            (PostalAddress) paIter2.next();
                        Collection slots = pAd.getSlots();
                        Iterator slotsIter = slots.iterator();
                        System.out.println("  Postal Address (Slot methods):");
                        while (slotsIter.hasNext()) {
                            Slot slot = (Slot) slotsIter.next();
                            Collection values = slot.getValues();
                            Iterator slIter = values.iterator();
                            while (slIter.hasNext()) {
                                String line = (String) slIter.next();
                                System.out.println("    Line: " + line);
                            }
                        }
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
