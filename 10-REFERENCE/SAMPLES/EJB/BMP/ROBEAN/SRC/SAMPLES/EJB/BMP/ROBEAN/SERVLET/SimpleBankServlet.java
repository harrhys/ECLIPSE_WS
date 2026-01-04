/*
 *
 * Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.ejb.bmp.robean.servlet; 

import java.io.*; 
import java.util.*; 
import javax.servlet.*; 
import javax.naming.*; 
import javax.servlet.http.*; 
import javax.rmi.PortableRemoteObject;
import javax.ejb.*; 

import samples.ejb.bmp.robean.ejb.*; 
import com.sun.ejb.ReadOnlyBeanNotifier;
import com.sun.ejb.containers.ReadOnlyBeanHelper;

public class SimpleBankServlet extends HttpServlet {  

 
    InitialContext initContext = null;
    AddressHome addressHome = null;
    CustomerHome customerHome = null;
    CustomerTransactionalHome customerTransactionalHome = null;
    CustomerRefreshHome customerRefreshHome = null;
    CustomerProgRefreshHome customerProgRefreshHome = null;
    Address address = null;
    Customer customer = null;
    CustomerTransactional customerTransactional = null;
    CustomerRefresh customerRefresh = null;
    CustomerProgRefresh customerProgRefresh = null;
    Hashtable env = new java.util.Hashtable(1);
    String JNDIName = null;
    Object objref = null;

    public void doGet (HttpServletRequest request,HttpServletResponse response) 
        throws ServletException, IOException { 
        doPost(request, response);
    }  

    public void doPost (HttpServletRequest request,HttpServletResponse response) 
        throws ServletException, IOException { 
        String SSN = request.getParameter("SSN");

        try {
            address = addressHome.findByPrimaryKey(SSN);
        } catch (Exception e) {
            System.out.println("Could not create the address remote bean : " + e.toString());
            return;
        }

        try {
            customer = customerHome.findByPrimaryKey(new PKString(SSN));
        } catch (Exception e) {
            System.out.println("Could not create the customer remote bean : " + e.toString());
            return;
        }

        try {
            customerTransactional = customerTransactionalHome.findByPrimaryKey(SSN);
        } catch (Exception e) {
            System.out.println("Could not create the customerTransactional remote bean : " + e.toString());
            return;
        }

        try {
            customerRefresh = customerRefreshHome.findByPrimaryKey(SSN);
        } catch (Exception e) {
            System.out.println("Could not create the customerRefresh remote bean : " + e.toString());
            return;
        }

        try {
            customerProgRefresh = customerProgRefreshHome.findByPrimaryKey(new PKString1(SSN));
        } catch (Exception e) {
            System.out.println("Could not create the customerProgRefresh remote bean : " + e.toString());
            return;
        }


        String name = address.getName();
        String addressStr = address.getAddress();
        SSN = address.getSSN();
        double currentBalance = 0;
        double transactionalBalance = Double.parseDouble(request.getParameter("transactionalBalance"));
        double refreshBalance = 0;
        double progRefreshBalance = 0;
        double autoRefreshBalance = 0;
        String action = request.getParameter("action");
        if (action !=null) {
            if (action.equalsIgnoreCase("Enter")) {
                transactionalBalance = customerTransactional.getBalance();
            } else if (action.equals("Update")) {
                String operation = request.getParameter("operation");
                String amount = request.getParameter("amount");
                if (operation.equals("credit") && amount!=null) {
                    customer.doCredit(Double.parseDouble(amount));
                } else if(amount!=null){
                    customer.doDebit(Double.parseDouble(amount));
                }
            } else if (action.equals("Get Trans. Balance")) {
                transactionalBalance = customerTransactional.getBalance();
            } else if (action.equals("Get Prog. Balance")) {
                try {
                    ReadOnlyBeanNotifier notifier = 
                        ReadOnlyBeanHelper.getReadOnlyBeanNotifier("java:comp/env/ejb/customerProgRefresh");
                    notifier.refresh(new PKString1(SSN));
                } catch (Exception e) {
                    System.out.println("Exception while looking up ReadOnlyBeanNotifier class");
                    return;
                }
            }
        }
        currentBalance = customer.getBalance();
        refreshBalance = customerRefresh.getBalance();
        progRefreshBalance = customerProgRefresh.getBalance();
        request.setAttribute("name", name);
        request.setAttribute("address", addressStr);
        request.setAttribute("SSN", SSN);
        request.setAttribute("currentBalance", String.valueOf(currentBalance));
        request.setAttribute("refreshBalance", String.valueOf(refreshBalance));
        request.setAttribute("transactionalBalance", String.valueOf(transactionalBalance));
        request.setAttribute("progRefreshBalance", String.valueOf(progRefreshBalance));
        request.setAttribute("autoRefreshBalance", String.valueOf(autoRefreshBalance));
        response.setContentType("text/html");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/SimpleBank.jsp");
        dispatcher.include(request, response);
        return;
    } 

    public void doLookup() {
        try {
            initContext = new javax.naming.InitialContext();
        } catch (Exception e) {
            System.out.println("Exception occured when creating InitialContext: " + e.toString());
            return;
        }
    
        try {
            JNDIName = "java:comp/env/ejb/address";
            objref = initContext.lookup(JNDIName);
            addressHome = (AddressHome)PortableRemoteObject.narrow(objref, AddressHome.class);
        } catch (Exception e) {
            System.out.println("Addressbean home not found - Is the bean registered with JNDI? : " + e.toString());
            return;
        }
    
        try {
            JNDIName = "java:comp/env/ejb/customer";
            objref = initContext.lookup(JNDIName);
            customerHome = (CustomerHome)PortableRemoteObject.narrow(objref, CustomerHome.class);
        } catch (Exception e) {
            System.out.println("Customerbean home not found - Is the bean registered with JNDI? : " + e.toString());
            return;
        }
        
        try {
            JNDIName = "java:comp/env/ejb/customerTransactional";
            objref = initContext.lookup(JNDIName);
            customerTransactionalHome = (CustomerTransactionalHome)PortableRemoteObject.narrow(objref, CustomerTransactionalHome.class);
        } catch (Exception e) {
            System.out.println("CustomerTransactional bean home not found - Is the bean registered with JNDI? : " + e.toString());
            return;
        }
    
        try {
            JNDIName = "java:comp/env/ejb/customerRefresh";
            objref = initContext.lookup(JNDIName);
            customerRefreshHome = (CustomerRefreshHome)PortableRemoteObject.narrow(objref, CustomerRefreshHome.class);
        } catch (Exception e) {
            System.out.println("CustomerRefresh bean home not found - Is the bean registered with JNDI? : " + e.toString());
            return;
        }
    
        try {
            JNDIName = "java:comp/env/ejb/customerProgRefresh";
            objref = initContext.lookup(JNDIName);
            customerProgRefreshHome = (CustomerProgRefreshHome)PortableRemoteObject.narrow(objref, CustomerProgRefreshHome.class);
        } catch (Exception e) {
            System.out.println("CustomerProgRefresh bean home not found - Is the bean registered with JNDI? : " + e.toString());
            return;
        }
    }
    
    public void init() {
        doLookup();
    }

    public void destroy() {
    }
}
