/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;


import javax.resource.cci.*;
import javax.resource.ResourceException;
import java.util.*;


// CustomCodeBegin globalScope
// CustomCodeEnd



/**
 * This implementation class enables a component to execute EIS functions.
 */

public class CometInteraction implements Interaction {

  private javax.resource.cci.Connection connection;

  // CustomCodeBegin classScope
  /*example:
  //method that call the EIS
  public boolean callBackEnd(String procName,Record input,Record output){
    boolean rc=true;
    .
    .
    .
    return rc;
  }
  */
  // CustomCodeEnd



  /**
   *Constructor
   *@param con The Connection that created this Interaction instance
   */

  public CometInteraction(javax.resource.cci.Connection con) {
        System.out.println("6.1 ------before set connection");
        connection = con;
        System.out.println("6.1 ------after set connection");
  }


  /**
   *Closes the current Interaction and release all the resources held for this instance by
   * the resource adapter.
   */

  public void close() throws ResourceException{
        connection = null;
  }


  /**
   *Gets the Connection associated with the Interaction.
   *@return  Connection - Connection instance associated with the Interaction
   */

  public Connection getConnection(){
        return connection;
  }



  /**
   * Executes an interaction represented by the InteractionSpec. This form of invocation takes
   * an input Record and updates the output Record.
   * @param ispec InteractionSpec representing a target EIS data/function module
   * @param input  Input Record
   * @param output Output Record
   *@return boolean - true if execution of the EIS function has been successful and output
   *                  Record has been updated; false otherwise
   */

  public boolean execute(InteractionSpec ispec, Record input, Record output) throws ResourceException{
        boolean rc=true;
        if (ispec == null ||
            (!(ispec instanceof CometInteractionSpec))) {
            throw new ResourceException(Messages.getMessage(Messages.INVALID_INTERACTION_SPEC),
                                        Messages.INVALID_INTERACTION_SPEC);
        }

        // ToDo: Add service specific code here
        // CustomCodeBegin execute
        /* example code:
        String procName = ((CometInteractionSpec)ispec).getFunctionName();
        rc=callBackEnd(procName,input,output);   //call the EIS
        */
        // CustomCodeEnd

      	System.out.println(" 8. In CometInteraction::execute .... this="+this);
        MappedRecord zinput = (MappedRecord)input;

//        System.out.println("\n\n"+zinput.values()+"\n\n");

        String name = (String)zinput.get("NAME");
        ((CometConnection)connection).getManagedConnection().sendData(name);
        String outMsg = (String)((CometConnection)connection).getManagedConnection().getData();
        MappedRecord zoutput = (MappedRecord)output;

//        System.out.println("\n\n"+zoutput.values()+"\n\n");

        zoutput.put("WELCOME_MESSAGE",outMsg);
        return rc;
  }


  /**
   * Executes an interaction represented by the InteractionSpec. This form of invocation takes
   * an input Record and returns an output Record if the execution of the Interaction has been
   * successfull.
   * @param ispec  InteractionSpec representing a target EIS data/function module
   * @param input  Input Record
   * @return Record -  if execution of the EIS function has been successful; null otherwise
   */

  public Record execute(InteractionSpec ispec, Record input) throws ResourceException{
        Record output=null;

        if (ispec == null ||
            (!(ispec instanceof CometInteractionSpec))) {
            throw new ResourceException(Messages.getMessage(Messages.INVALID_INTERACTION_SPEC),
                                        Messages.INVALID_INTERACTION_SPEC);
        }

        // ToDo: Add service specific code here
        // CustomCodeBegin execute
        /* example code:
        String procName = ((CometInteractionSpec)ispec).getFunctionName();
        output=new CometIndexedRecord();
        boolean rc=callBackEnd(procName,input,output);   //call the EIS
        if(!rc)
           return null;
        */
	System.out.println("In CometInteraction::execute (2)");
        // CustomCodeEnd

        return output;

  }


  /**
   *Gets the first ResourceWarning from the chain of warnings associated with this Interaction
   *instance.
   *@return  ResourceWarning - ResourceWarning at top of the warning chain
   */

  public ResourceWarning getWarnings() throws ResourceException{
     ResourceWarning resWarning=null;
     // ToDo: Add service specific code here
     // CustomCodeBegin execute
     // CustomCodeEnd
     return resWarning;
  }


  /**
   *Clears all the warning reported by this Interaction instance. After a call to this method,
   * the method getWarnings will return null until a new warning is reported for this Interaction.
   */

  public void clearWarnings() throws ResourceException{
     // ToDo: Add service specific code here
     // CustomCodeBegin clearWarnings
     // CustomCodeEnd
  }
}

