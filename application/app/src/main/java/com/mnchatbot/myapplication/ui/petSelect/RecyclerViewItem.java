package com.mnchatbot.myapplication.ui.petSelect;

public class RecyclerViewItem {
    private String mpetSpecies;
    private String mpetName;
    private int mpetSerial;

    public String getImgName() {
        return mpetSpecies;
    }

    public void setImgName(String imgName) {
        this.mpetSpecies = imgName;
    }

    public String getMainText() {
        return mpetName;
    }

    public void setMainText(String mainText) {
        this.mpetName = mainText;
    }

    public int getPetSerial() {
        return mpetSerial;
    }

    public void setPetSerial(int petSerial) {
        this.mpetSerial = petSerial;
    }

}