
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

package com.sun.j2ee.blueprints.cart.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collection;

// j2ee imports
import javax.naming.InitialContext;
import javax.ejb.CreateException;

// shopping cart
import com.sun.j2ee.blueprints.cart.ejb.ShoppingCartLocal;
import com.sun.j2ee.blueprints.cart.ejb.ShoppingCartLocalHome;
import com.sun.j2ee.blueprints.cart.model.CartItem;

/**
 * Client Independent access to the Local ShoppingCart Session EJB
 *
 * Access is made to the Catalog via the CatlogClientHelper to build
 * the CartItems
*/

public class ShoppingCartClientHelper implements java.io.Serializable {

    private ShoppingCartLocal cart = null;

    public ShoppingCartClientHelper () {
      try {
        InitialContext ic = new InitialContext();
        Object o = ic.lookup("java:comp/env/ejb/local/ShoppingCart");
        ShoppingCartLocalHome home =(ShoppingCartLocalHome)o;
        cart = home.create();
     } catch (javax.ejb.CreateException cx) {
         throw new RuntimeException("Failed to Create CartClientHelper: caught " + cx);
     } catch (javax.naming.NamingException nx) {
         throw new RuntimeException("Failed to Create CartClientHelper: caught " + nx);
     }
    }

   /**
    * Return a collection of CartItems
   */
   public Collection getItems() {
       return cart.getItems(java.util.Locale.getDefault());
   }

    /*
    public HashMap getDetails() {
        return cart.getDetails();
    }
    */

    public void deleteItem(String itemId) {
        cart.deleteItem(itemId);
    }

    public void addItem(String itemId) {
        cart.addItem(itemId);
    }

    /*
    public void addItem(String itemId, int quantity) {
        cart.addItem(itemId, quantity);
    }
    */

    public void empty() {
        cart.empty();
    }
}

