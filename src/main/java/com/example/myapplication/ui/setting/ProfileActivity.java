package com.example.myapplication.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ui.join.PasswordChangeActivity;
import com.example.myapplication.ui.join.RetrofitClient;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.ui.login.LoginRequest;
import com.example.myapplication.ui.login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends SettingActivity {
    private Intent intent;
    private TextView ID, pwchange, logout, deleteinfo;
    private ProfileAPI profileAPI = RetrofitClient.getClient().create(ProfileAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        ID = findViewById(R.id.userID);
        pwchange = findViewById(R.id.pwchange);
        logout = findViewById(R.id.logout);
        deleteinfo = findViewById(R.id.deleteinfo);

        calluserInfo();

        //비밀번호 변경
        pwchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //비밀번호 재설정 화면으로 넘어가기
                Intent intent = new Intent(getApplicationContext(), PasswordChangeActivity.class);
                startActivity(intent);
            }
        });
        
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 화면으로 돌아가기
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //회원 탈퇴
        deleteinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder name = new AlertDialog.Builder(ProfileActivity.this);
                name.setTitle("회원탈퇴");
                name.setMessage("정말로 탈퇴하시겠습니까?");

                name.setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        ProfileDelete();
                    }
                });
                name.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                name.show();
            }
        });
    }
    
    //회원 탈퇴
    private void ProfileDelete() {
        Call<ProfileResponse> call = profileAPI.deletePost(10); //이게 무슨 의미인지 잘 모르겠음. 그러나 작동은 됨.
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (!response.equals(200)) {
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    ProfileActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
    public void calluserInfo(){
        String userID = ID.getText().toString().trim();
        LoginRequest loginRequest = new LoginRequest(userID, null);
        Call<LoginResponse> call = profileAPI.getLoginResponse(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!response.equals(200)) {
                    //정보 받아오는 것에서 오류 발생
                    ID.setText(response.body().getuserID());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });


    }
}
