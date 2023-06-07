package com.example.qmread.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.databinding.BookmarkItemBinding;
import com.example.qmread.databinding.FragmentItemBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<String> text;
    BookmarkItemBinding binding;

    public BookmarkAdapter(List<String> text) {
        this.text = text;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = BookmarkItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BookmarkHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        BookmarkHolder holder1 = (BookmarkHolder) holder;
        String exam = "书签"+(position+1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        holder1.seq.setText(exam);
        holder1.time.setText(simpleDateFormat.format(date));
        holder1.text.setText(text.get(position));
    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    public static class BookmarkHolder extends RecyclerView.ViewHolder{

        private final TextView seq,time,text;
        public BookmarkHolder(@NonNull @NotNull BookmarkItemBinding bookmarkItemBinding) {
            super(bookmarkItemBinding.getRoot());
            seq = bookmarkItemBinding.tvChapter;
            time = bookmarkItemBinding.tvCreateTime;
            text = bookmarkItemBinding.tvContext;
        }
    }
}
