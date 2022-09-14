package com.example.myapplication.ui.hospital;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.mainPage.MainActivity;

import java.util.Calendar;

public class Fragment_hospital_detail extends Fragment {

    MainActivity mainActivity;
    private EditText Name, Number, reason;
    private Spinner spn_quesionNaire;
    private ImageView datePicker, camera;
    private TextView Tdate, Ttime;
    private Button timePicker, send, backTolist;
    private RadioGroup radioGroup;

    DatePickerDialog datePickerDialog;
    Calendar calendar = Calendar.getInstance();

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

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_hospital_detail,container,false);
        Name = rootview.findViewById(R.id.h_Name);
        Number = rootview.findViewById(R.id.h_num);
        spn_quesionNaire = rootview.findViewById(R.id.spn_questionNaire);
        datePicker = rootview.findViewById(R.id.btn_DatePicker);
        timePicker = rootview.findViewById(R.id.btn_TimePicker);
        Tdate = rootview.findViewById(R.id.h_date);
        Ttime = rootview.findViewById(R.id.h_time);
        reason = rootview.findViewById(R.id.h_reson);
        camera = rootview.findViewById(R.id.btn_img);
        send = rootview.findViewById(R.id.btn_send);
        backTolist = rootview.findViewById(R.id.btn_backTolist);
        radioGroup =rootview.findViewById(R.id.h_radioGroup);


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        String date = year + "-" + month + "-" + day;
                        Tdate.setText(date);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked) {
                    case R.id.h_radioYes:
                        Toast.makeText(getActivity(), "예!!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.h_radioNo:
                        Toast.makeText(getActivity(), "아니오!!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.getText();
                Number.getText();
            }
        });
        backTolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onChangeFragment(5);
            }
        });





        return rootview;
    }

    public void showTimePicker() {
        int pHour = calendar.get(Calendar.HOUR);
        int pMinutes = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    calendar.set(Calendar.HOUR, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    Ttime.setText(String.format("%02d:%02d", hourOfDay, minute));
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, pHour, pMinutes, true);
        timePickerDialog.setTitle("희망 시간대");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}
