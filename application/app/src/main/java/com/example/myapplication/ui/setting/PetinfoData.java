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
    public String petGender;

    @SerializedName("petNeutering")
    public String petNeutering;

    public PetinfoData(String petName, String petAge, String petBreed, String petGender, String petNeutering) {
        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petNeutering = petNeutering;

    }
}
