package com.example.myapplication.ui.hospital;

import static android.widget.ArrayAdapter.createFromResource;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;

public class Fragment_hospital extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<hospitalViewItem> mList;
    private ArrayList<hospitalViewItem> search_list;
    private hospitalAdapter mAdapter, searchAdapter;
    private EditText editText;
    private Spinner City1, City2;
    private ArrayAdapter City1_Adapter, City2_Adapter;

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_hospital,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        editText = rootview.findViewById(R.id.editText);
        City1 = rootview.findViewById(R.id.spn_city1);
        City2 = rootview.findViewById(R.id.spn_city2);
        mList = new ArrayList<>();
        search_list = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)
        addItem("모모병원", "02-111-2222");
        addItem("김가네병원", "02-222-3333");
        addItem("동물사랑병원", "02-333-4444");

        mAdapter = new hospitalAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        searchAdapter = new hospitalAdapter(search_list);

        mAdapter.setOnItemClickListener(new hospitalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(6);
            }
        });

        //스피너
        City1_Adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.city1, android.R.layout.simple_spinner_item);
            City1_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            City1.setAdapter(City1_Adapter);

        City1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String a = City1_Adapter.getItem(i).toString();
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                Toast.makeText(getActivity(), "~"+City1_Adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.city2, android.R.layout.simple_spinner_item);
        City2_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        City2.setAdapter(City2_Adapter);

        City2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String a = City1_Adapter.getItem(i).toString();
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                Toast.makeText(getActivity(), "~"+City2_Adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //최신 등록순으로 정렬, rootview return이 있으므로 모든 메소드의 제일 끝에 위치해야 함.
        LinearLayoutManager manger = new LinearLayoutManager(getActivity());
        manger.setReverseLayout(true);
        manger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(manger);
        return rootview;
    }
    // 리사이클러뷰에 데이터추가
    public void addItem(String HospitalName, String HospitalNum){
        hospitalViewItem item = new hospitalViewItem();
        item.setHospitalName(HospitalName);
        item.setHospitalNumber(HospitalNum);
        mList.add(item);
    }


}

