package com.app.foodapp.models;

import com.google.gson.annotations.SerializedName;

public class ResponseAddFoods {
    @SerializedName("data")
    private DataItem data;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    public DataItem getData() {
        return data;
    }

    public void setData(DataItem data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
