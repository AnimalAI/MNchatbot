package com.example.myapplication.ui.questionNaire;

public class QuestionViewItem {
    private int MedicalSerial;
    private String QuestionName;
    private String QuestionDate;

    public int getMedicalSerial() {
        return MedicalSerial;
    }

    public void setMedicalSerial(int medicalSerial) {
        this.MedicalSerial = medicalSerial;
    }

    public String getQuestionName() {
        return QuestionName;
    }

    public void setQuestionName(String Name) {
        this.QuestionName = Name;
    }

    public String getQuestionDate() {
        return QuestionDate;
    }

    public void setQuestionDate(String Date) {
        this.QuestionDate = Date;
    }
}
