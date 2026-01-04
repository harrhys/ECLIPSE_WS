package com.ui.generator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import android.annotation.SuppressLint;

public class UIGenerator {

	static ContentHandler hd = null;

	static Properties UILayouts = new Properties();

	static Properties attributes = new Properties();

	static Properties elements = new Properties();

	static {
		init();
	}

	@SuppressLint("NewApi")
	static boolean init() {

		try {

			FileReader reader = new FileReader("UILayout.properties");

			UILayouts = new Properties();
			UILayouts.load(reader);

			reader = new FileReader("attributes.properties");
			attributes = new Properties();
			attributes.load(reader);

			reader = new FileReader("elements.properties");
			elements = new Properties();
			elements.load(reader);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	public static void main(String[] args) throws Exception {

		for (String key : UILayouts.stringPropertyNames()) {

			String filename = "D:\\WORKSPACE\\PROJECTS\\test1\\AGENTAPP\\uigen\\com\\ui\\generator\\activity_"
					+ key + ".xml";
			FileOutputStream fos = new FileOutputStream(filename);
			OutputFormat of = new OutputFormat("XML", "utf-8", true);
			of.setIndent(1);
			of.setIndenting(true);

			XMLSerializer serializer = new XMLSerializer(fos, of);
			hd = serializer.asContentHandler();
			hd.startDocument();

			AttributesImpl attrs = new AttributesImpl();
			addAttributes("xmlns:android",
					"http://schemas.android.com/apk/res/android", attrs);
			addAttributes("xmlns:tools", "http://schemas.android.com/tools",
					attrs);
			addAttributes("tools:context", "." + UILayouts.getProperty(key),
					attrs);

			List<String> properties = loadFile(key + ".properties");

			for (String property : properties) {
				System.out.println(property);

				String[] pair = property.split("=");

				String pkey = pair[0];
				String pvalue = pair[1];

				if (!property.startsWith("close")) {

					startElement(elements.getProperty(pkey),
							addAttributes(getAttributes(pvalue), attrs));
				} else {
					String name = elements.getProperty(pvalue);
					endElement(name);
				}
				attrs = new AttributesImpl();

			}

			hd.endDocument();
			fos.close();

		}

	}

	public static List getAttributes(String attrs) {
		
		List<String[]> ats = new ArrayList();
		
		String[] aa = attrs.split(",");

		for (String a : aa) {

			String[] bb = a.split(":");

			if (bb.length == 1) {

				String[] cc = attributes.getProperty(bb[0]).split(",");
				
				ats.add(cc);

			} else {

				if (attributes.getProperty(bb[0]) != null) {
					String[] cc = attributes.getProperty(bb[0]).split(",");
					String[] ddd = {cc[0],bb[1]};
					ats.add(ddd);
				} else {

					ats.add(bb);
				}

			}

		}

		return ats;
	}

	public static void startElement(String Name, AttributesImpl atts) {
		try {
			hd.startElement("", "", Name, atts);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void endElement(String Name) {
		try {
			hd.endElement("", "", Name);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static AttributesImpl addAttributes(String name, String value,
			AttributesImpl atts) {
		atts.addAttribute("", "", name, "", value);
		return atts;
	}

	public static AttributesImpl addAttributes(List<String[]> list, AttributesImpl atts) {
		for (String[] item : list) {

			atts.addAttribute("", "", item[0], "", (String) item[1]);

		}

		return atts;
	}

	public static List loadFile(String filename) {

		List properties = new ArrayList();

		FileInputStream fstream;
		try {
			fstream = new FileInputStream(filename);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				System.out.println(strLine);
				properties.add(strLine);

			}
			// Close the input stream
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties;
	}

}
