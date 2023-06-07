package com.example.qmread.MainViewModule;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.Adapter.SearchHistoryAdapter;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.databinding.ActivitySearchBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class searchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    ImageView back;
    TextView search,clear,change;
    TextInputEditText editText;
    RelativeLayout history;
    RecyclerView searchItems;
    List<String> example = new ArrayList<>();
    SearchHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back = binding.ivBackTMain;
        back.setOnClickListener(v -> {
            finish();
        });
        searchItems = binding.rvHistory;
        adapter = new SearchHistoryAdapter(example);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        searchItems.setLayoutManager(layoutManager);
        searchItems.setAdapter(adapter);
        search = binding.tvDone;
        editText = binding.edSearch;
        editText.requestFocus();
        InputMethodManager manager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(editText,0);
        editText.setOnEditorActionListener((v, actionId, event) ->{
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                String word = Objects.requireNonNull(editText.getText()).toString();
                example.add(word);
                adapter.notifyDataSetChanged();
                editText.clearFocus();
                manager.hideSoftInputFromWindow(v.getWindowToken(),0);
                Log.i("TAG", "onCreate: -----------"+example.size());
                return true;
            }
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clear = binding.tvClearAll;
        history = binding.rlHistory;
        clear.setOnClickListener(v -> {
            example.clear();
            adapter.notifyDataSetChanged();
            history.setVisibility(View.GONE);
        });
        if(!example.isEmpty()){
            history.setVisibility(View.VISIBLE);
        }
    }
}