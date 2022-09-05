package com.example.myapplication.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.petSelect.PetSelectActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetProfileActivity extends SettingActivity {
    private MaterialButtonToggleGroup Gendertoggle, Neuteringtoggle;
    private MaterialButton man, woman, NeuteringYes, NeuteringNo;
    private String CATDOG;
    private ImageView petSpecies;
    private TextView petAge;
    private EditText petBreed,petNickName;
    private Button btnAge, btnSave, btnDelete;
    private Response_DataList petSerial;
    ProfileResponse dataList;
    List<Response_DataList> petdata;

    //서버 통신
    private String TOKEN = getToken();
    private ServiceAPI profileAPI = ServiceGenerator.createService(ServiceAPI.class, TOKEN);
    public String getToken() {
        return TOKEN;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petprofile);

        petNickName = findViewById(R.id.petNickname);
        petAge = findViewById(R.id.petAge);
        petBreed = findViewById(R.id.petBreed);
        btnAge = findViewById(R.id.btnAge);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        petSpecies = findViewById(R.id.pic);

        Gendertoggle = findViewById(R.id.Gendertoggle);
        Neuteringtoggle = findViewById(R.id.Neuteringtoggle);
        man = Gendertoggle.findViewById(R.id.man);
        woman = Gendertoggle.findViewById(R.id.woman);
        NeuteringYes = Neuteringtoggle.findViewById(R.id.Neuteringyes);
        NeuteringNo = Neuteringtoggle.findViewById(R.id.Neuteringno);
        
        //반려동물 정보 불러오기
        callPetinfo();
        
        //나이 변경
        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder AgePicker = new AlertDialog.Builder(PetProfileActivity.this);

                AgePicker.setTitle("나이변경");
                final NumberPicker AP = new NumberPicker(PetProfileActivity.this);
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

        //저장 버튼
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //바뀐 정보 모두 DB로 이동
                updatePetPost();
            }
        });

        //삭제 버튼
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder name = new AlertDialog.Builder(PetProfileActivity.this);
                name.setTitle("반려동물 삭제");
                name.setMessage("정말로 삭제하시겠습니까?");

                name.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        PetProfileDelete();
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

    //반려동물 정보 변경
    public void updatePetPost(){
        Long Serial = null;
        String Species = "";
        String Name = petNickName.getText().toString().trim();
        int Age = Integer.parseInt(petAge.getText().toString());
        String Breed = petBreed.getText().toString().trim();
        String Gender = null;
        String Neutering = null;
        if (man.isChecked()) { Gender = "MALE";
        } else if (woman.isChecked()) { Gender = "FEMALE";}

        if (NeuteringYes.isChecked()) { Neutering = "NEUTER";
        } else if (NeuteringNo.isChecked()) { Neutering = "NOTNEUTER"; }

        PetinfoData petinfoData = new PetinfoData(Serial, Species, Name, Age, Breed, Gender, Neutering);

        Call<ProfileResponse> call = profileAPI.updatePetPost(Age, petinfoData);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (!response.equals(200)) {
                    Toast.makeText(getApplicationContext(),"변경되었습니다.", Toast.LENGTH_SHORT).show();
                    PetProfileActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PetProfileActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

    //반려동물 정보 삭제
    private void PetProfileDelete() {
        Call<ProfileResponse> call = profileAPI.deletePetPost(10); //이게 무슨 의미인지 잘 모르겠음. 그러나 작동은 됨.
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (!response.equals(200)) {
                    Intent intent = new Intent(PetProfileActivity.this, PetSelectActivity.class);
                    startActivity(intent);
                    PetProfileActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PetProfileActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
    
    //반려동물 정보 불러오기
    public void callPetinfo(){
        petdata = new ArrayList<>();
        Call<ProfileResponse> call = profileAPI.getPetinfo(1L);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (!response.equals(200)) {
                    dataList = response.body();
                    Log.d("~", dataList.toString());
                    petdata = dataList.data;

                    CATDOG = petdata.get(0).getpetSpecies();
                    if (CATDOG.equals("CAT")) {petSpecies.setImageResource(R.drawable.cat2);}
                    else {petSpecies.setImageResource(R.drawable.dog2);}
                    petNickName.setHint(petdata.get(0).getPetName());
                    petBreed.setHint(petdata.get(0).getPetBreed());
                    petAge.setText(String.valueOf(petdata.get(0).getPetAge()));
                    if (petdata.get(0).getPetGender().equals("MALE")) {
                        man.setChecked(true);
                    } else { woman.setChecked(true); }
                    if (petdata.get(0).getPetNeutering().equals("NEUTER")) {
                        NeuteringYes.setChecked(true);
                    } else { NeuteringNo.setChecked(true); }

                    Toast.makeText(getApplicationContext(),"설정되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PetProfileActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
                Log.d("~", t.toString());
            }
        });
    }

}
