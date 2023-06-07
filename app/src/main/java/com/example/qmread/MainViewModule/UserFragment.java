package com.example.qmread.MainViewModule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qmread.ComplaintModule.BookStoreActivity;
import com.example.qmread.R;
import com.example.qmread.SettingModule.HelpAndFeedbackActivity;
import com.example.qmread.SettingModule.NoticeMessageActivity;
import com.example.qmread.SettingModule.PersonInfoActivity;
import com.example.qmread.SettingModule.user_setting_page.LoginRegisterActivity;
import com.example.qmread.SettingModule.user_setting_page.SettingActivity;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.databinding.FragmentUserBinding;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FragmentUserBinding binding;
    RoundedImageView button;
    TextView name;
    boolean register;
    boolean readMessage;
    public UserFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.binding = FragmentUserBinding.bind(view);
        register = ToolKits.getBoolean(getContext(),"register",false);
        readMessage = ToolKits.getBoolean(getContext(),"readMessage",false);
        ImageView tip = binding.ivTip;
        if(readMessage){
            tip.setVisibility(View.GONE);
        }else {
            tip.setVisibility(View.VISIBLE);
        }
        ImageView message = binding.ivMessage;
        message.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(),NoticeMessageActivity.class);
            startActivity(intent);
        });
        RelativeLayout personInfo = binding.rlPersonInfo;
        personInfo.setOnClickListener(v -> {
            if(register){
                startActivity(new Intent(getActivity(),PersonInfoActivity.class));
            }else {
                startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
            }
        });
        RelativeLayout book = binding.rlBooks;
        book.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), BookStoreActivity.class));
        });
        RelativeLayout action = binding.rlInteraction;
        action.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "敬请期待", Toast.LENGTH_SHORT).show();
        });
        RelativeLayout set = binding.rlSet;
        set.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });
        RelativeLayout help = binding.rlHelp;
        help.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), HelpAndFeedbackActivity.class));
        });
        button = binding.ibHead;
        Bitmap port = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
        Bitmap source = ToolKits.getBitmap(requireContext(),"photo",port);
        button.setImageBitmap(source);
        name = binding.tvNickName;
        if(!register){
            name.setText(ToolKits.getString(requireContext(),"nickName","姜饼麻子"));
        }else {
            name.setText(ToolKits.getString(requireContext(),"nickName","登录/注册"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: rrrrrrrrrrrrrrrr");
        name.setText(ToolKits.getString(requireContext(),"nickName","登录/注册"));
        register = ToolKits.getBoolean(getContext(),"register",false);
    }
}