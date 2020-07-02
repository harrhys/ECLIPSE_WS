
package com.practise.xml.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="P1"/>
 *     &lt;enumeration value="P2"/>
 *     &lt;enumeration value="P3"/>
 *     &lt;enumeration value="P4"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProductCode", namespace = "http://farbig.com/Product")
@XmlEnum
public enum ProductCode {

    @XmlEnumValue("P1")
    P_1("P1"),
    @XmlEnumValue("P2")
    P_2("P2"),
    @XmlEnumValue("P3")
    P_3("P3"),
    @XmlEnumValue("P4")
    P_4("P4");
    private final String value;

    ProductCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProductCode fromValue(String v) {
        for (ProductCode c: ProductCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
