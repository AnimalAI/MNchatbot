package com.mnchatbot.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

//비밀번호 변경 요청
public class PasswordChangeRequest {
    @SerializedName("memberNewPassword")
    public String inputPw;

    public PasswordChangeRequest(String inputPw) {
        this.inputPw = inputPw;
    }
}
