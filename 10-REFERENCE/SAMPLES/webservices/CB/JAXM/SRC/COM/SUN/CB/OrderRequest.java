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
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

import javax.xml.soap.*;

public class OrderRequest {
  String url;

  public OrderRequest(String url){
    this.url = url;
  }

  public ConfirmationBean placeOrder(OrderBean orderBean) {
      ConfirmationBean cb = null;   
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
          orderID.addTextNode(orderBean.getId());

          //customer
          Name childName = envelope.createName("customer");
          SOAPElement customer = order.addChildElement(childName);

          childName = envelope.createName("last-name");
          SOAPElement lastName = customer.addChildElement(childName);
          lastName.addTextNode(orderBean.getCustomer().getLastName());

          childName = envelope.createName("first-name");
          SOAPElement firstName = customer.addChildElement(childName);
          firstName.addTextNode(orderBean.getCustomer().getFirstName());

          childName = envelope.createName("phone-number");
          SOAPElement phoneNumber = customer.addChildElement(childName);
          phoneNumber.addTextNode(orderBean.getCustomer().getPhoneNumber());

          childName = envelope.createName("email-address");
          SOAPElement emailAddress = 
              customer.addChildElement(childName);
          emailAddress.addTextNode(orderBean.getCustomer().getEmailAddress());

          //address
          childName = envelope.createName("address");
          SOAPElement address = order.addChildElement(childName);

          childName = envelope.createName("street");
          SOAPElement street = address.addChildElement(childName);
          street.addTextNode(orderBean.getAddress().getStreet());

          childName = envelope.createName("city");
          SOAPElement city = address.addChildElement(childName);
          city.addTextNode(orderBean.getAddress().getCity());

          childName = envelope.createName("state");
          SOAPElement state = address.addChildElement(childName);
          state.addTextNode(orderBean.getAddress().getState());

          childName = envelope.createName("zip");
          SOAPElement zip = address.addChildElement(childName);
          zip.addTextNode(orderBean.getAddress().getZip());
    
          for (Iterator it=orderBean.getLineItems().iterator(); it.hasNext(); ) {
            LineItemBean lib = (LineItemBean)it.next();

            childName = envelope.createName("line-item");
            SOAPElement lineItem = 
                  order.addChildElement(childName);

            childName = envelope.createName("coffeeName");
            SOAPElement coffeeName = 
              lineItem.addChildElement(childName);
            coffeeName.addTextNode(lib.getCoffeeName());

            childName = envelope.createName("pounds");
            SOAPElement pounds = 
              lineItem.addChildElement(childName);
            pounds.addTextNode(lib.getPounds().toString());

            childName = envelope.createName("price");
            SOAPElement price = 
                  lineItem.addChildElement(childName);
            price.addTextNode(lib.getPrice().toString());

          }

        //total
        childName = envelope.createName("total");
        SOAPElement total = 
              order.addChildElement(childName);
        total.addTextNode(orderBean.getTotal().toString()); 
              
        URL endpoint = new URL(url);
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
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date date = df.parse(shippingDate);
        cb = new ConfirmationBean(id, date);    
      } catch(Exception e) {
        e.printStackTrace();
      }
      return cb;
    }
}
