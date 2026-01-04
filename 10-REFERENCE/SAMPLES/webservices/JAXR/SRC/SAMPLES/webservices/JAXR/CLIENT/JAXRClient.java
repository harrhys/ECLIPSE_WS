/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package samples.webservices.jaxr.client;
import samples.webservices.jaxr.service.*;
import javax.xml.registry.infomodel.Key;

/**
 * The JAXRClient is wrapper class which calls JAXRQuery,
 * JAXRPublish, JAXRDelete and JAXRQueryByWSDLClassification.
 * It searches a registry, publishes to a registry and 
 * deletes from a registry.
 *
 */
public class JAXRClient {
    static String action = "";
    public static void main(String[] args) {
        String queryURL = "";
        String publishURL ="";
        String username = "";
        String password ="";
        String queryString = "";
        String keyString ="";
        String uuidString ="";
        try {
            if (args.length<1) usage(0);
            action = new String(args[0]);
            if (action.equalsIgnoreCase("run-query")) {
                if (args.length < 3) usage(1);
                queryURL = new String(args[1]);
                queryString = new String(args[2]);
                JAXRQuery jq = new JAXRQuery();
                jq.makeConnection(queryURL, publishURL);
                jq.executeQuery(queryString);
            } else if (action.equalsIgnoreCase("run-query-wsdl")) {
                if (args.length < 3) usage(2);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                JAXRQueryByWSDLClassification jq = new JAXRQueryByWSDLClassification();
                jq.makeConnection(queryURL, publishURL);
                jq.executeQuery();
            } else if (action.equalsIgnoreCase("run-query-naics")) {
                if (args.length < 3) usage(3);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                JAXRQueryByNAICSClassification jq = new JAXRQueryByNAICSClassification();
                jq.makeConnection(queryURL, publishURL);
                jq.executeQuery();
            } else if (action.equalsIgnoreCase("run-publish")) {
                if (args.length < 5) usage(4);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                username = new String(args[3]);
                password = new String(args[4]);
                JAXRPublish jp = new JAXRPublish();
                jp.makeConnection(queryURL, publishURL);
                jp.executePublish(username,password);
            } else if (action.equalsIgnoreCase("run-delete")) {
                if (args.length < 6) usage(5);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                username = new String(args[3]);
                password = new String(args[4]);
                keyString = new String(args[5]);
                JAXRDelete jd = new JAXRDelete();
                jd.makeConnection(queryURL, publishURL);
                Key key = jd.createOrgKey(keyString);
                if (key != null) {
                    jd.executeRemove(key, username, password);
                } else {
                    System.out.println("Key Not found, nothing to remove");
                }
            } else if (action.equalsIgnoreCase("run-save-scheme")) {
                if (args.length < 5) usage(6);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                username = new String(args[3]);
                password = new String(args[4]);
                JAXRSaveClassificationScheme jscs = new JAXRSaveClassificationScheme();
                jscs.makeConnection(queryURL, publishURL);
                jscs.executePublish(username,password);
            } else if (action.equalsIgnoreCase("run-delete-scheme")) {
                if (args.length < 6) usage(7);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                username = new String(args[3]);
                password = new String(args[4]);
                keyString = new String(args[5]);
                JAXRDeleteScheme jdcs = new JAXRDeleteScheme();
                jdcs.makeConnection(queryURL, publishURL);
                Key key = jdcs.createSchemeKey(keyString);
                if (key != null) {
                    jdcs.executeRemove(key, username, password);
                } else {
                    System.out.println("Key Not found, nothing to remove");
                }
            } else if (action.equalsIgnoreCase("run-publish-postal")) {
                if (args.length < 5) usage(8);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                uuidString = new String(args[3]);
                username = new String(args[4]);
                password = new String(args[5]);
                JAXRPublishPostal jpp = new JAXRPublishPostal();
                jpp.makeConnection(queryURL, publishURL, uuidString);
                jpp.executePublish(username,password);
            } else if (action.equalsIgnoreCase("run-query-postal")) {
                if (args.length < 4) usage(9);
                queryURL = new String(args[1]);
                uuidString = new String(args[2]);
                queryString = new String(args[3]);
                JAXRQueryPostal jqp = new JAXRQueryPostal();
                jqp.makeConnection(queryURL, publishURL,uuidString);
                jqp.executeQuery(queryString);
            } else if (action.equalsIgnoreCase("run-query-objects")) {
                if (args.length < 5) usage(4);
                queryURL = new String(args[1]);
                publishURL = new String(args[2]);
                username = new String(args[3]);
                password = new String(args[4]);
                JAXRGetMyObjects jqo = new JAXRGetMyObjects();
                jqo.makeConnection(queryURL, publishURL);
                jqo.executeQuery(username,password);
           } else {
                usage(0);
            }
        } catch (Exception e) {
            System.out.println("Caught exception"+e.getLocalizedMessage());
            usage(0);
        }
    }

    /**
     * Displays usage of JAXRClient commands.
     * an empty string if the value is null.
     *
     * @param errcode	an errorCode
     */
    static void usage(int errcode){             
        if (errcode==0) {
            System.out.println("Usage: asant <command> [operands...]");
            System.out.println("where <command> is one of the following:");
            System.out.println("run-query           - "+
                "Queries a registry server with user-supplied string.");
            System.out.println("run-query-wsdl      - "+
                "Queries a registry server for organization that offer WSDL-compliant services.");
            System.out.println("run-query-naics     - "+
                "Queries a registry server for organization using an NAICS classification.");
            System.out.println("run-publish         - "+
                "Publishes an Organization to a registry server.");
            System.out.println("run-delete          - "+
                "Deletes an Organization from a registry server.");
            System.out.println("run-save-scheme     - "+
                "Publishes a classification scheme to a registry server.");
            System.out.println("run-delete-scheme   - "+
                "Deletes a  classification scheme from a registry server.");
            System.out.println("run-publish-postal  - "+
                "Publishes an Organization with a primary contact, having postal address.");
            System.out.println("run-query-postal    - "+
                "Queries a registry server and displays postal addresses using user-specified UUID scheme");
            System.out.println("run-query-objects   - "+
                "Retrieves all the objects in registry server owned by a user.");
            System.out.println("Examples:");
        } else {
            System.out.println("Invalid number of operands received.");
            System.out.println("Command '"+action+"' was not executed successfully.");
            System.out.print("Usage:");
        }
        if (errcode==0 || errcode==1)
            System.out.println("asant run-query"+
                " -Dquery-url=<query-url>"+
                " -Dquery-string=<query-string>");
        if (errcode==0 || errcode==2)
            System.out.println("asant run-query-wsdl"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>");
        if (errcode==0 || errcode==3)
            System.out.println("asant run-query-naics"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>");
        if (errcode==0 || errcode==4)
            System.out.println("asant run-publish"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>"+
                " -Dusername=<publish-username>"+
                " -Dpassword=<publish-password>");
        if (errcode==0 || errcode==5)
            System.out.println("asant run-delete"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>"+
                " -Dusername=<publish-username>"+
                " -Dpassword=<publish-password>"+
                " -Dkey-string=<key-from-run-publish>" );
        if (errcode==0 || errcode==6)
            System.out.println("asant run-save-scheme"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>"+
                " -Dusername=<publish-username>"+
                " -Dpassword=<publish-password>");
        if (errcode==0 || errcode==7)
            System.out.println("asant run-delete-scheme"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>"+
                " -Dusername=<publish-username>"+
                " -Dpassword=<publish-password>"+
                " -Dkey-string=<key-from-run-save-scheme>" );
        if (errcode==0 || errcode==8)
            System.out.println("asant run-publish-postal"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>"+
                " -Duuid-string=<uuid-string>"+
                " -Dusername=<publish-username>"+
                " -Dpassword=<publish-password>");
        if (errcode==0 || errcode==9)
            System.out.println("asant run-query-publish"+
                " -Dquery-url=<query-url>"+
                " -Duuid-string=<uuid-string>"+
                " -Dquery-string=<query-string>");
        if (errcode==0 || errcode==10)
            System.out.println("asant run-query-objects"+
                " -Dquery-url=<query-url>"+
                " -Dpublish-url=<publish-url>"+
                " -Dusername=<publish-username>"+
                " -Dpassword=<publish-password>");
        System.exit(1);
   }
}
