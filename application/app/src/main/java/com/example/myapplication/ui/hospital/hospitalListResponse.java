package com.example.myapplication.ui.hospital;

import com.example.myapplication.ui.petSelect.Response_DataList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class hospitalListResponse {
    @SerializedName("statusCode")
    private int code;

    @SerializedName("responseMessage")
    private String message;

    @SerializedName("data")
    public List<Response_hDataList> data;

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
}
