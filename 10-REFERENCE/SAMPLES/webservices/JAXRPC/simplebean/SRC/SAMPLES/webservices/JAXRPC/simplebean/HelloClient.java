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


package samples.webservices.jaxrpc.simplebean;

import java.math.BigDecimal;

public class HelloClient {

    public static void main(String[] args) {

        try {

            HelloIF_Stub stub = getStub();
            // System.out.println(stub.sayHello("Duke!"));

            demoArray(stub);
            demoBean(stub);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } // main   

    private static HelloIF_Stub getStub() {

        HelloIF_Stub stub = null;
        try {
            stub =
                (HelloIF_Stub)(new HelloWorld_Impl().getHelloIFPort());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stub;

    } // getStub

    private static void demoArray(HelloIF_Stub stub) {

        try {
            String[] words = {"it", "was", "a", "dark", "and",
                              "stormy", "night"};

            System.out.println("demoArray method:");
            for (int j = 0; j < words.length; j++) {
                System.out.print(words[j] + " ");
            }
            System.out.println();

            String[] backwards = stub.reverse(words);

            for (int j = 0; j < backwards.length; j++) {
                System.out.print(backwards[j] + " ");
            }
            System.out.println();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } // demoArray

    private static void demoBean(HelloIF_Stub stub) {

        try {
            System.out.println();
            System.out.println("demoBean method:");
            SimpleAccountBean dukesAccount = new SimpleAccountBean();
            dukesAccount.setBalance(new BigDecimal("1200.00"));
            dukesAccount.setCustomerName("Duke");
            BigDecimal newBalance = stub.calculateInterest(dukesAccount);
            System.out.println("newBalance: " + newBalance);
            dukesAccount.setBalance(newBalance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } // demoBean

} // class
