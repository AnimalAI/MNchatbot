//
// EmailCodeData.java
// Created by 배주현
// 이메일 코드 확인 데이터 전달
//
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
