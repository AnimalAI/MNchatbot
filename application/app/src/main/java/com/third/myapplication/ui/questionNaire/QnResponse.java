package com.example.myapplication.ui.questionNaire;
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
        @SerializedName("medicalFormName")
        private String QnName;
        @SerializedName("medicalFormDate")
        private String QnDate;
        @SerializedName("medicalFormTime")
        private String QnTime;

        @SerializedName("medicalFormQ1")
        private String QnReason;
        @SerializedName("medicalFormQ2")
        private String QnDisease;
        @SerializedName("medicalFormQ3")
        private boolean QnMedichine;
        @SerializedName("medicalFormQ4")
        private String QnMedichine2;

        @SerializedName("medicalFormQ5")
        private boolean QnSurgery;
        @SerializedName("medicalFormQ6")
        private boolean QnExercise;
        @SerializedName("medicalFormQ7")
        private String QnEtc;

        public String getQnName() { return QnName; }
        public String getQnDate() { return QnDate; }
        public String getQnTime() { return QnTime; }

        public String getQnReason() { return QnReason;}
        public String getQnDisease() { return QnDisease;}
        public boolean getQnMedichine() { return QnMedichine;}
        public String getQnMedichine2() { return QnMedichine2;}

        public boolean getQnSurgery() { return QnSurgery;}
        public boolean getQnExercise() { return QnExercise;}
        public String getQnEtc() { return QnEtc;}
    }
}
