package com.example.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

public class EmailValidationData {
    @SerializedName("receiveMail")
    private String userEmail;

    public EmailValidationData(String userEmail) {
        this.userEmail = userEmail;
    }
}
