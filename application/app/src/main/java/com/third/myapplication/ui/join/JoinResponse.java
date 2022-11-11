package com.example.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

public class JoinResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    private int data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getData() {
        return data;
    }
}
