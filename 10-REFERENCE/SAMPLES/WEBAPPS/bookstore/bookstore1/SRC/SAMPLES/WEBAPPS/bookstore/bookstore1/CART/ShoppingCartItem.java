/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package samples.webapps.bookstore.bookstore1.cart; 

public class ShoppingCartItem {
    Object item;
    int quantity;

    public ShoppingCartItem(Object anItem) {
        item = anItem;
        quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public Object getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}









