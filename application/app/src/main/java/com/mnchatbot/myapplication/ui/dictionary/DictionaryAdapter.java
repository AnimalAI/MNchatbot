package com.mnchatbot.myapplication.ui.dictionary;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnchatbot.myapplication.R;

import java.util.ArrayList;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {

    private SharedPreferences preferences;
    private Context context;
    private ArrayList<DictionaryViewItem> mList;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public DictionaryAdapter(ArrayList<DictionaryViewItem> mList, Context context) {
        this.mList = mList; this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView diseaseName;
    ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            diseaseName = itemView.findViewById(R.id.diseaseName);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                            DictionaryViewItem item = mList.get(position);
                            String dsId = item.getDiseaseId();
                            Log.d("dsId", item.getDiseaseId());

                            preferences = context.getSharedPreferences("dsId", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("dsId", dsId);
                            editor.commit();
                        }
                    }
                }
            });
        }
    }


    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public DictionaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.f_dictionary_item, parent, false);
            DictionaryAdapter.ViewHolder viewHolder = new DictionaryAdapter.ViewHolder(view);
            return viewHolder;
        } else {
            View view = inflater.inflate(R.layout.loading_row, parent, false);
            DictionaryAdapter.ViewHolder viewHolder = new DictionaryAdapter.ViewHolder(view);
            return viewHolder;
        }

    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull DictionaryAdapter.ViewHolder holder, int position) {
        DictionaryViewItem item = mList.get(position);

        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                ViewHolder viewHolder = holder;
                holder.diseaseName.setText(item.getDiseaseName());
                break;
            case VIEW_TYPE_LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                holder.progressBar.drawableHotspotChanged(50, 50);
                break;
        }
    }
    private class LoadingViewHolder extends ViewHolder {
        ProgressBar mProgressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

// 리사이클러 뷰 클릭 이벤트를 위한 코드
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
    private DictionaryAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(DictionaryAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
