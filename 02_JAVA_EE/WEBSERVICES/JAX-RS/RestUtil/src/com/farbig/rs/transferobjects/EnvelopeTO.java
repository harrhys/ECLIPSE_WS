package com.farbig.rs.transferobjects;



public class EnvelopeTO {
	private RequestTO request;
	private ResponseTO response;

	public RequestTO getRequest() {
		return request;
	}

	public void setRequest(RequestTO request) {
		this.request = request;
	}

	public ResponseTO getResponse() {
		return response;
	}

	public void setResponse(ResponseTO response) {
		this.response = response;
	}

	public EnvelopeTO()
	{
	
	}
}
