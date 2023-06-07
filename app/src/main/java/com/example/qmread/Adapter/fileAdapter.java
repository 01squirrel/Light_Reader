package com.example.qmread.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.qmread.R;
import com.example.qmread.bean.FileItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class fileAdapter extends BaseAdapter {

    private final List<FileItem> list;
    private static HashMap<Integer,Boolean> selected = new HashMap<>();
    private static int i;
    private static final Set<Integer> m = new LinkedHashSet<>();

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        fileAdapter.selected = isSelected;
    }

    public static class ViewHolder {
        public TextView mBookName,fileSize,createTime;
        public CheckBox mCheckBox;
    }

    public fileAdapter(List<FileItem> list, Context context,HashMap<Integer, Boolean> checkNum) {
        this.list = list;
        selected = checkNum;
        LayoutInflater inflater = LayoutInflater.from(context);
        initData();
    }

    private void initData() {
        for (int i = 0;i<list.size();i++){
           getIsSelected().put(i,false);
        }
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return selected;
    }

    public static int selectedNumber(){
        int num = 0;
        if (selected !=null){
            for(int i = 0; i< selected.size(); i++){
                if(selected.get(i)){
                    num+=1;
                }
            }
        }
        return num;
    }

    public static Set<Integer> selected(){
        return  m;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item,parent,false);
            mViewHolder.mBookName = view.findViewById(R.id.book_name);
            mViewHolder.fileSize = view.findViewById(R.id.tv_fileSize);
            mViewHolder.createTime = view.findViewById(R.id.tv_fileTime);
            mViewHolder.mCheckBox = view.findViewById(R.id.cb);
            view.setTag(mViewHolder);// 将ViewHolder存储在View中
        } else {
            view =  convertView;
            mViewHolder = (ViewHolder) view.getTag();// 重新获取ViewHolder
        }
            mViewHolder.mBookName.setText(list.get(position).getFileName());
            mViewHolder.fileSize.setText(list.get(position).getFileSize());
            mViewHolder.createTime.setText(list.get(position).getFileDate());
            mViewHolder.mCheckBox.setVisibility(View.VISIBLE);
            mViewHolder.mCheckBox.setChecked(selected.get(position));
            mViewHolder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Log.i("选中的是：", position +"----"+isChecked);
                if(isChecked){
                    selected.put(position, true);
                    i = position;
                    m.add(position);
                }else {
                    selected.put(position,false);
                    m.remove(position);
                }
                buttonView.setChecked(selected.get(position));
            });
        return view;
    }
}
