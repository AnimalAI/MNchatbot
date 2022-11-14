package com.mnchatbot.myapplication.ui.questionNaire;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mnchatbot.myapplication.R;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceAPI;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceGenerator;
import com.mnchatbot.myapplication.ui.mainPage.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Question extends Fragment {

    MainActivity mainActivity;

    private RecyclerView mRecyclerView;
    private ArrayList<QuestionViewItem> mList;
    private QuestionAdapter mAdapter;
    private FloatingActionButton btnAdd;

    List<QnListResponse.QnDataList> Qndata;
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
    public int getmedicalSerial() {
        pre2 = getActivity().getSharedPreferences("Serial", MODE_PRIVATE);
        int medicalSerial = pre2.getInt("medicalSerial", 0);
        return medicalSerial;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_question,container,false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        btnAdd = rootview.findViewById(R.id.btnAdd);

        mList = new ArrayList<>();

        mAdapter = new QuestionAdapter(mList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        getQuestion();

        mAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mainActivity.onChangeFragment(10);
            }
        });
        mAdapter.setOnLongItemClickListener(new QuestionAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("문진표 삭제")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setPositiveButton("아니오", null)
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteQuestion(pos);
                            }
                        })
                        .create()
                        .show();
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
    public void addItem(int MedicalSerial, String QuestionName, String Date){
        QuestionViewItem item = new QuestionViewItem();
        item.setMedicalSerial(MedicalSerial);
        item.setQuestionName(QuestionName);
        item.setQuestionDate(Date);
        mList.add(item);
    }

    //문진표 목록
    public void getQuestion() {
        Qndata = new ArrayList<>();
        ServiceAPI QnListAPI = ServiceGenerator.createService(ServiceAPI.class, getToken());
        Call<QnListResponse> call = QnListAPI.getQnList(getpetSerial());
        call.enqueue(new Callback<QnListResponse>() {
            @Override
            public void onResponse(Call<QnListResponse> call, Response<QnListResponse> response) {
                if (!response.equals(200)) {
                    Qndata = response.body().data;
                    if (Qndata == null) {
                    } else {
                        for(int i=0; i< Qndata.size(); i++) {
                            int mSerial = Qndata.get(i).getMedicalFormSerial();
                            String Name = Qndata.get(i).getQnName();
                            String ID = Qndata.get(i).getQnDate();
                            addItem(mSerial, Name, ID);
                            mAdapter.notifyDataSetChanged();
                        }}

                } else if (!response.equals(404)) {Log.d("QnList", "404");
                }
            }

            @Override
            public void onFailure(Call<QnListResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });


    }

    //문진표 삭제
    private void DeleteQuestion(int pos) {
        ServiceAPI QnListAPI = ServiceGenerator.createService(ServiceAPI.class, getToken());
        Call<QnListResponse> call = QnListAPI.deleteQuestion(getmedicalSerial());

        call.enqueue(new Callback<QnListResponse>() {
            @Override
            public void onResponse(Call<QnListResponse> call, Response<QnListResponse> response) {
                if (!response.equals(200)) {
                    QuestionViewItem item = mList.get(pos);
                    mList.remove(item);
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<QnListResponse> call, Throwable t) {
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
