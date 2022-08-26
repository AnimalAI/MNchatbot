package com.example.myapplication.ui.diagnosis;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;

public class Fragment_diagnosis extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<diagnosisViewItem> mList;
    private diagnosisAdapter mAdapter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_diagnosis,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)
        addItem("고관절 이형성증", "2022.08.25");
        addItem("췌장염", "2022.08.25");

        mAdapter = new diagnosisAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new diagnosisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(1);
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
    public void addItem(String DiseaseName, String Date){
        diagnosisViewItem item = new diagnosisViewItem();
        item.setDiseaseName(DiseaseName);
        item.setDiseaseDate(Date);
        mList.add(item);
    }


}