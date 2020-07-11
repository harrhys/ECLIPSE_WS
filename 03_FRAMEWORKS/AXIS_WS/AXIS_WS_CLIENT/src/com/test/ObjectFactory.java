package com.test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.test package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _GetEmployees_QNAME = new QName(
			"http://test.com/", "getEmployees");
	private final static QName _GetEmployeesResponse_QNAME = new QName(
			"http://test.com/", "getEmployeesResponse");
	private final static QName _AddEmployeeResponse_QNAME = new QName(
			"http://test.com/", "addEmployeeResponse");
	private final static QName _AddEmployee_QNAME = new QName(
			"http://test.com/", "addEmployee");
	private final static QName _SayHello_QNAME = new QName("http://test.com/",
			"sayHello");
	private final static QName _SayHelloResponse_QNAME = new QName(
			"http://test.com/", "sayHelloResponse");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.test
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link AddEmployee }
	 * 
	 */
	public AddEmployee createAddEmployee() {
		return new AddEmployee();
	}

	/**
	 * Create an instance of {@link SayHello }
	 * 
	 */
	public SayHello createSayHello() {
		return new SayHello();
	}

	/**
	 * Create an instance of {@link EmpTO }
	 * 
	 */
	public EmpTO createEmpTO() {
		return new EmpTO();
	}

	/**
	 * Create an instance of {@link GetEmployees }
	 * 
	 */
	public GetEmployees createGetEmployees() {
		return new GetEmployees();
	}

	/**
	 * Create an instance of {@link SayHelloResponse }
	 * 
	 */
	public SayHelloResponse createSayHelloResponse() {
		return new SayHelloResponse();
	}

	/**
	 * Create an instance of {@link GetEmployeesResponse }
	 * 
	 */
	public GetEmployeesResponse createGetEmployeesResponse() {
		return new GetEmployeesResponse();
	}

	/**
	 * Create an instance of {@link AddEmployeeResponse }
	 * 
	 */
	public AddEmployeeResponse createAddEmployeeResponse() {
		return new AddEmployeeResponse();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployees }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://test.com/", name = "getEmployees")
	public JAXBElement<GetEmployees> createGetEmployees(GetEmployees value) {
		return new JAXBElement<GetEmployees>(_GetEmployees_QNAME,
				GetEmployees.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link GetEmployeesResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://test.com/", name = "getEmployeesResponse")
	public JAXBElement<GetEmployeesResponse> createGetEmployeesResponse(
			GetEmployeesResponse value) {
		return new JAXBElement<GetEmployeesResponse>(
				_GetEmployeesResponse_QNAME, GetEmployeesResponse.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link AddEmployeeResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://test.com/", name = "addEmployeeResponse")
	public JAXBElement<AddEmployeeResponse> createAddEmployeeResponse(
			AddEmployeeResponse value) {
		return new JAXBElement<AddEmployeeResponse>(_AddEmployeeResponse_QNAME,
				AddEmployeeResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployee }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://test.com/", name = "addEmployee")
	public JAXBElement<AddEmployee> createAddEmployee(AddEmployee value) {
		return new JAXBElement<AddEmployee>(_AddEmployee_QNAME,
				AddEmployee.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://test.com/", name = "sayHello")
	public JAXBElement<SayHello> createSayHello(SayHello value) {
		return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link SayHelloResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://test.com/", name = "sayHelloResponse")
	public JAXBElement<SayHelloResponse> createSayHelloResponse(
			SayHelloResponse value) {
		return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME,
				SayHelloResponse.class, null, value);
	}

}
