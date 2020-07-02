package com.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for empTO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="empTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "empTO", propOrder = { "department", "id", "name" })
public class EmpTO {

	protected String department;
	@XmlElement(name = "ID")
	protected long id;
	protected String name;

	/**
	 * Gets the value of the department property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Sets the value of the department property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDepartment(String value) {
		this.department = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 */
	public long getID() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 */
	public void setID(long value) {
		this.id = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

}
