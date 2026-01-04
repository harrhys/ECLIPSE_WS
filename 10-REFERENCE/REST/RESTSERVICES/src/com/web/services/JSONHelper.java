package com.web.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.services.transferobjects.BodyTO;
import com.web.services.transferobjects.EnvelopeTO;
import com.web.services.transferobjects.HeaderTO;
import com.web.services.transferobjects.RequestTO;

public class JSONHelper {

	Gson gson = null;
	JsonParser parser = null;

	public JSONHelper() {
		gson = new Gson();
		parser = new JsonParser();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JSONHelper helper = new JSONHelper();
		
		String request = helper.jsonRequest("bank unload");
		
		System.out.println(request);
		
		System.out.println(helper.stringToBean(request).getRequest().getBody().getTxnType());
		
		

	}

	public String convertToJsonString(EnvelopeTO envelope) {
		String str = gson.toJson(envelope);
		return str;
	}

	public EnvelopeTO stringToBean(String jsonString) {

		EnvelopeTO envelope = null;
		JsonObject jsonobject = (JsonObject) parser.parse(jsonString);
		envelope = gson.fromJson(jsonobject, EnvelopeTO.class);
		return envelope;
	}

	public JsonObject stringToJsonObj(String jsonString) {
		JsonObject jsonObject = (JsonObject) parser.parse(jsonString);
		return jsonObject;
	}

	public String jsonRequest(String txnType) {

		HeaderTO header = genericRequestHeader();
		BodyTO body = new BodyTO();
		body.setTxnType(txnType);
		RequestTO requestTO = new RequestTO();
		EnvelopeTO envelopeTO = new EnvelopeTO();

		requestTO.setHeader(header);
		requestTO.setBody(body);
		envelopeTO.setRequest(requestTO);

		String str = convertToJsonString(envelopeTO);
		System.out.println(str);
		JsonObject jsonObject = stringToJsonObj(str);
		//str = prettyPrint(jsonObject);

		return str;
	}
	

	public String prettyPrint(JsonObject jsonobject) {
		StringBuffer buffer = new StringBuffer();
		addMessageWithTabs(buffer, "{", 0);
		prettyPrint(jsonobject, buffer, -1);
		addMessageWithTabs(buffer, "}", 0);
		return buffer.toString();
	}

	public String prettyPrint(JsonObject jsonobject, StringBuffer buffer,
			int depth) {
		depth++;

		Set<Map.Entry<String, JsonElement>> set = jsonobject.entrySet();

		Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
		boolean commaAdded = false;

		while (iterator.hasNext()) {
			Map.Entry<String, JsonElement> entry = iterator.next();
			if (entry.getValue() instanceof JsonObject) {
				addMessageWithTabs(buffer, entry.getKey() + ":" + " { ", depth);
				prettyPrint((JsonObject) entry.getValue(), buffer, depth);
				addMessageWithTabs(buffer, "}", depth);
				buffer.append(",");
				commaAdded = true;
			}

			else {
				addMessageWithTabs(buffer,
						entry.getKey() + ":" + entry.getValue(), depth);
				buffer.append(",");
				commaAdded = true;
			}
		}

		if (commaAdded)
			buffer.deleteCharAt(buffer.length() - 1);

		depth--;
		return buffer.toString();

	}

	public void addMessageWithTabs(StringBuffer buffer, String message, int tab) {

		buffer.append("\n");
		for (int i = 0; i < tab; i++) {
			buffer.append("    ");
		}
		buffer.append(message);

	}

	private HeaderTO genericRequestHeader() {
		HeaderTO header = new HeaderTO();

		header.setVersion("1.0");
		header.setOutput("json");
		header.setEncoding("utf-8");
		header.setUserId("USERNAME");
		header.setPassword("PASSWORD");
		header.setTerminalId("2342342342");
		header.setEtid("1111" + getRandomNumber());
		header.setPartner("PARTNER");
		header.setLocale("en_UG");
		return header;
	}

	private Long getRandomNumber() {
		SecureRandom prng;
		Integer mas = null;
		Long number = null;
		try {
			prng = SecureRandom.getInstance("SHA1PRNG");
			mas = new Integer(prng.nextInt(99999));
			number = new Long(mas);

		} catch (NoSuchAlgorithmException e) {
		}

		return number;

	}

}
