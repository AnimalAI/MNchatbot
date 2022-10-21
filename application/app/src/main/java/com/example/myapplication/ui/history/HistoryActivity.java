package com.example.myapplication.ui.history;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.diagnosis.diagnosisViewItem;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<historyViewItem> mList;
    private historyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_history);

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
                mainActivity.onChangeFragment(6);
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
}
