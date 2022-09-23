package com.example.myapplication.ui.hospital;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class hospitalListResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<HospDataList> data;

    @Override
    public String toString() {
        return "ProfileResponse{" + "data=" + data+ "}";
    }

    public class HospDataList {
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

}
