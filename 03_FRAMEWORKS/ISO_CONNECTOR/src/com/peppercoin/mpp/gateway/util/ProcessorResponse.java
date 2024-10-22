package com.peppercoin.mpp.gateway.util;

import com.peppercoin.common.xml.XMLDoc;
import com.peppercoin.mpp.data.dao.AVSResponseCode;
import com.peppercoin.mpp.data.dao.CVVResponseCode;
import com.peppercoin.mpp.data.dao.ResponseCode;

public interface ProcessorResponse {
	String getReferenceId();

	ResponseCode getResponseCode();

	XMLDoc getResponseXML();

	XMLDoc getInternalXML();

	CVVResponseCode getCvvResponse();

	AVSResponseCode getAvsResponse();

	boolean wasSuccessful();
}