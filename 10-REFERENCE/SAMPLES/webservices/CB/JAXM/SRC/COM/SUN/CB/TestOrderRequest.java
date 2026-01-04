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

import java.net.*;
import java.io.*;
import java.util.*;

import javax.xml.soap.*;

public class TestOrderRequest {
  public static void main(String [] args) {    
        try {
      SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
        SOAPConnection con = scf.createConnection();

    MessageFactory mf = MessageFactory.newInstance();
        
        SOAPMessage msg = mf.createMessage();

            // Access the SOABBody object
        SOAPPart part = msg.getSOAPPart();
        SOAPEnvelope envelope = part.getEnvelope();
        SOAPBody body = envelope.getBody();

        // Create the appropriate elements and add them

        Name bodyName = envelope.createName("coffee-order", "PO",
                            "http://sonata.coffeebreak.com");
        SOAPBodyElement order = body.addBodyElement(bodyName);

        //orderID
        Name orderIDName = envelope.createName("orderID");
        SOAPElement orderID =
                 order.addChildElement(orderIDName);
        orderID.addTextNode("1234");

            //customer
        Name childName = envelope.createName("customer");
        SOAPElement customer = order.addChildElement(childName);

        childName = envelope.createName("last-name");
        SOAPElement lastName = customer.addChildElement(childName);
        lastName.addTextNode("Pental");

        childName = envelope.createName("first-name");
        SOAPElement firstName = customer.addChildElement(childName);
        firstName.addTextNode("Ragni");

        childName = envelope.createName("phone-number");
        SOAPElement phoneNumber = customer.addChildElement(childName);
        phoneNumber.addTextNode("908 983-6789");

        childName = envelope.createName("email-address");
        SOAPElement emailAddress = 
            customer.addChildElement(childName);
        emailAddress.addTextNode("ragnip@aol.com");

        //address
        childName = envelope.createName("address");
        SOAPElement address = order.addChildElement(childName);

        childName = envelope.createName("street");
        SOAPElement street = address.addChildElement(childName);
        street.addTextNode("9876 Central Way");

        childName = envelope.createName("city");
        SOAPElement city = address.addChildElement(childName);
        city.addTextNode("Rainbow");

        childName = envelope.createName("state");
        SOAPElement state = address.addChildElement(childName);
        state.addTextNode("CA");

        childName = envelope.createName("zip");
        SOAPElement zip = address.addChildElement(childName);
        zip.addTextNode("99999");
    
        //line-item 1
        childName = envelope.createName("line-item");
        SOAPElement lineItem = 
              order.addChildElement(childName);

        childName = envelope.createName("coffeeName");
        SOAPElement coffeeName = 
          lineItem.addChildElement(childName);
        coffeeName.addTextNode("arabica");

        childName = envelope.createName("pounds");
        SOAPElement pounds = 
          lineItem.addChildElement(childName);
        pounds.addTextNode("2");

        childName = envelope.createName("price");
        SOAPElement price = 
              lineItem.addChildElement(childName);
        price.addTextNode("10.95");

        //line-item 2
        childName = envelope.createName("coffee-name");
        SOAPElement coffeeName2 = lineItem.addChildElement(childName);
        coffeeName2.addTextNode("espresso");

        childName = envelope.createName("pounds");
        SOAPElement pounds2 = lineItem.addChildElement(childName);
        pounds2.addTextNode("3");

        childName = envelope.createName("price");
        SOAPElement price2 = lineItem.addChildElement(childName);
        price2.addTextNode("10.95");

        //total
        childName = envelope.createName("total");
        SOAPElement total = 
              order.addChildElement(childName);
        total.addTextNode("21.90");
    
            
        String jaxmSpropsName = "com.sun.cb.JAXMService";

        ResourceBundle jaxmSpropBundle =
           ResourceBundle.getBundle(jaxmSpropsName);

        String orderURL = jaxmSpropBundle.getString("order.url");
        URL endpoint = new URL(orderURL);

            SOAPMessage reply = con.call(msg, endpoint);
      con.close();

      //extract content of reply
         //Extracting order ID and ship date
            SOAPBody sBody = reply.getSOAPPart().
                             getEnvelope().getBody();
            Iterator bodyIt = sBody.getChildElements();
            SOAPBodyElement sbEl = (SOAPBodyElement)bodyIt.next();
            Iterator bodyIt2 = sbEl.getChildElements();

            //get orderID
            SOAPElement ID = (SOAPElement)bodyIt2.next();
            String id = ID.getValue();

            //get ship date
            SOAPElement sDate = (SOAPElement)bodyIt2.next();
            String shippingDate = sDate.getValue();

            System.out.println("");
            System.out.println("");
            System.out.println("Confirmation for order #" + id);
            System.out.print("Your order will be shipped on ");
            System.out.println(shippingDate);

    
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
}
