package com.example.myapplication.ui.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myapplication.ui.petSelect.RecyclerViewAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

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

    private RecyclerViewAdapter mRecyclerViewAdapter = null;
    private Context context;

    private SharedPreferences preferences;
    PetProfileResponse.PetDataObject petdata;

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
        preferences = getSharedPreferences("petSerial", MODE_PRIVATE);
        int Serial = preferences.getInt("Serial", 0);
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

        Call<PetProfileResponse> call = profileAPI.EditPetPost(petinfoData);
                //.updatePetPost(Age, petinfoData);

        call.enqueue(new Callback<PetProfileResponse>() {
            @Override
            public void onResponse(Call<PetProfileResponse> call, Response<PetProfileResponse> response) {
                if (!response.equals(200)) {
                    Toast.makeText(getApplicationContext(),"변경되었습니다.", Toast.LENGTH_SHORT).show();
                    PetProfileActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<PetProfileResponse> call, Throwable t) {
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
        ArrayList List = new ArrayList();
        preferences = getSharedPreferences("Serial", MODE_PRIVATE);
        int Serial = preferences.getInt("petSerial", 0);
        Call<PetProfileResponse> call = profileAPI.deletePetPost(Serial);
        call.enqueue(new Callback<PetProfileResponse>() {
            @Override
            public void onResponse(Call<PetProfileResponse> call, Response<PetProfileResponse> response) {
                if (!response.equals(200)) {
                    //펫 리스트 목록으로 받기
                    String json = preferences.getString("key", null);
                    if (json != null) {
                        try {
                            JSONArray a = new JSONArray(json);
                            for (int i = 0; i < a.length(); i++) {
                                String list = a.optString(i);
                                List.add(list);
                            }
                            mRecyclerViewAdapter = new RecyclerViewAdapter(List, context);
                            Intent intent = new Intent(PetProfileActivity.this, PetSelectActivity.class);
                            String item = (String) List.get(Serial -1);
                            List.remove(item);
                            mRecyclerViewAdapter.notifyDataSetChanged();
                            Log.d("~", String.valueOf(item));
                            startActivity(intent);
                            PetProfileActivity.this.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
            @Override
            public void onFailure(Call<PetProfileResponse> call, Throwable t) {
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
        preferences = getSharedPreferences("Serial", MODE_PRIVATE);
        int Serial = preferences.getInt("petSerial", 0);
        Log.d("!!", String.valueOf(Serial));
        Call<PetProfileResponse> call = profileAPI.getPetinfo(Serial);

        call.enqueue(new Callback<PetProfileResponse>() {
            @Override
            public void onResponse(Call<PetProfileResponse> call, Response<PetProfileResponse> response) {
                if (!response.equals(200)) {
                    petdata = response.body().data;
                    //Log.d("petdataType", petdata.toString());

                    CATDOG = petdata.getpetSpecies();
                    if (CATDOG.equals("CAT")) {petSpecies.setImageResource(R.drawable.cat2);}
                    else {petSpecies.setImageResource(R.drawable.dog2);}
                    petNickName.setHint(petdata.getPetName());
                    petBreed.setHint(petdata.getPetBreed());
                    petAge.setText(String.valueOf(petdata.getPetAge()));
                    if (petdata.getPetGender().equals("MALE")) {
                        man.setChecked(true);
                    } else { woman.setChecked(true); }
                    if (petdata.getPetNeutering().equals("NEUTER")) {
                        NeuteringYes.setChecked(true);
                    } else { NeuteringNo.setChecked(true); }

                    Toast.makeText(getApplicationContext(),"설정되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PetProfileResponse> call, Throwable t) {
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
