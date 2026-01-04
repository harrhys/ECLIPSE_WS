/*
 *
 * Copyright 2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.rmi_iiop.cpp.ejb;

public class ComplexObject implements java.io.Serializable
{
    private Employee employeeObj;
    private InterfaceTest interfaceTestRef;

    public InterfaceTest getInterfaceTest ()
    {
        return interfaceTestRef;
    }

    public void setInterfaceTest(InterfaceTest interfaceTestRef_)
    {
        interfaceTestRef = interfaceTestRef_;
    }

    public Employee getEmployeeData()
    {
        return employeeObj;
    }

    public void setEmployeeData(Employee employeeObj_)
    {
        employeeObj = employeeObj_;
    }

} 
