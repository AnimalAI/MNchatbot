package com.mnchatbot.myapplication.ui.setting;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.mnchatbot.myapplication.R;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> listSet;

    //onItemClickListener 인터페이스 선언
    public interface OnItemclickListener {
        void onItemclicked(int position, String data);
    }
    //OnItemClickListener 참조변수 선언
    private OnItemclickListener itemclickListener;

    //OnItemclickListener 전달 메소드
    public void setOnItemClickListener (OnItemclickListener listener) {
        itemclickListener = listener;
    }

    //뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);

        }
        public TextView getTextView() { return textView; }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
    //생성자, 데이터 전달받게 하기 위함.
    public SettingAdapter(ArrayList<String> list) {
        listSet = list;
    }

    @NonNull
    @Override //ViewHolder 객체 생성, 리턴.
    public SettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_setting_item, parent, false);
        SettingAdapter.ViewHolder viewHolder = new SettingAdapter.ViewHolder(view);

        //=================[Click 이벤트]=============
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                int postiotion = viewHolder.getAdapterPosition();
                if (postiotion != RecyclerView.NO_POSITION) {
                    data = viewHolder.getTextView().getText().toString();
                }
                itemclickListener.onItemclicked(postiotion, data);
            }
        });

        return viewHolder;
    }

    @Override //ViewHolder 내용, postion에 해당하는 데이터로 교체.
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = listSet.get(position);
        holder.textView.setText(text);
    }

    @Override //전체 데이터 갯수 리턴
    public int getItemCount() {
        return listSet.size();
    }


}
