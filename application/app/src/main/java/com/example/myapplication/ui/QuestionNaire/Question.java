package com.example.myapplication.ui.QuestionNaire;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("petSerial")
    public int petSerial;

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

    public Question(int petSerial, String QnName, String QnDate, String QnTime, String QnReason, String QnDisease, boolean QnMedichine, String QnMedichine2, boolean QnSurgery, boolean QnExercise, String QnEtc) {
        this.petSerial = petSerial;
        this.QnName = QnName;
        this.QnDate = QnDate;
        this.QnTime = QnTime;
        this.QnReason = QnReason;
        this.QnDisease = QnDisease;
        this.QnMedichine = QnMedichine;
        this.QnMedichine2 = QnMedichine2;
        this.QnSurgery = QnSurgery;
        this.QnExercise = QnExercise;
        this.QnEtc = QnEtc;
    }
}
