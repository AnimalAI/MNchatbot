package com.example.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

public class PetProfileResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public PetDataObject data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" + "data=" + data + "}";
    }

    public class PetDataObject {
        //반려동물
        @SerializedName("petSerial")
        private Long petSerial;
        @SerializedName("petSpecies")
        private String petSpecies;
        @SerializedName("petName")
        private String petName;
        @SerializedName("petBreed")
        private String petBreed;
        @SerializedName("petAge")
        private int petAge;
        @SerializedName("petGender")
        private String petGender;
        @SerializedName("petNeutralization")
        private String petNeutering;

        public Long getpetSerial() {
            return petSerial;
        }
        public String getpetSpecies() {
            return petSpecies;
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
    }

}
