package com.example.myapplication.ui.ServiceSetting;

import com.example.myapplication.ui.join.EmailCodeData;
import com.example.myapplication.ui.join.EmailValidationData;
import com.example.myapplication.ui.join.JoinData;
import com.example.myapplication.ui.join.JoinResponse;
import com.example.myapplication.ui.login.LoginRequest;
import com.example.myapplication.ui.login.LoginResponse;
import com.example.myapplication.ui.petSelect.petListResponse;
import com.example.myapplication.ui.setting.PetinfoData;
import com.example.myapplication.ui.setting.ProfileResponse;
import com.example.myapplication.ui.setting.UserinfoData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAPI {
    //로그인
    @POST("/login")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    //회원가입, 이메일 중복 확인
    @POST("/validateDuplicateEmail")
    Call<JoinResponse> emailValidation(@Body EmailValidationData data);

    //이메일
    @POST("/sendEmailCode")
    Call<JoinResponse> sendEmail(@Body EmailValidationData data);

    //회원가입, 인증코드
    @POST("/enterEmailCode/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    //비밀번호 변경, 인증코드
    @POST("/enterEmailCode")
    Call<JoinResponse> enterEmailCode(@Body EmailCodeData data);

    //비밀번호 변경
    @POST("/member/changePw")
    Call<JoinResponse> changePw(@Body LoginRequest data);

    //회원 이메일(ID) 받아오는 api
    @GET("/member/email")
    Call<ProfileResponse> GetmemberEmail (@Body UserinfoData userinfoData);

    //회원 탈퇴
    @DELETE("/member/delete/{memberEmail}")
    Call<ProfileResponse> deletePost (@Path("memberEmail")int id);

    //반려동물 정보 등록
    @POST("/pet/add")
    Call<ProfileResponse> setPetinfo(@Body PetinfoData petinfoData);

    //======================
    //반려동물 목록 보여주기
    @GET("/pet/petList")
    Call<petListResponse> setPetlist();
    
    //반려동물 정보 보여주기, 초기 셋팅값
    @GET("/pet/{petSerial}")
    Call<ProfileResponse> getPetinfo(@Path("petSerial")Long Serial);


    //(수정 필요) 반려동물 정보 수정
    @PATCH("/pet/changeInfo")
    Call<ProfileResponse> EditPetPost();

    /*@PUT("/Profile/{petAge}")
    Call<ProfileResponse> updatePetPost(@Path("petAge") int petAge,
                                        @Body PetinfoData petinfoData);*/

    //반려동물 삭제
    @DELETE("/pet/delete/{petSerial}")
    Call<ProfileResponse> deletePetPost(@Path("petSerial")Long Serial);
}
