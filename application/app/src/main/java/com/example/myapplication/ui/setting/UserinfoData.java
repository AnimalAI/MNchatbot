package com.example.myapplication.ui.setting;

import com.google.gson.annotations.SerializedName;

//프로필 이메일, pw 정보 요청
//반려동물 이름, 품종, 나이, 성별, 중성화여부 정보 요청
public class UserinfoData {
    @SerializedName("memberEmail")
    public String userEmail;

    @SerializedName("memberPassword")
    public String userPwd;

    public UserinfoData(String userEmail, String userPwd) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;

    }
}
