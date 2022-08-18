package com.example.myapplication.ui.ServiceSetting;

import com.example.myapplication.ui.join.EmailCodeData;
import com.example.myapplication.ui.join.EmailValidationData;
import com.example.myapplication.ui.join.JoinData;
import com.example.myapplication.ui.join.JoinResponse;
import com.example.myapplication.ui.login.LoginRequest;
import com.example.myapplication.ui.login.LoginResponse;
import com.example.myapplication.ui.setting.PetinfoData;
import com.example.myapplication.ui.setting.ProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    //반려동물 정보 등록, 초기 셋팅값
    @POST("/PetSelect")
    Call<ProfileResponse> getPetinfo(@Body PetinfoData petinfoData);

    //회원 탈퇴
    @DELETE("/Profile/{memberEmail}")
    Call<ProfileResponse> deletePost(@Path("memberEmail")int id);

    //반려동물 정보 수정
    @PUT("/Profile/{petAge}")
    Call<ProfileResponse> updatePetPost(@Path("petAge") String petAge,
                                        @Body PetinfoData petinfoData);

    //반려동물 삭제
    @DELETE("/Profile/{petName}")
    Call<ProfileResponse> deletePetPost(@Path("petName")int name);
}
