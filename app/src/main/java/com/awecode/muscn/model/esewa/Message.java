package com.awecode.muscn.model.esewa;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Message{

	@SerializedName("technicalSuccessMessage")
	private String technicalSuccessMessage;

	@SerializedName("successMessage")
	private String successMessage;

	public String getTechnicalSuccessMessage(){
		return technicalSuccessMessage;
	}

	public String getSuccessMessage(){
		return successMessage;
	}
}