package com.example.myapplication.ui.petSelect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.setting.PetProfileActivity;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private SharedPreferences preferences;
    private ArrayList<RecyclerViewItem> mList;

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> mList, Context context) {
        this.mList = mList; this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_item;
        TextView petNickName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView_item = (ImageView) itemView.findViewById(R.id.imgView_item);
            petNickName = (TextView) itemView.findViewById(R.id.petName);

            imgView_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                            RecyclerViewItem item = mList.get(position);
                            int Serial = item.getPetSerial();
                            Log.d("Serial", String.valueOf(Serial));

                            //간단한 data의 경우 사용. 앱 폴더 내 파일로 저장되는 형태.
                            JSONArray a = new JSONArray();
                            preferences = context.getSharedPreferences("petSerial", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("Serial", Serial);
                            //펫 리스트(arraylist), string화 하여 넘기기
                            for (int i = 0; i < mList.size(); i++) {
                                a.put(mList.get(i));
                            }
                            if (!mList.isEmpty()) {
                                editor.putString("key", a.toString());
                            } else {
                                editor.putString("key", null);
                            }
                            editor.commit();
                            Log.d("!", String.valueOf(preferences.getLong("Serial", 0)));
                        }
                    }
                }
            });

            imgView_item.setOnLongClickListener(new View.OnLongClickListener() {
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

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.pet_activity_recycler_item, parent, false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        RecyclerViewItem item = mList.get(position);

        if(item.getImgName().equals("CAT")) // 아이템의 이미지 이름이 cat이면 고양이 사진으로 설정
            holder.imgView_item.setImageResource(R.drawable.cat);
        else
            holder.imgView_item.setImageResource(R.drawable.dog);

        holder.petNickName.setText(item.getMainText());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // 리사이클러 뷰 클릭 이벤트를 위한 코드
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

}