package com.mnchatbot.myapplication.ui.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DsPageResponse {

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
        //총 페이지 수, 다음 페이지 여부
        @SerializedName("totalDiseaseCnt")
        private int totalCnt;
        @SerializedName("hasNextPage")
        private boolean nextPage;

        @SerializedName("diseaseList")
        public List<DsPageListItem> dsPageList;

        public int gettotalCnt() {
            return totalCnt;
        }
        public boolean getnextPage() {
            return nextPage;
        }
    }

    public class DsPageListItem {
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
