package com.example.qmread.ComplaintModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qmread.Adapter.BookItemAdapter;
import com.example.qmread.R;
import com.example.qmread.ReadingModule.ReadBook;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.bean.BookEntity;
import com.example.qmread.databinding.ActivityBookStoreBinding;
import com.example.qmread.databinding.SexDialogBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class BookStoreActivity extends AppCompatActivity {

    ActivityBookStoreBinding binding;
    SexDialogBinding sexDialogBinding;
    ImageView back;
    RecyclerView bookList;
    private List<BookEntity> books;
    BookItemAdapter adapter;
    DaoUtilsStore store;
    Display display;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityBookStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back = binding.ivBack;
        back.setOnClickListener(v -> {
            finish();
        });
        display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        store = DaoUtilsStore.getInstance();
        books= store.getUserDaoUtils().queryAll();
        bookList = binding.rvBooks;
        adapter = new BookItemAdapter(books);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        bookList.setLayoutManager(layoutManager);
        bookList.setAdapter(adapter);
        adapter.setOnItemClickListener(this::showBottom);
    }

    private void showBottom(int p){
        Dialog sexDialog = new Dialog(this, R.style.BottomDialog);
        sexDialogBinding = SexDialogBinding.inflate(getLayoutInflater());
        sexDialog.setContentView(sexDialogBinding.getRoot());
        TextView one,two,three;
        one = sexDialogBinding.tvMan;
        two = sexDialogBinding.tvWomen;
        three = sexDialogBinding.tvCancel;
        one.setText("开始阅读");
        two.setText("删除");
        ViewGroup.LayoutParams params = sexDialogBinding.getRoot().getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels;
        sexDialogBinding.getRoot().setLayoutParams(params);
        sexDialog.getWindow().setGravity(Gravity.BOTTOM);
        sexDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        sexDialog.show();
        one.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReadBook.class);
            intent.putExtra("BookName",books.get(p).getName());
            ToolKits.putString(this,"lastBook",books.get(p).getName());
            startActivity(intent);
            sexDialog.dismiss();
            finish();
        });
        two.setOnClickListener(v -> {
            sexDialog.dismiss();
            Toast toast = Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT);
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("删除提醒")
                    .setMessage("是否确定从本地删除该书籍")
                    .setNegativeButton("取消",(dialog,which)-> dialog.dismiss())
                    .setPositiveButton("确定",(dialog,which)->{
                        boolean delete = DaoUtilsStore.getInstance().getUserDaoUtils().delete(books.get(p));
                        dialog.dismiss();
                        if (delete) {
                            toast.setGravity(Gravity.TOP,0,height/4);
                            toast.show();
                        }
                        books= store.getUserDaoUtils().queryAll();
                        adapter = new BookItemAdapter(books);
                        bookList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    });

        });
        three.setOnClickListener(v -> sexDialog.dismiss());
    }
}