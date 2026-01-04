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

import java.util.ResourceBundle;
import javax.xml.registry.*; 
import javax.xml.registry.infomodel.Key;
import java.io.*;

public class OrgRemover {

    Connection connection = null;

    public static void main(String[] args) {

        String keyStr = null;

        ResourceBundle registryBundle =
           ResourceBundle.getBundle("com.sun.cb.CoffeeRegistry");

        String queryURL = registryBundle.getString("query.url");
        String publishURL = registryBundle.getString("publish.url");
        String username = registryBundle.getString("registry.username");
        String password = registryBundle.getString("registry.password");
        String keyFile = registryBundle.getString("key.file");

        try {
            FileReader in = new FileReader(keyFile);
            char[] buf = new char[512];
            while (in.read(buf, 0, 512) >= 0) { }
            in.close();
            keyStr = new String(buf).trim();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        JAXRRemover remover = new JAXRRemover();
        remover.makeConnection(queryURL, publishURL);
        javax.xml.registry.infomodel.Key modelKey = null;
        modelKey = remover.createOrgKey(keyStr);
        remover.executeRemove(modelKey, username, password);
    }
}
