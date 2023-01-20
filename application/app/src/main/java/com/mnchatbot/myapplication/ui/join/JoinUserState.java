package com.mnchatbot.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinUserState {

    private String email = null;
    private String password = null;
    private String rePassword = null;

    public boolean isEmailValid() {
        if (email == null || !email.contains("@")) {
            return false;
        } else {
            return !email.trim().isEmpty();
        }
    }

    public boolean isPasswordValid() {
        // 비밀번호 유효성 검사식1 : 영문자 대소문자, 숫자, 특수문자 중 3가지 이상 포함되어야 한다.
        String val_symbol = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자

        // 정규표현식 컴파일
        Pattern pattern_symbol = Pattern.compile(val_symbol);

        if (password == null || password.length() == 0)
            return false;

        Matcher matcher_symbol = pattern_symbol.matcher(password);

        if (!matcher_symbol.find()) {
            return false;
        }

        return true;
    }

    public boolean isPasswordSame(){
        if(password == null || rePassword == null)
            return false;
        return password.equals(rePassword);
    }

    public boolean isValidData() {
        return isPasswordSame() &&  isPasswordValid() && isEmailValid();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

}
