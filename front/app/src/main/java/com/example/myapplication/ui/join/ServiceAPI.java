package com.example.myapplication.ui.join;

import com.example.myapplication.ui.login.LoginRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface ServiceAPI {
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
