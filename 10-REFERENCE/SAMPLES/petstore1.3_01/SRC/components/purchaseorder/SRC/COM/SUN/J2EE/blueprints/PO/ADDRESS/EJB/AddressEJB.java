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
package com.sun.j2ee.blueprints.po.address.ejb;

import java.lang.Object;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

/**
 * This is the main Entity Bean class for AddressEJB
 */

public abstract class AddressEJB implements EntityBean {

    private EntityContext context = null;

    // getters and setters for PO CMP fields

    public abstract String getFirstName();
    public abstract void setFirstName(String name);
    public abstract String getLastName();
    public abstract void setLastName(String name);
    public abstract String getStreet1();
    public abstract void setStreet1(String name);
    public abstract String getStreet2();
    public abstract void setStreet2(String name);
    public abstract String getCity();
    public abstract void setCity(String name);
    public abstract String getState();
    public abstract void setState(String name);
    public abstract String getCountry();
    public abstract void setCountry(String name);
    public abstract String getZip();
    public abstract void setZip(String name);

    public Object ejbCreate(String fName, String lName, String s1,
                            String s2, String cy, String st,
                            String cnty, String pcode)
                                              throws CreateException {
        setFirstName(fName);
        setLastName(lName);
        setStreet1(s1);
        setStreet2(s2);
        setCity(cy);
        setState(st);
        setCountry(cnty);
        setZip(pcode);
        return null;
    }

    public void ejbPostCreate(String fName, String lName, String street1,
                              String street2, String city, String state,
                              String country, String zip)
        throws CreateException{}
    public void setEntityContext(EntityContext c){ context = c; }
    public void unsetEntityContext(){}
    public void ejbRemove() throws RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbStore() {}
    public void ejbLoad() {}
}

