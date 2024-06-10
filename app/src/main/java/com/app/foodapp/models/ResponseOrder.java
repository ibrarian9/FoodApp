package com.app.foodapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseOrder {
    @SerializedName("data")
    private DataOrder data;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    public DataOrder getData() {
        return data;
    }

    public void setData(DataOrder data) {
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
