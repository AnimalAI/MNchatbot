package com.example.myapplication.ui.history;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.diagnosis.diagListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<historyViewItem> mList;
    private historyAdapter mAdapter;

    List<diagListResponse.DiagList> DiagList;
    private SharedPreferences pre, pre2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_history);

        mRecyclerView = findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)
        addItem("모모병원", "2022.10.05");
        addItem("따옴병원", "2022.10.05");


        mAdapter = new historyAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new historyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(getApplicationContext(), HistoryDetail.class);
                startActivity(intent);
            }
        });
        mAdapter.setOnLongItemClickListener(new historyAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("예상진단 삭제")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setPositiveButton("아니오", null)
                        .setNegativeButton("예", null)
                        .create()
                        .show();
            }
        });

        //최신 등록순으로 정렬
        LinearLayoutManager manger = new LinearLayoutManager(HistoryActivity.this);
        manger.setReverseLayout(true);
        manger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(manger);
        return;
    }

    // 리사이클러뷰에 데이터추가
    public void addItem(String HospitalName, String Date) {
        historyViewItem item = new historyViewItem();
        item.setHospitalName(HospitalName);
        item.setHospitalDate(Date);
        mList.add(item);
    }

    //상담내역 로드
    public void setHistoryList() {
        DiagList = new ArrayList<>();
        pre = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = pre.getString("TOKEN", null);
        pre2 = getSharedPreferences("Serial", MODE_PRIVATE);
        int petSerial = pre2.getInt("petSerial", 0);
        ServiceAPI DiagAPI = ServiceGenerator.createService(ServiceAPI.class, token);

        Call<diagListResponse> call = DiagAPI.getDiagList(petSerial);

        call.enqueue(new Callback<diagListResponse>() {
            @Override
            public void onResponse(Call<diagListResponse> call, Response<diagListResponse> response) {
                if(response.isSuccessful()) {
                    DiagList = response.body().data;
                    if(DiagList == null) {
                        Log.d("비어있음", "성공");
                    } else {
                        Log.d("자료있음", "성공");
                        for(int i=0; i< DiagList.size(); i++) {
                            int dSerial = DiagList.get(i).getDsSerial();
                            String Name = DiagList.get(i).getDsName();
                            String Date = DiagList.get(i).getDsDate();
                            addItem(Name, Date);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<diagListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}
