package com.obopay.obopayagent.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerVerification implements Parcelable {

	String firstName;
	String lastName;
	String dateOfBirth;
	String programCode;
	String status;
	String mobileNumber;	
	String registrationDate;
	String idType1;
	String idValue1;
	String idType2;
	String idValue2;
	
	public CustomerVerification(Parcel in) {
		String dataStrings[] = new String[11];
		in.readStringArray(dataStrings);
		this.programCode = dataStrings[0];
		this.firstName = dataStrings[1];
		this.lastName = dataStrings[2];
		this.dateOfBirth = dataStrings[3];
		this.status = dataStrings[4];
		this.registrationDate = dataStrings[5];
		this.mobileNumber = dataStrings[6];
		this.idType1 = dataStrings[7];
		this.idValue1 = dataStrings[8];
		this.idType2 = dataStrings[9];
		this.idValue2 = dataStrings[10];
	}
	public CustomerVerification() {
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
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
		dest.writeStringArray(new String[] {programCode, firstName,lastName,dateOfBirth,status,
				registrationDate,mobileNumber, idType1, idValue1, idType2, idValue2});
	}

	public static final Parcelable.Creator<CustomerVerification> CREATOR = new Parcelable.Creator<CustomerVerification>() {

		@Override
		public CustomerVerification createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new CustomerVerification(in);
		}

		@Override
		public CustomerVerification[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CustomerVerification[size];
		}
	};
}
