package com.example.myapplication.ui.join;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.serviceSetting.ServiceAPI;
import com.example.myapplication.ui.serviceSetting.ServiceGenerator;
import com.example.myapplication.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChangeActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private JoinUserState joinUserState = new JoinUserState();
    private Dialog enterCodeDialog;
    private boolean isEmailValidated = false;
    private int codeEntered;
    private int codeReceived;

    //서버 통신
    private String TOKEN = getToken();
    private ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, TOKEN);
    public String getToken() {
        return TOKEN;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_password_change);

        final EditText emailEditText = (EditText) findViewById(R.id.editTextEmailAddress);
        final Button emailCheckButton = (Button) findViewById(R.id.emailCheckButton);
        final Button checkButton = (Button) findViewById(R.id.checkButton);
        final EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        final EditText passwordEditTextCheck = (EditText) findViewById(R.id.editTextPasswordCheck);

        // 사용자 입력에 따라 텍스트 알림창을 설정
        TextWatcher passwordAfterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                joinUserState.setPassword(passwordEditText.getText().toString());
                if (!joinUserState.isPasswordValid())
                    passwordEditText.setError("비밀번호는 여섯자리 이상, 영어와 숫자로 구성해주세요.");
            }
        };
        TextWatcher passwordReEnterAfterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                joinUserState.setRePassword(passwordEditTextCheck.getText().toString());
                if (!joinUserState.isPasswordSame())
                    passwordEditTextCheck.setError("비밀번호가 다릅니다.");
            }
        };
        passwordEditText.addTextChangedListener(passwordAfterTextChangedListener);
        passwordEditTextCheck.addTextChangedListener(passwordReEnterAfterTextChangedListener);

        // 본인확인 버튼 구현
        emailCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                joinUserState.setEmail(email);
                if (joinUserState.isEmailValid()) {
                    // 이메일 인증 절차
                    sendEmail(new EmailValidationData(joinUserState.getEmail()));
                } else {
                    emailEditText.setError("이메일을 확인해주세요.");
                }
            }
        });

        // 재설정 버튼 구현
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinUserState.setPassword(passwordEditText.getText().toString());
                joinUserState.setRePassword(passwordEditTextCheck.getText().toString());
                if (joinUserState.isValidData() && isEmailValidated) {
                    changePw(new PasswordChangeRequest(joinUserState.getPassword()));
                } else {
                    Toast.makeText(getApplicationContext(), "다시 입력해주세요", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // 이메일 전송
    private void sendEmail(EmailValidationData data) {
        service.sendEmail(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, retrofit2.Response<JoinResponse> response) {
                JoinResponse result = response.body();

                if (result.getCode() == 200) {
                    Toast.makeText(PasswordChangeActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    enterCodeDialog = new Dialog(PasswordChangeActivity.this);
                    enterCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    enterCodeDialog.setContentView(R.layout.email_check_dialog);
                    codeReceived = result.getData();
                    showEmailCodeEnterDialog();
                } else {
                    String faileResult = "입력하신 이메일로 코드를 전송하는데 실패하였습니다. 다시 시도해주세요.";
                    Toast.makeText(PasswordChangeActivity.this, faileResult, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(PasswordChangeActivity.this, "에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
            }
        });
    }

    // 이메일로 전송된 코드를 입력하는 다이얼로그
    public void showEmailCodeEnterDialog() {
        enterCodeDialog.show();
        Button button = enterCodeDialog.findViewById(R.id.emailCheckButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText codeEditText = enterCodeDialog.findViewById(R.id.editTextNumberPassword);
                codeEntered = Integer.parseInt(codeEditText.getText().toString());
                enterCodeDialog.cancel();
                enterEmailCode(new EmailCodeData(codeReceived,codeEntered));
            }
        });
    }
    // 입력된 코드를 인증하는 리퀘스트
    private void enterEmailCode(EmailCodeData data) {
        service.enterEmailCode(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, retrofit2.Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(PasswordChangeActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.getCode() == 200) {
                    isEmailValidated = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(PasswordChangeActivity.this);
                    dialog = builder.setMessage("이메일이 인증되었습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .create();
                    dialog.show();
                } else {
                    String faileResult = "이메일로 인증에 실패하였습니다. 다시 시도해주세요.";
                    Toast.makeText(PasswordChangeActivity.this, faileResult, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(PasswordChangeActivity.this, "에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
            }
        });
    }
    // 패스워드 변경 리퀘스트
    private void changePw(PasswordChangeRequest data) {
        service.changePw(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PasswordChangeActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PasswordChangeActivity.this);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("패스워드변경", String.valueOf(response.code()));
                    String faileResult = "비밀번호 변경에 실패하였습니다. 다시 시도해주세요.";
                    Toast.makeText(PasswordChangeActivity.this, faileResult, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(PasswordChangeActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
            }
        });
    }
}