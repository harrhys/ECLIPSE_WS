
/*
 * @(#)AddressBookReader01.java 1.9 98/11/10
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
public class AddressBookReader01 
{
 
    public static void main (String argv [])
    {
        // Check the arguments
        if (argv.length != 1) {
            System.err.println ("Usage: java AddressBookReader filename");
            System.exit (1);
        }
        String filename = argv[0];
        File f = new File(filename);
        AddressBookReader01 reader = new AddressBookReader01();
        reader.parse(f);
    }
     
   
 
    /** Parse the input */
    public void parse(File f) 
 
    {
        try {
            // Get an efficient reader for the file
 
            FileReader r = new FileReader(f);
 
 
            BufferedReader br = new BufferedReader(r);
          
            // Read the file and display it's contents.
            String line = br.readLine();
            while (null != (line = br.readLine())) {
              if (line.startsWith("xmozillanickname: ")) break;
            }
 
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
 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }    
 
    void output(String name, String prefix, String line)
 
    {
      int startIndex = prefix.length() + 2; // 2=length of ": " after the name
      String text = line.substring(startIndex);
 
      System.out.println(name + ": " + text);
 
 
    }
  
}
