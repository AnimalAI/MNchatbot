package com.example.myapplication.ui.history;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class HistoryResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public HistoryData data;

    public String getMessage() {
        return message;
    }

    public class HistoryData {
        //검색 결과 내용
        @SerializedName("apptDate")
        private String HistoryDate;
        @SerializedName("apptTime")
        private String HistoryTime;
        @SerializedName("apptMemberName")
        private String MemberName;
        @SerializedName("apptMemberTell")
        private String MemberTell;
        @SerializedName("petName")
        private String petName;
        @SerializedName("petAge")
        private int petAge;
        @SerializedName("petSpecies")
        private String petSpecies;
        @SerializedName("petBreed")
        private String petBreed;
        @SerializedName("petGender")
        private String petGender;
        @SerializedName("isNeutralization")
        private String Neutralization;
        @SerializedName("underDisease")
        private String underDisease;
        @SerializedName("isSpecialNoteMedication")
        private String SpecialNoteMedication;
        @SerializedName("medication")
        private String medication;
        @SerializedName("isSurgeryOrAne")
        private String SurgeryOrAne;
        @SerializedName("isHyperExercise")
        private String HyperExercise;
        @SerializedName("etc")
        private String etc;
        @SerializedName("apptReasone")
        private String apptReason;
        @SerializedName("isCostRequest")
        private String Bill;
        @SerializedName("apptImage")
        private URL apptImage;

        public String getHistoryDate() {
            return HistoryDate;
        }
        public String getHistoryTime() {
            return HistoryTime;
        }
        public String getMemberName() {
            return MemberName;
        }
        public String getMemberTell() { return MemberTell;}
        public String getpetName() { return petName;}
        public int getpetAge() {
            return petAge;
        }
        public String getpetSpecies() {
            return petSpecies;
        }
        public String getpetBreed() {
            return petBreed;
        }
        public String getpetGender() { return petGender;}
        public String getNeutralization() { return Neutralization;}
        public String getunderDisease() { return underDisease;}
        public String getSpecialNoteMedication() { return SpecialNoteMedication;}
        public String getmedication() { return medication;}
        public String getSurgeryOrAne() { return SurgeryOrAne;}
        public String getHyperExercise() { return HyperExercise;}
        public String getEtc() { return etc;}
        public String getapptReason() { return apptReason;}
        public String getBill() { return Bill;}
        public URL getapptImage() { return apptImage;}

    }
}
