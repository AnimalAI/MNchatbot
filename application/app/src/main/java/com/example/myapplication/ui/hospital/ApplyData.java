package com.example.myapplication.ui.hospital;

import com.google.gson.annotations.SerializedName;

public class ApplyData {
    @SerializedName("h_Name")
    public String h_Name;
    @SerializedName("h_num")
    public String h_num;
    //문진표 스피너 선택값
    @SerializedName("h_questionNaire")
    public String h_questionNaire;
    @SerializedName("h_date")
    public String h_date;
    @SerializedName("h_time")
    public String h_time;
    //예상 비용 안내여부
    @SerializedName("h_bill")
    public String h_bill;

    @SerializedName("h_reson")
    public String h_reson;

    public ApplyData(String h_Name, String h_num, String h_questionNaire, String h_date, String h_time, String h_bill, String h_reson) {
        this.h_Name = h_Name;
        this.h_num = h_num;
        this.h_questionNaire = h_questionNaire;
        this.h_date = h_date;
        this.h_time = h_time;
        this.h_bill = h_bill;
        this.h_reson = h_reson;

    }
}
