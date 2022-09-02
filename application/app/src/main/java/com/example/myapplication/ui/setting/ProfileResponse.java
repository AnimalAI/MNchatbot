package com.example.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    private String data;

    //회원 기본 정보
    @SerializedName("memberEmail")
    public String memberEmail;

    //반려동물 기본 정보
    @SerializedName("petName")
    private String petName;
    @SerializedName("petBreed")
    private String petBreed;
    @SerializedName("petAge")
    private int petAge;
    @SerializedName("petGender")
    public String petGender;
    @SerializedName("petNeutering")
    public String petNeutering;

    public String getuserID() {
        return memberEmail;
    }

    public String getPetName() {
        return petName;
    }
    public String getPetBreed() {
        return petBreed;
    }
    public int getPetAge() {
        return petAge;
    }
    public String getPetGender() { return petGender;}
    public String getPetNeutering() { return petNeutering;}

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
