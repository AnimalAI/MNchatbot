package com.example.myapplication.ui.petSelect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.setting.PetinfoData;
import com.example.myapplication.ui.setting.ProfileResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetActivity extends AppCompatActivity {
    private MaterialButtonToggleGroup Gendertoggle, Neuteringtoggle;
    private MaterialButton man, woman, NeuteringYes, NeuteringNo;
    private String CATDOG;
    private ImageView petprofile;
    private TextView petAge;
    private EditText petBreed,petNickName;
    private Button btnAge, btnSave, btnCancel, selectCatButton, selectDogButton;

    //서버 통신
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpetprofile);

        petprofile = findViewById(R.id.pic);
        btnAge = findViewById(R.id.btnAge);
        btnSave = findViewById(R.id.btnAddPetSave);
        btnCancel = findViewById(R.id.btnAddPetCancel);
        selectCatButton = findViewById(R.id.selectCat);
        selectDogButton = findViewById(R.id.selectDog);

        Gendertoggle = findViewById(R.id.Gendertoggle);
        Neuteringtoggle = findViewById(R.id.Neuteringtoggle);
        man = Gendertoggle.findViewById(R.id.man);
        woman = Gendertoggle.findViewById(R.id.woman);
        NeuteringYes = Neuteringtoggle.findViewById(R.id.Neuteringyes);
        NeuteringNo = Neuteringtoggle.findViewById(R.id.Neuteringno);

        petBreed = findViewById(R.id.petbreed);
        petNickName = findViewById(R.id.petNickname);
        petAge = findViewById(R.id.petAge);
        

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setPetinfo();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), PetSelectActivity.class);
                startActivity(intent);
            }
        });

        //나이 변경
        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder AgePicker = new AlertDialog.Builder(AddPetActivity.this);

                AgePicker.setTitle("나이변경");
                final NumberPicker AP = new NumberPicker(AddPetActivity.this);
                AgePicker.setView(AP);

                AP.setMinValue(0);
                AP.setMaxValue(30);
                AP.setWrapSelectorWheel(false);
                AP.setValue(0);

                AP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    }
                });

                AgePicker.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        petAge.setText(String.valueOf(AP.getValue()));
                        dialog.dismiss();
                    }
                });
                AgePicker.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AgePicker.show();
            }
        });
        //개/고양이 버튼 클릭 시에 해당 사진으로 이미지뷰 변경
        selectDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petprofile.setImageResource(R.drawable.dog2);
                CATDOG = "DOG";
            }
        });
        selectCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petprofile.setImageResource(R.drawable.cat2);
                CATDOG = "CAT";
            }
        });

    }
    //반려동물 정보 설정
    public void setPetinfo(){
        preferences = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", null);
        ServiceAPI petAddAPI = ServiceGenerator.createService(ServiceAPI.class, token);

        String Species = CATDOG;
        String Name = petNickName.getText().toString().trim();
        int Age = Integer.parseInt(petAge.getText().toString());
        String Breed = petBreed.getText().toString().trim();
        String Gender = null;
        String Neutering = null;
        if (man.isChecked()) { Gender = "MALE";
        } else if (woman.isChecked()) { Gender = "FEMALE";}

        if (NeuteringYes.isChecked()) { Neutering = "NEUTER";
        } else if (NeuteringNo.isChecked()) { Neutering = "NOTNEUTER"; }

        PetinfoData petinfoData = new PetinfoData(null, Species, Name, Age, Breed, Gender, Neutering);
        Call<ProfileResponse> call = petAddAPI.setPetinfo(petinfoData);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (!response.equals(200)) {
                    Toast.makeText(getApplicationContext(),"등록되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPetActivity.this, PetSelectActivity.class);
                    intent.putExtra("petName", Name);
                    intent.putExtra("CATDOG", CATDOG);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPetActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}