package com.example.myapplication.ui.mainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.ui.Dictionary.Fragment_Dictionary;
import com.example.myapplication.ui.Dictionary.Fragment_Dictionary_detail;
import com.example.myapplication.ui.diagnosis.Fragment_diagnosis;
import com.example.myapplication.ui.diagnosis.Fragment_diagnosis_detail;
import com.example.myapplication.ui.hospital.Fragment_hospital;
import com.example.myapplication.ui.hospital.Fragment_hospital_detail;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.ui.petSelect.PetSelectActivity;
import com.example.myapplication.ui.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    HomeTutor homeTutor;
    ManuFragment manuFragment;

    ChatbotWeb chatbotWeb;
    Fragment_diagnosis diagnosis;
    Fragment_diagnosis_detail diagnosis_detail;
    Fragment_hospital hospital;
    Fragment_hospital_detail hospital_detail;
    Fragment_Dictionary dictionary;
    Fragment_Dictionary_detail dictionary_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        homeTutor = new HomeTutor();
        manuFragment = new ManuFragment();
        chatbotWeb = new ChatbotWeb();
        diagnosis = new Fragment_diagnosis();
        diagnosis_detail = new Fragment_diagnosis_detail();
        hospital = new Fragment_hospital();
        hospital_detail = new Fragment_hospital_detail();
        dictionary = new Fragment_Dictionary();
        dictionary_detail = new Fragment_Dictionary_detail();

        //툴바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 버튼 선언
        Button loginPage = (Button) findViewById(R.id.drawer_button_login);
        Button petPage = (Button) findViewById(R.id.drawer_button_pet);
        ImageButton settingBtn = (ImageButton) findViewById(R.id.setting_btn);
        ImageButton drawerBtn = (ImageButton) findViewById(R.id.toolbar_btn);
        TextView drawerLogo = findViewById(R.id.toolbar_logo);

        onChangeFragment(0);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_chatbot:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,chatbotWeb).commit();
                        return true;
                    case R.id.tab_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeTutor).commit();
                        return true;

                    case R.id.tab_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, diagnosis).commit();
                        return true;
                    case R.id.tab_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,hospital).commit();
                        return true;
                    case R.id.tab_5:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,dictionary).commit();
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
                if(!drawer.isDrawerOpen(Gravity.LEFT)) drawer.openDrawer(Gravity.LEFT);
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

        // (상세) 드로어 버튼 클릭 시의 화면 이동 구현
        loginPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        petPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PetSelectActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onChangeFragment(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeTutor).commit();
        }else if(index ==5){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,hospital).commit();
        }else if(index ==6){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,diagnosis_detail).commit();
        }
        else if(index ==7){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,hospital_detail).commit();
        }
    }
}
