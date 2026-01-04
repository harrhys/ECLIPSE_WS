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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.xml.registry.*; 
import javax.xml.registry.infomodel.*; 

public class RetailPriceList implements Serializable {

    private ArrayList retailPriceItems;
    private ArrayList distributors;

    public RetailPriceList() {
      String propsName = "com.sun.cb.CoffeeRegistry";

      ResourceBundle cbpropBundle =
          ResourceBundle.getBundle(propsName);

      String queryURL = cbpropBundle.getString("query.url");
      String publishURL = cbpropBundle.getString("publish.url");

      
      String RPCDistributor = "JAXRPCCoffeeDistributor";
      retailPriceItems = new ArrayList();
      distributors = new ArrayList();

      JAXRQueryByName jq = new JAXRQueryByName();
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
              for (Iterator it=priceItems.iterator(); it.hasNext(); ) {
                PriceItemBean pib = (PriceItemBean)it.next();
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

    public ArrayList getItems() {
        return retailPriceItems;
    }

    public ArrayList getDistributors() {
        return distributors;
    }

    public void setItems(ArrayList priceItems) {
        this.retailPriceItems = priceItems;
    }

    public void setDistributors(ArrayList distributors) {
        this.distributors = distributors;
    }
}
