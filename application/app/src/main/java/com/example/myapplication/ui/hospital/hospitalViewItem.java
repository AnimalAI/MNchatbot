package com.example.myapplication.ui.hospital;

public class hospitalViewItem {
    private String HospitalType, HospitalName, HospitalNumber, location, email, field;

    public String getHospitalType() {
        return HospitalType;
    }
    public void setHospitalType(String Type) {
        this.HospitalType = Type;
    }

    public String getHospitalName() {
        return HospitalName;
    }
    public void setHospitalName(String Name) {
        this.HospitalName = Name;
    }

    public String getHospitalNumber() {
        return HospitalNumber;
    }
    public void setHospitalNumber(String Num) {
        this.HospitalNumber = Num;
    }

    public String getlocation() {
        return location;
    }
    public void setlocation(String Hospitallocation) {
        this.location = Hospitallocation;
    }

    public String getemail() {
        return email;
    }
    public void setemail(String Hospitalemail) {
        this.email = Hospitalemail;
    }

    public String getfield() {
        return field;
    }
    public void setfield(String Hospitalfield) {
        this.field = Hospitalfield;
    }
}
