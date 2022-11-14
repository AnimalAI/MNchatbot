package com.mnchatbot.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

public class PetinfoData {

    //식별번호
    @SerializedName("petSerial")
    public int petSerial;

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

    public PetinfoData(int petSerial, String petSpecies, String petBreed, String petName, int petAge,  String petGender, String petNeutering) {
        this.petSerial = petSerial;
        this.petSpecies = petSpecies;
        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petNeutering = petNeutering;

    }
}
