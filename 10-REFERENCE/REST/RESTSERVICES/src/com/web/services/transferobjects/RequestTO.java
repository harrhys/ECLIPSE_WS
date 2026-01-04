package com.web.services.transferobjects;

public class RequestTO {
	
	
	private HeaderTO header;
	private BodyTO body;
	
	public RequestTO()
	{
		
	}	

	public HeaderTO getHeader() {
		return header;
	}

	public void setHeader(HeaderTO header) {
		this.header = header;
	}

	public BodyTO getBody() {
		return body;
	}

	public void setBody(BodyTO body) {
		this.body = body;
	}
	
		
}
