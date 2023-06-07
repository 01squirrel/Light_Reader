package com.example.qmread.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.databinding.FragmentItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CatelogueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Integer size;
    private final List<String> data;
    FragmentItemBinding binding;
    public CatelogueAdapter(Integer size, List<String> data){
        this.size = size;
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CatelogueHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        CatelogueHolder holder1 = (CatelogueHolder) holder;
        String no = "第"+(position+1)+"章";
        holder1.num.setText(no);
        holder1.content.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return size;
    }


    public static class CatelogueHolder extends RecyclerView.ViewHolder{

        private final TextView  num,content;

        public CatelogueHolder(@NonNull @NotNull FragmentItemBinding binding) {
            super(binding.getRoot());
            content = binding.content;
            num = binding.itemNumber;
        }
    }
}
