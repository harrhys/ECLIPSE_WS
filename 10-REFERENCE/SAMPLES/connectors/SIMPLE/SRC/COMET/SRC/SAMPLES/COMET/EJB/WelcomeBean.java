/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.comet.ejb;

import java.rmi.*;
import javax.ejb.*;
import samples.connectors.simple.*;
import javax.resource.cci.*;
import javax.naming.InitialContext;
import samples.connectors.simple.CometInteractionSpec;


public class WelcomeBean implements SessionBean
{
    SessionContext _sessionContext;
    String name="";
    String message="";

    public void setSessionContext(SessionContext ctx)
        throws RemoteException
    {
        _sessionContext = ctx;
    }    

    public void ejbCreate() throws javax.ejb.CreateException , RemoteException
    {
    }
    
    public void ejbRemove() throws RemoteException
    {
    }
    
    public void ejbPassivate() throws RemoteException
    {
    }
    
    public void ejbActivate() throws RemoteException
    {
    }

    public void setName(String name)throws javax.resource.ResourceException,java.rmi.RemoteException{
        this.name=name;
    }

    public String getMessage()throws javax.resource.ResourceException,java.rmi.RemoteException{
        return this.message;   
    }

    public boolean execute()throws javax.resource.ResourceException,java.rmi.RemoteException{
             boolean ok=false;
        ConnectionFactory cf=null; 

        try {
            javax.naming.Context nc=new InitialContext();
            cf=(ConnectionFactory)nc.lookup("java:comp/env/eis/Comet");
        } catch(Exception e){
            throw new javax.resource.ResourceException(e.getMessage());
        }       

        RecordFactory rf=cf.getRecordFactory();
        MappedRecord input=rf.createMappedRecord("INPUT");
        MappedRecord output=rf.createMappedRecord("OUTPUT");
        input.put("NAME",this.name);
        output.put("WELCOME_MESSAGE","");          

        Connection con=cf.getConnection();

        Interaction inter=con.createInteraction();

        CometInteractionSpec spec=new CometInteractionSpec();
        ok=inter.execute(spec,input,output);
        this.message=(String)output.get("WELCOME_MESSAGE");          
        con.close();

        return ok;
    }
}
