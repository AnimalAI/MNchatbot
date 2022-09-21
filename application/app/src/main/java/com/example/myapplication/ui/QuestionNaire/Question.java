package com.example.myapplication.ui.QuestionNaire;

import com.google.gson.annotations.SerializedName;

public class Question {
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

    public Question(String QnName, String QnDate, String QnTime, String QnReason, String QnMedichine, String QnEtc) {
        this.QnName = QnName;
        this.QnDate = QnDate;
        this.QnTime = QnTime;
        this.QnReason = QnReason;
        this.QnMedichine = QnMedichine;
        this.QnEtc = QnEtc;
    }
}
