/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.util;

/**
 * This interface defines names in code used as args for lookup().
 */

public interface CodedNames {

    public static final String BANK_DATABASE =
	"java:comp/env/jdbc/BankDB"; 

    public static final String ACCOUNT_EJBHOME =
        "java:comp/env/ejb/account";

    public static final String ACCOUNT_CONTROLLER_EJBHOME =
        "java:comp/env/ejb/accountController";

    public static final String CUSTOMER_EJBHOME =
        "java:comp/env/ejb/customer";

    public static final String CUSTOMER_CONTROLLER_EJBHOME =
        "java:comp/env/ejb/customerController";

    public static final String TX_EJBHOME =
        "java:comp/env/ejb/tx";

    public static final String TX_CONTROLLER_EJBHOME =
        "java:comp/env/ejb/txController";

} // CodedNames
