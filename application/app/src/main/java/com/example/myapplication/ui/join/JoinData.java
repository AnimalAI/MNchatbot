package com.example.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("memberEmail")
    private String userEmail;

    @SerializedName("memberPassword")
    private String userPwd;

    @SerializedName("sendCode")
    private int sendCode;

    @SerializedName("receivedCode")
    private int receivedCode;

    public JoinData(String userEmail, String userPwd, int sendCode, int receivedCode) {
        this.userEmail = userEmail;
        this.sendCode = sendCode;
        this.userPwd = userPwd;
        this.receivedCode = receivedCode;
    }
}
