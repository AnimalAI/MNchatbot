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
        @SerializedName("hospSerial")
        private String hospSerial;

        @SerializedName("hospName")
        private String hospName;
        @SerializedName("hospAddress")
        private String hospAddress;

        @SerializedName("totalHospEmail")
        private String totalHospEmail;

        @SerializedName("hospTel")
        private String hospTel;

        @SerializedName("totalHospField")
        private String totalHospField;
        @SerializedName("hospType")
        private String hospType;

        public String getHospSerial() {
            return hospSerial;
        }
        public String getHospType() {
            return hospType;
        }
        public String getHospName() {
            return hospName;
        }
        public String getHospAddress() {
            return hospAddress;
        }

        public String getHospEmail() {
            return totalHospEmail;
        }
        public String getHospTel() {
            return hospTel;
        }
        public String getHospField() {
            return totalHospField;
        }
    }

}
