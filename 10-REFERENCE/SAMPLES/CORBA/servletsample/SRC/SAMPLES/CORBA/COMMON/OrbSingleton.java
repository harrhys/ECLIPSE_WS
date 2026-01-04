/*
 * @(#)OrbSingleton.java 02/03/05
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.corba.common;

import java.util.*;
import java.io.*;
import javax.naming.*;

import org.omg.CORBA.*;

//import com.iona.corba.util.SystemExceptionDisplayHelper;

/** Initialization of multiple ORBs is avoided through the use of this singleton class.
 *
 * Regardless of the number of instances of the stateless session bean created by the EJB container, the singleton
 * approach ensures that at most one instance of the ORB is created for the entire application.
 *
 * The default private constructor is used to Override ORB classes bundled in Sun ONE application server with
 * Orbix ORB implementation classes and set the Orbix specific parameters.
 *
 */
public class OrbSingleton {

    private ORB orb;
    static private String orbDomain = null;
    static private String orbCfg = null;
 	static private OrbSingleton _instance = null;


    private OrbSingleton() {
      System.out.println("OrbSingleton() constructor: Calling ORB.init().");
      System.out.println("OrbSingleton() constructor: orbDomainName=" + orbDomain);
      System.out.println("OrbSingleton() constructor: orbCfgDir=" + orbCfg);

      /* Override ORB classes bundled in app server with */
      /* Orbix classes. */
      Properties orbProperties = new Properties();
      orbProperties.put("org.omg.CORBA.ORBClass",
                         "com.iona.corba.art.artimpl.ORBImpl");

      orbProperties.put("org.omg.CORBA.ORBSingletonClass",
                        "com.iona.corba.art.artimpl.ORBSingleton");

      String[] args = { "-ORBdomain_name", orbDomain,
                        "-ORBconfig_domains_dir", orbCfg };

      try {
        orb = ORB.init(args, orbProperties);
      }
      catch (SystemException ex) {
      //  System.out.println("OrbSingleton() constructor: ORB.init() exception: " + SystemExceptionDisplayHelper.toString(ex));
      }

      System.out.println("OrbSingleton() constructor: ORB.init() successful.");
    }

    /** Called by CorbaClientServlet to create the OrbSingleton class instance.
     * @param orbDomainName Your Orbix domain name.
     * @param orbCfgDir location of Orbix configuration directory.
     * @return OrbSingleton class instance.
     */    
    static public synchronized OrbSingleton getInstance(String orbDomainName, String orbCfgDir)
    {
	  if (null == _instance) {
		orbDomain = orbDomainName;
		orbCfg = orbCfgDir;
        _instance = new OrbSingleton();
      }
      else {
		System.out.println("OrbSingleton().getInstance(): Singleton already exists.");
      }
      return _instance;
    }

    /** Called by CorbaClientServlet to get the ORB instance.
     * @return Singleton ORB instance (new if not existing already).
     */    
    public ORB getOrb()
	{
      return orb;
    }
}
