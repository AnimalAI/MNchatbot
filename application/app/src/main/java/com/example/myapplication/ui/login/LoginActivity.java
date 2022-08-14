package com.example.myapplication.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.join.JoinActivity;
import com.example.myapplication.ui.join.PasswordChangeActivity;
import com.example.myapplication.ui.join.RetrofitClient;
import com.example.myapplication.ui.petSelect.PetSelectActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private LoginAPI loginAPI = RetrofitClient.getClient().create(LoginAPI.class);

    private EditText login_email, login_password;
    private Button login_button, join_button, pw_change;
    private LoginFormState LoginFormState = new LoginFormState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        login_email = findViewById( R.id.username );
        login_password = findViewById( R.id.password );

        join_button = findViewById( R.id.signup );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, JoinActivity.class );
                startActivity( intent );
            }
        });
        //비밀번호 잊어버렸습니까? 버튼 눌렀을 때
        pw_change = findViewById(R.id.pwbtn);
        pw_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, PasswordChangeActivity.class );
                startActivity( intent );
            }
        });

        //이메일, pw 입력창 액션 리스너
        TextWatcher emailAfterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                LoginFormState.setEmail(login_email.getText().toString());
                if(!LoginFormState.isEmailValid()) {
                    login_email.setError("이메일 형식이 잘못되었습니다.");
                }
                //data 형식이 유효하면 로그인 버튼 활성화
                login_button.setEnabled(LoginFormState.isValidData());
            }
        };
        TextWatcher passwordAfterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                LoginFormState.setPassword(login_password.getText().toString());
                if(!LoginFormState.isPasswordValid()) {
                    login_password.setError("6자리 이상 입력해주세요.");
                }
                //data 형식이 유효하면 로그인 버튼 활성화
                login_button.setEnabled(LoginFormState.isValidData());
            }
        };

        login_email.addTextChangedListener(emailAfterTextChangedListener);
        login_password.addTextChangedListener(passwordAfterTextChangedListener);

        //pw 입력창, 핸드폰 자체 키보드에 '확인' 누르면 입력한 값 받아오기.
        login_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login_email.getText().toString();
                    login_password.getText().toString();
                }
                return false;
            }
        });

        login_button = findViewById( R.id.login );
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginResponse();
            }
        });
    }
    public void LoginResponse() {
        String userID = login_email.getText().toString().trim();
        String userPassword = login_password.getText().toString().trim();

        //loginRequest에 사용자가 입력한 id와 pw를 저장
        LoginRequest loginRequest = new LoginRequest(userID, userPassword);

        //loginRequest에 저장된 데이터와 함께 LoginAPI에서 정의한 getLoginResponse 함수를 실행한 후 응답을 받음
        loginAPI.getLoginResponse(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //response.body()를 result에 저장
                LoginResponse result = response.body();

                //받은 코드 저장
                int statusCode = result.getStatusCode();

                if (result.getStatusCode()==200) {
                    String userID = login_email.getText().toString();

                    Toast.makeText(LoginActivity.this, userID + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, PetSelectActivity.class);
                    intent.putExtra("userId", userID);
                    startActivity(intent);
                    LoginActivity.this.finish();

                }  else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("알림")
                            .setMessage("아이디 혹은 비밀번호 오류입니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }

            //통신 실패
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}
