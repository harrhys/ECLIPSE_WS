package com.web.services.transferobjects;


public class BodyTO {

	private String programName;
	private String accountStatus;
	private String devicenumber;
	private String programcode;
	private String otp;
	private String amount;
	private String transID;
	private String storeID;
	private String etid;
	private String batchID;
	private String[] requestList;
	private String[] responseList;
	private String dob;
	private String crn;
	
	private String registrationDate;
	private String mmid;
	private String transNumber;
	
	private String firstName;
	private String lastName;
	private String language;
	
	private String promoterCode;
		
	private String optName1;	
	private String optVal1;
	private String optData1;
	private String optName2;
	private String optVal2;	
	private String optData2;
	private String optName3;
	private String optVal3;
	private String optData3;
	private String optName4;
	private String optVal4;
	private String optData4;
	private String optName5;
	private String optVal5;
	private String optData5;	

	private String resName1;
	private String resValue1;
	private String resName2;
	private String resValue2;
	private String resName3;
	private String resValue3;
	private String resName4;
	private String resValue4;
	private String resName5;
	private String resValue5;
	
	private String kycType1;
	private String kycDocNumber1;
	private String kycExpiry1;
	
	private String kycType2;
	private String kycDocNumber2;
	private String kycExpiry2;
	
	private String issuer;
	// added for queryStatus
	private String txnType;
	private String responseCode;
	private String responseMessage;
	private String responseType;
	
	//added for change password
	private String newPassword;
	private String newPasswordConfirm;
	
	//added for viralpick
	private String idType1;
	private String idValue1;
	private String idType2;
	private String idValue2;

	//for balance
	private String ubiAgentBalance;
	private String rahaAgentBalance;
	private String ubiMerchantBalance;
	private String rahaMerchantBalance;
	
	private String ubiAgentStatus;
	private String rahaAgentStatus;
	private String ubiMerchantStatus;
	private String rahaMerchantStatus;
	
	private String status;
	private String fee;
	
	private String grossAmount;
	private String netAmount;
	private String receiverDevicenumber;
	private String companyName;
	private String tradingLicenceNo;
	
	private String customerType;
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTradingLicenceNo() {
		return tradingLicenceNo;
	}

	public void setTradingLicenceNo(String tradingLicenceNo) {
		this.tradingLicenceNo = tradingLicenceNo;
	}

	public String getReceiverDevicenumber() {
		return receiverDevicenumber;
	}

	public void setReceiverDevicenumber(String receiverDevicenumber) {
		this.receiverDevicenumber = receiverDevicenumber;
	}

	public String getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(String grossAmount) {
		this.grossAmount = grossAmount;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUbiAgentStatus() {
		return ubiAgentStatus;
	}

	public void setUbiAgentStatus(String ubiAgentStatus) {
		this.ubiAgentStatus = ubiAgentStatus;
	}

	public String getRahaAgentStatus() {
		return rahaAgentStatus;
	}

	public void setRahaAgentStatus(String rahaAgentStatus) {
		this.rahaAgentStatus = rahaAgentStatus;
	}
	
	public String getUbiMerchantBalance() {
		return ubiMerchantBalance;
	}

	public void setUbiMerchantBalance(String ubiMerchantBalance) {
		this.ubiMerchantBalance = ubiMerchantBalance;
	}

	public String getRahaMerchantBalance() {
		return rahaMerchantBalance;
	}

	public void setRahaMerchantBalance(String rahaMerchantBalance) {
		this.rahaMerchantBalance = rahaMerchantBalance;
	}

	public String getUbiMerchantStatus() {
		return ubiMerchantStatus;
	}

	public void setUbiMerchantStatus(String ubiMerchantStatus) {
		this.ubiMerchantStatus = ubiMerchantStatus;
	}

	public String getRahaMerchantStatus() {
		return rahaMerchantStatus;
	}

	public void setRahaMerchantStatus(String rahaMerchantStatus) {
		this.rahaMerchantStatus = rahaMerchantStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getOptName1() {
		return optName1;
	}

	public String getOptVal1() {
		return optVal1;
	}
	public void setOptVal1(String optVal1) {
		this.optVal1 = optVal1;
	}
	public String getOptName2() {
		return optName2;
	}
	public void setOptName2(String optName2) {
		this.optName2 = optName2;
	}
	public String getOptVal2() {
		return optVal2;
	}
	public void setOptVal2(String optVal2) {
		this.optVal2 = optVal2;
	}
	public String getOptName3() {
		return optName3;
	}
	public void setOptName3(String optName3) {
		this.optName3 = optName3;
	}
	public String getOptVal3() {
		return optVal3;
	}
	public void setOptVal3(String optVal3) {
		this.optVal3 = optVal3;
	}
	public String getOptName4() {
		return optName4;
	}
	public void setOptName4(String optName4) {
		this.optName4 = optName4;
	}
	public String getOptVal4() {
		return optVal4;
	}
	public void setOptVal4(String optVal4) {
		this.optVal4 = optVal4;
	}
	public String getOptName5() {
		return optName5;
	}
	public void setOptName5(String optName5) {
		this.optName5 = optName5;
	}
	public String getOptVal5() {
		return optVal5;
	}
	public void setOptVal5(String optVal5) {
		this.optVal5 = optVal5;
	}
	public String getDevicenumber() {
		return devicenumber;
	}
	public void setDevicenumber(String devicenumber) {
		this.devicenumber = devicenumber;
	}
	public String getProgramcode() {
		return programcode;
	}
	public void setProgramcode(String programcode) {
		this.programcode = programcode;
	}




	public String getBatchID() {
		return batchID;
	}




	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}



	public String[] getRequestList() {
		return requestList;
	}

	public void setRequestList(String[] requestList) {
		this.requestList = requestList;
	}

	public String getDob() {
		return dob;
	}




	public void setDob(String dob) {
		this.dob = dob;
	}




	public String getCrn() {
		return crn;
	}




	public void setCrn(String crn) {
		this.crn = crn;
	}





	public String getStoreID() {
		return storeID;
	}



	public void setStoreID(String storeID) {
		this.storeID = storeID;
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




	public String getLanguage() {
		return language;
	}




	public void setLanguage(String language) {
		this.language = language;
	}




	public String getOptData1() {
		return optData1;
	}




	public void setOptData1(String optData1) {
		this.optData1 = optData1;
	}




	public String getOptData2() {
		return optData2;
	}




	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public void setOptData2(String optData2) {
		this.optData2 = optData2;
	}




	public String getOptData3() {
		return optData3;
	}




	public void setOptData3(String optData3) {
		this.optData3 = optData3;
	}




	public String getOptData4() {
		return optData4;
	}




	public void setOptData4(String optData4) {
		this.optData4 = optData4;
	}




	public String getOptData5() {
		return optData5;
	}




	public void setOptData5(String optData5) {
		this.optData5 = optData5;
	}






	public void setOptName1(String optName1) {
		this.optName1 = optName1;
	}




	public String getResName1() {
		return resName1;
	}



	public void setResName1(String resName1) {
		this.resName1 = resName1;
	}



	public String getResValue1() {
		return resValue1;
	}



	public void setResValue1(String resValue1) {
		this.resValue1 = resValue1;
	}



	public String getResName2() {
		return resName2;
	}



	public void setResName2(String resName2) {
		this.resName2 = resName2;
	}



	public String getResValue2() {
		return resValue2;
	}



	public void setResValue2(String resValue2) {
		this.resValue2 = resValue2;
	}



	public String getResName3() {
		return resName3;
	}



	public void setResName3(String resName3) {
		this.resName3 = resName3;
	}



	public String getResValue3() {
		return resValue3;
	}



	public void setResValue3(String resValue3) {
		this.resValue3 = resValue3;
	}



	public String getResName4() {
		return resName4;
	}



	public void setResName4(String resName4) {
		this.resName4 = resName4;
	}



	public String getResValue4() {
		return resValue4;
	}



	public void setResValue4(String resValue4) {
		this.resValue4 = resValue4;
	}



	public String getResName5() {
		return resName5;
	}



	public void setResName5(String resName5) {
		this.resName5 = resName5;
	}



	public String getResValue5() {
		return resValue5;
	}



	public void setResValue5(String resValue5) {
		this.resValue5 = resValue5;
	}

	public String getMmid() {
		return mmid;
	}

	public void setMmid(String mmid) {
		this.mmid = mmid;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public String getEtid() {
		return etid;
	}

	public void setEtid(String etid) {
		this.etid = etid;
	}

	public String getTransNumber() {
		return transNumber;
	}

	public void setTransNumber(String transNumber) {
		this.transNumber = transNumber;
	}


	public String[] getResponseList() {
		return responseList;
	}

	public void setResponseList(String[] responseList) {
		this.responseList = responseList;
	}



	public String getKycType1() {
		return kycType1;
	}

	public void setKycType1(String kycType1) {
		this.kycType1 = kycType1;
	}

	public String getKycDocNumber1() {
		return kycDocNumber1;
	}

	public void setKycDocNumber1(String kycDocNumber1) {
		this.kycDocNumber1 = kycDocNumber1;
	}

	public String getKycExpiry1() {
		return kycExpiry1;
	}

	public void setKycExpiry1(String kycExpiry1) {
		this.kycExpiry1 = kycExpiry1;
	}

	public String getKycType2() {
		return kycType2;
	}

	public void setKycType2(String kycType2) {
		this.kycType2 = kycType2;
	}

	public String getKycDocNumber2() {
		return kycDocNumber2;
	}

	public void setKycDocNumber2(String kycDocNumber2) {
		this.kycDocNumber2 = kycDocNumber2;
	}

	public String getKycExpiry2() {
		return kycExpiry2;
	}

	public void setKycExpiry2(String kycExpiry2) {
		this.kycExpiry2 = kycExpiry2;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
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

	public String getUbiAgentBalance() {
		return ubiAgentBalance;
	}

	public void setUbiAgentBalance(String ubiAgentBalance) {
		this.ubiAgentBalance = ubiAgentBalance;
	}
	
	public String getRahaAgentBalance() {
		return rahaAgentBalance;
	}

	public void setRahaAgentBalance(String rahaAgentBalance) {
		this.rahaAgentBalance = rahaAgentBalance;
}

	public String getPromoterCode() {
		return promoterCode;
	}

	public void setPromoterCode(String promoterCode) {
		this.promoterCode = promoterCode;
	}

	
	

}


