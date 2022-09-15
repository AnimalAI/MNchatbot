package com.example.myapplication.ui.hospital;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.ArrayAdapter.createFromResource;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.mainPage.MainActivity;
import com.example.myapplication.ui.petSelect.PetSelectActivity;
import com.example.myapplication.ui.petSelect.Response_DataList;
import com.example.myapplication.ui.petSelect.petListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_hospital extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<hospitalViewItem> mList;
    private hospitalAdapter mAdapter;
    private EditText editText;
    private Spinner City1, City2;
    private ArrayAdapter City1_Adapter, City2_Adapter;
    private Button btn_city;
    private String City1Name, City2Name;

    hospitalListResponse dataList;
    List<Response_hDataList> hospdata;
    private SharedPreferences preferences;

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
        btn_city = rootview.findViewById(R.id.btn_city);

        mList = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)
        addItem("모모병원", "02-111-2222", "서울람람", "1@naver.com", "개 순환기계질환 전문");
        addItem("김가네병원", "02-222-3333", "부산람람", "2@naver.com", null);
        addItem("동물사랑병원", "02-333-4444", "강릉람람", "3@naver.com", null);

        mAdapter = new hospitalAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new hospitalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(7);
            }
        });

        btn_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //스피너
        City1_Adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.city1, R.layout.row_spinner);
            City1_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            City1.setAdapter(City1_Adapter);


        City1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                City1Name = City1_Adapter.getItem(i).toString();
                setCity2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        City2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                City2Name = City2_Adapter.getItem(i).toString();
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
    public void addItem(String HospitalName, String HospitalNum, String location, String email, String field){
        hospitalViewItem item = new hospitalViewItem();
        item.setHospitalName(HospitalName);
        item.setHospitalNumber(HospitalNum);
        item.setlocation(location);
        item.setemail(email);
        item.setfield(field);
        mList.add(item);
    }
    //모든 연계병원 보기
    public void call_totalList(){
        hospdata = new ArrayList<>();
        preferences = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", null);
        ServiceAPI hospListAPI = ServiceGenerator.createService(ServiceAPI.class, token);

        Call<hospitalListResponse> call = hospListAPI.Allhosplist();

        call.enqueue(new Callback<hospitalListResponse>() {
            @Override
            public void onResponse(Call<hospitalListResponse> call, Response<hospitalListResponse> response) {
                if (!response.equals(200)) {
                    dataList = response.body();
                    hospdata = dataList.data;
                    for(int i=0; i< hospdata.size(); i++) {
                        String o = hospdata.get(i).getHospType();
                        String a = hospdata.get(i).getHospName();
                        String b = hospdata.get(i).getHospTel();
                        String c = hospdata.get(i).getHospAddress();
                        String d = hospdata.get(i).getHospEmail();
                        String e = hospdata.get(i).getHospField();
                        addItem(a, b, c, d, e);
                        mAdapter.notifyDataSetChanged();
                    }
                    Log.d("hospList", "200");
                } else if (!response.equals(404)) {Log.d("hospList", "404");
                }
            }

            @Override
            public void onFailure(Call<hospitalListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
    public void setCity2() {
        switch (City1Name) {
            case "강원도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.강원도, R.layout.row_spinner);
                break;
            case "경기도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.경기도, R.layout.row_spinner);
                break;
            case "경상남도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.경상남도, R.layout.row_spinner);
                break;
            case "경상북도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.경상북도, R.layout.row_spinner);
                break;
            case "광주광역시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.광주광역시, R.layout.row_spinner);
                break;
            case "대구광역시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.대구광역시, R.layout.row_spinner);
                break;
            case "대전광역시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.대전광역시, R.layout.row_spinner);
                break;
            case "부산광역시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.부산광역시, R.layout.row_spinner);
                break;
            case "서울특별시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.서울특별시, R.layout.row_spinner);
                break;
            case "세종특별자치시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.세종특별자치시, R.layout.row_spinner);
                break;
            case "울산광역시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.울산광역시, R.layout.row_spinner);
                break;
            case "인천광역시" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.인천광역시, R.layout.row_spinner);
                break;
            case "전라남도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.전라남도, R.layout.row_spinner);
                break;
            case "전라북도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.전라북도, R.layout.row_spinner);
                break;
            case "제주특별자치도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.제주특별자치도, R.layout.row_spinner);
                break;
            case "충청남도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.충청남도, R.layout.row_spinner);
                break;
            case "충청북도" :
                City2_Adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.충청북도, R.layout.row_spinner);
                break;
        }
        City2_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        City2.setAdapter(City2_Adapter);
    }


}

