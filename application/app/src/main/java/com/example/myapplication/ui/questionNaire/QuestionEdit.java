package com.example.myapplication.ui.questionNaire;

import com.google.gson.annotations.SerializedName;

public class QuestionEdit {
    @SerializedName("medicalFormSerial")
    public int medicalSerial;

    @SerializedName("medicalFormName")
    private String QnName;
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

    public QuestionEdit(int medicalSerial, String QnName, String QnReason, String QnDisease, boolean QnMedichine, String QnMedichine2, boolean QnSurgery, boolean QnExercise, String QnEtc) {
        this.medicalSerial = medicalSerial;
        this.QnName = QnName;
        this.QnReason = QnReason;
        this.QnDisease = QnDisease;
        this.QnMedichine = QnMedichine;
        this.QnMedichine2 = QnMedichine2;
        this.QnSurgery = QnSurgery;
        this.QnExercise = QnExercise;
        this.QnEtc = QnEtc;
    }
}
