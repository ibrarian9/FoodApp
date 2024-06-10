package com.app.foodapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFoods{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private Boolean success;

	@SerializedName("message")
	private String message;

	public List<DataItem> getData() {
		return data;
	}

	public void setData(List<DataItem> data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success){
		this.success = success;
	}

	public Boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}