package com.example.myapplication.ui.diagnosis;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class diagListResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<DiagList> data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public class DiagList {
        @SerializedName("diagSerial")
        private int dsSerial;
        @SerializedName("diagDsName")
        private String dsName;
        @SerializedName("diagDate")
        private String dsDate;

        public int getDsSerial() {
            return dsSerial;
        }
        public String getDsName() {
            return dsName;
        }
        public String getDsDate() {
            return dsDate;
        }
    }

}
