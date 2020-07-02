
package com.practise.xml.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseOrders complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseOrders">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PurchaseOrder" type="{http://farbig.com/PurchaseOrder}PurchaseOrder"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseOrders", namespace = "http://farbig.com/PurchaseOrder", propOrder = {
    "purchaseOrder"
})
public class PurchaseOrders {

    @XmlElement(name = "PurchaseOrder", namespace = "http://farbig.com/PurchaseOrder", required = true)
    protected PurchaseOrder purchaseOrder;

    /**
     * Gets the value of the purchaseOrder property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrder }
     *     
     */
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * Sets the value of the purchaseOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrder }
     *     
     */
    public void setPurchaseOrder(PurchaseOrder value) {
        this.purchaseOrder = value;
    }

}
