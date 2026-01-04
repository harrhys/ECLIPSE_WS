
/*
 * @(#)AddressBookReader02.java 1.9 98/11/10
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 * 
 */

package samples.xml.xslt;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

/**
 * AddressBookReader -- an application that reads an address book file
 * exported from Netscape Messenger using the Line Delimited Interchange
 * Format (LDIF).
 * <p>
 * LDIF address book files have this format:<pre>
 *   dn: cn=FirstName LastName,mail=emailAddress
 *   modifytimestamp: 20010328014700Z
 *   cn: FirstName LastName  --display name (concatenation of givenname+sn)
 *   xmozillanickname: Fred        --------+
 *   mail: fred                            |
 *   xmozillausehtmlmail: TRUE             +-- We care about these
 *   givenname: Fred                       |
 *   sn: Flintstone   --(surname)          |
 *   telephonenumber: 999-Quarry           |
 *   homephone: 999-BedrockLane            |
 *   facsimiletelephonenumber: 888-Squawk  |
 *   pagerphone: 777-pager                 |
 *   cellphone: 666-cell           --------+
 *   xmozillaanyphone: Work#
 *   objectclass: top
 *   objectclass: person
 * </pre>
 *
 * @author Eric Armstrong
 */
public class AddressBookReader02 
  implements XMLReader
 
{
   
 
    ContentHandler handler;
    
    // We're not doing namespaces, and we have no
    // attributes on our elements. 
    String nsu = "";  // NamespaceURI
    Attributes atts = new AttributesImpl();
    String rootElement = "addressbook";
  
    String indent = "\n    "; // for readability!
        
 
    /** Parse the input */
    public void parse( 
InputSource input)   
    throws IOException, SAXException
 
    {
        try {
            // Get an efficient reader for the file
 
 
            java.io.Reader r = input.getCharacterStream();
 
            BufferedReader br = new BufferedReader(r);
          
            // Read the file and display it's contents.
            String line = br.readLine();
            while (null != (line = br.readLine())) {
              if (line.startsWith("xmozillanickname: ")) break;
            }
 
            if (handler==null) {
              throw new SAXException("No content handler");
            }
            // Note: 
            // We're ignoring setDocumentLocator(), as well
            handler.startDocument();      
            handler.startElement(nsu, rootElement, rootElement, atts);
 
            output("nickname", "xmozillanickname", line);
            line = br.readLine();
            output("email",    "mail",             line);
            line = br.readLine();
            output("html",     "xmozillausehtmlmail", line);
            line = br.readLine();
            output("firstname","givenname",        line);
            line = br.readLine();
            output("lastname", "sn",               line);
            line = br.readLine();
            output("work",     "telephonenumber",  line);
            line = br.readLine();
            output("home",     "homephone",        line);
            line = br.readLine();
            output("fax",      "facsimiletelephonenumber", line);
            line = br.readLine();
            output("pager",    "pagerphone",       line);
            line = br.readLine();
            output("cell",     "cellphone",        line);
 
        handler.ignorableWhitespace("\n".toCharArray(), 
                                    0, // start index
                                    1  // length
                                    );  
          handler.endElement(nsu, rootElement, rootElement);
          handler.endDocument();
 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }    
 
    void output(String name, String prefix, String line)
 
    throws SAXException
 
    {
      int startIndex = prefix.length() + 2; // 2=length of ": " after the name
      String text = line.substring(startIndex);
 
 
      int textLength = line.length() - startIndex;
      handler.ignorableWhitespace(indent.toCharArray(), 
                                  0, // start index
                                  indent.length() 
                                  );
      handler.startElement(nsu, name, name /*"qName"*/, atts);
      handler.characters(line.toCharArray(), 
                         startIndex,
                         textLength);
      handler.endElement(nsu, name, name);
 
    }
  
    
    /** Allow an application to register a content event handler. */
    public void setContentHandler(ContentHandler handler) {
      this.handler = handler;
    } 

    /** Return the current content handler. */
    public ContentHandler getContentHandler() {
      return this.handler;
    } 
    
    //=============================================
    // IMPLEMENT THESE FOR A ROBUST APP
    //=============================================
    /** Allow an application to register an error event handler. */
    public void setErrorHandler(ErrorHandler handler)
    { }
    
    /** Return the current error handler. */
    public ErrorHandler getErrorHandler()
    { return null; }

    //=============================================
    // IGNORE THESE
    //=============================================   
    /** Parse an XML document from a system identifier (URI). */
    public void parse(String systemId)
    throws IOException, SAXException 
    { }
     
    /** Return the current DTD handler. */
    public DTDHandler getDTDHandler()
    { return null; }
  
    /** Return the current entity resolver. */
    public EntityResolver getEntityResolver()
    { return null; }
  
    /** Allow an application to register an entity resolver. */
    public void setEntityResolver(EntityResolver resolver)
    { }
  
    /** Allow an application to register a DTD event handler. */
    public void setDTDHandler(DTDHandler handler)
    { }
  
    /** Look up the value of a property. */
    public Object getProperty(String name)
    { return null; }
    
    /** Set the value of a property. */
    public void setProperty(String name, Object value)
    { } 

    /** Set the state of a feature. */
    public void setFeature(String name, boolean value)
    { }
    
    /** Look up the value of a feature. */
    public boolean getFeature(String name)
    { return false; }
}
