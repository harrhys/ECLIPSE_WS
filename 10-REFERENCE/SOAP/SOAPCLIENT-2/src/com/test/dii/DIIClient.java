package com.test.dii;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.encoding.XMLType;

public class DIIClient {

	public static void main(String[] args) throws Exception {

		QName serviceName = new QName(
				"http://localhost:7001/SOAP/CalculatorImpl?WSDL", "CalculatorImpl");
		ServiceFactory factory = ServiceFactory.newInstance();
		Service service = factory.createService(serviceName);

		// Call object
		Call call = service.createCall();

		// operation name
		QName operationName = new QName("vishar.com",
				"add");
		call.setOperationName(operationName);

		// input parameter
		call.addParameter("arg0", // parameter name
				XMLType.XSD_INT, // parameter XML type QName
				int.class, // parameter Java type class
				ParameterMode.IN); // parameter mode
		
		// input parameter
				call.addParameter("arg1", // parameter name
						XMLType.XSD_INT, // parameter XML type QName
						int.class, // parameter Java type class
						ParameterMode.IN); // parameter mode

		// setting return type
		call.setReturnType(XMLType.XSD_STRING);

		// specify the RPC-style operation.
		call.setProperty(Call.OPERATION_STYLE_PROPERTY, "rpc");
		// and the encoding style
		call.setProperty(Call.ENCODINGSTYLE_URI_PROPERTY,
				"");
		


		// the target endpoint
		call.setTargetEndpointAddress("http://localhost:7001/SOAP/CalculatorImpl");

		// Invoke the method readLSpercent
		Object[] myArgs = { 25,26 };
		Object lsVal = call.invoke(myArgs);

		System.out.println("Result value:  " + lsVal.toString());

	}
}
