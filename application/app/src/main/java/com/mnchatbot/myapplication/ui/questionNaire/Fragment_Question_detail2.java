package com.mnchatbot.myapplication.ui.questionNaire;

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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mnchatbot.myapplication.R;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceAPI;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceGenerator;
import com.mnchatbot.myapplication.ui.mainPage.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Question_detail2 extends Fragment {
    MainActivity mainActivity;
    private EditText q_Name, q_reason, q_medichine, q_etc;
    private TextView q_Date, q_time;
    private Spinner spn_Disease;
    private ArrayAdapter Disease_Adapter;
    private String DiseaseName, etc;
    private RadioGroup q_radioGroup1, q_radioGroup2, q_radioGroup3;
    private boolean radio1, radio2, radio3;
    private Button btn_back, btn_save;

    QnResponse.QnData Qndata;
    private SharedPreferences pre, pre2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
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
    public int getmedicalSerial() {
        pre2 = getActivity().getSharedPreferences("Serial", MODE_PRIVATE);
        int medicalSerial = pre2.getInt("medicalSerial", 0);
        return medicalSerial;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_question_detail,container,false);
        q_Name = rootview.findViewById(R.id.q_Name);
        q_reason = rootview.findViewById(R.id.q_reson);
        q_medichine = rootview.findViewById(R.id.q_medichine);
        q_etc = rootview.findViewById(R.id.q_etc);
        q_Date = rootview.findViewById(R.id.q_Date);
        q_time = rootview.findViewById(R.id.q_time);
        spn_Disease = rootview.findViewById(R.id.spn_Disease);
        q_radioGroup1 = rootview.findViewById(R.id.q_radioGroup1);
        q_radioGroup2 = rootview.findViewById(R.id.q_radioGroup2);
        q_radioGroup3 = rootview.findViewById(R.id.q_radioGroup3);
        btn_back = rootview.findViewById(R.id.btn_back);
        btn_save = rootview.findViewById(R.id.btn_save);

        callQuestion();

        Disease_Adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.기저질환, R.layout.row_spinner);
        Disease_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Disease.setAdapter(Disease_Adapter);

        spn_Disease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DiseaseName = Disease_Adapter.getItem(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        q_radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q_radioYes1:
                        radio1 = true;
                        q_medichine.setVisibility(View.VISIBLE);
                        break;
                    case R.id.q_radioNo1:
                        radio1 = false;
                        q_medichine.setVisibility(View.GONE);
                        break;
                }
            }
        });

        q_radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q_radioYes2:
                        radio2 = true;
                        break;
                    case R.id.q_radioNo2:
                        radio2 = false;
                        break;
                }
            }
        });
        q_radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.q_radioYes3:
                        radio3 = true;
                        break;
                    case R.id.q_radioNo3:
                        radio3 = false;
                        break;
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePetPost();
                mainActivity.onChangeFragment(2);
                Toast.makeText(getActivity(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onChangeFragment(2);
            }
        });

        return rootview;
    }


    public void callQuestion() {
        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());
        service.getQuestion(getmedicalSerial()).enqueue(new Callback<QnResponse>() {
            @Override
            public void onResponse(Call<QnResponse> call, Response<QnResponse> response) {
                if (!response.equals(200)) {
                    Qndata = response.body().data;
                    q_Name.setText(Qndata.getQnName());
                    q_Date.setText(Qndata.getQnDate().substring(0, 10));
                    Log.d("날짜", Qndata.getQnDate());
                    q_time.setText(Qndata.getQnTime().substring(0, 5));
                    q_reason.setText(Qndata.getQnReason());
                    switch (Qndata.getQnDisease()) {
                        case "내분비질환":
                            spn_Disease.setSelection(0);
                            break;
                        case "피부질환":
                            spn_Disease.setSelection(1);
                            break;
                        case "근골격계질환":
                            spn_Disease.setSelection(2);
                            break;
                        case "순환기질환":
                            spn_Disease.setSelection(3);
                            break;
                        case "없음":
                            spn_Disease.setSelection(4);
                            break;
                    }
                    if (Qndata.getQnMedichine()) {
                        q_radioGroup1.check(R.id.q_radioYes1);
                        q_medichine.setVisibility(View.VISIBLE);
                        q_medichine.setText(Qndata.getQnMedichine2());
                    } else if (Qndata.getQnMedichine() == false){q_radioGroup1.check(R.id.q_radioNo1);}
                    if (Qndata.getQnSurgery()) {q_radioGroup2.check(R.id.q_radioYes2);}
                    else if (Qndata.getQnSurgery() == false) {q_radioGroup2.check(R.id.q_radioNo2);}
                    if (Qndata.getQnExercise()) {q_radioGroup3.check(R.id.q_radioYes3);}
                    else if (Qndata.getQnExercise() == false) {q_radioGroup3.check(R.id.q_radioNo3);}

                    q_etc.setText(Qndata.getQnEtc());


                } else if (!response.equals(404)) {
                    Log.d("QnData", "404");
                }
            }

            @Override
            public void onFailure(Call<QnResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });

    }

    //문진표 수정
    public void updatePetPost(){
        String Qname = q_Name.getText().toString();
        String Qreason = q_reason.getText().toString();
        String Disease = DiseaseName;
        boolean Radio1 = radio1;
        boolean Radio2 = radio2;
        boolean Radio3 = radio3;
        String Qmedichine = q_medichine.getText().toString();
        String Qetc = null; etc = q_etc.getText().toString();
        if (etc.getBytes().length<=0) {Qetc = "특이사항 없음";}
        else if (etc.getBytes().length>0){Qetc = q_etc.getText().toString(); }

        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());
        QuestionEdit questionNaire = new QuestionEdit(getmedicalSerial(), Qname, Qreason, Disease, Radio1, Qmedichine, Radio2, Radio3, Qetc);

        service.EditQuestion(questionNaire).enqueue(new Callback<QnResponse>() {
            @Override
            public void onResponse(Call<QnResponse> call, Response<QnResponse> response) {
                if (!response.equals(200)) {
                }
            }

            @Override
            public void onFailure(Call<QnResponse> call, Throwable t) {
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
