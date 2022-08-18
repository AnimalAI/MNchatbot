package com.example.myapplication.ui.join;

import com.example.myapplication.ui.login.LoginRequest;
import com.example.myapplication.ui.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceAPI {
    //로그인
    @POST("/login")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    @POST("/enterEmailCode/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/sendEmail")
    Call<JoinResponse> sendEmail(@Body EmailValidationData data);

    @POST("/validateDuplicateEmail")
    Call<JoinResponse> emailValidation(@Body EmailValidationData data);

    @POST("/changePw") //정보 수정.. put으로 가능함!
    Call<JoinResponse> changePw(@Body LoginRequest data);

    @POST("/enterEmailCode/changePw")
    Call<JoinResponse> enterEmailCode(@Body EmailCodeData data);
}
