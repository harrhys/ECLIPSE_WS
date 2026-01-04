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

package com.sun.j2ee.blueprints.contactinfo.ejb;

import javax.ejb.EntityContext;
import javax.ejb.RemoveException;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.naming.InitialContext;

import com.sun.j2ee.blueprints.address.ejb.AddressLocal;
import com.sun.j2ee.blueprints.address.ejb.AddressLocalHome;

public abstract class ContactInfoEJB implements javax.ejb.EntityBean {

    private EntityContext context = null;

    // getters and setters for CMP fields
    //====================================

    public abstract String getFamilyName();
    public abstract void setFamilyName(String familyName);

    public abstract String getGivenName();
    public abstract void setGivenName(String givenName);

    public abstract String getTelephone();
    public abstract void setTelephone(String telephone);

    public abstract String getEmail();
    public abstract void setEmail(String email);

    // CMR fields
    public abstract AddressLocal getAddress();
    public abstract void setAddress(AddressLocal address);

    // EJB create methods
    //===================
    public Object ejbCreate () throws CreateException {
        return null;
    }

    public void ejbPostCreate () throws CreateException {
        try {
         InitialContext ic = new InitialContext();
        AddressLocalHome adh = (AddressLocalHome) ic.lookup("java:comp/env/ejb/local/Address");
        AddressLocal address = adh.create();
        setAddress(address);
        } catch (javax.naming.NamingException ne) {
            throw new CreateException("ContactInfoEJB error: naming exception looking up address");
        }
    }

    public Object ejbCreate (String givenName, String familyName,
    String telephone, String email, AddressLocal address) throws CreateException {
        setGivenName(givenName);
        setFamilyName(familyName);
        setTelephone(telephone);
        setEmail(email);
        return null;
    }

    public void ejbPostCreate (String givenName, String familyName,
    String telephone, String email, AddressLocal address) throws CreateException {
        setAddress(address);
    }

    // Misc Method
    //=============
    public void setEntityContext(EntityContext c) {
        context = c;
    }
    public void unsetEntityContext() {
        context = null;
    }
    public void ejbRemove() throws RemoveException {
    }
    public void ejbActivate() {
    }
    public void ejbPassivate() {
    }
    public void ejbStore() {
    }
    public void ejbLoad() {
    }
}
