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

package com.sun.j2ee.blueprints.po.purchaseorder.ejb;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.Locale;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.sun.j2ee.blueprints.xmldocuments.PO;
import com.sun.j2ee.blueprints.xmldocuments.SupplierPO;
import com.sun.j2ee.blueprints.xmldocuments.OrderApproval;
import com.sun.j2ee.blueprints.xmldocuments.LineItems;
import com.sun.j2ee.blueprints.xmldocuments.Invoice;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;

import com.sun.j2ee.blueprints.po.purchaseorder.ejb.PurchaseOrder;
import com.sun.j2ee.blueprints.po.purchaseorder.ejb.PurchaseOrderLocalHome;
import com.sun.j2ee.blueprints.po.address.ejb.AddressLocal;
import com.sun.j2ee.blueprints.po.address.ejb.AddressLocalHome;
import com.sun.j2ee.blueprints.po.card.ejb.CardLocal;
import com.sun.j2ee.blueprints.po.card.ejb.CardLocalHome;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocal;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocalHome;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItem;

/**
 * Helper class to create, lookup and update purchase order
 */

public class PurchaseOrderHelper {

    public final String PO_EJB = "java:comp/env/ejb/local/PurchaseOrder";
    public final String ADDR_EJB = "java:comp/env/ejb/local/Address";
    public final String CARD_EJB = "java:comp/env/ejb/local/Card";
    public final String LI_EJB = "java:comp/env/ejb/local/LineItem";

    private PO poObject;

    public PurchaseOrderHelper() {}

    /**
     * Method persists the PO's CMP info
     * @return true if creation was ok; false if not
     */
    private boolean persistPoCMPInfo() {
        try {
            InitialContext initial = new InitialContext();
            Object objref = initial.lookup(PO_EJB);
            PurchaseOrderLocalHome ref = (PurchaseOrderLocalHome) objref;
            ref.create(poObject.getOrderId(), poObject.getUserId(),
                       poObject.getEmailId(),
                       poObject.getOrderDate().getTime(),
                       poObject.getLocale().toString(),
                       poObject.getTotalPrice(), "PENDING");
        } catch(CreateException ne) {
            System.out.println("Create Ex while persisting PO CMP :" +
                    ne.getMessage());
            return(false);
        } catch(NamingException ne) {
            System.out.println("naming Ex while persisting PO CMP :" +
                    ne.getMessage());
            return(false);
        }
        return(true);
    }

    /**
     * Method returns the local interface of given PO
     * @param String  the ID
     * @return <Code>PurchaseOrderLocal</Code>
     */
    private PurchaseOrder findPO(String localOrderId) {
        PurchaseOrder po = null;
        try {
            InitialContext initial = new InitialContext();
            Object objref = initial.lookup(PO_EJB);
            PurchaseOrderLocalHome poref = (PurchaseOrderLocalHome) objref;
            po = poref.findByPrimaryKey(localOrderId);
        } catch(FinderException ne) {
            System.out.println("finder Ex while lloking up PO :" +
                    ne.getMessage());
            return(null);
        } catch(NamingException ne) {
            System.out.println("naming Ex while persisting PO CMR :" +
                    ne.getMessage());
            return(null);
        }
        return(po);
    }

    /**
     * Tis method persists PO's CMR info; Ideally this should goto the ejb's
     * post create method
     * @return true if creation was ok; false if not
     */
    private boolean persistPoCMRInfo() {
        try {
            InitialContext initial = new InitialContext();
            PurchaseOrder po = findPO(poObject.getOrderId());
            Object objref = initial.lookup(ADDR_EJB);
            AddressLocalHome addref = (AddressLocalHome) objref;
            AddressLocal addloc = (AddressLocal)
                addref.create(poObject.getShipFirstName(),
                              poObject.getShipLastName(),
                              poObject.getShipStreet(), "",
                              poObject.getShipCity(), poObject.getShipState(),
                              poObject.getShipCountry(),
                              poObject.getShipZipCode());
            po.setAddress(addloc);
            objref = initial.lookup(CARD_EJB);
            CardLocalHome cardref = (CardLocalHome) objref;
            CardLocal cardloc = (CardLocal)
                cardref.create(poObject.getCreditCardNo(),
                               poObject.getCardType(),
                               new Date());
            po.setCard(cardloc);
            objref = initial.lookup(LI_EJB);
            LineItemLocalHome lineItemref = (LineItemLocalHome) objref;
            Collection litems = poObject.getLineItems();
            Iterator it = litems.iterator();
            while((it != null) && (it.hasNext())) {
                LineItems li = (LineItems) it.next();
                LineItemLocal lineItemloc = (LineItemLocal)
                    lineItemref.create(li.getCategoryId(), li.getProductId(),
                                       li.getItemId(), li.getLineNo(),
                                       li.getQuantity(), li.getUnitPrice(),
                                       "PENDING");
                po.addLineItem(lineItemloc);
            }
        } catch(CreateException ne) {
            System.out.println("Create Ex while persisting PO CMR :" +
                    ne.getMessage());
            return(false);
        } catch(NamingException ne) {
            System.out.println("naming Ex while persisting PO CMR :" +
                    ne.getMessage());
            return(false);
        }
        return(true);
    }

    /**
     * This method persists all info for a new po
     * @param <Code>PO</Code> the java object representation of PO
     * @return true or false for success / failure
     */
    public boolean persistPO(PO obj) {

        // Persist the information
        this.poObject = obj;
        if(persistPoCMPInfo() && persistPoCMRInfo())
            return(true);
        return(false);
    }

    /**
     * given a po id, this method find that po and sets its status to given
     * status
     * @param id
     * @param status
     * @return true / false
     */
    public boolean setPOStatus(String id, String status) {
        PurchaseOrder po = findPO(id);
        if(po == null)
            return(false);
        po.setPoStatus(status);
        return(true);
    }

    /**
     * This method processes invoice received from supplier by opc. Its
     * job is to set the LieItem status to completed for the recd invoices
     * and if all invoices of the given PO are shipped, it will set the
     * status of PO to completed
     * @param <Code>Invoice</Code>
     */
    public void processInvoice(Invoice invoice) {
        PurchaseOrder po = findPO(invoice.getOrderId());
        if(po == null)
            return;

        // load the items for which you got the invoice in a hashmap
        HashMap itemids = new HashMap();
        Collection items = invoice.getLineItems();
        Iterator it = items.iterator();
        while((it != null) && (it.hasNext())) {
            LineItems li = (LineItems) it.next();
            itemids.put(li.getItemId(), li.getLineNo());
        }

        // now set the status of line items to COMPLETED
        boolean poComplete = true;
        Collection liColl = po.getLineItems();
        Iterator liIt = liColl.iterator();
        while((liIt != null) && (liIt.hasNext())) {
            LineItemLocal li = (LineItemLocal) liIt.next();
            if(li.getLineItemStatus().equals("COMPLETED"))
                continue;
            if((itemids.containsKey(li.getItemId())) &&
               (itemids.get(li.getItemId()).equals(li.getLineNumber()))) {
                li.setLineItemStatus("COMPLETED");
            } else {
                poComplete = false;
            }
        }

        // if all lineitems are COMPLETED, set PO status to COMPLETED
        if(poComplete) {
            po.setPoStatus("COMPLETED");
        }
        return;
    }

    /**
     * Given a PO id, this gets its locale
     */
    public Locale getPOLocale(String id) {
        PurchaseOrder po = findPO(id);
        if(po == null)
            return(null);
        return(getLocaleFromString(po.getPoLocale()));
    }

    /**
     * Given a po id, ths returns the po's status
     * @param String po id
     * @return Stirng the status
     */
    public String getPOStatus(String id) {
        PurchaseOrder po = findPO(id);
        if(po == null)
            return(null);
        return(po.getPoStatus());
    }

    /**
     * Given a PO id, this gets the email
     */
    public String getPOEmailId(String id) {
        PurchaseOrder po = findPO(id);
        if(po == null)
            return(null);
        return(po.getPoEmailId());
    }

    /**
     * Given a PO id, its gets all info and builds the Supplier PO
     * @param the po id
     * @return the Supplier PO in xml format
     */
    public String getXmlPO(String id) {
        PurchaseOrder po = findPO(id);
        if(po == null)
            return(null);
        SupplierPO xmlPO = new SupplierPO();
        xmlPO.setOrderId(po.getPoId());
        Date tmpDate = new Date(po.getPoDate());
        xmlPO.setOrderDate(tmpDate);
        AddressLocal addr = po.getAddress();
        xmlPO.setShipInfo(addr.getFirstName(), addr.getLastName(),
                          addr.getStreet1(), addr.getCity(), addr.getState(),
                          addr.getCountry(), addr.getZip());
        Collection liColl = po.getAllItems();
        Iterator liIt = liColl.iterator();
        while((liIt != null) && (liIt.hasNext())) {
            LineItem li = (LineItem) liIt.next();
            xmlPO.addLineItem(li.getCatId(), li.getProdId(), li.getItemId(),
                              li.getLineNo(), li.getQty(), li.getPrice());
        }
        String doc = null;
        try {
            doc = xmlPO.toXML();
        } catch (XMLDocumentException de) {
            System.out.println("XMLDoc EX : " + de.getMessage());
        }
        return(doc);
    }

    private  Locale getLocaleFromString(String localeString) {
        if (localeString == null) return null;
        if (localeString.toLowerCase().equals("default")) return Locale.getDefault();
        int languageIndex = localeString.indexOf('_');
        if (languageIndex  == -1) return null;
        int countryIndex = localeString.indexOf('_', languageIndex +1);
        String country = null;
        if (countryIndex  == -1) {
            if (localeString.length() > languageIndex) {
                country = localeString.substring(languageIndex +1, localeString.length());
            } else {
                return null;
            }
        }
        int variantIndex = -1;
        if (countryIndex != -1) countryIndex = localeString.indexOf('_', countryIndex +1);
        String language = localeString.substring(0, languageIndex);
        String variant = null;
        if (variantIndex  != -1) {
            variant = localeString.substring(variantIndex +1, localeString.length());
        }
        if (variant != null) {
            return new Locale(language, country, variant);
        } else {
            return new Locale(language, country);
        }

    }
}

