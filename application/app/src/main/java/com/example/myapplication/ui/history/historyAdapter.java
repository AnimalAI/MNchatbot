package com.example.myapplication.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;


public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView HospitalName, HospitalDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            HospitalName = itemView.findViewById(R.id.HospitalName);
            HospitalDate = itemView.findViewById(R.id.HospitalDate);

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
    private ArrayList<historyViewItem> mList = null;

    public historyAdapter(ArrayList<historyViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public historyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_history_item, parent, false);
        historyAdapter.ViewHolder viewHolder = new historyAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull historyAdapter.ViewHolder holder, int position) {
        historyViewItem item = mList.get(position);

        holder.HospitalName.setText(item.getHospitalName());
        holder.HospitalDate.setText(item.getHospitalDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // 리사이클러 뷰 클릭 이벤트를 위한 코드
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private historyAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(historyAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private historyAdapter.OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(historyAdapter.OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

}
