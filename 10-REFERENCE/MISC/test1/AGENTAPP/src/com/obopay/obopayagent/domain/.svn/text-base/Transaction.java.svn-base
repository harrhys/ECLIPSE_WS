package com.obopay.obopayagent.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {
	private Integer seriesNumber;
	private String shortDescription;
	private String description;
	private String date;
	private String dateTime;
	private String amount;
	private Map<String, String> responseMetadata;
	
	public Transaction(Parcel in) {
		this.seriesNumber = in.readInt();
		String dataStrings[] = new String[5];
		in.readStringArray(dataStrings);
		this.shortDescription = dataStrings[0];
		this.description = dataStrings[1];
		this.date = dataStrings[2];
		this.dateTime = dataStrings[3];
		this.amount = dataStrings[4];
		this.responseMetadata = readMap(in);
	}
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Integer getSeriesNumber() {
		return seriesNumber;
	}
	public void setSeriesNumber(Integer seriesNumber) {
		this.seriesNumber = seriesNumber;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Map<String, String> getResponseMetadata() {
		return responseMetadata;
	}
	public void setResponseMetadata(Map<String, String> responseMetadata) {
		this.responseMetadata = responseMetadata;
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
		dest.writeInt(seriesNumber);
		dest.writeStringArray(new String[] {shortDescription, description,date,dateTime,amount});
		writeMap(responseMetadata, dest);
	}

	public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {

		@Override
		public Transaction createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Transaction(in);
		}

		@Override
		public Transaction[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Transaction[size];
		}
	};
	
}