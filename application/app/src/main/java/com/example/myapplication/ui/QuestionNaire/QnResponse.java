package com.example.myapplication.ui.QuestionNaire;
import com.google.gson.annotations.SerializedName;

public class QnResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public QnResponse.QnData data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public class QnData {
        @SerializedName("QnName")
        private String QnName;
        @SerializedName("QnDate")
        private String QnDate;
        @SerializedName("QnTime")
        private String QnTime;
        @SerializedName("QnReason")
        private String QnReason;
        @SerializedName("QnMedichine")
        private String QnMedichine;

        @SerializedName("dsEpidemiology")
        private String dsEpidemiology;
        @SerializedName("dsSymptom")
        private String dsSymptom;
        @SerializedName("dsDiagnosis")
        private String dsDiagnosis;
        @SerializedName("dsTreatment")
        private String dsTreatment;
        @SerializedName("dsPrevention")
        private String dsPrevention;
        @SerializedName("dsPrognosis")
        private String dsPrognosis;

        @SerializedName("QnEtc")
        private String QnEtc;

        public String getQnName() { return QnName; }
        public String getQnDate() { return QnDate; }
        public String getQnTime() { return QnTime; }
        public String getQnReason() { return QnReason;}
        public String getQnMedichine() { return QnMedichine;}

        public String getdsEpidemiology() { return dsEpidemiology; }
        public String getdsSymptom() { return dsSymptom; }
        public String getdsDiagnosis() { return dsDiagnosis; }
        public String getdsTreatment() { return dsTreatment;}
        public String getdsPrevention() { return dsPrevention;}
        public String getdsPrognosis() { return dsPrognosis;}

        public String getQnEtc() { return QnEtc;}

    }
}
