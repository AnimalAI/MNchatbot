package com.example.myapplication.ui.petSelect;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class petListResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<Response_DataList> data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" + "data=" + data+ "}";
    }
}
