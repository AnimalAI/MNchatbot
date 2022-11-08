package com.example.myapplication.ui.dictionary;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DsListResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<DsDataList> data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "DsSearchResponse{" + "data=" + data+ "}";
    }

    public class DsDataList {
        //검색결과
        @SerializedName("diseaseId")
        private String dsId;
        @SerializedName("diseaseName")
        private String dsName;

        public String getdiseaseId() {
            return dsId;
        }
        public String getdiseaseName() {
            return dsName;
        }
    }

}
