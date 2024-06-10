package com.app.foodapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseListOrder {
    @SerializedName("data")
    private List<DataOrder> data;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    public List<DataOrder> getData() {
        return data;
    }

    public void setData(List<DataOrder> data) {
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
