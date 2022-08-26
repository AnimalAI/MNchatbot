package com.example.myapplication.ui.QuestionNaire;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.diagnosis.diagnosisAdapter;
import com.example.myapplication.ui.diagnosis.diagnosisViewItem;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;

public class Fragment_Question extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<QuestionViewItem> mList;
    private ArrayList<QuestionViewItem> search_list;
    private QuestionAdapter mAdapter, searchAdapter;
    private EditText editText;

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_question,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        editText = rootview.findViewById(R.id.editText);
        mList = new ArrayList<>();
        search_list = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)
        addItem("심장 사상충", "2022.08.25");
        addItem("췌장염", "2022.08.30");
        addItem("ccc", "2022.08.30");

        mAdapter = new QuestionAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        searchAdapter = new QuestionAdapter(search_list);

        mAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(1);
            }
        });

        // editText 리스터 작성
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if (searchText.equals("")) {
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < mList.size(); a++) {
                        if (mList.get(a).getDiseaseName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(mList.get(a));
                        }
                        mRecyclerView.setAdapter(searchAdapter);
                    }
                }
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
        QuestionViewItem item = new QuestionViewItem();
        item.setDiseaseName(DiseaseName);
        item.setDiseaseDate(Date);
        mList.add(item);
    }


}
