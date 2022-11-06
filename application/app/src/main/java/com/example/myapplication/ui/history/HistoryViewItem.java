package com.example.myapplication.ui.history;

public class HistoryViewItem {
    private int apptSerial;
    private String HospitalName;
    private String HospitalDate;

    public int getapptSerial() {
        return apptSerial;
    }
    public void setapptSerial(int apptSerial) {
        this.apptSerial = apptSerial;
    }

    public String getHospitalName() {
        return HospitalName;
    }
    public void setHospitalName(String Name) {
        this.HospitalName = Name;
    }

    public String getHospitalDate() {
        return HospitalDate;
    }
    public void setHospitalDate(String hsDate) {
        this.HospitalDate = hsDate;
    }
}
