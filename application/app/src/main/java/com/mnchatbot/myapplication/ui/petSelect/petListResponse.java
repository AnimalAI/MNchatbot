package com.mnchatbot.myapplication.ui.petSelect;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class petListResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<PetDataList> data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" + "data=" + data+ "}";
    }

    public class PetDataList {
        //반려동물
        @SerializedName("petSerial")
        private int petSerial;
        @SerializedName("petSpecies")
        private String petSpecies;
        @SerializedName("petName")
        private String petName;

        public int getpetSerial() {
            return petSerial;
        }
        public String getpetSpecies() {
            return petSpecies;
        }
        public String getPetName() {
            return petName;
        }
    }

}
