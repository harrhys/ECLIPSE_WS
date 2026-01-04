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

import java.math.BigDecimal;
import java.util.*;

public class TestOrderCaller {
    public static void main(String[] args) {
        try {

            AddressBean address = new AddressBean("455 Apple Way",
               "Santa Clara", "CA", "95123");
            CustomerBean customer = new CustomerBean("Buzz",
               "Murphy", "247-5566", "buzz.murphy@clover.com");

            Collection lineItems = new ArrayList();

            lineItems.add(new LineItemBean("mocha", new BigDecimal("1.0"), new BigDecimal("9.50")));
            lineItems.add(new LineItemBean("special blend", new BigDecimal("5.0"), new BigDecimal("8.00")));
            lineItems.add(new LineItemBean("wakeup call", new BigDecimal("0.5"), new BigDecimal("10.00")));

            OrderBean order = new OrderBean("123", customer, lineItems, 
                new BigDecimal("55.67"), address);
               
            OrderCaller oc = new OrderCaller(args[0]);
            ConfirmationBean confirmation = oc.placeOrder(order);

            System.out.println(confirmation.getOrderId()  + " " +
                DateHelper.format(confirmation.getShippingDate(), "MM/dd/yy"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
}
