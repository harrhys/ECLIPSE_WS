package com.farbig.services.ws.employee;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;


public class AxisClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String endpoint = "http://localhost:7777/MySimpleService/SimplePort";
			Service service = new Service(new QName("WSImplService"));
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("http://test.com/","getFullName"));
			call.addParameter("first", XMLType.XSD_STRING,String.class, ParameterMode.IN);
			call.addParameter("last", XMLType.XSD_STRING, String.class,	ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String ret = (String) call.invoke(new Object[] { "Balaji", "Lord" });
			System.out.println("Result" + ret );
		} catch (Exception e) {
			System.err.println(e.toString());
		}

	}

}
