package com.example.qmread.ReadingModule;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.qmread.R;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.databinding.ActivityCatalogueBinding;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class Catalogue extends AppCompatActivity {
    private final static String[] title = {"目录","书签","笔记"};
    ActivityCatalogueBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pageAdapter adapter = new pageAdapter(getSupportFragmentManager(), getLifecycle());
        TabLayout tabLayout = binding.tab;
        ViewPager2 pager = binding.vp;
        pager.setAdapter(adapter);
        TabLayout.Tab tab1 = tabLayout.newTab().setText("目录");
        tabLayout.addTab(tab1,0);
        TabLayout.Tab tab2 = tabLayout.newTab().setText("书签");
        tabLayout.addTab(tab2,1);
        TabLayout.Tab tab3 = tabLayout.newTab().setText("笔记");
        tabLayout.addTab(tab3,2);

        binding.ivToRead.setOnClickListener(v ->{
            finish();
        });

    }
    static class pageAdapter extends FragmentStateAdapter {


        public pageAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public pageAdapter(@NonNull @NotNull Fragment fragment) {
            super(fragment);
        }

        public pageAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {
         if (position == 0){
             return new catalogueFragment();
         }else if(position == 1){
             return new BookmarkFragment();
         }else if (position == 2){
             return new Fragment(R.layout.fragment_readnote);
         }
            return null;
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public boolean containsItem(long itemId) {
            return super.containsItem(itemId);
        }
    }
}