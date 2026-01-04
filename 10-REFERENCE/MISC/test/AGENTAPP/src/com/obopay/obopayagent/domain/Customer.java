package com.obopay.obopayagent.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable{

	String promoterCode;
	String firstName;
	String lastName;
	String dateOfBirth;
	String product;
	int programType;
	String language;
	String mobileNumber;
	String gender;
	String idType;
	String idNo;
	int registrationFees;
	int amount;
	String atmCardRefNo;
	String genderType;
	int idTypeValue;
	int registrationType;
	
	public Customer(Parcel in) {
		String[] data = new String[12];
		in.readStringArray(data);
		this.promoterCode = data[0];
		this.firstName = data[1];
		this.lastName = data[2];
		this.dateOfBirth = data[3];
		this.product = data[4];
		this.language = data[5];
		this.mobileNumber = data[6];
		this.gender = data[7];
		this.idType = data[8];
		this.idNo = data[9];
		this.atmCardRefNo = data[10];
		this.genderType = data[11];
		
		int[] dataInts = new int[5];
		in.readIntArray(dataInts);
		this.programType = dataInts[0];
		this.amount = dataInts[1];
		this.idTypeValue = dataInts[2];
		this.registrationType = dataInts[3];
		this.registrationFees = dataInts[4];
	}
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPromoterCode() {
		return promoterCode;
	}
	public void setPromoterCode(String promoterCode) {
		this.promoterCode = promoterCode;
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
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getProgramType() {
		return programType;
	}
	public void setProgramType(int programType) {
		this.programType = programType;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public int getRegistrationFees() {
		return registrationFees;
	}
	public void setRegistrationFees(int registrationFees) {
		this.registrationFees = registrationFees;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getAtmCardRefNo() {
		return atmCardRefNo;
	}
	public void setAtmCardRefNo(String atmCardRefNo) {
		this.atmCardRefNo = atmCardRefNo;
	}
	public String getGenderType() {
		return genderType;
	}
	public void setGenderType(String genderType) {
		this.genderType = genderType;
	}
	public int getIdTypeValue() {
		return idTypeValue;
	}
	public void setIdTypeValue(int idTypeValue) {
		this.idTypeValue = idTypeValue;
	}
	public int getRegistrationType() {
		return registrationType;
	}
	public void setRegistrationType(int registrationType) {
		this.registrationType = registrationType;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {promoterCode, firstName,lastName,dateOfBirth,product,language,mobileNumber,gender,idType,idNo,atmCardRefNo,genderType});
		dest.writeIntArray(new int[]{programType,amount,idTypeValue,registrationType, registrationFees});
	}

	public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {

		@Override
		public Customer createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new Customer(in);
		}

		@Override
		public Customer[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Customer[size];
		}
	};
}
