package com.example.myapplication.ui.ServiceSetting;

import com.example.myapplication.ui.Dictionary.DsResponse;
import com.example.myapplication.ui.Dictionary.dsListResponse;
import com.example.myapplication.ui.QuestionNaire.QnResponse;
import com.example.myapplication.ui.QuestionNaire.Question;
import com.example.myapplication.ui.hospital.ApplyData;
import com.example.myapplication.ui.hospital.hospitalListResponse;
import com.example.myapplication.ui.join.EmailCodeData;
import com.example.myapplication.ui.join.EmailValidationData;
import com.example.myapplication.ui.join.JoinData;
import com.example.myapplication.ui.join.JoinResponse;
import com.example.myapplication.ui.login.LoginRequest;
import com.example.myapplication.ui.login.LoginResponse;
import com.example.myapplication.ui.petSelect.Petinfo;
import com.example.myapplication.ui.petSelect.petListResponse;
import com.example.myapplication.ui.setting.PetinfoData;
import com.example.myapplication.ui.setting.PetProfileResponse;
import com.example.myapplication.ui.setting.ProfileResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Call<ProfileResponse> GetmemberEmail();

    //회원 탈퇴 >> 코드 수정 필요 / userinfo 파일 삭제여부
    @DELETE("/member/delete/{memberEmail}")
    Call<PetProfileResponse> deletePost (@Path("memberEmail")int id);

    //반려동물 정보 등록
    @POST("/pet/add")
    Call<PetProfileResponse> setPetinfo(@Body Petinfo petinfo);

    //======================
    //반려동물 목록 보여주기
    @GET("/pet/petList")
    Call<petListResponse> setPetlist();
    
    //반려동물 정보 보여주기, 초기 셋팅값
    @GET("/pet/{petSerial}")
    Call<PetProfileResponse> getPetinfo(@Path("petSerial")int Serial);

    //(수정 필요) 반려동물 정보 수정
    @PATCH("/pet/changeInfo")
    Call<PetProfileResponse> EditPetPost(@Body PetinfoData petinfoData);

    /*@PUT("/Profile/{petAge}")
    Call<ProfileResponse> updatePetPost(@Path("petAge") int petAge,
                                        @Body PetinfoData petinfoData);*/

    //반려동물 삭제
    @DELETE("/pet/delete/{petSerial}")
    Call<PetProfileResponse> deletePetPost(@Path("petSerial")int Serial);

    //======================
    //질병백과 목록 보여주기
    //@GET("/disease")
    //Call<hospitalListResponse> Allhosplist();

    //질병백과 검색 결과
    @GET("/disease/dsList/{dsName}")
    Call<dsListResponse> getDsinfo(@Path("dsName")String dsName);

    //질병백과 검색 결과 내용
    @GET("/disease/{dsId}")
    Call<DsResponse> getDsSearchinfo(@Path("dsId")String dsId);

    //======================
    //연계병원 목록 보여주기
    @GET("/hospital/{region}/{city}")
    Call<hospitalListResponse> Allhosplist(@Path("region")String region, @Path("city")String city);

    /*연계병원 상담신청
    @POST("/hospital/apply")
    Call<hospitalListResponse> apply(@Body ApplyData applyData);*/

    @Multipart
    @POST("/hospital/apply")
    Call<hospitalListResponse> apply(@Part MultipartBody.Part postImg,
                                     @PartMap HashMap<String, RequestBody> data);

    //======================
    //문진표 작성
    @POST("/Question/add")
    Call<QnResponse> setQuestion(@Body Question questionNaire);
}
