
package com.practise.xml.schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Product" type="{http://farbig.com/Product}ProductCode"/>
 *         &lt;element name="Quantity" type="{http://farbig.com/PurchaseOrder}Quantity"/>
 *         &lt;element name="Price" type="{http://farbig.com/Product}Price"/>
 *         &lt;element name="OrderItemPrice" type="{http://farbig.com/Product}Price"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://farbig.com/PurchaseOrder}OrderItemId" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderItem", namespace = "http://farbig.com/PurchaseOrder", propOrder = {
    "product",
    "quantity",
    "price",
    "orderItemPrice"
})
public class OrderItem {

    @XmlElement(name = "Product", namespace = "http://farbig.com/PurchaseOrder", required = true)
    @XmlSchemaType(name = "string")
    protected ProductCode product;
    @XmlElement(name = "Quantity", namespace = "http://farbig.com/PurchaseOrder")
    protected int quantity;
    @XmlElement(name = "Price", namespace = "http://farbig.com/PurchaseOrder", required = true)
    protected BigInteger price;
    @XmlElement(name = "OrderItemPrice", namespace = "http://farbig.com/PurchaseOrder", required = true)
    protected BigInteger orderItemPrice;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCode }
     *     
     */
    public ProductCode getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductCode }
     *     
     */
    public void setProduct(ProductCode value) {
        this.product = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPrice(BigInteger value) {
        this.price = value;
    }

    /**
     * Gets the value of the orderItemPrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOrderItemPrice() {
        return orderItemPrice;
    }

    /**
     * Sets the value of the orderItemPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOrderItemPrice(BigInteger value) {
        this.orderItemPrice = value;
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
