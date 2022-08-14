package com.example.myapplication.ui.join;

import com.google.gson.annotations.SerializedName;

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
        return password != null && password.trim().length() > 5 && isAlphaOrDigit(password);
    }

    public boolean isPasswordSame(){
        if(password == null || rePassword == null)
            return false;
        return password.equals(rePassword);
    }

    public boolean isValidData() {
        return isPasswordSame() &&  isPasswordValid() && isEmailValid();
    }

    private boolean isAlphaOrDigit(String password) {
        for(int i=0;i<password.length();i++) {
            if (!Character.isLetterOrDigit(password.charAt(i)))
                return false;
        }
        return true;
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

    public static class PasswordChangeRequest {
        @SerializedName("memberEmail")
        public String inputId;

        @SerializedName("memberPassword")
        public String inputPw;

        @SerializedName("sendCode")
        public int sendCode;

        @SerializedName("receivedCode")
        public int receivedCode;

        public PasswordChangeRequest(String inputId, String inputPw, int sendCode, int receivedCode) {
            this.inputId = inputId;
            this.inputPw = inputPw;
            this.sendCode = sendCode;
            this.receivedCode = receivedCode;
        }
    }
}
