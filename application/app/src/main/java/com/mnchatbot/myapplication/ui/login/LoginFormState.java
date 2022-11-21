package com.mnchatbot.myapplication.ui.login;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // 비밀번호 유효성 검사식1 : 숫자, 특수문자가 포함되어야 한다.
        String val_symbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";
        // 비밀번호 유효성 검사식2 : 영문자 대소문자가 적어도 하나씩은 포함되어야 한다.
        String val_alpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";
        // 정규표현식 컴파일
        Pattern pattern_symbol = Pattern.compile(val_symbol);
        Pattern pattern_alpha = Pattern.compile(val_alpha);

        if (password == null) {
            return false;
        }

        Matcher matcher_symbol = pattern_symbol.matcher(password);
        Matcher matcher_alpha = pattern_alpha.matcher(password);

        if (!matcher_symbol.find() || !matcher_alpha.find()) {
            return false;
        }

        return password.trim().length() > 7;
    }

    public boolean isValidData() {
        return isPasswordValid() && isEmailValid();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
