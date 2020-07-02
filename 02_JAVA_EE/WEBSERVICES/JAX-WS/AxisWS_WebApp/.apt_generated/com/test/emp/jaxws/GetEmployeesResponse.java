
package com.test.emp.jaxws;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getEmployeesResponse", namespace = "http://emp.test.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEmployeesResponse", namespace = "http://emp.test.com/")
public class GetEmployeesResponse {

    @XmlElement(name = "return", namespace = "")
    private ArrayList<com.farbig.services.ws.emp.EmpTO> _return;

    /**
     * 
     * @return
     *     returns ArrayList<EmpTO>
     */
    public ArrayList<com.farbig.services.ws.emp.EmpTO> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(ArrayList<com.farbig.services.ws.emp.EmpTO> _return) {
        this._return = _return;
    }

}
