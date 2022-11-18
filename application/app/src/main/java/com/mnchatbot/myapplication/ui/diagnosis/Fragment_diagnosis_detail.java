package com.mnchatbot.myapplication.ui.diagnosis;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mnchatbot.myapplication.R;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceAPI;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceGenerator;
import com.mnchatbot.myapplication.ui.mainPage.MainActivity;

import java.sql.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_diagnosis_detail extends Fragment {

    MainActivity mainActivity;

    private SharedPreferences pre, pre2;
    private TextView dia_Date, dia_Time, dia_Name, dia_Breed, dia_definition, dia_cause, dia_advice, more, more1, more2;

    DiagResponse.DiagData DiagData;
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
    public int getdiagSerial() {
        pre2 = getActivity().getSharedPreferences("Serial", MODE_PRIVATE);
        int diagSerial = pre2.getInt("diagSerial", 0);
        return diagSerial;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_diagnosis_detail,container,false);

        dia_Date = rootview.findViewById(R.id.dia_Date);
        dia_Time = rootview.findViewById(R.id.dia_time);
        dia_Name = rootview.findViewById(R.id.dia_Name);
        dia_Breed = rootview.findViewById(R.id.dia_Breed);
        dia_definition = rootview.findViewById(R.id.dia_definition);
        dia_cause = rootview.findViewById(R.id.dia_cause);
        dia_advice = rootview.findViewById(R.id.dia_advice);
        more = rootview.findViewById(R.id.more);
        more1 = rootview.findViewById(R.id.more1);
        more2 = rootview.findViewById(R.id.more2);

        call_Diagnosis();
        return rootview;
    }

    public static boolean isEllipsize(TextView textView, TextView moreTextView){
        boolean result = false;
        Layout layout = textView.getLayout();
        if(layout != null) {
            int lines = layout.getLineCount();
            if(lines > 0) {
                int ellipsisCount = layout.getEllipsisCount(lines-1);
                if ( ellipsisCount > 0) {
                    moreTextView.setVisibility(View.VISIBLE);
                    moreTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            textView.setMaxLines(1000);
                            moreTextView.setVisibility(View.GONE);
                        }
                    });
                }else{
                    result = false;
                }
            }
        } return result;
    }

    public void call_Diagnosis() {
        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());
        service.getDiag(getdiagSerial()).enqueue(new Callback<DiagResponse>() {
            @Override
            public void onResponse(Call<DiagResponse> call, Response<DiagResponse> response) {
                if(!response.equals(200)) {
                    DiagData = response.body().data;
                    if (DiagData == null) {
                        Toast.makeText(getActivity(), "챗봇 상담을 먼저 진행하세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        dia_Date.setText(DiagData.getdiagDate());
                        dia_Time.setText(DiagData.getdiagTime());
                        dia_Name.setText(DiagData.getdsName());
                        dia_Breed.setText(DiagData.getdsBreed());
                        dia_definition.setText(DiagData.getdsDefinition());
                        isEllipsize(dia_definition, more);
                        dia_cause.setText(DiagData.getdsCause());
                        isEllipsize(dia_cause, more1);
                        dia_advice.setText(DiagData.getdsAdvice());
                        isEllipsize(dia_advice, more2);
                    }
                }
            }

            @Override
            public void onFailure(Call<DiagResponse> call, Throwable t) {
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
