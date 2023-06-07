package com.example.qmread.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.databinding.ImageItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Drawable> data;
    ImageItemBinding binding;

    public ImageAdapter(Context context, ArrayList<Drawable> data) {
        this.data = data;
    }

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
        void delete(boolean act,int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = ImageItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ImageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ImageHolder holder1 = (ImageHolder) holder;
        holder1.imageView.setImageDrawable(data.get(position));
        holder1.imageView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(position);
            }
        });
        if(position != data.size()-1){
            holder1.cancel.setVisibility(View.VISIBLE);
            holder1.cancel.setOnClickListener(v -> {
                if (listener != null) {
                    listener.delete(true,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final ImageView cancel;
        public ImageHolder(@NonNull @NotNull ImageItemBinding binding) {
            super(binding.getRoot());
            imageView = binding.ivImageItem;
            cancel = binding.ivCancel;
        }
    }
}
