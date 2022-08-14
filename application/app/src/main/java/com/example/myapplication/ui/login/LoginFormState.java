package com.example.myapplication.ui.login;


/**
 * Data validation state of the login form.
 * 로그인 양식의 데이터 유효성 검사 상태입니다.
 */
class LoginFormState {
    private String email;
    private String password;

    //로그인 폼 상태가 유효한가
    public boolean isEmailValid() {
        if (email == null || !email.contains("@")) {
            return false;
        } else {
            return !email.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    public boolean isPasswordValid() {
        return password != null && password.trim().length() > 5 && isAlphaOrDigit(password);
    }

    public boolean isValidData() {
        return isPasswordValid() && isEmailValid();
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

}
