package com.example.myapplication.ui.Dictionary;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.mainPage.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Dictionary_detail extends Fragment {

    MainActivity mainActivity;
    private SharedPreferences preferences, preferences2;
    private TextView ds_Name, ds_Species, ds_definition, ds_cause, ds_pathogenesis,
            ds_epidemiology, ds_symptom, ds_diagnosis, ds_treatment, ds_prevention,
            ds_prognosis, ds_advice, more, more1, more2, more3, more4, more5, more6, more7, more8, more9;

    DsResponse.DsData Dsdata;

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_dictionary_detail,container,false);
        ds_Name = rootview.findViewById(R.id.ds_Name);
        ds_Species = rootview.findViewById(R.id.ds_Species);
        ds_definition = rootview.findViewById(R.id.ds_definition);
        ds_cause = rootview.findViewById(R.id.ds_cause);
        ds_pathogenesis = rootview.findViewById(R.id.ds_pathogenesis);
        ds_epidemiology = rootview.findViewById(R.id.ds_epidemiology);
        ds_symptom = rootview.findViewById(R.id.ds_symptom);
        ds_diagnosis = rootview.findViewById(R.id.ds_diagnosis);
        ds_treatment = rootview.findViewById(R.id.ds_treatment);
        ds_prevention = rootview.findViewById(R.id.ds_prevention);
        ds_prognosis = rootview.findViewById(R.id.ds_prognosis);
        ds_advice = rootview.findViewById(R.id.ds_advice);
        more = rootview.findViewById(R.id.more);
        more1 = rootview.findViewById(R.id.more1);
        more2 = rootview.findViewById(R.id.more2);
        more3 = rootview.findViewById(R.id.more3);
        more4 = rootview.findViewById(R.id.more4);
        more5 = rootview.findViewById(R.id.more5);
        more6 = rootview.findViewById(R.id.more6);
        more7 = rootview.findViewById(R.id.more7);
        more8 = rootview.findViewById(R.id.more8);
        more9 = rootview.findViewById(R.id.more9);

        callDsSearchinfo();
        return rootview;
    }
    //더보기
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

    public void callDsSearchinfo() {
        preferences = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", null);
        ServiceAPI DsSearchAPI = ServiceGenerator.createService(ServiceAPI.class, token);

        preferences2 = getActivity().getSharedPreferences("dsId", MODE_PRIVATE);
        String diseaseId = preferences2.getString("dsId", null);

        Call<DsResponse> call = DsSearchAPI.getDsSearchinfo(diseaseId);
        call.enqueue(new Callback<DsResponse>() {
            @Override
            public void onResponse(Call<DsResponse> call, Response<DsResponse> response) {
                Dsdata = response.body().data;
                ds_Name.setText(Dsdata.getdsName());
                ds_Species.setText(Dsdata.getdsAmlBreed());
                ds_definition.setText(Dsdata.getdsDefinition());
                isEllipsize(ds_definition, more);
                ds_cause.setText(Dsdata.getdsCause());
                isEllipsize(ds_cause, more1);
                ds_pathogenesis.setText(Dsdata.getdsPathogenesis());
                isEllipsize(ds_pathogenesis, more2);
                ds_epidemiology.setText(Dsdata.getdsEpidemiology());
                isEllipsize(ds_epidemiology, more3);
                ds_symptom.setText(Dsdata.getdsSymptom());
                isEllipsize(ds_symptom, more4);
                ds_diagnosis.setText(Dsdata.getdsDiagnosis());
                isEllipsize(ds_diagnosis, more5);
                ds_treatment.setText(Dsdata.getdsTreatment());
                isEllipsize(ds_treatment, more6);
                ds_prevention.setText(Dsdata.getdsPrevention());
                isEllipsize(ds_prevention, more7);
                ds_prognosis.setText(Dsdata.getdsPrognosis());
                isEllipsize(ds_prognosis, more8);
                ds_advice.setText(Dsdata.getdsAdvice());
                isEllipsize(ds_advice, more9);
            }

            @Override
            public void onFailure(Call<DsResponse> call, Throwable t) {

            }
        });

    }
}