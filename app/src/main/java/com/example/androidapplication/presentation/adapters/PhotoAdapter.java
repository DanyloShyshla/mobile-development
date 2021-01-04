package com.example.androidapplication.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;
import com.example.androidapplication.presentation.OnItemClick;
import com.example.androidapplication.presentation.uidata.PhotoViewData;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private final List<PhotoViewData> dataList = new ArrayList<>();

    //Listeners
    private OnItemClick onItemClickListener;

    public PhotoAdapter(OnItemClick onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PhotoAdapter() {
        onItemClickListener = null;
    }

    public void setItems(List<PhotoViewData> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos, parent, false);
        return new PhotoViewHolder(rootView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.bindTo(dataList.get(position));
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(dataList.get(position)));
    }

    public void setOnItemClickListener(OnItemClick listener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        //Timber.wtf("dataList size: %s", String.valueOf(dataList.size()));
        return dataList.size();
    }

}
