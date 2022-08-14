package com.example.myapplication.ui.login;

import com.google.gson.annotations.SerializedName;

//로그인 이메일, pw 정보 요청
public class LoginRequest {
    @SerializedName("memberEmail")
    public String inputId;

    @SerializedName("memberPassword")
    public String inputPw;

    public LoginRequest(String inputId, String inputPw) {
        this.inputId = inputId;
        this.inputPw = inputPw;
    }
}
