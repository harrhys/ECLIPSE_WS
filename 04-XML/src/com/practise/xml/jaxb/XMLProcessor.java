package com.practise.xml.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.practise.xml.jaxb.objects.MenuItem;
import com.practise.xml.jaxb.objects.Survey;

public class XMLProcessor {

	public static void main(String[] args) {

		XMLProcessor processor = new XMLProcessor();
		
		processor.processXMLFiles("src/com/practise/xml/jaxb/inputxml/menus", MenuItem.class);

		processor.processXMLFiles("src/com/practise/xml/jaxb/inputxml/surveys", Survey.class);

	}

	public <T> void processXMLFiles(String xmlFolderPath, Class<T> xmlObjectClass) {

		File rootDir = new File(xmlFolderPath);

		File[] files = rootDir.listFiles(new XMLFileFilter());

		String path = null;
		
		if(files!=null && files.length>0)

		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {

				T xmlObject = (T) XMLToObject(files[i], xmlObjectClass);

				System.out.println(xmlObject);

				String newPath = "src/com/practise/xml/jaxb/outputxml/" + xmlObjectClass.getSimpleName() + (i + 1) + ".xml";

				File newXMLFile = new File(newPath);

				ObjectToXML(xmlObject, newXMLFile);

				System.out.println();

			}
		}
	}

	public <T> Object XMLToObject(File xmlFile, Class<T> xmlObjectClass) {

		System.out.println("Processing file [" + xmlFile.getPath() + "]");

		Object xmlObject = null;

		try {

			JAXBContext context = JAXBContext.newInstance(xmlObjectClass);

			Unmarshaller um = context.createUnmarshaller();

			xmlObject = um.unmarshal(xmlFile);

		} catch (Exception e) {

			System.out.println("Error processing file [" + xmlFile.getPath() + "] - ");
			e.printStackTrace();
		}

		return xmlObject;
	}

	public void ObjectToXML(Object xmlObject, File newXMLFile) {

		System.out.println("Creating new file [" + newXMLFile.getPath() + "]");

		try {

			JAXBContext context = JAXBContext.newInstance(xmlObject.getClass());

			Marshaller marshaller = context.createMarshaller();

			marshaller.marshal(xmlObject, newXMLFile);

		} catch (Exception e) {

			System.out.println("Error creating file [" + newXMLFile.getPath() + "] - ");
			e.printStackTrace();
		}
	}
}