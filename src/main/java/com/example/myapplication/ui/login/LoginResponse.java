package com.example.myapplication.ui.login;

import com.google.gson.annotations.SerializedName;

//서버에서 받은 결과
public class LoginResponse {
    @SerializedName("statusCode")
    public int statusCode;

    @SerializedName("responseMessage")
    private String message;
    //회원 기본 정보
    @SerializedName("memberEmail")
    public String inputId;

    public int getStatusCode() { return statusCode; }

    public String getMessage() {
        return message;
    }

    public String getuserID() {
        return inputId;
    }

}
