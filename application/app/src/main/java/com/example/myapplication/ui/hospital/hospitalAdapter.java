package com.example.myapplication.ui.hospital;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class hospitalAdapter extends RecyclerView.Adapter<hospitalAdapter.ViewHolder> {
    private Context context;
    private SharedPreferences preferences;

    public hospitalAdapter(ArrayList<hospitalViewItem> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView HospitalName, HospitalNumber, location, email, field, tv_field;
        Button btnHospital;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            HospitalName = itemView.findViewById(R.id.HospitalName);
            HospitalNumber = itemView.findViewById(R.id.HospitalNumber);
            location = itemView.findViewById(R.id.location);
            email = itemView.findViewById(R.id.email);
            field = itemView.findViewById(R.id.field);
            tv_field = itemView.findViewById(R.id.tv_field);

            btnHospital = itemView.findViewById(R.id.btnHospital);

            btnHospital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                            hospitalViewItem item = mList.get(position);
                            int hospSerial = item.getHospitalSerial();
                            Log.d("hospSerial", String.valueOf(hospSerial));

                            preferences = context.getSharedPreferences("Serial", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("hospSerial", hospSerial);
                            editor.commit();
                            Log.d("저장된 hospSerial", String.valueOf(preferences.getInt("hospSerial", 0)));
                        }
                    }
                }
            });
        }
    }

    //ArrayList
    private ArrayList<hospitalViewItem> mList = null;

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public hospitalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.f_hospital_item, parent, false);
        hospitalAdapter.ViewHolder viewHolder = new hospitalAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull hospitalAdapter.ViewHolder holder, int position) {
        hospitalViewItem item = mList.get(position);

        holder.HospitalName.setText(item.getHospitalName());
        holder.HospitalNumber.setText(item.getHospitalNumber());
        holder.location.setText(item.getlocation());
        holder.email.setText(item.getemail());
        holder.field.setText(item.getfield());
        holder.btnHospital.setVisibility(View.INVISIBLE);
        if (item.getHospitalType().equals("PARTNER")) {holder.btnHospital.setVisibility(View.VISIBLE);}
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // 리사이클러 뷰 클릭 이벤트를 위한 코드
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private hospitalAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(hospitalAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
