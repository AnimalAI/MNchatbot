package com.example.myapplication.ui.setting;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileAPI {
    //@통신 방식("통신 API명")

    //반려동물 정보 등록
    @POST("/PetSelect")
    Call<ProfileResponse> getPetinfo(@Body PetinfoData petinfoData);

    //반려동물 초기 셋팅
    @POST("/PetProfile/{petName}")
    Call<ProfileResponse> getNameBreedAge(
            @Path("petName") String petName,
            @Body PetinfoData petinfoData
    );

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
