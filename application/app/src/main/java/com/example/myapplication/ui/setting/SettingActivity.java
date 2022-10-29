package com.example.myapplication.ui.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;

import com.example.myapplication.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setting_page);

        //설정 목록 생성
        ArrayList<String> listSet = new ArrayList<>();
        listSet.add("내 프로필");
        listSet.add("반려동물 프로필");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageButton drawerBtn = (ImageButton) findViewById(R.id.toolbar_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager); //LayoutManger 설정

        SettingAdapter settingAdapter = new SettingAdapter(listSet);
        //==========[Click 이벤트]=========
        settingAdapter.setOnItemClickListener(new SettingAdapter.OnItemclickListener() {
            @Override
            public void onItemclicked(int position, String data) {
                if(position==0) {
                    // 내 프로필로 이동
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                } else if(position==1) {
                    // 반려동물 프로필
                    Intent intent = new Intent(getApplicationContext(), PetProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
        //====================================

        recyclerView.setAdapter(settingAdapter); //어댑터 설정
    }
}