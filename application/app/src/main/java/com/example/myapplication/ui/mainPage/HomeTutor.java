package com.example.myapplication.ui.mainPage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;

public class HomeTutor extends Fragment {

    MainActivity mainActivity;
    private ImageView Img_tutor;
    private Button btn_tutor;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.a_home_main2,container,false);

        Img_tutor = rootview.findViewById(R.id.Img_tutor);
        btn_tutor = rootview.findViewById(R.id.btn_tutor);

        btn_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Img_tutor.setImageResource(R.drawable.home_2);
            }
        });
        btn_tutor.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Img_tutor.setImageResource(R.drawable.home_1);
                return false;
            }
        });

        return rootview;
    }
}