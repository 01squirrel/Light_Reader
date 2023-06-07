package com.example.qmread.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.bean.BookEntity;
import com.example.qmread.databinding.BookExampleBinding;
import com.example.qmread.databinding.SexDialogBinding;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    BookExampleBinding binding;
    private final List<BookEntity> books;

    public BookItemAdapter(List<BookEntity> books) {
        this.books = books;
    }

    public interface onItemClickListener{
        void onClick(int position);
    }
    public onItemClickListener listener;
    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = BookExampleBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BookItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        BookItemHolder itemHolder = (BookItemHolder) holder;
        itemHolder.name.setText(books.get(position).getName());
        if(books.get(position).getPosition()>0){
            itemHolder.status.setText("已读");
        }
        itemHolder.option.setOnClickListener(v -> {
            if(listener != null){
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookItemHolder extends RecyclerView.ViewHolder{

        private final TextView name,status;
        private final ShapeableImageView option;

        public BookItemHolder(@NonNull @NotNull BookExampleBinding binding) {
            super(binding.getRoot());
            name = binding.tvBookName;
            status = binding.tvStatus;
            option = binding.ivOperation;
        }
    }

}
