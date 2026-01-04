
package com.test.emp.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addEmployee", namespace = "http://emp.test.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addEmployee", namespace = "http://emp.test.com/")
public class AddEmployee {

    @XmlElement(name = "arg0", namespace = "")
    private com.test.emp.dto.EmpTO arg0;

    /**
     * 
     * @return
     *     returns EmpTO
     */
    public com.test.emp.dto.EmpTO getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(com.test.emp.dto.EmpTO arg0) {
        this.arg0 = arg0;
    }

}
