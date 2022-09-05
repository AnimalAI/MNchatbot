package com.example.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<Response_DataList> data;

    //회원 기본 정보
    @SerializedName("memberEmail")
    public String memberEmail;

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
