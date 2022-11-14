package com.mnchatbot.myapplication.ui.hospital;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalListResponse {
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
        private int hospSerial;

        @SerializedName("hospName")
        private String hospName;
        @SerializedName("hospAddress")
        private String hospAddress;
        @SerializedName("hospTel")
        private String hospTel;
        @SerializedName("hospEmail")
        private String hospEmail;
        @SerializedName("hospField")
        private String hospField;
        @SerializedName("hospType")
        private String hospType;

        public int getHospSerial() {
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
            return hospEmail;
        }
        public String getHospTel() {
            return hospTel;
        }
        public String getHospField() {
            return hospField;
        }
    }

}
