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

import javax.xml.registry.*; 
import javax.xml.registry.infomodel.*; 
import java.net.*;
import java.security.*;
import java.util.*;

/**
 * The JAXRRemover class consists of a makeConnection method,
 * a createOrgKey method, and an executeRemove method. It 
 * finds and deletes the organization that the OrgPublisher 
 * class created.
 */
public class JAXRRemover {

    Connection connection = null;
    RegistryService rs = null;

    public JAXRRemover() {}
    
    /**
     * Establishes a connection to a registry.
     *
     * @param queryUrl	the URL of the query registry
     * @param publishUrl	the URL of the publish registry
     */
    public void makeConnection(String queryUrl, 
        String publishUrl) {

        /*
         * Define connection configuration properties. 
         * To delete, you need both the query URL and the 
         * publish URL.
         */
        Properties props = new Properties();
        props.setProperty("javax.xml.registry.queryManagerURL",
            queryUrl);
        props.setProperty("javax.xml.registry.lifeCycleManagerURL", 
            publishUrl);

        try {
            // Create the connection, passing it the 
            // configuration properties
            ConnectionFactory factory = 
                ConnectionFactory.newInstance();
            factory.setProperties(props);
            connection = factory.createConnection();
            System.out.println("Created connection to registry");
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {}
            }
        }
    }

    /**
     * Searches for the organization created by the JAXRPublish
     * program, verifying it by checking that the key strings
     * match.
     *
     * @param keyStr	the key of the published organization
     *
     * @return	the key of the organization found
     */
    public javax.xml.registry.infomodel.Key createOrgKey(String keyStr) {

        BusinessLifeCycleManager blcm = null;
        javax.xml.registry.infomodel.Key orgKey = null;

        try {
            rs = connection.getRegistryService();
            blcm = rs.getBusinessLifeCycleManager();
            System.out.println("Got registry service and " +
                "life cycle manager");
            
            orgKey = blcm.createKey(keyStr);
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {
                    System.err.println("Connection close failed");
                }
            }
        }
        return orgKey;
    }

    /**
     * Removes the organization with the specified key value.
     *
     * @param key	the Key of the organization
     */
    public void executeRemove(javax.xml.registry.infomodel.Key key,
        String username, String password) {

        BusinessLifeCycleManager blcm = null;
    
        try {
            blcm = rs.getBusinessLifeCycleManager();

            // Get authorization from the registry
            PasswordAuthentication passwdAuth =
                new PasswordAuthentication(username, 
                    password.toCharArray());

            Set creds = new HashSet();
            creds.add(passwdAuth);
            connection.setCredentials(creds);
            System.out.println("Established security credentials");

            String id = key.getId();
            System.out.println("Deleting organization with id " + id);
            Collection keys = new ArrayList();
            keys.add(key);
            BulkResponse response = blcm.deleteOrganizations(keys);
            Collection exceptions = response.getExceptions();
            if (exceptions == null) {
                System.out.println("Organization deleted");
                Collection retKeys = response.getCollection();
                Iterator keyIter = retKeys.iterator();
                javax.xml.registry.infomodel.Key orgKey = null;
                if (keyIter.hasNext()) {
                    orgKey = 
                        (javax.xml.registry.infomodel.Key) keyIter.next();
                    id = orgKey.getId();
                    System.out.println("Organization key was " + id);
                }
            } else {
                Iterator excIter = exceptions.iterator();
                Exception exception = null;
                while (excIter.hasNext()) {
                    exception = (Exception) excIter.next();
                    System.err.println("Exception on delete: " + 
                    exception.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally  {
            // At end, close connection to registry
            if (connection != null) {
                try {
                    connection.close();
                } catch (JAXRException je) {
                    System.err.println("Connection close failed");
                }
            }
        }
    }
}
