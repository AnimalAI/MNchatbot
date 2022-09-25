package com.example.myapplication.ui.diagnosis;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.Dictionary.DsResponse;
import com.example.myapplication.ui.ServiceSetting.ServiceAPI;
import com.example.myapplication.ui.ServiceSetting.ServiceGenerator;
import com.example.myapplication.ui.mainPage.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_diagnosis_detail extends Fragment {

    MainActivity mainActivity;
    private SharedPreferences preferences;
    private TextView dia_Date, dia_Time, dia_Name, dia_Species, dia_definition, dia_cause, dia_advice, more, more1, more2;

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_diagnosis_detail,container,false);
        dia_Name = rootview.findViewById(R.id.dia_Name);
        dia_Species = rootview.findViewById(R.id.dia_Species);
        dia_definition = rootview.findViewById(R.id.dia_definition);
        dia_cause = rootview.findViewById(R.id.dia_cause);
        dia_advice = rootview.findViewById(R.id.dia_advice);
        more = rootview.findViewById(R.id.more);
        more1 = rootview.findViewById(R.id.more1);
        more2 = rootview.findViewById(R.id.more2);

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
        /*preferences = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = preferences.getString("TOKEN", null);
        ServiceAPI DsSearchAPI = ServiceGenerator.createService(ServiceAPI.class, token);

        Call<DsResponse> call = DsSearchAPI.getDsSearchinfo(diseaseId);
        call.enqueue(new Callback<DsResponse>() {
            @Override
            public void onResponse(Call<DsResponse> call, Response<DsResponse> response) {
                Diadata = response.body().data;
                dia_Date.setText(Diadata.);
                dia_Time.setText(Diadata.);
                dia_Name.setText(Diadata.getdsName());
                dia_Species.setText(Diadata.getdsAmlBreed());
                dia_definition.setText(Diadata.getdsDefinition());
                isEllipsize(dia_definition, more);
                dia_cause.setText(Diadata.getdsCause());
                isEllipsize(dia_cause, more1);
                dia_advice.setText(Diadata.getdsAdvice());
                isEllipsize(dia_advice, more2);
            }

            @Override
            public void onFailure(Call<DsResponse> call, Throwable t) {

            }
        });*/

    }
}
