/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package com.sun.j2ee.blueprints.petstore.controller.ejb.actions;


import java.util.Collection;
import java.util.Iterator;
import java.util.Date;
import java.util.Locale;

// j2ee imports
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

// waf imports
import com.sun.j2ee.blueprints.waf.event.Event;
import com.sun.j2ee.blueprints.waf.event.EventResponse;
import com.sun.j2ee.blueprints.waf.event.EventException;
import com.sun.j2ee.blueprints.waf.controller.ejb.action.EJBActionSupport;

// po component imports
import com.sun.j2ee.blueprints.xmldocuments.PO;
import com.sun.j2ee.blueprints.xmldocuments.LineItems;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;
import com.sun.j2ee.blueprints.petstore.controller.events.OrderEvent;
import com.sun.j2ee.blueprints.customer.account.ejb.ContactInfo;
import com.sun.j2ee.blueprints.customer.account.ejb.CreditCard;

// async component imports
import com.sun.j2ee.blueprints.asyncsender.util.AsyncHelper;

// unidue id generator imports
import com.sun.j2ee.blueprints.uidgen.ejb.UniqueIdGeneratorLocal;
import com.sun.j2ee.blueprints.uidgen.ejb.UniqueIdGeneratorLocalHome;

// shoppingcart component imports
import com.sun.j2ee.blueprints.cart.ejb.ShoppingCartLocal;
import com.sun.j2ee.blueprints.cart.model.CartItem;

// petstore imports

import com.sun.j2ee.blueprints.petstore.controller.ejb.ShoppingClientFacadeLocal;
import com.sun.j2ee.blueprints.petstore.controller.events.OrderEvent;
import com.sun.j2ee.blueprints.petstore.controller.events.OrderEventResponse;
import com.sun.j2ee.blueprints.petstore.controller.exceptions.ShoppingCartEmptyOrderException;

public class OrderEJBAction extends EJBActionSupport {

  public EventResponse perform(Event e) throws EventException {
      OrderEvent oe = (OrderEvent)e;
      PO po = new PO();
      ContactInfo billTo = oe.getBillTo();
      ContactInfo shipTo = oe.getShipTo();
      CreditCard creditCard = oe.getCreditCard();
      String orderIdString = null;

      // get the UniqueIdGenerator EJB
      UniqueIdGeneratorLocal uidgen = null;
      try {
            InitialContext ic = new InitialContext();
            Object o = ic.lookup("java:comp/env/ejb/local/UniqueIdGenerator");
            UniqueIdGeneratorLocalHome home =(UniqueIdGeneratorLocalHome)o;
            uidgen = home.create();
      } catch (javax.ejb.CreateException cx) {
             cx.printStackTrace();
      } catch (javax.naming.NamingException nx) {
          nx.printStackTrace();
      }
      orderIdString = uidgen.getUniqueId("1001");
      // get ther userId
      ShoppingClientFacadeLocal scf = null;
      scf = (ShoppingClientFacadeLocal)machine.getAttribute("shoppingClientFacade");
      String userId = scf.getUserId();
      po.setOrderId(orderIdString);
      po.setUserId(userId);
      po.setEmailId(billTo.getEmail());
      po.setOrderDate(new Date());
      po.setShipInfo(billTo.getGivenName(),
                              billTo.getFamilyName(),
                              billTo.getAddress().getStreetName1(),
                              billTo.getAddress().getCity(),
                              billTo.getAddress().getState(),
                              billTo.getAddress().getCountry(),
                              billTo.getAddress().getZipCode());

         po.setBillInfo(billTo.getGivenName(),
                              billTo.getFamilyName(),
                              billTo.getAddress().getStreetName1(),
                              billTo.getAddress().getCity(),
                              billTo.getAddress().getState(),
                              billTo.getAddress().getCountry(),
                              billTo.getAddress().getZipCode());

         po.setCreditCardInfo(creditCard.getCardNumber(),
                                           creditCard.getExpiryDate(),
                                           creditCard.getCardType());
          int lineItemCount =0;
          float totalCost = 0;
         // Add the items from the shopping cart
          ShoppingCartLocal cart = scf.getShoppingCart();
          Locale locale = (Locale)machine.getAttribute("locale");
          po.setLocale(locale);
          // set to default if not previously set.
          if (locale == null) locale= Locale.getDefault();
          Collection items = cart.getItems(locale);
          // if the cart is empty throw an exception saying so
          if (items.size() == 0) {
              throw new ShoppingCartEmptyOrderException("Shopping cart is empty");
          }
          Iterator it = items.iterator();
          while (it.hasNext()) {
              CartItem item = (CartItem)it.next();

              float cost = new Float(item.getUnitCost()).floatValue();
              totalCost += (cost*item.getQuantity());
              po.addLineItem(new LineItems(item.getCategory(),
                                           item.getProductId(),
                                           item.getItemId(),
                                           (lineItemCount++) + "",
                                           item.getQuantity(),
                                           cost));
          }
        po.setTotalPrice(totalCost);
        AsyncHelper helper = new AsyncHelper();
        try {
          helper.sendMessage(po.toXML());
        } catch (XMLDocumentException exception) {
          exception.printStackTrace(System.err);
          System.err.println(exception.getRootCause().getMessage());
        }
        // empty the shopping cart
        cart.empty();
        return new OrderEventResponse(billTo, shipTo, orderIdString);
  }
}

