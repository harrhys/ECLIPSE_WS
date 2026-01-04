package com.test.menu;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MenuToObject {

	public static void main(String[] args) {
		
		HashMap<String,List<MenuItem>> map = new HashMap<String,List<MenuItem>>();

		File rootDir = new File("D:\\WORKSPACE\\PROJECTS\\XMLPROJ\\menus");
		File[] files = rootDir.listFiles(new ConfigurationFileFilter());
		String path = null;
		int i = 0;
		for (i = 0; i < files.length; i++) {
			try {
				if (files[i].isFile()) {
					path = files[i].getPath();
					

					System.out.println("processing file [" + path + "]");

					File file = new File(path);
					JAXBContext jaxbContext = JAXBContext
							.newInstance(MenuItem.class);

					Unmarshaller jaxbUnmarshaller = jaxbContext
							.createUnmarshaller();
					
					
					
					
					MenuItem menu = (MenuItem) jaxbUnmarshaller.unmarshal(file);
					
					
					Marshaller marshaller = jaxbContext.createMarshaller();
					
					File dd = new File("test.xml");
					
					marshaller.marshal(menu, dd);
					
					System.out.println(dd);
					

					

					System.out.println(menu.getId() + " " + menu.getMenuCode());
					System.out.println("CHILD MENUS:");
					List<MenuItem> list = menu.getMenuItem();
					for (MenuItem menus : list)
						System.out.println(menus.getId() + " "
								+ menus.getMenuCode());
					
					
					
					
					
					map.put(menu.getMenuCode(), list);

				}
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Error processing file [" + path + "] - ");
			}

		}
		
		System.out.println(map.toString());

	}
}