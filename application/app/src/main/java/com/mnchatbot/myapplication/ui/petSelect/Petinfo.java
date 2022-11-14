package com.mnchatbot.myapplication.ui.petSelect;

import com.google.gson.annotations.SerializedName;

public class Petinfo {
    //축종
    @SerializedName("petSpecies")
    public String petSpecies;

    @SerializedName("petName")
    public String petName;

    //품종
    @SerializedName("petBreed")
    public String petBreed;

    @SerializedName("petAge")
    public int petAge;

    @SerializedName("petGender")
    public String petGender;

    @SerializedName("petNeutralization")
    public String petNeutering;

    public Petinfo(String petSpecies, String petName, int petAge, String petBreed, String petGender, String petNeutering) {
        this.petSpecies = petSpecies;
        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petNeutering = petNeutering;

    }
}
