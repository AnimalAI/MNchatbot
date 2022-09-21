package com.example.myapplication.ui.QuestionNaire;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
    private Spinner spn_questionNaire;
    private RadioGroup q_radioGroup1, q_radioGroup2, q_radioGroup3;
    private RadioButton q_radioYes1, q_radioNo1, q_radioYes2, q_radioNo2, q_radioYes3, q_radioNo3;
    private Button btn_back, btn_save;

    private SharedPreferences preferences;

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
        spn_questionNaire = rootview.findViewById(R.id.spn_questionNaire);
        q_radioGroup1 = rootview.findViewById(R.id.q_radioGroup1);
        q_radioGroup2 = rootview.findViewById(R.id.q_radioGroup2);
        q_radioGroup3 = rootview.findViewById(R.id.q_radioGroup3);
        q_radioYes1 = rootview.findViewById(R.id.q_radioYes1);
        q_radioYes2 = rootview.findViewById(R.id.q_radioYes2);
        q_radioYes3 = rootview.findViewById(R.id.q_radioYes3);
        q_radioNo1 = rootview.findViewById(R.id.q_radioNo1);
        q_radioNo2 = rootview.findViewById(R.id.q_radioNo2);
        q_radioNo3 = rootview.findViewById(R.id.q_radioNo3);
        btn_back = rootview.findViewById(R.id.btn_back);
        btn_save = rootview.findViewById(R.id.btn_save);



        DateTime date = new DateTime();
        q_Date.setText(date.getDate());
        q_time.setText(date.getTime());

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
            }
        });

        return rootview;
    }

    public class DateTime {
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        public String getDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
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
        preferences = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", null);
        ServiceAPI QuestionAPI = ServiceGenerator.createService(ServiceAPI.class, token);

        Question questionNaire = new Question(null, null, null, null, null, null);

        Call<QnResponse> call = QuestionAPI.setQuestion(questionNaire);
        call.enqueue(new Callback<QnResponse>() {
            @Override
            public void onResponse(Call<QnResponse> call, Response<QnResponse> response) {
            }

            @Override
            public void onFailure(Call<QnResponse> call, Throwable t) {

            }
        });

    }

}
