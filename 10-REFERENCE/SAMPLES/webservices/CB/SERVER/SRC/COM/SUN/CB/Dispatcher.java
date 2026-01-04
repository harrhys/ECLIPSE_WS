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

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.math.BigDecimal;

public class Dispatcher extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) {   
      HttpSession session = request.getSession();
      ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
      if (messages == null) {
         Locale locale=request.getLocale();
         messages = ResourceBundle.getBundle("com.sun.cb.messages.CBMessages", locale); 
         session.setAttribute("messages", messages);
      }

    ServletContext context = getServletContext();
    RetailPriceList rpl = (RetailPriceList)context.getAttribute("retailPriceList");
    if (rpl == null) {
      try {
          rpl = new RetailPriceList();
          context.setAttribute("retailPriceList", rpl);
        } catch (Exception ex) {
          context.log("Couldn't create price list: " + ex.getMessage());
        }
    }
    ShoppingCart cart = (ShoppingCart)session.getAttribute("cart");
    if (cart == null) {
        cart = new ShoppingCart(rpl);
        session.setAttribute("cart", cart);
    }


    String selectedScreen = request.getServletPath();

    request.setAttribute("selectedScreen", selectedScreen);

    if (selectedScreen.equals("/checkoutForm")) {
      CheckoutFormBean checkoutFormBean = new CheckoutFormBean(cart, rpl, messages);

      request.setAttribute("checkoutFormBean", checkoutFormBean);
      try {
        checkoutFormBean.setFirstName(request.getParameter("firstName"));
        checkoutFormBean.setLastName(request.getParameter("lastName"));
        checkoutFormBean.setEmail(request.getParameter("email"));
        checkoutFormBean.setAreaCode(request.getParameter("areaCode"));
        checkoutFormBean.setPhoneNumber(request.getParameter("phoneNumber"));
        checkoutFormBean.setStreet(request.getParameter("street"));
        checkoutFormBean.setCity(request.getParameter("city"));
        checkoutFormBean.setState(request.getParameter("state"));
        checkoutFormBean.setZip(request.getParameter("zip"));
        checkoutFormBean.setCCNumber(request.getParameter("CCNumber"));
        checkoutFormBean.setCCOption(Integer.parseInt(request.getParameter("CCOption")));
      } catch (NumberFormatException e) {
        // not possible
      }
    }
    try {
        request.getRequestDispatcher("/template.jsp").forward(request, response);
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
    String selectedScreen = request.getServletPath();
    request.setAttribute("selectedScreen", selectedScreen);

    ServletContext context = getServletContext();
    RetailPriceList rpl = (RetailPriceList)context.getAttribute("retailPriceList");
    if (rpl == null) {
      try {
          rpl = new RetailPriceList();
          context.setAttribute("retailPriceList", rpl);
        } catch (Exception ex) {
          context.log("Couldn't create price list: " + ex.getMessage());
        }
    }
    ShoppingCart cart = (ShoppingCart)session.getAttribute("cart");
    if (cart == null ) {
        cart = new ShoppingCart(rpl);
        session.setAttribute("cart", cart);
    }
 
    if (selectedScreen.equals("/orderForm")) {
      cart.clear();
      for(Iterator i = rpl.getItems().iterator(); i.hasNext(); ) {
        RetailPriceItem item = (RetailPriceItem) i.next();
        String coffeeName = item.getCoffeeName();
        BigDecimal pounds = new BigDecimal(request.getParameter(coffeeName + "_pounds"));
        BigDecimal price = item.getRetailPricePerPound().multiply(pounds).setScale(2, BigDecimal.ROUND_HALF_UP);
        ShoppingCartItem sci = new ShoppingCartItem(item, pounds, price);
        cart.add(sci);
      }

    } else if (selectedScreen.equals("/checkoutAck")) {
      CheckoutFormBean checkoutFormBean = new CheckoutFormBean(cart, rpl, messages);

      request.setAttribute("checkoutFormBean", checkoutFormBean);
      try {
        checkoutFormBean.setFirstName(request.getParameter("firstName"));
        checkoutFormBean.setLastName(request.getParameter("lastName"));
        checkoutFormBean.setEmail(request.getParameter("email"));
        checkoutFormBean.setAreaCode(request.getParameter("areaCode"));
        checkoutFormBean.setPhoneNumber(request.getParameter("phoneNumber"));
        checkoutFormBean.setStreet(request.getParameter("street"));
        checkoutFormBean.setCity(request.getParameter("city"));
        checkoutFormBean.setState(request.getParameter("state"));
        checkoutFormBean.setZip(request.getParameter("zip"));
        checkoutFormBean.setCCNumber(request.getParameter("CCNumber"));
        checkoutFormBean.setCCOption(Integer.parseInt(request.getParameter("CCOption")));
      } catch (NumberFormatException e) {
        // not possible
      }
      if (!checkoutFormBean.validate())
        request.setAttribute("selectedScreen", "/checkoutForm");
    }

    try {
        request.getRequestDispatcher("/template.jsp").forward(request, response);
    } catch(Exception e) {
    }
  }
}






