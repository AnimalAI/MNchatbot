/*
[예상진단 목록 화면] 등록된 예상진단 목록을 보여줌.*/

package com.mnchatbot.myapplication.ui.diagnosis;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mnchatbot.myapplication.R;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceAPI;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceGenerator;
import com.mnchatbot.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_diagnosis extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<DiagnosisViewItem> mList;
    private DiagnosisAdapter mAdapter;

    List<DiagListResponse.DiagList> DiagList;

    private SharedPreferences pre, pre2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }
    //서버통신
    public String getToken() {
        pre = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = pre.getString("TOKEN", null);
        return token;
    }
    public int getpetSerial() {
        pre2 = getActivity().getSharedPreferences("Serial", MODE_PRIVATE);
        int petSerial = pre2.getInt("petSerial", 0);
        return petSerial;
    }
    public int getdiagSerial() {
        pre2 = getActivity().getSharedPreferences("Serial", MODE_PRIVATE);
        int diagSerial = pre2.getInt("diagSerial", 0);
        return diagSerial;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_diagnosis,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        mList = new ArrayList<>();

        mAdapter = new DiagnosisAdapter(mList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setDiagList();

        mAdapter.setOnItemClickListener(new DiagnosisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(6);
            }
        });
        mAdapter.setOnLongItemClickListener(new DiagnosisAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("예상진단 삭제")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setPositiveButton("아니오", null)
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteDiag(pos);
                            }
                        })
                        .create()
                        .show();
            }
        });

        //최신 등록순으로 정렬
        LinearLayoutManager manger = new LinearLayoutManager(getActivity());
        manger.setReverseLayout(true);
        manger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(manger);
        return rootview;
    }
    // 리사이클러뷰에 데이터추가
    public void addItem(int DiagSerial, String DiseaseName, String Date){
        DiagnosisViewItem item = new DiagnosisViewItem();
        item.setdiagSerial(DiagSerial);
        item.setDiseaseName(DiseaseName);
        item.setDiseaseDate(Date);
        mList.add(item);
    }

    //예상진단 로드
    public void setDiagList() {
        DiagList = new ArrayList<>();
        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());

        service.getDiagList(getpetSerial()).enqueue(new Callback<DiagListResponse>() {
            @Override
            public void onResponse(Call<DiagListResponse> call, Response<DiagListResponse> response) {
                if(response.isSuccessful()) {
                    DiagList = response.body().data;
                    if(DiagList == null) {
                        Log.d("예상진단", "리스트 비어있음");
                    } else {
                        Log.d("예상진단", "성공");
                        for(int i=0; i< DiagList.size(); i++) {
                            int dSerial = DiagList.get(i).getDsSerial();
                            String Name = DiagList.get(i).getDsName();
                            String Date = DiagList.get(i).getDsDate();
                            addItem(dSerial, Name, Date);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {Log.d("response 실패", "404");}
            }

            @Override
            public void onFailure(Call<DiagListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

    //예상진단 삭제
    public void DeleteDiag(int pos) {
        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());

        service.deleteDiag(getdiagSerial()).enqueue(new Callback<DiagListResponse>() {
            @Override
            public void onResponse(Call<DiagListResponse> call, Response<DiagListResponse> response) {
                if (response.isSuccessful()) {
                    DiagnosisViewItem item = mList.get(pos);
                    mList.remove(item);
                    mAdapter.notifyDataSetChanged();
                } else {Log.d("response 실패", "404");}
            }
            @Override
            public void onFailure(Call<DiagListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

}