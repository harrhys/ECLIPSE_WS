package com.farbig.examples.jsf.html5;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

/**
 * Simple bean for storing form field values.
 */
@Named
@SessionScoped
public class Bean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name = "Balaji";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String totalValue = "99.00";

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	private String email = "";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String emailAgain = "";

	public String getEmailAgain() {
		return emailAgain;
	}

	public void setEmailAgain(String emailAgain) {
		this.emailAgain = emailAgain;
	}

	private String arrival = "";

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	private String nights = "1";

	public String getNights() {
		return nights;
	}

	public void setNights(String nights) {
		this.nights = nights;
	}

	private String guests = "1";

	public String getGuests() {
		return guests;
	}

	public void setGuests(String guests) {
		this.guests = guests;
	}

	public void calculateTotal(AjaxBehaviorEvent event) throws AbortProcessingException {
		int nightsNum = 0;
		int guestsNum = 1;
		int total = 0;

		if (nights.trim().length() > 0) {
			nightsNum = Integer.parseInt(nights);
		}
		if (guests.trim().length() > 0) {
			guestsNum = Integer.parseInt(guests);
		}

		total = (nightsNum * 99) + ((guestsNum - 1) * 10);
		totalValue = String.valueOf(total) + ".00";
	}

	public void clear(AjaxBehaviorEvent event) throws AbortProcessingException {
		name = "";
		email = "";
		emailAgain = "";
		arrival = "";
		totalValue = "99.00";
		nights = "1";
		guests = "1";
	}

}
