package com.example.myapplication.ui.hospital;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class hospitalAdapter extends RecyclerView.Adapter<hospitalAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView HospitalName, HospitalNumber, location, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            HospitalName = itemView.findViewById(R.id.HospitalName);
            HospitalNumber = itemView.findViewById(R.id.HospitalNumber);
            location = itemView.findViewById(R.id.location);
            time = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onLongItemClickListener != null) {
                            onLongItemClickListener.onLongItemClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    //ArrayList
    private ArrayList<hospitalViewItem> mList = null;

    public hospitalAdapter(ArrayList<hospitalViewItem> mList) {
        this.mList = mList;
    }

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


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private hospitalAdapter.OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(hospitalAdapter.OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

}
