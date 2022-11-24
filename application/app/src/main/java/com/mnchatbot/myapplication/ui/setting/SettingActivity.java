/*
[설정 화면] 내 프로필, 반려동물 프로필을 선택할 수 있음.*/

package com.mnchatbot.myapplication.ui.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;

import com.mnchatbot.myapplication.R;

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

        SettingAdapter settingAdapter = new SettingAdapter(listSet);
        recyclerView.setAdapter(settingAdapter); //어댑터 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this, recyclerView.VERTICAL, false)); //LayoutManger 설정

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
    }
}