package com.example.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

public class EmailCodeData {
    @SerializedName("sendCode")
    private int sendCode;

    @SerializedName("receivedCode")
    private int receivedCode;

    public EmailCodeData(int sendCode, int receivedCode) {
        this.sendCode = sendCode;
        this.receivedCode = receivedCode;
    }
}
