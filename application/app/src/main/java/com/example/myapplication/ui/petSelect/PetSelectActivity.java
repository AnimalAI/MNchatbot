package com.example.myapplication.ui.petSelect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetSelectActivity extends AppCompatActivity {

    private Context context;

    private RecyclerView mRecyclerView;
    private ArrayList<RecyclerViewItem> mList;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    petListResponse dataList;
    List<Response_DataList> petdata;

    //서버 통신
    private String TOKEN = getToken();
    private ServiceAPI petListAPI = ServiceGenerator.createService(ServiceAPI.class, TOKEN);
    public String getToken() { return TOKEN; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mList = new ArrayList<>();

        //서버에 등록된 반려동물 목록 불러오기
        callPetList();

        mRecyclerViewAdapter = new RecyclerViewAdapter(mList, context);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        // 리사이클러뷰 안의 아이템(반려동물 아이콘) 클릭시 메인화면으로 이동
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "안녕!" + mList.get(pos).getMainText(), Toast.LENGTH_SHORT).show();
            }
        });

        // 반려동물 추가 버튼 클릭
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AddPetActivity.class);
                startActivityForResult(intent, 101);
            }

        });

    }
    //사용자가 작성한 반려동물 정보 불러오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {
            if (intent !=null) {
                String petName = intent.getStringExtra("petName");
                String CATDOG = intent.getStringExtra("CATDOG");
                addItem(petName, CATDOG, null);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    // 리사이클러뷰에 데이터추가
    public void addItem(String mainText, String imgName, Long petSerial){
        RecyclerViewItem item = new RecyclerViewItem();
        item.setImgName(imgName);
        item.setMainText(mainText);
        item.setPetSerial(petSerial);
        mList.add(item);
    }

    //등록한 반려동물 목록 보기
    public void callPetList(){
        petdata = new ArrayList<>();
        Call<petListResponse> call = petListAPI.setPetlist();

        call.enqueue(new Callback<petListResponse>() {
            @Override
            public void onResponse(Call<petListResponse> call, Response<petListResponse> response) {
                if (!response.equals(200)) {
                    dataList = response.body();
                    petdata = dataList.data;
                    for(int i=0; i< petdata.size(); i++) {
                        String a = petdata.get(i).getPetName();
                        String b = petdata.get(i).getpetSpecies();
                        Long c = petdata.get(i).getpetSerial();
                        addItem(a, b, c);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<petListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PetSelectActivity.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}