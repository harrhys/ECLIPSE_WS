package com.javacodegeeks.examples.rivu;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

class MyStringResponseHandler implements ResponseHandler<String>{
	public String handleResponse(final HttpResponse response) {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				try {
					return null == entity ? "" : EntityUtils.toString(entity);
				} catch (ParseException | IOException e) {
					return "Error : "+e.getMessage();
				}
			} else {
				return "Unexpected response status: " + status;
			}
	}
}
