package com.example.myapplication.ui.Dictionary;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.hospital.hospitalListResponse;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Dictionary extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<DictionaryViewItem> mList;
    private DictionaryAdapter mAdapter;
    private EditText editText;
    private Button btnDictionary;
    private Context context;

    dsPageResponse.DsPageList dsPageList;
    List<dsPageResponse.DsPageListItem> dsPageItems;
    List<dsListResponse.DsDataList> DsSearchdata;

    private SharedPreferences preferences;

    private long totalCnt = 0;
    private boolean hasNext = false;
    private int page = 0;
    private int itemCnt = 10;

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_dictionary,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        editText = rootview.findViewById(R.id.editText);
        btnDictionary = rootview.findViewById(R.id.btnDictionary);

        mList = new ArrayList<>();
        // 리사이클러뷰에 데이터추가 (함수가 밑에 구현되어있음)

        loadDsinfo();
        ScrollListener();
        mAdapter = new DictionaryAdapter(mList, context);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new DictionaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(8);
            }
        });

        btnDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.clear();
                getDsinfo();
            }
        });
        //정렬
        LinearLayoutManager manger = new LinearLayoutManager(getActivity());
        manger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(manger);
        return rootview;
    }

    // 리사이클러뷰에 데이터추가
    public void addItem(String DiseaseName, String DiseaseId){
        DictionaryViewItem item = new DictionaryViewItem();
        item.setDiseaseName(DiseaseName);
        item.setDiseaseId(DiseaseId);
        mList.add(item);
    }
    //로드 화면
    public void loadDsinfo() {
        ServiceAPI LoadAPI = ServiceGenerator.createService(ServiceAPI.class, getToken());

        int item = itemCnt;
        Call<dsPageResponse> call = LoadAPI.callDsinfo(getPage(),item);

        call.enqueue(new Callback<dsPageResponse>() {
            @Override
            public void onResponse(Call<dsPageResponse> call, Response<dsPageResponse> response) {
                if(response.isSuccessful()) {
                    dsPageList = response.body().data;
                    totalCnt = dsPageList.gettotalCnt();
                    hasNext = dsPageList.getnextPage();
                    Log.d("dsPage", String.valueOf(hasNext));

                    if(hasNext != false) {
                        dsPageItems = dsPageList.dsPageList;
                        for(int i=0; i< dsPageItems.size(); i++) {
                            String Name = dsPageItems.get(i).getdiseaseName();
                            String ID = dsPageItems.get(i).getdiseaseId();
                            addItem(Name, ID);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<dsPageResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
    public void ScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (hasNextPage()) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                    if (lastVisibleItemPosition == itemTotalCount) {
                        loadDsinfo();
                        setNextPage(false);
                    }
                }

            }
        });
    }
    public void getDsinfo(){
        DsSearchdata = new ArrayList<>();
        ServiceAPI DsSearchListAPI = ServiceGenerator.createService(ServiceAPI.class, getToken());
        String dsName = editText.getText().toString();

        Call<dsListResponse> call = DsSearchListAPI.getDsinfo(dsName);

        call.enqueue(new Callback<dsListResponse>() {
            @Override
            public void onResponse(Call<dsListResponse> call, Response<dsListResponse> response) {
                if (!response.equals(200)) {
                    DsSearchdata = response.body().data;
                    if (DsSearchdata == null) {
                        Toast.makeText(getActivity(), "정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0; i< DsSearchdata.size(); i++) {
                            String Name = DsSearchdata.get(i).getdiseaseName();
                            String ID = DsSearchdata.get(i).getdiseaseId();
                            addItem(Name, ID);
                            mAdapter.notifyDataSetChanged();
                            setNextPage(false);
                        }}

                } else if (!response.equals(404)) {Log.d("DsSearchList", "404");
                }
            }

            @Override
            public void onFailure(Call<dsListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

    private int getPage() {
        page++;
        return page;
    }
    private Boolean hasNextPage() {
        return hasNext;
    }
    private void setNextPage(Boolean b) {
        hasNext = b;
    }
}
