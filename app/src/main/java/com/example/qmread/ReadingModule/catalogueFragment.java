package com.example.qmread.ReadingModule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qmread.Adapter.CatelogueAdapter;
import com.example.qmread.R;
import com.example.qmread.databinding.FragmentCatalogueBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link catalogueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class catalogueFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView catalogue;
    FragmentCatalogueBinding binding;
    CatelogueAdapter adapter;
    List<String> data = new ArrayList<>();
    List<String> data2 = new ArrayList<>();
    List<String> catalogueList = new ArrayList<>();


    public catalogueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment catalogueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static catalogueFragment newInstance(String param1, String param2) {
        catalogueFragment fragment = new catalogueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       TextView tv = new TextView(getContext());
       Bundle bundle = new Bundle();
       String title = bundle.getString("title");
       tv.setText(title);
        // Inflate the layout for this fragment
        binding = FragmentCatalogueBinding.inflate(getLayoutInflater());
        catalogue = binding.lvCatalogue;
        data.add("牢狱之灾");data.add("妖物作祟");data.add("仙侠世界一样能推理");data.add("是时候表演真正的技术了");data.add("揭开谜题");data.add("懵逼的二叔");
        data.add("这个妹妹好漂亮");data.add("妹子，你偷看为兄干嘛");data.add("暴走的婶婶");data.add("县衙命案");data.add("摸鱼");data.add("一顿操作猛如虎");
        data.add("审问");data.add("心理博弈");data.add("古往今来人类不变的劣根");data.add("许七安的日记");data.add("日常怼婶婶");data.add("带着妹子逛街去");
        data.add("送行诗");data.add("半却恶霸多嚣张");data.add("教公子一个道理");data.add("刑部缉拿人贩");data.add("蓝皮书");data.add("救兵");
        data.add("德行");data.add("替人（第一更）");data.add("拍死我这蝼蚁（第二更）");data.add("辞旧，你大哥待你不薄");data.add("化学课");data.add("我站在烈风在");
        data.add("徐玲月：这辈子要好好报达");data.add("书房议事");data.add("捣蛋鬼");data.add("劝学");data.add("诗成");data.add("那徐平志不当人");
        data.add("争斗");data.add("一个蓄力的适才");data.add("亚圣和他的妻子");data.add("题字");data.add("逃之夭夭");data.add("大哥真讨厌");
        data.add("买首饰");data.add("日常气婶婶");data.add("婶婶：哼，小王八蛋");data.add("社会性死亡");data.add("投壶");data.add("打茶围");
        data.add("一家人就算要真真切切");data.add("妖物作祟");data.add("截胡");data.add("计划褚橙");data.add("计划的核心");data.add("绑架");
        data.add("flag");data.add("这个孩子太难了，我不会教");data.add("打更人上门");data.add("铁证如山");data.add("资质测试");data.add("许七安：我还有抢救的机会");
        data2 = data;
        catalogueList.addAll(data);
        catalogueList.addAll(data2);
        adapter = new CatelogueAdapter(100,catalogueList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        catalogue.setLayoutManager(layoutManager);
        catalogue.setAdapter(adapter);
        return binding.getRoot();
    }
}