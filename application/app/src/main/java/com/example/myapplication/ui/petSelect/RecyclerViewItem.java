package com.example.myapplication.ui.petSelect;

public class RecyclerViewItem {
    private String mpetSpecies;
    private String mpetName;
    private Long mpetSerial;

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

    public Long getPetSerial() {
        return mpetSerial;
    }

    public void setPetSerial(Long petSerial) {
        this.mpetSerial = petSerial;
    }

}