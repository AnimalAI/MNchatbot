package com.example.myapplication.ui.Dictionary;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;

public class Fragment_Dictionary extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<DictionaryViewItem> mList;
    private DictionaryAdapter mAdapter;
    private EditText editText;
    private Button btnDictionary;

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_dictionary,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        editText = rootview.findViewById(R.id.editText);
        btnDictionary = rootview.findViewById(R.id.btnDictionary);

        mList = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)
        addItem("심장 사상충", "2022.08.25");
        addItem("췌장염", "2022.08.30");
        addItem("ccc", "2022.08.30");

        mAdapter = new DictionaryAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DictionaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(6);
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
        DictionaryViewItem item = new DictionaryViewItem();
        item.setDiseaseName(DiseaseName);
        item.setDiseaseDate(Date);
        mList.add(item);
    }


}
