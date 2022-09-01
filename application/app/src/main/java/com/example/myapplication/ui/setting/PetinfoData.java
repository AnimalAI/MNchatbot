package com.example.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

public class PetinfoData {

    /*식별번호
    @SerializedName("petSerial")
    public int petSerial;

    //축종
    @SerializedName("petSpecies")
    public String petSpecies;*/

    @SerializedName("petName")
    public String petName;

    //품종
    @SerializedName("petBreed")
    public String petBreed;

    @SerializedName("petAge")
    public int petAge;

    @SerializedName("petGender")
    public String petGender;

    @SerializedName("petNeutering")
    public String petNeutering;

    public PetinfoData(String petName, int petAge, String petBreed, String petGender, String petNeutering) {
        //this.petSerial = petSerial;
        //this.petSpecies = petSpecies;
        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petNeutering = petNeutering;

    }
}
