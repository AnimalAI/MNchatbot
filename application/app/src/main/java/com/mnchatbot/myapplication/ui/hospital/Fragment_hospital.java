package com.mnchatbot.myapplication.ui.hospital;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mnchatbot.myapplication.R;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceAPI;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceGenerator;
import com.mnchatbot.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_hospital extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<HospitalViewItem> mList;
    private HospitalAdapter mAdapter;
    private Spinner City1, City2;
    private ArrayAdapter City1_Adapter, City2_Adapter;
    private Button btn_city;
    private String City1Name, City2Name, type;

    HospitalListResponse dataList;
    List<HospitalListResponse.HospDataList> hospdata;
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
    //서버통신
    public String getToken() {
        preferences = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", null);
        return token;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_hospital,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        City1 = rootview.findViewById(R.id.spn_city1);
        City2 = rootview.findViewById(R.id.spn_city2);
        btn_city = rootview.findViewById(R.id.btn_city);

        mList = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)

        mAdapter = new HospitalAdapter(mList, getActivity());
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new HospitalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getActivity(), "상담신청 가능한 연계병원이 아직 없습니다.", Toast.LENGTH_SHORT).show();
                //mainActivity.onChangeFragment(7);
            }
        });

        btn_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { call_totalList(); }
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
    public void addItem(int HospSerial, String HospType, String HospitalName, String HospitalNum, String location, String email, String field){
        HospitalViewItem item = new HospitalViewItem();
        item.setHospitalSerial(HospSerial);
        item.setHospitalType(HospType);
        item.setHospitalName(HospitalName);
        item.setHospitalNumber(HospitalNum);
        item.setlocation(location);
        item.setemail(email);
        item.setfield(field);
        mList.add(item);
    }
    //모든 동물병원 보기
    public void call_totalList(){
        String region = City1Name;
        String city = City2Name;
        hospdata = new ArrayList<>();

        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());
        service.Allhosplist(region, city).enqueue(new Callback<HospitalListResponse>() {
            @Override
            public void onResponse(Call<HospitalListResponse> call, Response<HospitalListResponse> response) {
                if (response.isSuccessful()) {
                    dataList = response.body();
                    hospdata = dataList.data;
                    if (mList.size() == 0){
                        callCities();
                    } else if (mList.size() > 0) {
                        for (int q = 0; q<= mList.size(); q++) {
                            mList.clear();
                            mAdapter.notifyDataSetChanged();
                            callCities();
                        }
                    }
                    Log.d("hospList", "200");
                } else {Log.d("response 실패", "404");}
            }

            @Override
            public void onFailure(Call<HospitalListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
    //조건 필터에 맞는 도시 불러오는 반복문
    public void callCities() {
        for(int i=0; i< hospdata.size(); i++) {
            int hospSerial = hospdata.get(i).getHospSerial();
            type = hospdata.get(i).getHospType();
            String a = hospdata.get(i).getHospName();
            String b = hospdata.get(i).getHospTel();
            String c = hospdata.get(i).getHospAddress();
            String d = hospdata.get(i).getHospEmail();
            String e = hospdata.get(i).getHospField();
            addItem(hospSerial, type, a, b, c, d, e);
            mAdapter.notifyDataSetChanged();
            Log.d("hospList3", "200");
        }
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

