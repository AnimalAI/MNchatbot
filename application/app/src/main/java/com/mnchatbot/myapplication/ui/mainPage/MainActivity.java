/*
[메인화면] 튜토리얼/챗봇/문진표/예상진단/동물병원/질병백과 화면을 출력함.
상단 툴바를 통해 로그인, 펫 선택, 상담신청 내역 화면을 볼 수 있음.*/

package com.mnchatbot.myapplication.ui.mainPage;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.mnchatbot.myapplication.R;
import com.mnchatbot.myapplication.ui.dictionary.Fragment_Dictionary;
import com.mnchatbot.myapplication.ui.dictionary.Fragment_Dictionary_detail;
import com.mnchatbot.myapplication.ui.questionNaire.Fragment_Question;
import com.mnchatbot.myapplication.ui.questionNaire.Fragment_Question_detail;
import com.mnchatbot.myapplication.ui.questionNaire.Fragment_Question_detail2;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceAPI;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceGenerator;
import com.mnchatbot.myapplication.ui.diagnosis.Fragment_diagnosis;
import com.mnchatbot.myapplication.ui.diagnosis.Fragment_diagnosis_detail;
import com.mnchatbot.myapplication.ui.history.HistoryActivity;
import com.mnchatbot.myapplication.ui.hospital.Fragment_hospital;
import com.mnchatbot.myapplication.ui.hospital.Fragment_hospital_detail;
import com.mnchatbot.myapplication.ui.login.LoginActivity;
import com.mnchatbot.myapplication.ui.petSelect.PetSelectActivity;
import com.mnchatbot.myapplication.ui.setting.PetProfileResponse;
import com.mnchatbot.myapplication.ui.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pre, pre2;
    PetProfileResponse.PetDataObject petdata;

    HomeTutor homeTutor;

    ChatbotWeb chatbotWeb;
    Fragment_diagnosis diagnosis;
    Fragment_diagnosis_detail diagnosis_detail;
    Fragment_hospital hospital;
    Fragment_hospital_detail hospital_detail;
    Fragment_Dictionary dictionary;
    Fragment_Dictionary_detail dictionary_detail;
    Fragment_Question question;
    Fragment_Question_detail question_detail;
    Fragment_Question_detail2 question_detail2;

    //서버통신
    public String getToken() {
        pre = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = pre.getString("TOKEN", null);
        return token;
    }
    public int getpetSerial() {
        pre2 = getSharedPreferences("Serial", MODE_PRIVATE);
        int petSerial = pre2.getInt("petSerial", 0);
        return petSerial;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_home_main);

        homeTutor = new HomeTutor();
        chatbotWeb = new ChatbotWeb();
        diagnosis = new Fragment_diagnosis();
        diagnosis_detail = new Fragment_diagnosis_detail();
        hospital = new Fragment_hospital();
        hospital_detail = new Fragment_hospital_detail();
        dictionary = new Fragment_Dictionary();
        dictionary_detail = new Fragment_Dictionary_detail();
        question = new Fragment_Question();
        question_detail = new Fragment_Question_detail();
        question_detail2 = new Fragment_Question_detail2();


        //툴바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(0);

        ImageButton settingBtn = (ImageButton) findViewById(R.id.setting_btn);
        ImageButton drawerBtn = (ImageButton) findViewById(R.id.toolbar_btn);
        TextView drawerLogo = findViewById(R.id.toolbar_logo);


        onChangeFragment(0);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        NavigationView Navigation = findViewById(R.id.drawer_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_chatbot:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,chatbotWeb).commit();
                        return true;
                    case R.id.tab_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, question).commit();
                        return true;

                    case R.id.tab_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, diagnosis).commit();
                        Toast.makeText(MainActivity.this, "챗봇 상담을 진행해주세요.", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.tab_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,hospital).commit();
                        return true;
                    case R.id.tab_5:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, dictionary).commit();
                        return true;
                }
                return false;
            }
        });

        // 툴바 드로어 버튼 눌렀을 때 동작
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

        //로고 클릭하면 홈화면으로 이동.
        drawerLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeTutor).commit();
            }
        });
        drawerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!drawer.isDrawerOpen(Gravity.LEFT)) {drawer.openDrawer(Gravity.LEFT); callpetName();}
                else drawer. closeDrawer(Gravity.LEFT);
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        Navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onOptionsItemSelected(item);
                Log.d("item", item.toString());
                return false;
            }
        });

    }

    public void onChangeFragment(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeTutor).commit();
        }else if(index ==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,chatbotWeb).commit();
        }else if(index ==2){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,question).commit();
        }else if(index ==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,diagnosis).commit();
        }else if(index ==4){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,hospital).commit();
        }else if(index ==5){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,dictionary).commit();
        }
        else if(index ==6){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,diagnosis_detail).commit();
        }else if(index ==7){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,hospital_detail).commit();
        }else if(index ==8){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,dictionary_detail).commit();
        }else if(index ==9){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,question_detail).commit();
        }else if(index ==10){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,question_detail2).commit();
        }
    }

    public void callpetName() {
        TextView petName = (TextView) findViewById(R.id.petName);

        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());
        service.getPetinfo(getpetSerial()).enqueue(new Callback<PetProfileResponse>() {
            @Override
            public void onResponse(Call<PetProfileResponse> call, Response<PetProfileResponse> response) {
                if (response.isSuccessful()) {
                    petdata = response.body().data;
                    petName.setText(petdata.getPetName());
                } else {Log.d("response 실패", "404");}
            }

            @Override
            public void onFailure(Call<PetProfileResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
                Log.d("~", t.toString());
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.petSelect:
                Intent intent2 = new Intent(getApplicationContext(), PetSelectActivity.class);
                startActivity(intent2);
                return true;
            case R.id.showHistory:
                Intent intent3 = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent3);
                return true;
        }
        return false;
    }
}
