package com.example.myapplication.ui.mainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.ui.login.LoginActivity;
import com.example.myapplication.ui.petSelect.PetSelectActivity;
import com.example.myapplication.ui.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    ManuFragment manuFragment;
    ChatbotWeb chatbotWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank);

        mainFragment = new MainFragment();
        manuFragment = new ManuFragment();
        chatbotWeb = new ChatbotWeb();

        //툴바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 버튼 선언
        Button loginPage = (Button) findViewById(R.id.drawer_button_login);
        Button petPage = (Button) findViewById(R.id.drawer_button_pet);
        Button homePage = (Button) findViewById(R.id.drawer_button_home);
        ImageButton settingBtn = (ImageButton) findViewById(R.id.setting_btn);
        ImageButton drawerBtn = (ImageButton) findViewById(R.id.toolbar_btn);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_chatbot:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,chatbotWeb).commit();
                        return true;
                    case R.id.tab_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();
                        return true;

                    case R.id.tab_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();
                        return true;
                    case R.id.tab_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();
                        return true;
                    case R.id.tab_5:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();
                        return true;
                }
                return false;
            }
        });

        // 툴바 드로어 버튼 눌렀을 때 동작
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
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
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
    }

    public void onChangeFragment(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();
        }else if(index ==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,manuFragment).commit();
        }
    }
}
