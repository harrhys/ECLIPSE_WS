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
import java.util.Map;
import java.util.Collection;
import javax.resource.ResourceException;

/**
 * This implementation class is used for creating IndexedRecord or MappedRecord
 * instances.
 */

public class CometRecordFactory implements javax.resource.cci.RecordFactory{

    /**
     *Default Constructor
     */
    public CometRecordFactory() {
	System.out.println(" 3. In CometRecordFactory ctor");
    }

    /**
     *Creates a MappedRecord. The method takes the name of the record that is to be created by
     *the RecordFactory. The name of the record acts as a pointer to the meta information
     *(stored in the metadata repository) for a specific record type.
     *@param recordName  Name of the Record
     *@return MappedRecord - MappedRecord instance
     */
    public MappedRecord createMappedRecord(String recordName)
        throws ResourceException {
        return new CometMappedRecord(recordName);
    }

    /**
     *Creates a IndexedRecord. The method takes the name of the record that is to be created
     *by the RecordFactory. The name of the record acts as a pointer to the meta informatio
     * (stored in the metadata repository) for a specific record type.
     *@param recordName  Name of the Record
     *@return IndexedRecord - IndexedRecord instance     
     */
    public IndexedRecord createIndexedRecord(String recordName)
        throws ResourceException {
        return new CometIndexedRecord(recordName);
    }

}
