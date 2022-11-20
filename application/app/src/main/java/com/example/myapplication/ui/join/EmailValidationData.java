//
// EmailValidationData.java
// Created by 배주현
// 이메일 확인 데이터 전달
//
package com.example.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

public class EmailValidationData {
    @SerializedName("receiveMail")
    private String userEmail;

    public EmailValidationData(String userEmail) {
        this.userEmail = userEmail;
    }
}
