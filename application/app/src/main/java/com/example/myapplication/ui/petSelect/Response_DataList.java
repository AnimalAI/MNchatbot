package com.example.myapplication.ui.petSelect;

import com.google.gson.annotations.SerializedName;

public class Response_DataList {
    //반려동물
    @SerializedName("petSerial")
    private Long petSerial;
    @SerializedName("petSpecies")
    private String petSpecies;
    @SerializedName("petName")
    private String petName;

    public Long getpetSerial() {
        return petSerial;
    }
    public String getpetSpecies() {
        return petSpecies;
    }
    public String getPetName() {
        return petName;
    }
}
