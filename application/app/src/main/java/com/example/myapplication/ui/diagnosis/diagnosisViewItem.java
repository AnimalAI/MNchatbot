package com.example.myapplication.ui.diagnosis;

public class diagnosisViewItem {
    private int diagSerial;
    private String DiseaseName;
    private String DiseaseDate;

    public int getdiagSerial() {
        return diagSerial;
    }
    public void setdiagSerial(int dSerial) {
        this.diagSerial = dSerial;
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public void setDiseaseName(String Name) {
        this.DiseaseName = Name;
    }

    public String getDiseaseDate() {
        return DiseaseDate;
    }

    public void setDiseaseDate(String Time) {
        this.DiseaseDate = Time;
    }
}
