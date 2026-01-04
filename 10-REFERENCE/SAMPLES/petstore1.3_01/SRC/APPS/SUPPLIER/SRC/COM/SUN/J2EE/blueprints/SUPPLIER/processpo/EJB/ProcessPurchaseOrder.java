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

package com.sun.j2ee.blueprints.supplier.processpo.ejb;

import java.util.Collection;
import java.util.Iterator;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.ejb.FinderException;

import javax.jms.JMSException;

import com.sun.j2ee.blueprints.xmldocuments.SupplierPO;
import com.sun.j2ee.blueprints.xmldocuments.Invoice;
import com.sun.j2ee.blueprints.xmldocuments.LineItems;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;

import com.sun.j2ee.blueprints.supplierpo.order.ejb.SupplierOrderHelper;
import com.sun.j2ee.blueprints.supplierpo.order.ejb.SupplierOrderLocal;
import com.sun.j2ee.blueprints.supplierpo.order.ejb.SupplierOrderLocalHome;

import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocal;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocalHome;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItem;

import com.sun.j2ee.blueprints.supplier.inventory.ejb.InventoryLocal;
import com.sun.j2ee.blueprints.supplier.inventory.ejb.InventoryLocalHome;

public class ProcessPurchaseOrder {

    public final String PO_EJB = "java:comp/env/ejb/local/SupplierOrder";
    public final String LI_EJB = "java:comp/env/ejb/local/LineItem";
    public final String INV_EJB = "java:comp/env/ejb/local/Inventory";

    private SupplierPO poObject = null;

    public ProcessPurchaseOrder() {}

    private boolean checkInventory() throws NamingException {
        InventoryLocal inv;

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(INV_EJB);
        try {
            InventoryLocalHome invHome = (InventoryLocalHome) objref;
            Collection items = poObject.getLineItems();
            Iterator it = items.iterator();
            while((it != null) && (it.hasNext())) {
                LineItems anItem = (LineItems)it.next();
                inv = invHome.findByPrimaryKey(anItem.getItemId());
                if(inv.getQuantity() < anItem.getQuantity())
                    return(false);
                inv.reduceQuantity(anItem.getQuantity());
            }
        } catch(FinderException fe) {
            // swallow the finder exception because this means
            // supplier has not been populated; throwing exception upwards
            // will force the MDB to try resending the message
            return(false);
        }
        return(true);
    }

    public String createInvoice(String poId) {
        Invoice inv = new Invoice();
        inv.setOrderId(poObject.getOrderId());
        inv.setUserId("Dear PetStore Customer");
        inv.setOrderDate(poObject.getOrderDate().toString());
        Date curDate = new Date();
        inv.setShipDate(curDate.toString());
        Collection items = poObject.getLineItems();
        Iterator it = items.iterator();
        while((it != null) && (it.hasNext())) {
            LineItems anItem = (LineItems)it.next();
            inv.addLineItem(anItem);
        }
        String invDoc = null;
        try {
            invDoc = inv.toXML();
        } catch(XMLDocumentException de) {
            System.out.println("XML Doc Exception : " + de.getMessage());
            return(null);
        }
        return(invDoc);
    }

    public String createInvoice(SupplierOrderLocal po) {
        Invoice inv = new Invoice();
        inv.setOrderId(po.getPoId());
        inv.setUserId("Dear PetStore Customer");
        Date poDate = new Date(po.getPoDate());
        inv.setOrderDate(poDate.toString());
        Date curDate = new Date();
        inv.setShipDate(curDate.toString());
        Collection items = po.getAllItems();
        Iterator it = items.iterator();
        while((it != null) && (it.hasNext())) {
            LineItem anItem = (LineItem)it.next();
            inv.addLineItem(new LineItems(anItem.getCatId(),
                                          anItem.getProdId(),
                                          anItem.getItemId(),
                                          anItem.getLineNo(),
                                          anItem.getQty(), anItem.getPrice()));
        }
        String invDoc = null;
        try {
            invDoc = inv.toXML();
        } catch(XMLDocumentException de) {
            System.out.println("XML Doc Exception : " + de.getMessage());
            return(null);
        }
        return(invDoc);
    }

    public void processPendingPO() {
        boolean sendInvoice;

        try {
            InitialContext initial = new InitialContext();

            Object objref = initial.lookup(PO_EJB);
            SupplierOrderLocalHome ref = (SupplierOrderLocalHome) objref;

            Object invref = initial.lookup(INV_EJB);
            InventoryLocalHome invHome = (InventoryLocalHome) invref;

            Collection coll = ref.findOrdersByStatus("PENDING");
            if(coll != null) {
                Iterator it = coll.iterator();
                while((it!=null) && (it.hasNext())) {
                    SupplierOrderLocal order = (SupplierOrderLocal) it.next();
                    sendInvoice = true;
                    Collection liColl = order.getAllItems();
                    Iterator liIt = liColl.iterator();
                    while((liIt != null) && (liIt.hasNext())) {
                        LineItem li = (LineItem) liIt.next();
                        InventoryLocal inventory =
                            invHome.findByPrimaryKey(li.getItemId());
                        if(inventory.getQuantity() < li.getQty()) {
                            sendInvoice = false;
                            break;
                        } else {
                            inventory.reduceQuantity(li.getQty());
                        }
                    }
                    if(sendInvoice) {
                        order.setPoStatus("COMPLETED");
                        String newInv = createInvoice(order);
                        TopicSender sender = new TopicSender();
                        sender.sendMessage(newInv);
                    }
                }
            }
        } catch(FinderException fe) {
            System.out.println("finder Ex while processPendingPO :" +
                    fe.getMessage());
        } catch(NamingException ne) {
            System.out.println("naming Ex while processPendingPO :" +
                    ne.getMessage());
        } catch(JMSException ne) {
            System.out.println("naming Ex while processPendingPO :" +
                    ne.getMessage());
        }
    }

    public String processPO(String poXmlDoc) throws NamingException {

        // Convert Po in XML to PO as Java Obj
        try {
            poObject = SupplierPO.fromXML(poXmlDoc);
        } catch(XMLDocumentException de) {
            System.out.println("XML Doc Exception : " + de.getMessage());
            return(null);
        }

        // Persist the information
        SupplierOrderHelper pohelper = new SupplierOrderHelper();
        if(!pohelper.persistPO(poObject))
            return(null);

        // Check inventory and see if all lineitems are present
        // If not dont send invoice
        boolean inventoryPresent = checkInventory();
        if(!inventoryPresent)
            return(null);

        // inventory is present and hence items will be shipped;
        // Change status of po to COMPLETED
        // send invoice for this PO
        if(!(pohelper.setPOStatus(poObject.getOrderId(), "COMPLETED")))
            return(null);
        return(createInvoice(poObject.getOrderId()));
    }
}

