package com.mnchatbot.myapplication.ui.history;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mnchatbot.myapplication.R;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceAPI;
import com.mnchatbot.myapplication.ui.serviceSetting.ServiceGenerator;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetail extends AppCompatActivity {

    private SharedPreferences pre, pre2;
    private HistoryResponse.HistoryData HistoryObject;
    private TextView date, time, memberName, memberTell, petName, petAge, petSpecies, petBreed,
            petGender, neutralization, underDisease, specialNote, medication, surgery,
            exercise, etc, reason, bill;
    private ImageView Image;

    //서버통신
    public String getToken() {
        pre = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = pre.getString("TOKEN", null);
        return token;
    }

    public int getapptSerial() {
        pre2 = getSharedPreferences("Serial", MODE_PRIVATE);
        int apptSerial = pre2.getInt("apptSerial", 0);
        return apptSerial;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_history_detail);

        date = findViewById(R.id.history_Date);
        time = findViewById(R.id.history_time);
        memberName = findViewById(R.id.history_Name);
        memberTell = findViewById(R.id.history_phone);
        petName = findViewById(R.id.history_petName);
        petAge = findViewById(R.id.history_petAge);
        petSpecies = findViewById(R.id.history_petSpecies);
        petBreed = findViewById(R.id.history_Breed);
        petGender = findViewById(R.id.history_petGender);
        neutralization = findViewById(R.id.history_Neutralization);
        underDisease = findViewById(R.id.history_underDisease);
        specialNote = findViewById(R.id.history_SpecialNote);
        medication = findViewById(R.id.history_medication);
        surgery = findViewById(R.id.history_SurgeryOrAne);
        exercise = findViewById(R.id.history_HyperExercise);
        etc = findViewById(R.id.history_etc);
        reason = findViewById(R.id.history_Reason);
        bill = findViewById(R.id.history_bill);
        Image = findViewById(R.id.history_Image);

        setHistoryItem();
    }
    public void setHistoryItem() {
        ServiceAPI service = ServiceGenerator.createService(ServiceAPI.class, getToken());

        service.getHistoryObject(getapptSerial()).enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful()) {
                    HistoryObject = response.body().data;
                    if(HistoryObject == null) {
                        Log.d("비어있음", "성공");
                    } else {
                        Log.d("자료있음", "성공");
                        date.setText(HistoryObject.getHistoryDate());
                        time.setText(HistoryObject.getHistoryTime());
                        memberName.setText(HistoryObject.getMemberName());
                        memberTell.setText(HistoryObject.getMemberTell());
                        petName.setText(HistoryObject.getpetName());
                        petAge.setText(String.valueOf(HistoryObject.getpetAge()));
                        petSpecies.setText(HistoryObject.getpetSpecies());
                        petBreed.setText(HistoryObject.getpetBreed());
                        petGender.setText(HistoryObject.getpetGender());
                        neutralization.setText(HistoryObject.getNeutralization());
                        underDisease.setText(HistoryObject.getunderDisease());
                        specialNote.setText(HistoryObject.getSpecialNoteMedication());
                        medication.setText(HistoryObject.getmedication());
                        surgery.setText(HistoryObject.getSurgeryOrAne());
                        exercise.setText(HistoryObject.getHyperExercise());
                        etc.setText(HistoryObject.getEtc());
                        reason.setText(HistoryObject.getapptReason());
                        bill.setText(HistoryObject.getBill());
                        URL url = HistoryObject.getapptImage();
                        Glide.with(HistoryDetail.this).load(url)
                                .error(android.R.drawable.btn_dialog)
                                .into(Image);
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryDetail.this);
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}
