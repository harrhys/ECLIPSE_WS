package com.obopay.obopayagent.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerDeposit implements Parcelable{

	String firstName;
	String lastName;
	String mobileNumber;	
	String amount;
	String pin;
	Map<String, String> responseMetaData;
	String programCode;
	
	public CustomerDeposit(Parcel in) {
		String dataStrings[] = new String[6];
		in.readStringArray(dataStrings);
		this.firstName = dataStrings[0];
		this.lastName = dataStrings[1];
		this.mobileNumber = dataStrings[2];
		this.amount = dataStrings[3];
		this.pin = dataStrings[4];
		this.programCode = dataStrings[5];
		
		this.responseMetaData = readMap(in);
	}
	public CustomerDeposit() {
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
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public Map<String, String> getResponseMetaData() {
		return responseMetaData;
	}
	public void setResponseMetaData(Map<String, String> responseMetaData) {
		this.responseMetaData = responseMetaData;
	}
	/**
	 * Reads a Map from a Parcel that was stored using a String array and a Bundle.
	 *
	 * @param in   the Parcel to retrieve the map from
	 * @param type the class used for the value objects in the map, equivalent to V.class before type erasure
	 * @return     a map containing the items retrieved from the parcel
	 */
	public static Map<String, String> readMap(Parcel in) {
		final int size = in.readInt();
		Map<String, String> retVal = new LinkedHashMap<String, String>();
		for (int i=0; i<size; i++) {
			retVal.put(in.readString(), in.readString());
		}
		return retVal;
	}

	/**
	 * Writes a Map to a Parcel using a String array and a Bundle.
	 *
	 * @param map the Map<String,V> to store in the parcel
	 * @param in  the Parcel to store the map in
	 */
	public static void writeMap(Map<String, String> map, Parcel out) {
		if(map == null) {
			out.writeInt(0);
			return;
		}
		out.writeInt(map.size());
		for (final Entry<String, String> entry : map.entrySet()) {
			out.writeString(entry.getKey());
			out.writeString(entry.getValue());
		}
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {firstName,lastName,mobileNumber,amount, pin, programCode});
		writeMap(responseMetaData, dest);
	}

	public static final Parcelable.Creator<CustomerDeposit> CREATOR = new Parcelable.Creator<CustomerDeposit>() {

		@Override
		public CustomerDeposit createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new CustomerDeposit(in);
		}

		@Override
		public CustomerDeposit[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CustomerDeposit[size];
		}
	};
}
