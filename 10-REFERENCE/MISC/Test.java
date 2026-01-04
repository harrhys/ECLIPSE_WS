import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;
//Xerces 1 or 2 additional classes.


public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String filename = "E:\\ANDROID\\PROJECTS\\XML\\src\\Test.xml";
		FileOutputStream fos = new FileOutputStream(filename);
		// XERCES 1 or 2 additionnal classes.
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(1);
		of.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(fos,of);
		// SAX2.0 ContentHandler.
		ContentHandler hd = serializer.asContentHandler();
		hd.startDocument();
		// Processing instruction sample.
		//hd.processingInstruction("xml-stylesheet","type=\"text/xsl\" href=\"users.xsl\"");
		// USER attributes.
		AttributesImpl atts = new AttributesImpl();
		
		atts.addAttribute("","","android:layout_width","","match_parent");
		atts.addAttribute("","","android:layout_height","","match_parent");
		atts.addAttribute("","","tools:context","",".MyAccountSelectOptionActivity");
		atts.addAttribute("","","xmlns:android","","http://schemas.android.com/apk/res/android");
		atts.addAttribute("","","xmlns:tools","","http://schemas.android.com/tools");
		// USERS tag.
		hd.startElement("","","ScrollView",atts);
		
		AttributesImpl atts1 = new AttributesImpl();
		
		atts1.addAttribute("","","android:layout_width","","match_parent");
		atts1.addAttribute("","","android:layout_height","","wrap_content");
		atts1.addAttribute("","","android:orientation","","vertical");
		
		hd.startElement("","","LinearLayout",atts1);
		
		
/*		// USER tags.
		String[] id = {"PWD122","MX787","A4Q45"};
		String[] type = {"customer","manager","employee"};
		String[] desc = {"Tim@Home","Jack&Moud","John D'oé"};
		for (int i=0;i<id.length;i++)
		{
		  atts.clear();
		  atts.addAttribute("","","ID","CDATA",id[i]);
		  atts.addAttribute("","","TYPE","CDATA",type[i]);
		  hd.startElement("","","USER",atts);
		  hd.characters(desc[i].toCharArray(),0,desc[i].length());
		  hd.endElement("","","USER");
		}*/
		hd.endElement("","","USERS");
		hd.endDocument();
		fos.close();

	}

}
