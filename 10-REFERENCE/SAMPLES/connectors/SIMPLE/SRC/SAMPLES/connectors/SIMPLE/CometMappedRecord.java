/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import java.util.*;

/**
 * The interface javax.resource.cci.MappedRecord is used for key-value map based representation
 *of record elements. The MappedRecord interface extends both Record and java.util.Mapinterfaces.
 */
public class CometMappedRecord extends HashMap implements javax.resource.cci.MappedRecord
{
	private String recordName;
	private String description;

	public CometMappedRecord() {
	}

	public CometMappedRecord(String name) {
		recordName = name;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String name) {
		recordName = name;
	}

	public String getRecordShortDescription() {
		return description;
	}

	public void setRecordShortDescription(String description) {
		description = description;
	}


}
