package com.obopay.obopayagent.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerRemittance implements Parcelable{

	String firstName;
	String lastName;
	String idType1;
	String idValue1;
	String idType2;
	String idValue2;
	String mobileNumber;	
	String amount;
	String transactionId;
	String transactionType;
	boolean isOTPEnabled;
	String pin;
	
	
	public CustomerRemittance(Parcel in) {
		this.isOTPEnabled = in.readByte() == 1; 
		String dataStrings[] = new String[11];
		in.readStringArray(dataStrings);
		this.firstName = dataStrings[0];
		this.lastName = dataStrings[1];
		this.mobileNumber = dataStrings[2];
		this.amount = dataStrings[3];
		this.pin = dataStrings[4];
		this.transactionId = dataStrings[5];
		this.transactionType = dataStrings[6];
		this.idType1 = dataStrings[7];
		this.idValue1 = dataStrings[8];
		this.idType2 = dataStrings[9];
		this.idValue2 = dataStrings[10];
	}
	public CustomerRemittance() {
		// TODO Auto-generated constructor stub
	}

	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public boolean getIsOTPEnabled() {
		return isOTPEnabled;
	}
	public void setIsOTPEnabled(boolean isOTPEnabled) {
		this.isOTPEnabled = isOTPEnabled;
	}
	
	public String getIdType1() {
		return idType1;
	}
	public void setIdType1(String idType1) {
		this.idType1 = idType1;
	}
	public String getIdValue1() {
		return idValue1;
	}
	public void setIdValue1(String idValue1) {
		this.idValue1 = idValue1;
	}
	public String getIdType2() {
		return idType2;
	}
	public void setIdType2(String idType2) {
		this.idType2 = idType2;
	}
	public String getIdValue2() {
		return idValue2;
	}
	public void setIdValue2(String idValue2) {
		this.idValue2 = idValue2;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (isOTPEnabled ? 1 : 0));
		dest.writeStringArray(new String[] {firstName,lastName,mobileNumber,amount, pin, transactionId, transactionType, idType1, idValue1, idType2, idValue2});
	}

	public static final Parcelable.Creator<CustomerRemittance> CREATOR = new Parcelable.Creator<CustomerRemittance>() {

		@Override
		public CustomerRemittance createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new CustomerRemittance(in);
		}

		@Override
		public CustomerRemittance[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CustomerRemittance[size];
		}
	};
}
