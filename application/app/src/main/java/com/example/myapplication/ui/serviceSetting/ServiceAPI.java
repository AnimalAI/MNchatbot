package com.example.myapplication.ui.serviceSetting;

import com.example.myapplication.ui.dictionary.DsResponse;
import com.example.myapplication.ui.dictionary.DsListResponse;
import com.example.myapplication.ui.dictionary.DsPageResponse;
import com.example.myapplication.ui.questionNaire.QnResponse;
import com.example.myapplication.ui.questionNaire.Question;
import com.example.myapplication.ui.questionNaire.QuestionEdit;
import com.example.myapplication.ui.questionNaire.QnListResponse;
import com.example.myapplication.ui.diagnosis.DiagListResponse;
import com.example.myapplication.ui.diagnosis.DiagResponse;
import com.example.myapplication.ui.history.HistoryListResponse;
import com.example.myapplication.ui.history.HistoryResponse;
import com.example.myapplication.ui.hospital.HospitalListResponse;
import com.example.myapplication.ui.join.EmailCodeData;
import com.example.myapplication.ui.join.EmailValidationData;
import com.example.myapplication.ui.join.JoinData;
import com.example.myapplication.ui.join.JoinResponse;
import com.example.myapplication.ui.join.PasswordChangeRequest;
import com.example.myapplication.ui.login.LoginRequest;
import com.example.myapplication.ui.login.LoginResponse;
import com.example.myapplication.ui.mainPage.ChatbotResponse;
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
    @PATCH("/member/changePw")
    Call<JoinResponse> changePw(@Body PasswordChangeRequest data);

    //회원 이메일(ID) 받아오는 api
    @GET("/member/email")
    Call<ProfileResponse> GetmemberEmail();

    //회원 탈퇴
    @DELETE("/member/delete/{memberEmail}")
    Call<PetProfileResponse> deletePost (@Path("memberEmail")String id);

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

    //반려동물 정보 수정
    @PATCH("/pet/changeInfo")
    Call<PetProfileResponse> EditPetPost(@Body PetinfoData petinfoData);

    //반려동물 삭제
    @DELETE("/pet/delete/{petSerial}")
    Call<PetProfileResponse> deletePetPost(@Path("petSerial")int Serial);

    //======================
    //질병백과 페이징처리
    @GET("/disease/totalDisease/{page}/{diseaseCnt}")
    Call<DsPageResponse> callDsinfo(@Path("page")int page, @Path("diseaseCnt")int itemCnt);

    //질병백과 검색 결과
    @GET("/disease/dsList/{dsName}")
    Call<DsListResponse> getDsinfo(@Path("dsName")String dsName);

    //질병백과 검색 결과 내용
    @GET("/disease/{dsId}")
    Call<DsResponse> getDsSearchinfo(@Path("dsId")String dsId);

    //======================
    //연계병원 목록 보여주기
    @GET("/hospital/{region}/{city}")
    Call<HospitalListResponse> Allhosplist(@Path("region")String region, @Path("city")String city);

    //연계병원 상담신청
    @Multipart
    @POST("/hospital/apply")
    Call<HospitalListResponse> apply(@Part MultipartBody.Part postImg,
                                     @PartMap HashMap<String, RequestBody> data);

    //======================
    //문진표 작성
    @POST("/medicalForm/add")
    Call<QnResponse> setQuestion(@Body Question questionNaire);

    //문진표 목록
    @GET("/medicalForm/medicalFormList/{petSerial}")
    Call<QnListResponse> getQnList(@Path("petSerial")int Serial);

    //문진표 세부 내용
    @GET("/medicalForm/{medicalFormSerial}")
    Call<QnResponse> getQuestion(@Path("medicalFormSerial")int mSerial);

    //문진표 정보 수정
    @PATCH("/medicalForm/update")
    Call<QnResponse> EditQuestion(@Body QuestionEdit questionNaire);

    //문진표 삭제
    @DELETE("/medicalForm/delete/{medicalFormSerial}")
    Call<QnListResponse> deleteQuestion(@Path("medicalFormSerial")int mSerial);

    //=======================
    //예상진단 목록
    @GET("/expectDiag/expectDiagList/{petSerial}")
    Call<DiagListResponse> getDiagList(@Path("petSerial")int Serial);

    //예상진단 세부목록
    @GET("/expectDiag/{expectDiagSerial}")
    Call<DiagResponse> getDiag(@Path("expectDiagSerial")int DiagSerial);

    //예상진단 삭제
    @DELETE("/expectDiag/delete/{expectDiagSerial}")
    Call<DiagListResponse> deleteDiag(@Path("expectDiagSerial")int DiagSerial);

    //예상진단 추가
    @POST("/expectDiag/add/{petSerial}")
    Call<ChatbotResponse> addDiag(@Path("petSerial")int Serial);

    //=======================
    //상담신청 내역
    @GET("/appointment/appointmentList/{petSerial}")
    Call<HistoryListResponse> getHistoryList(@Path("petSerial")int Serial);

    //상담신청 내역 세부정보
    @GET("/appointment/{appointmentSerial}")
    Call<HistoryResponse> getHistoryObject(@Path("appointmentSerial")int apptSerial);

    //상담신청 삭제
    @DELETE("/appointment/delete/{appointmentSerial}")
    Call<HistoryListResponse> deleteHistory(@Path("appointmentSerial")int apptSerial);
}
