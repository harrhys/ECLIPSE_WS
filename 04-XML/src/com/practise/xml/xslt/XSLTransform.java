package com.practise.xml.xslt;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XSLProcessor;
import oracle.xml.parser.v2.XSLStylesheet;

public class XSLTransform {
	public static void main(String args[]) throws Exception {
		DOMParser parser;
		XMLDocument xml, xsldoc, out;

		URL xslURL;
		URL xmlURL;

		try {
			if (args.length != 2) {
				// Pass in the names of the XSL and XML files
				System.err.println("Usage: java XSLTransform " + "xslfile xmlfile");
				System.exit(1);
			}

			// Parse XSL and XML documents
			parser = new DOMParser();
			parser.setPreserveWhitespace(true);

			xslURL = createURL(args[0]);
			parser.parse(xslURL);
			xsldoc = parser.getDocument();

			xmlURL = createURL(args[1]);
			parser.parse(xmlURL);
			xml = parser.getDocument();

			// Instantiate the stylesheet
			XSLStylesheet xsl = new XSLStylesheet(xsldoc, xslURL);

			XSLProcessor processor = new XSLProcessor();

			// Display any warnings that may occur
			processor.showWarnings(true);
			processor.setErrorStream(System.err);

			// Process XSL
			DocumentFragment result = processor.processXSL(xsl, xml);

			// Create an output document to hold the result
			out = new XMLDocument();

			// Create a dummy document element for the output document
			Element root = out.createElement("root");
			out.appendChild(root);

			// Append the transformed tree to the dummy document element
			root.appendChild(result);

			// Print the transformed document
			out.print(System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Helper method to create a URL from a file name
	   static URL createURL(String fileName)
	   {
	      URL url = null;
	      try 
	      {
	         url = new URL(fileName);
	      } 
	      catch (MalformedURLException ex) 
	      {
	         File f = new File(fileName);
	         try 
	         {
	            String path = f.getAbsolutePath();
	            // This is a bunch of weird code that is required to
	            // make a valid URL on the Windows platform, due
	            // to inconsistencies in what getAbsolutePath returns.
	            String fs = System.getProperty("file.separator");
	            if (fs.length() == 1)
	            {
	               char sep = fs.charAt(0);
	               if (sep != '/')
	                  path = path.replace(sep, '/');
	               if (path.charAt(0) != '/')
	                  path = '/' + path;
	            }
	            path = "file://" + path;
	            url = new URL(path);
	         } 
	         catch (MalformedURLException e) 
	         {
	            System.out.println("Cannot create url for: " + fileName);
	            System.exit(0);
	         }
	      }
	      return url;
	   }
}