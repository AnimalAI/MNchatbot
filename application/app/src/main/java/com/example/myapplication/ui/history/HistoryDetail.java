package com.example.myapplication.ui.history;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class HistoryDetail extends AppCompatActivity {

    private SharedPreferences pre, pre2;

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
    public int getapptSerial() {
        pre2 = getSharedPreferences("Serial", MODE_PRIVATE);
        int apptSerial = pre2.getInt("apptSerial", 0);
        return apptSerial;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.a_history_detail);
        }
}
