package com.example.qmread.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.MainViewModule.BookShelfFragment;
import com.example.qmread.bean.BookEntity;
import com.example.qmread.R;
import com.example.qmread.databinding.BookItemBinding;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<BookEntity> books;
    private BookItemBinding binding;
    private static HashMap<Integer,Boolean> chose = new HashMap<>();
    private static final Set<Integer> m = new LinkedHashSet<>();

    public interface onItemClickListener{
        void onClick(int position,Set<Integer> sum);
    }
    public onItemClickListener listener;
    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public BookAdapter(Context bookContext, List<BookEntity> books,  HashMap<Integer,Boolean> mCheckedList) {
        this.books = books;
        chose = mCheckedList;
        initData();
    }
    public static HashMap<Integer, Boolean> getChose() {
        return chose;
    }

    public static void setChose(HashMap<Integer, Boolean> chose) {
        BookAdapter.chose = chose;
    }

    public Set<Integer> picked(){
        return m;
    }

    public static int selectedNumber(){
        int num = 0;
        if (chose != null){
            for(int i = 0; i< chose.size(); i++){
                if(chose.get(i)){
                    num+=1;
                }
            }
        }
        return num;
    }
    private void initData() {
        for (int i = 0;i<books.size();i++){
            getChose().put(i,false);
        }
    }
    @Override
    public @NotNull bookHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new bookHolder(BookItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        bookHolder bookHolder = (bookHolder) holder;
        bookHolder.setIsRecyclable(false);
        if(!BookShelfFragment.is_action_mode){
            bookHolder.checkBox.setVisibility(View.INVISIBLE);
        }else {
            bookHolder.checkBox.setVisibility(View.VISIBLE);
        }
        bookHolder.checkBox.setChecked(chose.get(position));
        bookHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                chose.put(position,true);
                m.add(position);
            }else {
                chose.put(position,false);
                m.remove(position);
            }
            buttonView.setChecked(chose.get(position));
        });
        bookHolder.bookName.setText(books.get(position).getName());
            bookHolder.cover.setImageResource(R.mipmap.moleskine_80px);
//        {
//            String coverPath = books.get(position).getCover();
//            Bitmap bitmap = FileUtil.loadLocalPicture(coverPath);
//            if (bitmap != null) {
//                bookHolder.cover.setImageBitmap(bitmap);
//            } else {
//                bookHolder.cover.setImageResource(R.mipmap.moleskine_80px);
//            }
//        }
        String s1 = "章未读";
        if(books.get(position).getPosition()==0){
            bookHolder.chapter.setText("未读");
        }else{
            bookHolder.chapter.setText("已读");
           // bookHolder.chapter.setText(MessageFormat.format("{0}{1}", books.get(position).getChapterNum(), s1));
        }
        bookHolder.cardView.setOnClickListener(v -> {
            bookHolder.checkBox.setChecked(!bookHolder.checkBox.isChecked());
            if(listener != null){
                listener.onClick(position,m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class bookHolder extends RecyclerView.ViewHolder {
        public final ImageView cover;
        public final CheckBox checkBox;
        public final TextView bookName;
        public final TextView chapter;
        public final CardView cardView;

        public bookHolder(@NonNull @NotNull BookItemBinding binding) {
            super(binding.getRoot());
            cover = binding.ivBookImg;
            checkBox = binding.cbChoose;
            bookName = binding.bookName;
            chapter = binding.chapterNum;
            cardView = binding.cvItem;
        }
    }
}
