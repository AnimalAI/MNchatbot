package com.example.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public String memberEmail;
}
