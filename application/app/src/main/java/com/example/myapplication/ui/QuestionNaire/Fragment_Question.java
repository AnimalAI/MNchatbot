package com.example.myapplication.ui.QuestionNaire;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.diagnosis.diagnosisAdapter;
import com.example.myapplication.ui.diagnosis.diagnosisViewItem;
import com.example.myapplication.ui.mainPage.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_Question extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<QuestionViewItem> mList;
    private QuestionAdapter mAdapter;
    private FloatingActionButton btnAdd;

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_question,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        btnAdd = rootview.findViewById(R.id.btnAdd);

        mList = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)
        addItem("구토증상보임", "2022.08.25");
        addItem("설사함", "2022.08.30");

        mAdapter = new QuestionAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(9);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onChangeFragment(9);
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
    public void addItem(String QuestionName, String Date){
        QuestionViewItem item = new QuestionViewItem();
        item.setQuestionName(QuestionName);
        item.setQuestionDate(Date);
        mList.add(item);
    }


}
