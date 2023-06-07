package com.example.qmread.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.databinding.SearchHistoryItemBinding;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    SearchHistoryItemBinding binding;
    private final List<String> data;

    public SearchHistoryAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HistoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        HistoryHolder historyHolder = (HistoryHolder) holder;
        historyHolder.button.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder{
        private final MaterialButton button;

        public HistoryHolder(@NonNull @NotNull SearchHistoryItemBinding binding) {
            super(binding.getRoot());
           button = binding.mbHistory;
        }
    }
}
