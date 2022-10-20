package com.example.myapplication.ui.QuestionNaire;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.Dictionary.DictionaryViewItem;
import com.example.myapplication.ui.diagnosis.diagnosisAdapter;
import com.example.myapplication.ui.diagnosis.diagnosisViewItem;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private SharedPreferences preferences;
    private Context context;
    private ArrayList<QuestionViewItem> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView QuestionName, QuestionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            QuestionName = itemView.findViewById(R.id.QuestionName);
            QuestionDate = itemView.findViewById(R.id.QuestionDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);

                            QuestionViewItem item = mList.get(position);
                            int medicalSerial = item.getMedicalSerial();
                            Log.d("mSerial", String.valueOf(medicalSerial));

                            preferences = context.getSharedPreferences("Serial", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("medicalSerial", medicalSerial);
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

                            QuestionViewItem item = mList.get(position);
                            int medicalSerial = item.getMedicalSerial();
                            Log.d("mSerial", String.valueOf(medicalSerial));

                            preferences = context.getSharedPreferences("Serial", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("medicalSerial", medicalSerial);
                            editor.commit();
                        }
                    }
                    return false;
                }
            });
        }
    }

    public QuestionAdapter(ArrayList<QuestionViewItem> mList, Context context) {
        this.mList = mList; this.context = context;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.f_question_item, parent, false);
        QuestionAdapter.ViewHolder viewHolder = new QuestionAdapter.ViewHolder(view);

        return viewHolder;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        QuestionViewItem item = mList.get(position);

        holder.QuestionName.setText(item.getQuestionName());
        holder.QuestionDate.setText(item.getQuestionDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // 리사이클러 뷰 클릭 이벤트를 위한 코드
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private QuestionAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(QuestionAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private QuestionAdapter.OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(QuestionAdapter.OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

}
