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

import javax.xml.soap.*;
import java.util.*;
import java.math.BigDecimal;

import java.net.*;

public class PriceListRequest {
    String url;
  
    public PriceListRequest(String url){
      this.url = url;
    }

    public PriceListBean getPriceList() {
        PriceListBean plb = null;
        try {
            SOAPConnectionFactory scf = 
            SOAPConnectionFactory.newInstance();
            SOAPConnection con = scf.createConnection();

            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage msg = mf.createMessage();

            // Access the SOABBody object
            SOAPPart part = msg.getSOAPPart();
            SOAPEnvelope envelope = part.getEnvelope();
            SOAPBody body = envelope.getBody();

            //create SOAPBodyElement request 
            Name bodyName = envelope.createName("request-prices",
         "RequestPrices", "http://sonata.coffeebreak.com");
            SOAPBodyElement requestPrices =
                   body.addBodyElement(bodyName);
      
            Name requestName = envelope.createName("request");
            SOAPElement request = 
                  requestPrices.addChildElement(requestName);
            request.addTextNode("Send updated price list.");

            msg.saveChanges();

            //create the endpoint and send the message
            URL endpoint = new URL(url);
            SOAPMessage response = con.call(msg, endpoint);
            con.close();

            //get contents of response

            Vector list = new Vector();

            SOAPBody responseBody = response.getSOAPPart().
                           getEnvelope().getBody();
            Iterator it1 = responseBody.getChildElements(); 
            // get price-list element
            while (it1.hasNext()) {
                SOAPBodyElement bodyEl = (SOAPBodyElement)it1.next();
                Iterator it2 = bodyEl.getChildElements();
                // get coffee elements
                while (it2.hasNext()) {
                    SOAPElement child2 = (SOAPElement)it2.next();
            				Iterator it3 = child2.getChildElements();
                    // get coffee-name and price elements
                    while (it3.hasNext()) {
                        SOAPElement child3 = (SOAPElement)it3.next();
                        String value = child3.getValue();
                        list.addElement(value);
                    }
                }
            }

            ArrayList priceItems = new ArrayList();
            for (int i = 0; i < list.size(); i = i + 2) {
                priceItems.add(new PriceItemBean(list.elementAt(i).toString(), new BigDecimal(list.elementAt(i + 1).toString())));
                System.out.print(list.elementAt(i) + "        ");
                System.out.println(list.elementAt(i + 1));
            }
            Date today = new Date();
            Date endDate = DateHelper.addDays(today, 30);
            plb = new PriceListBean(today, endDate, priceItems);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return plb;
    }
}


