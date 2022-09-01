package com.example.myapplication.ui.diagnosis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class diagnosisAdapter extends RecyclerView.Adapter<diagnosisAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView diseaseName, diseaseDate;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        diseaseName = itemView.findViewById(R.id.diseaseName);
        diseaseDate = itemView.findViewById(R.id.diseaseDate);

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
    private ArrayList<diagnosisViewItem> mList = null;

    public diagnosisAdapter(ArrayList<diagnosisViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public diagnosisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.f_diagnosis_item, parent, false);
        diagnosisAdapter.ViewHolder viewHolder = new diagnosisAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        diagnosisViewItem item = mList.get(position);

        holder.diseaseName.setText(item.getDiseaseName());
        holder.diseaseDate.setText(item.getDiseaseDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // 리사이클러 뷰 클릭 이벤트를 위한 코드
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private diagnosisAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(diagnosisAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private diagnosisAdapter.OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(diagnosisAdapter.OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

}
