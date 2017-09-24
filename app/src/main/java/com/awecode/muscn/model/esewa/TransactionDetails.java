package com.awecode.muscn.model.esewa;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TransactionDetails{

	@SerializedName("date")
	private String date;

	@SerializedName("referenceId")
	private String referenceId;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}

	public String getReferenceId(){
		return referenceId;
	}

	public String getStatus(){
		return status;
	}
}