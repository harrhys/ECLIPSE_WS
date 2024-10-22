package com.peppercoin.mpp.gateway.util;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.management.modelmbean.XMLParseException;

import com.peppercoin.common.Config;
import com.peppercoin.common.Util;
import com.peppercoin.common.exception.PpcnError;
import com.peppercoin.common.persistence.PersistentObject;
import com.peppercoin.common.purge.PurgeTimerTask;
import com.peppercoin.common.purge.SimpleTransactionalTablePurgeable;
import com.peppercoin.common.util.CreditCard;
import com.peppercoin.common.util.EncodedString;
import com.peppercoin.common.util.Money;
import com.peppercoin.common.util.PpcnCurrency;
import com.peppercoin.common.util.PpcnTime;
import com.peppercoin.common.xml.XMLEngine;
import com.peppercoin.common.xml.XMLMap;
import com.peppercoin.mpp.encryption.EncryptionProcessorSLSBDelegate;
import com.peppercoin.mpp.transaction.IncomingRequest;
import com.peppercoin.mpp.transaction.ReferenceIdEBDelegate;
import com.peppercoin.mpp.transaction.TransactionStatus;

public class TransactionRequest extends PersistentObject {
	private int transactionId;
	private String transactionType;
	private String posMode;
	private String transactionStatus;
	private String merchantId;
	private String refId;
	private String requestId;
	private Money amount;
	private Money transactionAmount;
	private PpcnTime createTime;
	private PpcnTime lastCapturedDate;
	private String expDate;
	private String postalCode;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String nameOnCard;
	private String ccType;
	private CreditCard card;
	private String ccSuffix;
	private String ccNumberSource;
	private String encryptedCcNumber;
	private String encryptionKeyId;
	private String comment1;
	private String comment2;
	private String countryCode;
	private int responseCode;
	private String responseMessage;
	private PpcnTime expirationTime;
	private String avsResponse;
	private String cvvResponse;
	private EncodedString validationKey;
	private String email;
	private String operator;
	private String trackingId;
	private String loginId;
	private BigDecimal transactionAmountDecimal;
	private BigDecimal amountDecimal;
	private String currencyCode;
	private String gateResponseXML;
	private String internalResponseXML;
	private String gateRefTransId;
	private String gatewayData;
	private String source;
	private boolean optOut;

	public TransactionRequest() {
	}

	public TransactionRequest(IncomingRequest transaction) {
		this.setTransactionType(transaction.getTransactionType());
		this.setPosMode(transaction.getPosMode());
		this.setTransactionStatus(transaction.getTransactionStatus().getCode());
		this.setMerchantId(transaction.getMerchantId());
		this.setRequestId(transaction.getRequestId());
		this.setCcType(transaction.getCardType());
		this.setAmount(transaction.getAmount());
		this.setTransactionAmount(transaction.getTransactionAmount());
		this.setComment1(transaction.getReqComment1());
		this.setComment2(transaction.getReqComment2());
		this.optOut = transaction.getOptout().equals("YES");
		if ("CRED".equals(transaction.getTransactionType())) {
			this.setCreditCard(transaction.getCardNumber());
			this.setRefId(transaction.getReferenceId());
			this.setLoginId(this.makeReferenceId());
			this.setCcNumberSource(transaction.getCcNumberSource());
			this.setCcSuffix(CreditCard.getSuffix(transaction.getCardNumberInString()));
		} else if ("AUTH".equals(transaction.getTransactionType())) {
			this.setExpDate(transaction.getCardExpirationDate());
			this.setStreet1(transaction.getAddrStreet1());
			this.setStreet2(transaction.getAddrStreet2());
			this.setPostalCode(transaction.getAddrPostalCode());
			this.setCity(transaction.getAddrCity());
			this.setState(transaction.getAddrState());
			this.setNameOnCard(transaction.getNameOnCard());
			this.setCreditCard(transaction.getCardNumber());
			this.setCcNumberSource(transaction.getCcNumberSource());
			this.setCcSuffix(CreditCard.getSuffix(transaction.getCardNumberInString()));
			if (transaction.getAddrCountry() != null) {
				this.setCountryCode(transaction.getAddrCountry().getCode());
			}

			this.setExpirationTime(transaction.getResponseExpirationTime());
			this.setRefId(this.makeReferenceId());
			this.setLoginId(this.getRefId());
			this.setValidationKey(new EncodedString(transaction.getCardNumberInString() + transaction.getCVVCode()));
		} else {
			this.setRefId(transaction.getReferenceId());
			this.setLoginId(transaction.getLoginId());
		}

		this.setCreateTime(new PpcnTime());
		this.setLastCapturedDate(transaction.getLastCapturedDate());
		this.setEmail(transaction.getEmail());
		this.setEncryptedCcNumber(transaction.getEncryptedCardNumber());
		this.setEncryptionKeyId(transaction.getEncryptionKeyId());
		this.setOperator(transaction.getOperator());

		try {
			this.setSource((new EncryptionProcessorSLSBDelegate())
					.encrypt(transaction.getSaveableMap().toXML("REQUEST", true, false).toString()));
		} catch (CreateException var3) {
			throw new PpcnError("Create", var3);
		}

		this.save();
	}

	public String getOutgoingRefId() {
		return "AUTH".equals(this.getTransactionType()) ? this.getRefId() : "" + this.getTransactionId();
	}

	public void recordResponse(ProcessorResponse response) {
		if (response != null) {
			this.setResponseCode(response.getResponseCode().getRespCode());
			this.setResponseMessage(response.getResponseCode().getRespMessage());
			if (this.getTransactionType().equals("AUTH")) {
				this.setAvsResponse(response.getAvsResponse().toString());
				this.setCvvResponse(response.getCvvResponse().toString());
				Object v = response.getResponseXML();
				if (v != null) {
					this.setGateResponseXML(v.toString());
				}

				v = response.getInternalXML();
				if (v != null) {
					this.setInternalResponseXML(v.toString());
				}

				this.setGateRefTransId(response.getReferenceId());
			}

			if (!response.wasSuccessful()) {
				this.setTransactionStatus(TransactionStatus.FAILED.getCode());
			}

		}
	}

	public int getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(int value) {
		this.transactionId = value;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String value) {
		this.transactionType = value;
	}

	public String getPosMode() {
		return this.posMode;
	}

	public void setPosMode(String value) {
		this.posMode = value;
	}

	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String value) {
		this.transactionStatus = value;
	}

	public void setTransactionStatus(TransactionStatus value) {
		this.setTransactionStatus(value.getCode());
	}

	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String value) {
		this.merchantId = value;
	}

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String value) {
		this.refId = value;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCcSuffix() {
		return this.ccSuffix;
	}

	private void setCcSuffix(String s) {
		this.ccSuffix = s;
	}

	public String getCcNumberSource() {
		return this.ccNumberSource;
	}

	private void setCcNumberSource(String s) {
		this.ccNumberSource = s;
	}

	public String getEncryptedCcNumber() {
		return this.encryptedCcNumber;
	}

	void setEncryptedCcNumber(String s) {
		this.encryptedCcNumber = s;
	}

	public String getEncryptionKeyId() {
		return this.encryptionKeyId;
	}

	void setEncryptionKeyId(String s) {
		this.encryptionKeyId = s;
	}

	public String getCcType() {
		return this.ccType;
	}

	private void setCcType(String s) {
		this.ccType = s;
	}

	public CreditCard getCreditCard() {
		return this.card;
	}

	public void setCreditCard(CreditCard value) {
		this.card = value;
	}

	public BigDecimal getAmountDecimal() {
		return this.amountDecimal;
	}

	public Money getAmount() {
		if (this.amount == null && this.amountDecimal != null && this.currencyCode != null) {
			this.amount = new Money(PpcnCurrency.getInstance(this.currencyCode), this.amountDecimal);
		}

		return this.amount;
	}

	public void setAmountDecimal(BigDecimal value) {
		this.amount = null;
		this.amountDecimal = value;
		this.getAmount();
	}

	public void setAmount(Money value) {
		this.setCurrencyCode(value.getCurrency().getCurrencyCode());
		this.setAmountDecimal(value.getAmount());
		this.amount = value;
	}

	public BigDecimal getTransactionAmountDecimal() {
		return this.transactionAmountDecimal;
	}

	public Money getTransactionAmount() {
		if (this.transactionAmount == null && this.transactionAmountDecimal != null && this.currencyCode != null) {
			this.transactionAmount = new Money(PpcnCurrency.getInstance(this.currencyCode),
					this.transactionAmountDecimal);
		}

		return this.transactionAmount;
	}

	public void setTransactionAmountDecimal(BigDecimal value) {
		this.transactionAmount = null;
		this.transactionAmountDecimal = value;
		this.getTransactionAmount();
	}

	public void setTransactionAmount(Money value) {
		this.setTransactionAmountDecimal(value.getAmount());
		this.setCurrencyCode(value.getCurrency().getCurrencyCode());
		this.transactionAmount = value;
	}

	public PpcnTime getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(PpcnTime value) {
		this.createTime = value;
	}

	public PpcnTime getLastCapturedDate() {
		return this.lastCapturedDate;
	}

	public void setLastCapturedDate(PpcnTime value) {
		this.lastCapturedDate = value;
	}

	public String getExpDate() {
		return this.expDate;
	}

	public void setExpDate(String value) {
		this.expDate = value;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String value) {
		this.postalCode = value;
	}

	public String getStreet1() {
		return this.street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String value) {
		this.city = value;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNameOnCard() {
		return this.nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getComment1() {
		return this.comment1;
	}

	public void setComment1(String comment1) {
		this.comment1 = comment1;
	}

	public String getComment2() {
		return this.comment2;
	}

	public void setComment2(String comment2) {
		this.comment2 = comment2;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String value) {
		this.countryCode = value;
	}

	public int getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}

	public void setResponseMessage(String value) {
		this.responseMessage = value;
	}

	public PpcnTime getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(PpcnTime value) {
		this.expirationTime = value;
	}

	public String getAvsResponse() {
		return this.avsResponse;
	}

	public void setAvsResponse(String value) {
		this.avsResponse = value;
	}

	public String getCvvResponse() {
		return this.cvvResponse;
	}

	public void setCvvResponse(String value) {
		this.cvvResponse = value;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public EncodedString getValidationKey() {
		return this.validationKey;
	}

	public void setValidationKey(EncodedString value) {
		this.validationKey = value;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String value) {
		this.operator = value;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String value) {
		this.source = value;
	}

	public XMLMap getXmlMap() {
		try {
			String s = (new EncryptionProcessorSLSBDelegate()).decrypt(this.getSource());
			return (new XMLEngine()).parse(s);
		} catch (CreateException var2) {
			throw new PpcnError("Create", var2);
		} catch (XMLParseException var3) {
			throw new PpcnError("Can't parse source.", var3);
		}
	}

	public String getCurrencyCode() {
		if (this.currencyCode != null) {
			return this.currencyCode;
		} else {
			return this.amount == null ? null : this.amount.getCurrency().toString();
		}
	}

	public void setCurrencyCode(String value) {
		this.currencyCode = value;
		this.getAmount();
		this.getTransactionAmount();
	}

	public String getTrackingId() {
		return this.trackingId;
	}

	private void setTrackingId(String s) {
		this.trackingId = s;
	}

	public String getGateResponseXML() {
		return this.gateResponseXML;
	}

	public void setGateResponseXML(String gateResponseXML) {
		this.gateResponseXML = gateResponseXML;
	}

	public String getInternalResponseXML() {
		return this.internalResponseXML;
	}

	public void setInternalResponseXML(String internalResponseXML) {
		this.internalResponseXML = internalResponseXML;
	}

	public String getGatewayData() {
		return this.gatewayData;
	}

	public void setGatewayData(String gatewayData) {
		this.gatewayData = gatewayData;
	}

	public String getGateRefTransId() {
		return this.gateRefTransId;
	}

	public void setGateRefTransId(String refTransId) {
		this.gateRefTransId = refTransId;
	}

	public void updateTrackingInfo(String trackId) {
		this.setTrackingId(trackId);
		this.setTransactionStatus(TransactionStatus.RECONCILED.getCode());
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String s) {
		this.loginId = s;
	}

	private synchronized String makeReferenceId() {
		String cardType = this.getCcType();

		while (true) {
			String refId = Util.genRandomKey(CreditCard.getSoftKeyLength(cardType));

			try {
				ReferenceIdEBDelegate.findByPrimaryKey(refId);
			} catch (FinderException var5) {
				try {
					ReferenceIdEBDelegate.create(refId);
					return refId;
				} catch (CreateException var4) {
					;
				}
			}
		}
	}

	public boolean getOptOut() {
		return this.optOut;
	}

	public void setOptOut(boolean value) {
		this.optOut = value;
	}

	public boolean doOneDollarAuth() {
		if (this.optOut) {
			return false;
		} else {
			String ccType = this.getCcType();
			String p = null;
			if (ccType.equals("American Express")) {
				p = "amex";
			} else if (ccType.equals("Visa")) {
				p = "visa";
			} else if (ccType.equals("MasterCard")) {
				p = "mc";
			} else {
				if (!ccType.equals("Discover")) {
					return true;
				}

				p = "discover";
			}

			return Config.getBoolean(p + "-post-pay", true);
		}
	}

	static {
		PurgeTimerTask.register(new SimpleTransactionalTablePurgeable("mpp", "transactionrequest", "create_ts"));
	}
}