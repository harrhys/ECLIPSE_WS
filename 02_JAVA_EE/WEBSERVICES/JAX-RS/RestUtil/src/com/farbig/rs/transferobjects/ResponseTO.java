package com.farbig.rs.transferobjects;


public class ResponseTO {

	private HeaderTO header;
	private BodyTO body;
	
	public ResponseTO()
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