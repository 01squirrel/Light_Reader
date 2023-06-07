package com.example.qmread.SettingModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.qmread.SettingModule.fragment.ItemFragment;
import com.example.qmread.Utils.StatusBarUtil;
import com.example.qmread.databinding.ActivityNoticeMessageBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NoticeMessageActivity extends AppCompatActivity {

    private final String[] tabs = {"通知", "互动",};
    ActivityNoticeMessageBinding binding;
    private final List<ItemFragment> tabFragmentList = new ArrayList<>();
    //private final List<PlaceholderContent.PlaceholderItem> values;

    public NoticeMessageActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarUtil.makeStatusBarTransparent(this,0);
        super.onCreate(savedInstanceState);
        binding = ActivityNoticeMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TabLayout tabNotice = binding.tabNotice;
        ViewPager2 viewPager2 = binding.vpMsg;
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        for (String tab : tabs) {
            tabNotice.addTab(tabNotice.newTab().setText(tab));
            tabFragmentList.add(ItemFragment.newInstance(tab));
        }
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return tabFragmentList.size();
            }
        });
        new TabLayoutMediator(tabNotice,viewPager2,true , (tab, position) -> {
            tab.setText(tabs[position]);
        }).attach();
        //tabNotice.setupWithViewPager(viewPager);
        ImageView back = binding.ivBackToRead;
        back.setOnClickListener(v -> {
            finish();
        });
    }
}