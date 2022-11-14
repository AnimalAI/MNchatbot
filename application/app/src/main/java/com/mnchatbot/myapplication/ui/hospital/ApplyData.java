package com.mnchatbot.myapplication.ui.hospital;

import com.google.gson.annotations.SerializedName;

public class ApplyData {
    @SerializedName("petSerial")
    public int petSerial;
    //문진표 스피너 선택값
    @SerializedName("medicalSerial")
    public int medicalSerial;
    @SerializedName("partnerSerial")
    public int partnerSerial;

    @SerializedName("apptDate")
    public String h_date;
    @SerializedName("apptTime")
    public String h_time;

    @SerializedName("apptMemberName")
    public String h_Name;
    @SerializedName("apptMemberTel")
    public String h_num;

    //예상 비용 안내여부
    @SerializedName("apptBill")
    public boolean h_bill;
    @SerializedName("apptReason")
    public String h_reson;
    @SerializedName("apptImage")
    public String h_Image;

    public ApplyData(int petSerial, int medicalSerial, int partnerSerial, String h_Name, String h_num, String h_date, String h_time, boolean h_bill, String h_reson, String h_Image) {
        this.petSerial = petSerial;
        this.medicalSerial = medicalSerial;
        this.partnerSerial = partnerSerial;

        this.h_Name = h_Name;
        this.h_num = h_num;
        this.h_date = h_date;
        this.h_time = h_time;
        this.h_bill = h_bill;
        this.h_reson = h_reson;
        this.h_Image = h_Image;

    }
}
