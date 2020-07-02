package com.farbig.cart.persistence;

public class DBConfig_NotUsed
{
	private String handlerName;
	private String filePath;
	private String transactionType;
	
	private String coreConfigFilePath;
	
	public String getCoreConfigFilePath() {
		return coreConfigFilePath;
	}
	public void setCoreConfigFilePath(String coreConfigFilePath) {
		this.coreConfigFilePath = coreConfigFilePath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
}
