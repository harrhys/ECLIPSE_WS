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

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;

import com.sun.j2ee.blueprints.po.address.ejb.AddressLocal;
import com.sun.j2ee.blueprints.po.card.ejb.CardLocal;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocal;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItem;

/**
 * This is the main Entity Bean class for PurchaseOrderEJB
 * It has a one-many relatioship with the LineItemEJB, and one-to-one
 * relationship between AddressEJB and CardEJB.
 */

public abstract class PurchaseOrderEJB implements EntityBean {

    private EntityContext context = null;

    /**
     * Accessor method for Purchasr Order ID
     * @return String   PO id
     */
    public abstract String getPoId();

    /**
     * Setter method for Purchasr Order ID
     * @param String   PO id
     */
    public abstract void setPoId(String id);

    /**
     * Accessor method for Purchasr Order user ID
     * @return String   PO user id
     */
    public abstract String getPoUserId();

    /**
     * setter method for Purchasr Order user ID
     * @param String   PO user id
     */
    public abstract void setPoUserId(String id);

    /**
     * Accessor method for Purchasr Order email ID
     * @return String   PO email id
     */
    public abstract String getPoEmailId();

    /**
     * Setterr method for Purchasr Order email ID
     * @param String   PO email id
     */
    public abstract void setPoEmailId(String id);

    /**
     * Accessor method for Purchasr Order date
     * @return long   PO date - time form epoch
     */
    public abstract long getPoDate();

    /**
     * Setter method for Purchasr Order date
     * @param long   PO date - time form epoch
     */
    public abstract void setPoDate(long orderDate);

    /**
     * Accessor method for Purchasr Order locale
     * @return String   PO locale
     */
    public abstract String getPoLocale();

    /**
     * Setterr method for Purchasr Order locale
     * @param String   PO locale
     */
    public abstract void setPoLocale(String loc);

    /**
     * Accessor method for Purchasr Order total value
     * @return float   PO total value
     */
    public abstract float getPoValue();

    /**
     * setter method for Purchasr Order total value
     * @param float   PO total value
     */
    public abstract void setPoValue(float amount);

    /**
     * Accessor method for Purchasr Order status
     * @return String   PO status
     */
    public abstract String getPoStatus();

    /**
     * setter method for Purchasr Order status
     * @param String   PO status
     */
    public abstract void setPoStatus(String stat);

    /**
     * Accessor method for Purchasr Order contact address
     * @return <Code>AddressLocal</Code>   PO contact address
     */
    public abstract AddressLocal getAddress();

    /**
     * setter method for Purchasr Order contact address
     * @param <Code>AddressLocal</Code>   PO contact address
     */
    public abstract void setAddress(AddressLocal addr);

    /**
     * Accessor method for Purchasr Order - credit card info
     * @return <Code>CardLocal</Code>   PO credit card info
     */
    public abstract CardLocal getCard();

    /**
     * Setter method for Purchasr Order - credit card info
     * @param <Code>CardLocal</Code>   PO credit card info
     */
    public abstract void setCard(CardLocal ccInfo);

    /**
     * Accessor method for Purchasr Order - line items
     * @return <code>Collection</Code>   PO line items
     */
    public abstract Collection getLineItems();

    /**
     * Setter method for Purchasr Order - line items
     * @param <code>Collection</Code>   PO line items
     */
    public abstract void setLineItems(Collection litems);


    /**
     * Method helps to add a line item into the CMR field
     * @param <Code>LineItemLocal</Code> the local interface of line item
     */
    public void addLineItem(LineItemLocal lItem){
        getLineItems().add(lItem);
    }

    /**
     * the ejb create method
     */
    public String ejbCreate(String id, String poUid, String poemailId,
                            long poDate, String poLoc, float poVal,
                            String poStat) throws CreateException {
        setPoId(id);
        setPoUserId(poUid);
        setPoEmailId(poemailId);
        setPoDate(poDate);
        setPoLocale(poLoc);
        setPoValue(poVal);
        setPoStatus(poStat);
        return null;
    }

    /**
     * This gets all line items for this po and returns a collection of
     * Value objects. This is required because managed objects cant be accessed
     * outside transaction bounsaries
     * @return <Code>Collection</Code> of <Code>LineItem</Code> value objects
     */
    public Collection getAllItems() {
        Collection liColl = getLineItems();
        if(liColl == null)
            return(null);
        ArrayList retVal = new ArrayList();
        Iterator it = liColl.iterator();
        while((it!=null) && (it.hasNext())) {
            LineItemLocal loc = (LineItemLocal) it.next();
            retVal.add(new LineItem(loc.getCategoryId(), loc.getProductId(),
                                    loc.getItemId(), loc.getLineNumber(),
                                    loc.getQuantity(), loc.getUnitPrice(),
                                    loc.getLineItemStatus()));
        }
        return(retVal);
    }

    /**
     * other ejb lifecycle method
     */
    public void ejbPostCreate(String id, String poUid, String poemailId,
                              long poDate,
                              String poLoc, float poVal, String poStat)
        throws CreateException{}
    public void setEntityContext(EntityContext c){ context = c; }
    public void unsetEntityContext(){}
    public void ejbRemove() throws RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbStore() {}
    public void ejbLoad() {}
}

