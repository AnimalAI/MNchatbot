package com.example.myapplication.ui.history;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private SharedPreferences preferences;
    private Context context;
    private ArrayList<HistoryViewItem> mList;

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
                            HistoryViewItem item = mList.get(position);
                            int apptSerial = item.getapptSerial();

                            preferences = context.getSharedPreferences("Serial", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("apptSerial", apptSerial);
                            editor.commit();
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

    public HistoryAdapter(ArrayList<HistoryViewItem> mList, Context context) {
        this.mList = mList; this.context = context;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_history_item, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryViewItem item = mList.get(position);

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

    private HistoryAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(HistoryAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private HistoryAdapter.OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(HistoryAdapter.OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

}
