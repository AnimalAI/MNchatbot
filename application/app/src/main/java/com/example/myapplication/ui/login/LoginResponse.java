//
// LoginResponse.java
// Created by 배주현
// 로그인 데이터 전달을 위한 클래스
//
package com.example.myapplication.ui.login;

import com.google.gson.annotations.SerializedName;

//서버에서 받은 결과
public class LoginResponse {
    @SerializedName("statusCode")
    public int statusCode;

    @SerializedName("responseMessage")
    private String message;

    public int getStatusCode() { return statusCode; }

    public String getMessage() {
        return message;
    }

}
