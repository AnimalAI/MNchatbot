package com.example.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

public class PetinfoData {

    @SerializedName("petName")
    public String petName;

    //품종
    @SerializedName("petBreed")
    public String petBreed;

    @SerializedName("petAge")
    public String petAge;

    @SerializedName("petGender")
    public int petGender;

    @SerializedName("pet중성화")
    public int pet중성화;

    public PetinfoData(String petName, String petAge, String petBreed, int petGender, int pet중성화) {
        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.pet중성화 = pet중성화;

    }
}
