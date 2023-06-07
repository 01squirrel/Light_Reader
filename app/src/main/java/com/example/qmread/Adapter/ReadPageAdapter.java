package com.example.qmread.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.R;
import com.example.qmread.databinding.ReadPageItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReadPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  ReadPageItemBinding binding;
    private final List<String> data;
    private final int size;
    private final Context context;
    public ReadPageAdapter(List<String> data, int size, Context context) {
        this.data = data;
        this.size = size;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ReadPageHolder(ReadPageItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ReadPageHolder holder1 = (ReadPageHolder) holder;
        holder1.textView.setText(data.get(position));
        holder1.textView.setTextSize(size);
        holder1.textView.setTextColor(ContextCompat.getColor(context, R.color.black_8c));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ReadPageHolder extends RecyclerView.ViewHolder{
        public LinearLayout llRead;
        public AppCompatTextView textView;
        public ReadPageHolder(@NonNull @NotNull ReadPageItemBinding binding) {
            super(binding.getRoot());
            llRead = binding.llRead;
            textView = binding.tvRead;
        }
    }
}
