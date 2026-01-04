/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import java.sql.*;
import java.util.Map;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.IllegalStateException;
import javax.resource.cci.*;

/**
 * this implementation class provides info about the capabilities of a 
 * resource adapter implementation.
 */
public class CometResourceAdapterMetaData implements ResourceAdapterMetaData {

    private String vendorName ="Sun" ;
    private String adapterVersion="1.0";
    private String specVersion="1.0";
    private String adapterName="Comet";
    private String description= "Resource Adapter for Comet";


    /**
     *Default Constructor
     */
    public CometResourceAdapterMetaData() {
	System.out.println("In CometResourceAdapterMetaData ctor");
    }

    
    public String getAdapterVersion() {
        return adapterVersion;
    }
                              
    public String getSpecVersion() {
        return specVersion;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public String getAdapterVendorName() {
        return vendorName ;
    }

    public String getAdapterShortDescription() {
        return description;
    }

    public void setAdapterVersion(String version) {
       this.adapterVersion = version;
    }
                              
    public void setSpecVersion(String version) {
       this.specVersion = version;
    }

    public void setAdapterName(String name) {
        this.adapterName = name;
    }

    public void setAdapterVendorName(String name) {
       this.vendorName = name;
    }

    public void setAdapterShortDescription(String description) {
        this.description = description;
    }

    public String[] getInteractionSpecsSupported() {
        String[] str = new String[1];
        str[0]=new String("samples.connectors.simple.CometInteractionSpec");
        return str;
    }

    public boolean supportsExecuteWithInputAndOutputRecord() {
        return true;
    }

    public boolean supportsExecuteWithInputRecordOnly() {
        return true;
    }

    public boolean supportsLocalTransactionDemarcation() {
        return false;
    }
}

