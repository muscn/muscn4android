package com.awecode.muscn.model.esewa;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class EsewaResponse{

	@SerializedName("totalAmount")
	private String totalAmount;

	@SerializedName("transactionDetails")
	private TransactionDetails transactionDetails;

	@SerializedName("environment")
	private String environment;

	@SerializedName("code")
	private String code;

	@SerializedName("productId")
	private String productId;

	@SerializedName("message")
	private Message message;

	@SerializedName("productName")
	private String productName;

	@SerializedName("merchantName")
	private String merchantName;

	public String getTotalAmount(){
		return totalAmount;
	}

	public TransactionDetails getTransactionDetails(){
		return transactionDetails;
	}

	public String getEnvironment(){
		return environment;
	}

	public String getCode(){
		return code;
	}

	public String getProductId(){
		return productId;
	}

	public Message getMessage(){
		return message;
	}

	public String getProductName(){
		return productName;
	}

	public String getMerchantName(){
		return merchantName;
	}
}