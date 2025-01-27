package com.peppercoin.mpp.gateway.util;

import com.peppercoin.common.xml.XMLDoc;
import com.peppercoin.mpp.transaction.ProcessorResponse;

public class GatewayNullResponse implements ProcessorResponse {
	private ResponseCode responseCode;
	private XMLDoc gatewayDoc;
	private XMLDoc internalDoc;

	public GatewayNullResponse(ResponseCode responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.gatewayDoc = new XMLDoc("GATEWAYRESPONSE");
		this.internalDoc = new XMLDoc("INTERNALGATEWAYRESPONSE");
		MessageUtil.addField(this.gatewayDoc, "ERROR-MESSAGE", responseMessage);
		MessageUtil.addField(this.internalDoc, "ERROR-MESSAGE", responseMessage);
	}

	public String getReferenceId() {
		return null;
	}

	public ResponseCode getResponseCode() {
		return this.responseCode;
	}

	public XMLDoc getResponseXML() {
		return this.gatewayDoc;
	}

	public XMLDoc getInternalXML() {
		return this.internalDoc;
	}

	public CVVResponseCode getCvvResponse() {
		return CVVResponseCode.NOT_CHECKED;
	}

	public AVSResponseCode getAvsResponse() {
		return AVSResponseCode.NOT_CHECKED;
	}

	public boolean wasSuccessful() {
		return this.responseCode.equals(ResponseCode.OK);
	}
}