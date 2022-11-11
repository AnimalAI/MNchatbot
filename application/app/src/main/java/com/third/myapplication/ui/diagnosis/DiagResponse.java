package com.example.myapplication.ui.diagnosis;

import com.google.gson.annotations.SerializedName;

public class DiagResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public DiagData data;

    public String getMessage() {
        return message;
    }

    public class DiagData {
        @SerializedName("date")
        private String diagDate;
        @SerializedName("time")
        private String diagTime;
        @SerializedName("diseaseName")
        private String dsName;
        @SerializedName("breed")
        private String dsBreed;
        @SerializedName("definition")
        private String dsDefinition;
        @SerializedName("cause")
        private String dsCause;
        @SerializedName("advice")
        private String dsAdvice;

        public String getdiagDate() {
            return diagDate;
        }
        public String getdiagTime() {
            return diagTime;
        }
        public String getdsName() {
            return dsName;
        }
        public String getdsBreed() {
            return dsBreed;
        }
        public String getdsDefinition() {
            return dsDefinition;
        }
        public String getdsCause() { return dsCause;}
        public String getdsAdvice() { return dsAdvice;}

    }
}
