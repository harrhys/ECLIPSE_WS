
package com.practise.xml.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderItem" type="{http://farbig.com/PurchaseOrder}OrderItem" maxOccurs="10" minOccurs="2"/>
 *         &lt;element name="OrderTotalPrice" type="{http://farbig.com/Product}Price"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://farbig.com/PurchaseOrder}OrderId" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseOrder", namespace = "http://farbig.com/PurchaseOrder", propOrder = {
    "orderItem",
    "orderTotalPrice"
})
public class PurchaseOrder {

    @XmlElement(name = "OrderItem", namespace = "http://farbig.com/PurchaseOrder", required = true)
    protected List<OrderItem> orderItem;
    @XmlElement(name = "OrderTotalPrice", namespace = "http://farbig.com/PurchaseOrder", required = true)
    protected BigInteger orderTotalPrice;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Gets the value of the orderItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderItem }
     * 
     * 
     */
    public List<OrderItem> getOrderItem() {
        if (orderItem == null) {
            orderItem = new ArrayList<OrderItem>();
        }
        return this.orderItem;
    }

    /**
     * Gets the value of the orderTotalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOrderTotalPrice() {
        return orderTotalPrice;
    }

    /**
     * Sets the value of the orderTotalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOrderTotalPrice(BigInteger value) {
        this.orderTotalPrice = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
