
package com.oracle.enterprisetest.jsca.cart;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for shoppingCartItem complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="shoppingCartItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shoppingCartItem", namespace = "", propOrder = {
    "id",
    "price",
    "quantity"
})
public class ShoppingCartItem implements Serializable {

	private static final long serialVersionUID = 1;

    protected int id;
    protected double price;
    protected int quantity;

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

    /**
     * Gets the value of the price property.
     *
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     */
    public void setPrice(double value) {
        this.price = value;
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

}
