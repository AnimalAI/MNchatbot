package com.example.myapplication.ui.Dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DsResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public DsData data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public class DsData {
        //검색 결과 내용
        @SerializedName("dsName")
        private String dsName;
        @SerializedName("dsAmlBreed")
        private String dsBreed;
        @SerializedName("dsDefinition")
        private String dsDefinition;
        @SerializedName("dsCause")
        private String dsCause;
        @SerializedName("dsPathogenesis")
        private String dsPathogenesis;
        @SerializedName("dsEpidemiology")
        private String dsEpidemiology;
        @SerializedName("dsSymptom")
        private String dsSymptom;
        @SerializedName("dsDiagnosis")
        private String dsDiagnosis;
        @SerializedName("dsTreatment")
        private String dsTreatment;
        @SerializedName("dsPrevention")
        private String dsPrevention;
        @SerializedName("dsPrognosis")
        private String dsPrognosis;
        @SerializedName("dsAdvice")
        private String dsAdvice;

        public String getdsName() {
            return dsName;
        }
        public String getdsAmlBreed() {
            return dsBreed;
        }
        public String getdsDefinition() {
            return dsDefinition;
        }
        public String getdsCause() { return dsCause;}
        public String getdsPathogenesis() { return dsPathogenesis;}
        public String getdsEpidemiology() {
            return dsEpidemiology;
        }
        public String getdsSymptom() {
            return dsSymptom;
        }
        public String getdsDiagnosis() {
            return dsDiagnosis;
        }
        public String getdsTreatment() { return dsTreatment;}
        public String getdsPrevention() { return dsPrevention;}
        public String getdsPrognosis() { return dsPrognosis;}
        public String getdsAdvice() { return dsAdvice;}

    }
}
