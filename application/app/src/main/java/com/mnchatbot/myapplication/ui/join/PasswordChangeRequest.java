package com.mnchatbot.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

//비밀번호 변경 요청
public class PasswordChangeRequest {
    @SerializedName("memberEmail")
    public String inputID;

    @SerializedName("memberNewPassword")
    public String inputPw;

    public PasswordChangeRequest(String inputID, String inputPw) {
        this.inputID = inputID; this.inputPw = inputPw;
    }
}
