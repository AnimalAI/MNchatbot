package com.example.myapplication.ui.hospital;

import com.google.gson.annotations.SerializedName;

public class Response_hDataList {
    @SerializedName("totalHospName")
    private String totalHospName;
    @SerializedName("totalHospAddress")
    private String totalHospAddress;
    @SerializedName("totalHospEmail")
    private String totalHospEmail;
    @SerializedName("totalHospTel")
    private String totalHospTel;
    @SerializedName("totalHospField")
    private String totalHospField;
    @SerializedName("totalHospType")
    private String totalHospType;

    public String getHospType() {
        return totalHospType;
    }
    public String getHospName() {
        return totalHospName;
    }
    public String getHospAddress() {
        return totalHospAddress;
    }
    public String getHospEmail() {
        return totalHospEmail;
    }
    public String getHospTel() {
        return totalHospTel;
    }
    public String getHospField() {
        return totalHospField;
    }
}
