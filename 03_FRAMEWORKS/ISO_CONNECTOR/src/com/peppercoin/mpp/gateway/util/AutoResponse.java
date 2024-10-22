package com.peppercoin.mpp.gateway.util;

import com.peppercoin.common.xml.XMLDoc;
import com.peppercoin.mpp.data.dao.TransactionRequest;

public class AutoResponse implements ProcessorResponse {
	private TransactionRequest transaction;

	public AutoResponse(TransactionRequest transaction) {
		this.transaction = transaction;
	}

	public String getReferenceId() {
		return "" + this.transaction.getTransactionId();
	}

	public CVVResponseCode getCvvResponse() {
		String r = this.transaction.getCvvResponse();
		return r != null && r.length() >= 1 ? CVVResponseCode.find(r.charAt(0)) : CVVResponseCode.NOT_CHECKED;
	}

	public AVSResponseCode getAvsResponse() {
		String r = this.transaction.getAvsResponse();
		return r != null && r.length() >= 1 ? AVSResponseCode.find(r.charAt(0)) : AVSResponseCode.NOT_CHECKED;
	}

	public ResponseCode getResponseCode() {
		return ResponseCode.find(this.transaction.getResponseCode());
	}

	public boolean wasSuccessful() {
		return this.getResponseCode().equals(ResponseCode.OK);
	}

	public XMLDoc getResponseXML() {
		return new XMLDoc("GATEWAYRESPONSE");
	}

	public XMLDoc getInternalXML() {
		return new XMLDoc("INTERNALGATEWAYRESPONSE");
	}
}