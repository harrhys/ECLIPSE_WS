package com.practise.xml.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

public class DOMParser {

	public static void main(String[] args) throws Exception {

		DOMParser parser = new DOMParser();

		Document document = parser.processXML("src/com/practise/xml/dom/xml/Employee.xml");

		parser.processByTagName(document);

		parser.processByTraversal(document);

	}

	public Document processXML(String filePath) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		File f = new File(filePath);
		Document document = builder.parse(f);
		document.getDocumentElement().normalize();
		return document;
	}

	public void processByTagName(Document document) {

		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());

		NodeList nList = document.getElementsByTagName("employee");
		System.out.println("=====================");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			System.out.println(""); // Just a separator
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				Element employee = (Element) node;

				String id = employee.getAttribute("id");
				String firstname = employee.getElementsByTagName("firstName").item(0).getTextContent();
				String lastname = employee.getElementsByTagName("lastName").item(0).getTextContent();
				String location = employee.getElementsByTagName("location").item(0).getTextContent();
				
				// Print each employee's detail
				System.out.println("id : " + id);
				System.out.println("firstName : " + firstname);
				System.out.println("lastName : " + lastname);
				System.out.println("location : " + location);
			}
		}

	}

	public void processByTraversal(Document document) {

		DocumentTraversal trav = (DocumentTraversal) document;

		NodeIterator it = trav.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ALL, null, true);

		int c = 1;

		System.out.println();

		for (Node node = it.nextNode(); node != null; node = it.nextNode()) {

			String name = node.getNodeName();
			
			String value = "";
			
			if(node.getNodeType()==node.ELEMENT_NODE)
			{
				node.getNodeValue();
			}

			System.out.printf("%d %s %s %s%n", c, name + " : ", node.getNodeType(), value);
			c++;
		}

	}

}
