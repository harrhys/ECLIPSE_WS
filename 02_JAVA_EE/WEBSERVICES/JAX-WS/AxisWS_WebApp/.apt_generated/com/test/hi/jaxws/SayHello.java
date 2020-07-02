
package com.test.hi.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "sayHello", namespace = "http://hi.test.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHello", namespace = "http://hi.test.com/")
public class SayHello {

    @XmlElement(name = "arg0", namespace = "")
    private com.farbig.services.ws.emp.EmpTO arg0;

    /**
     * 
     * @return
     *     returns EmpTO
     */
    public com.farbig.services.ws.emp.EmpTO getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(com.farbig.services.ws.emp.EmpTO arg0) {
        this.arg0 = arg0;
    }

}
