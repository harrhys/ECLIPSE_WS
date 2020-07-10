package com.farbig.rs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class RestUtil {

	String URLString;

	HttpURLConnection conn;

	public void baseURL(String baseURL) {
		URLString = baseURL;
	}

	public void path(String path) throws Exception {
		if (URLString == null || URLString.equals("")) {
			throw new Exception("Base URL needs to be set before setting the path");
		} else {
			URLString = URLString + "/" + path;
		}
	}

	public void pathParam(String pathParam) throws Exception {

		if (URLString == null || URLString.equals("")) {
			throw new Exception("Base URL and Path needs to be set before setting the pathParam");
		} else {
			URLString = URLString + "/" + pathParam;
		}

	}

	private void connect() throws IOException {
		URL url = new URL(URLString);
		System.out.println("Connecting to : " + URLString);
		conn = (HttpURLConnection) url.openConnection();

	}

	public String getTextHTML() throws IOException {
		return get("text/html");
	}

	public String getApplicationJson() throws IOException {
		return get("application/json");
	}

	public String get(String mediaType) throws IOException {
		
		if (conn == null)
			connect();
		
		conn.setRequestProperty("Accept", mediaType);
		conn.setRequestMethod("GET");
		
		isOK();

		return readResponse();
	}
	
	public String postApplicationJson(String jsonString) throws IOException
	{
		if (conn == null)
			connect();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		
		OutputStream os = conn.getOutputStream();
		os.write(jsonString.getBytes());
		os.flush();
		
		isCreated();
		
		return readResponse();
	}
	
	public String postNGetApplicationJson(String jsonString) throws IOException
	{
		if (conn == null)
			connect();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		
		OutputStream os = conn.getOutputStream();
		os.write(jsonString.getBytes());
		os.flush();
		
		isOK();
		
		return readResponse();
	}
	
	public String putTextHTML() throws IOException
	{
		return put("text/html");
	}
	
	public String putApplicationJson() throws IOException {
		return put("application/json");
	}
	
	public String put(String mediaType) throws IOException
	{
		if (conn == null )
			connect();
		
		conn.setRequestProperty("Accept", mediaType);
		conn.setRequestMethod("PUT");
		
		isOK();

		return readResponse();
	}
	
	public String deleteTextHTML() throws IOException
	{
		return delete("text/html");
	}
	
	public String deleteApplicationJson() throws IOException {
		return delete("application/json");
	}
	
	public String delete(String mediaType) throws IOException
	{
		if (conn == null )
			connect();
		
		conn.setRequestProperty("Accept", mediaType);
		conn.setRequestMethod("DELETE");
		
		isOK();

		return readResponse();
	}
	
	public String readResponse() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		StringBuffer buffer = new StringBuffer();
		String output;
		while ((output = br.readLine()) != null) {
			buffer.append(output);
		}

		return buffer.toString();
	}
	


	public void isOK() throws IOException {
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
	}

	public void isCreated() throws IOException {
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
	}

	public void disconnect() {
		conn.disconnect();
		conn = null;
	}

	public String JsonString(Object obj) {
		return new Gson().toJson(obj);
	}

	public <T> T JsonObject(String JsonString, Class<T> JsonObjClass) {
		return new Gson().fromJson(JsonString, JsonObjClass);
	}

}


