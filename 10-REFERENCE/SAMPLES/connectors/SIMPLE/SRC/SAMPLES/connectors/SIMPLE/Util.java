/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import java.util.*;
import javax.security.auth.Subject;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.resource.spi.security.PasswordCredential;
import javax.resource.ResourceException;
import javax.resource.spi.SecurityException;
import javax.resource.spi.*;

/**
 *This is a utility class.
 */
public class Util {

    /**
     *This method return the password credential associated to the managed connection factory,
     *uses the security information (passed as Subject) and additional ConnectionRequestInfo
     *@param mcf The managed connection factory that being used in order to get connection
     * to the backend
     *@param subject security information
     *@param info  Additional resource adapter specific connection request information
     *@return PasswordCredential - password credential associated to the managed connection factory
     *                             or null if no password credential was found.
     */
    static public PasswordCredential getPasswordCredential
        (ManagedConnectionFactory mcf,
         final Subject subject, ConnectionRequestInfo info)
        throws ResourceException {

        if (subject == null) {
            if (info == null) {
                return null;
            } else {
                CometConnectionRequestInfo myinfo =
                    (CometConnectionRequestInfo) info;
                PasswordCredential pc =
                    new PasswordCredential(myinfo.getUser(),
                                           myinfo.getPassword().toCharArray());
                pc.setManagedConnectionFactory(mcf);
                return pc;
            }
        } else {
	    Set creds = (Set) AccessController.doPrivileged
                (new PrivilegedAction() {
                    public Object run() {
                        return subject.getPrivateCredentials
                            (PasswordCredential.class);
                        }
                    });          
            PasswordCredential pc = null;
            Iterator iter = creds.iterator();
            while (iter.hasNext()) {
                PasswordCredential temp = 
                    (PasswordCredential) iter.next();
                if (temp.getManagedConnectionFactory().equals(mcf)) {
                    pc = temp;
                    break;
                }
            }
            if (pc == null) {
                throw new SecurityException(Messages.getMessage(Messages.NO_PASSWORD_CREDENTIAL),
                                            Messages.NO_PASSWORD_CREDENTIAL);
            } else {
                return pc;
            }
        }
    }

    /**
     *This method compares two strings.
     *@param a first String
     *@param b second String
     *@return boolean - true if a equal to b
     */
    static public boolean isEqual(String a, String b) {
        if (a == null) {
            return (b == null);
        } else {
            return a.equals(b);
        }
    }

}
