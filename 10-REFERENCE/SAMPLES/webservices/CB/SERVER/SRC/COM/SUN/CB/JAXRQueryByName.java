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
import java.util.*;

import java.math.BigDecimal;

/**
 * The JAXRQueryByName class consists of a main method, a 
 * makeConnection method, an executeQuery method, and some 
 * helper methods. It searches a registry for 
 * information about organizations whose names contain a 
 * user-supplied string.
 * 
 * To run this program, use the command 
 * 
 *     ant -Dquery-string=<value> run-query
 *
 * after starting Tomcat and Xindice.
 */
public class JAXRQueryByName {
    static Connection connection = null;
      private static ArrayList retailPriceItems;
      private static ArrayList distributors;

    public JAXRQueryByName() {}

    public static void main(String[] args) {

       String propsName = "com.sun.cb.CoffeeRegistry";
       
       ResourceBundle cbpropBundle =
           ResourceBundle.getBundle(propsName);

        String queryURL = cbpropBundle.getString("query.url");
        String publishURL = cbpropBundle.getString("publish.url");

        if (args.length < 1) {
            System.out.println("Usage: ant " +
                "-Dquery-string=<value> run-query");
            System.exit(1);
        }
        String queryString = new String(args[0]);
        System.out.println("Query string is " + queryString);
        
        JAXRQueryByName jq = new JAXRQueryByName();

        connection = jq.makeConnection(queryURL, publishURL);

        jq.executeQuery(queryString);

        orgQueryTest(queryURL, publishURL);
    }

public static void orgQueryTest(String queryURL, String publishURL) {

      String RPCDistributor = "JAXRPCCoffeeDistributor";
      retailPriceItems = new ArrayList();
      distributors = new ArrayList();

      JAXRQueryByName jq = new JAXRQueryByName();

      //Connection connection =  jq.makeConnection(RegistryURL, RegistryURL);
      Connection connection =  jq.makeConnection(queryURL, publishURL);
      Collection orgs = jq.executeQuery(RPCDistributor);
      Iterator orgIter = orgs.iterator();
      // Display organization information
      try {
        while (orgIter.hasNext()) {
          Organization org = (Organization) orgIter.next();
          System.out.println("Org name: " + jq.getName(org));
          System.out.println("Org description: " + jq.getDescription(org));
          System.out.println("Org key id: " + jq.getKey(org));

          // Display service and binding information
          Collection services = org.getServices();
          Iterator svcIter = services.iterator();
          while (svcIter.hasNext()) {
            Service svc = (Service) svcIter.next();
            System.out.println(" Service name: " + jq.getName(svc));
            System.out.println(" Service description: " + jq.getDescription(svc));
            Collection serviceBindings = svc.getServiceBindings();
            Iterator sbIter = serviceBindings.iterator();
            while (sbIter.hasNext()) {
              ServiceBinding sb = (ServiceBinding) sbIter.next();
              String distributor = sb.getAccessURI();
              System.out.println("  Binding Description: " + jq.getDescription(sb));
              System.out.println("  Access URI: " + distributor);

              // Get price list from service at distributor URI
              PriceListBean priceList = PriceFetcher.getPriceList(distributor);
              Collection priceItems = priceList.getPriceItems();
              PriceItemBean pib;
              for (Iterator it=priceItems.iterator(); it.hasNext(); ) {
                pib = (PriceItemBean)it.next();
		System.out.println(pib.getCoffeeName() + " " +
                                   pib.getPricePerPound());
                BigDecimal price = pib.getPricePerPound().multiply(new BigDecimal("1.35")).setScale(2, BigDecimal.ROUND_HALF_UP);
                RetailPriceItem rpi = new RetailPriceItem(pib.getCoffeeName(), pib.getPricePerPound(), price, distributor);
                retailPriceItems.add(rpi);
              }
              distributors.add(distributor);
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

      String jaxmSpropsName = "com.sun.cb.JAXMService";

      ResourceBundle jaxmSpropBundle =
          ResourceBundle.getBundle(jaxmSpropsName);

      String JAXMPriceListURL = jaxmSpropBundle.getString("pricelist.url");
      String JAXMOrderURL = jaxmSpropBundle.getString("order.url");

      PriceListRequest plr = new PriceListRequest(JAXMPriceListURL);
      PriceListBean priceList = plr.getPriceList();;
      Collection priceItems = priceList.getPriceItems();
      for (Iterator it=priceItems.iterator(); it.hasNext(); ) {
        PriceItemBean pib = (PriceItemBean)it.next();
        BigDecimal price = pib.getPricePerPound().multiply(new BigDecimal("1.35")).setScale(2, BigDecimal.ROUND_HALF_UP);
        RetailPriceItem rpi = new RetailPriceItem(pib.getCoffeeName(), pib.getPricePerPound(), price, JAXMOrderURL);
        retailPriceItems.add(rpi);
      }
      distributors.add(JAXMOrderURL);
}

    /**
     * Establishes a connection to a registry.
     *
     * @param queryUrl	the URL of the query registry
     * @param publishUrl	the URL of the publish registry
     * @return the connection
     */
    public Connection makeConnection(String queryUrl, 
        String publishUrl) {

        /*
         * Edit to provide your own proxy information
         *  if you are going beyond your firewall.
         * Host format: "host.subdomain.domain.com".
         * Port is usually 8080.
         * Leave blank to use Registry Server.
         */
        String httpProxyHost = "";
        String httpProxyPort = "8080";

        /* 
         * Define connection configuration properties. 
         * For simple queries, you need the query URL.
         * To obtain the connection factory class, set a System 
         *   property.
         */
/*
//This code snippet is needed for JWSDP 1.0, but not needed for 
//Sun One AppServer, so commenting out
        System.setProperty("com.sun.xml.registry.ConnectionFactoryClass", 
            "com.sun.xml.registry.uddi.ConnectionFactoryImpl");
*/

        Properties props = new Properties();
        props.setProperty("javax.xml.registry.factoryClass", 
            "com.sun.xml.registry.uddi.ConnectionFactoryImpl");
        props.setProperty("javax.xml.registry.queryManagerURL",
            queryUrl);
/*
//This code snippet is needed for JWSDP 1.0, but for 
//Sun One AppServer, this property must be set during start up of VM,
//otherwise would cause accessDenied exception. 
        props.setProperty("com.sun.xml.registry.http.proxyHost", 
            httpProxyHost);
        props.setProperty("com.sun.xml.registry.http.proxyPort", 
            httpProxyPort);
*/

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

      return connection;
    }
    
    /**
     * Returns  organizations containing a string.
     *
     * @param qString	the string argument
     * @return a collection of organizations
     */
    public Collection executeQuery(String qString) {
        RegistryService rs = null;
        BusinessQueryManager bqm = null;
        Collection orgs = null;

        try {
            // Get registry service and query manager
            rs = connection.getRegistryService();
            bqm = rs.getBusinessQueryManager();
            System.out.println("Got registry service and " + "query manager");

            // Define find qualifiers and name patterns
            Collection findQualifiers = new ArrayList();
            findQualifiers.add(FindQualifier.SORT_BY_NAME_DESC);
            Collection namePatterns = new ArrayList();
            // % still doesn't work
            namePatterns.add(qString);
            //namePatterns.add("%" + qString + "%");

            // Find using the name
            BulkResponse response = 
                bqm.findOrganizations(findQualifiers, 
                    namePatterns, null, null, null, null);
            orgs = response.getCollection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
        
        return orgs;
    }

    /**
     * Returns the name value for a registry object.
     *
     * @param ro	a RegistryObject
     * @return		the String value
     */
    public String getName(RegistryObject ro) 
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
     * @param ro	a RegistryObject
     * @return		the String value
     */
    public String getDescription(RegistryObject ro) 
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
     * @param ro	a RegistryObject
     * @return		the String value
     */
    public String getKey(RegistryObject ro) 
        throws JAXRException {

        try {
            return ro.getKey().getId();
        } catch (NullPointerException npe) {
            return "No Key";
        }
    }
}
