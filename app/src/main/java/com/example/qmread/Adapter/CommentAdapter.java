package com.example.qmread.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.R;
import com.example.qmread.bean.CommentEntity;
import com.example.qmread.databinding.CommentItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    CommentItemBinding binding;
    private final List<CommentEntity> data;
    private int number;
    boolean click = false;
    private Context context;

    public CommentAdapter(List<CommentEntity> data, Integer number) {
        this.data = data;
        this.number = number;

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CommentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        CommentHolder holder1 = (CommentHolder) holder;
        holder1.head.setImageBitmap(data.get(position).getHead());
        holder1.name.setText(data.get(position).getUserName());
        holder1.time.setText(data.get(position).getDate());
        holder1.content.setText(data.get(position).getContent());
        holder1.num.setText("0");
        holder1.praise.setOnClickListener(v -> {
            Log.i("TAG", "onBindViewHolder: "+position);
            if(!click){
                number++;
                holder1.num.setText(String.valueOf(number));
                //holder1.praise.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.love_48px));
            }else {
                number--;
                if(number>=0){
                    holder1.num.setText(String.valueOf(number));
                }

                //holder1.praise.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.favorite_50px));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder{
        private final ImageView head,praise;
        private final TextView name,time,content,num;

        public CommentHolder(@NonNull @NotNull CommentItemBinding binding) {
            super(binding.getRoot());
            head = binding.ivHead2;
            praise = binding.ivPraise;
            name = binding.tvUser;
            time = binding.tvTime;
            content = binding.tvContent;
            num = binding.tvPraiseNumber;
        }
    }
}
