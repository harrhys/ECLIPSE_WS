
package com.practise.xml.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.practise.xml.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PurchaseOrders_QNAME = new QName("http://farbig.com/PurchaseOrder", "PurchaseOrders");
    private final static QName _Products_QNAME = new QName("http://farbig.com/Product", "Products");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.practise.xml.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PurchaseOrders }
     * 
     */
    public PurchaseOrders createPurchaseOrders() {
        return new PurchaseOrders();
    }

    /**
     * Create an instance of {@link OrderItem }
     * 
     */
    public OrderItem createOrderItem() {
        return new OrderItem();
    }

    /**
     * Create an instance of {@link PurchaseOrder }
     * 
     */
    public PurchaseOrder createPurchaseOrder() {
        return new PurchaseOrder();
    }

    /**
     * Create an instance of {@link Products }
     * 
     */
    public Products createProducts() {
        return new Products();
    }

    /**
     * Create an instance of {@link Product }
     * 
     */
    public Product createProduct() {
        return new Product();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseOrders }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://farbig.com/PurchaseOrder", name = "PurchaseOrders")
    public JAXBElement<PurchaseOrders> createPurchaseOrders(PurchaseOrders value) {
        return new JAXBElement<PurchaseOrders>(_PurchaseOrders_QNAME, PurchaseOrders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Products }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://farbig.com/Product", name = "Products")
    public JAXBElement<Products> createProducts(Products value) {
        return new JAXBElement<Products>(_Products_QNAME, Products.class, null, value);
    }

}
