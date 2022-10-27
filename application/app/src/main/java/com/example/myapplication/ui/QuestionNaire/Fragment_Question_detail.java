package com.example.myapplication.ui.QuestionNaire;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.Dictionary.DsResponse;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.mainPage.MainActivity;
import com.example.myapplication.ui.setting.PetinfoData;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Question_detail extends Fragment {

    MainActivity mainActivity;
    private EditText q_Name, q_reason, q_medichine, q_etc;
    private TextView q_Date, q_time;
    private Spinner spn_Disease;
    private ArrayAdapter Disease_Adapter;
    private String DiseaseName, etc;
    private RadioGroup q_radioGroup1, q_radioGroup2, q_radioGroup3;
    private boolean radio1, radio2, radio3;
    private Button btn_back, btn_save;
    DateTime date;

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
    public int getpetSerial() {
        pre2 = getActivity().getSharedPreferences("Serial", MODE_PRIVATE);
        int petSerial = pre2.getInt("petSerial", 0);
        return petSerial;
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


        date = new DateTime();
        q_Date.setText(date.getDate());
        q_time.setText(date.getTime());

        Disease_Adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.기저질환, R.layout.row_spinner);
        Disease_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Disease.setAdapter(Disease_Adapter);
        spn_Disease.setSelection(4);

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
                addQuestion();
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

    public class DateTime {
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        public String getDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String getDate = sdf.format(date);
            return getDate;
        }

        public String getTime() {
            SimpleDateFormat sdT= new SimpleDateFormat("hh:mm");
            String getTime = sdT.format(date);
            return getTime;
        }
    }

    public void addQuestion() {
        String Qname = q_Name.getText().toString();
        String Date = date.getDate();
        String Time = date.getTime();
        String Qreason = q_reason.getText().toString();
        String Disease = DiseaseName;
        boolean Radio1 = radio1;
        boolean Radio2 = radio2;
        boolean Radio3 = radio3;
        String Qmedichine = q_medichine.getText().toString();
        String Qetc = null; etc = q_etc.getText().toString();
        if (etc.getBytes().length<=0) {Qetc = "특이사항 없음";}
        else if (etc.getBytes().length>0){Qetc = q_etc.getText().toString(); }

        ServiceAPI QuestionAPI = ServiceGenerator.createService(ServiceAPI.class, getToken());

        Question questionNaire = new Question(getpetSerial(), Qname, Date, Time, Qreason, Disease, Radio1, Qmedichine, Radio2, Radio3, Qetc);

        Call<QnResponse> call = QuestionAPI.setQuestion(questionNaire);
        call.enqueue(new Callback<QnResponse>() {
            @Override
            public void onResponse(Call<QnResponse> call, Response<QnResponse> response) {
                Toast.makeText(getActivity(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                mainActivity.onChangeFragment(2);
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
