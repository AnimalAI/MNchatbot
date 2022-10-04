package com.example.myapplication.ui.QuestionNaire;

import com.example.myapplication.ui.Dictionary.dsListResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class qnListResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<qnListResponse.QnDataList> data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public class QnDataList {
        @SerializedName("medicalFormSerial")
        private int medicalFormSerial;

        @SerializedName("medicalFormName")
        private String QnName;
        @SerializedName("medicalFormDate")
        private String QnDate;

        public int getMedicalFormSerial() {return medicalFormSerial;}
        public String getQnName() { return QnName; }
        public String getQnDate() { return QnDate; }
    }
}
