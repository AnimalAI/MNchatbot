package com.example.myapplication.ui.Dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class dsPageResponse {

    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public DsPageList data;

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public class DsPageList {
        //검색결과
        @SerializedName("diseaseId")
        private String dsId;
        @SerializedName("diseaseName")
        private String dsName;

        @SerializedName("diseaseList")
        private List<DsPageListItem> dsPageList;

        public String getdiseaseId() {
            return dsId;
        }
        public String getdiseaseName() {
            return dsName;
        }
    }

    public class DsPageListItem {
    }
}
