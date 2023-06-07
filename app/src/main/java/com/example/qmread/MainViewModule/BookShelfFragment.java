package com.example.qmread.MainViewModule;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qmread.Adapter.BookAdapter;
import com.example.qmread.R;
import com.example.qmread.ReadingModule.ReadBook;
import com.example.qmread.Utils.ToolKits;
import com.example.qmread.bean.BookEntity;
import com.example.qmread.MainViewModule.contract.BookShelfContract;
import com.example.qmread.Utils.entity.epub.OpfData;
import com.example.qmread.databinding.FragmentBookShelfBinding;
import com.example.qmread.databinding.ReadNoticeBinding;
import com.example.qmread.databinding.UpdateRemindDialogBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class BookShelfFragment extends Fragment implements BookShelfContract.View {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<BookEntity> books = new ArrayList<>();
    private FragmentBookShelfBinding binding;
    private BookAdapter bookAdapter;
    private final HashMap<Integer,Boolean> mCheckedList =  new HashMap<>();
    public static boolean is_action_mode = false;
    public static boolean is_selected_all = false;
    private onGetFragmentValueListener listener;
    Context bookFrg;
    RecyclerView rvBook;
    LinearLayout top,menu;
    RelativeLayout book;
    ImageView more,search;
    TextView manage,get,update;
    Dialog dialog;
    Dialog tipDialog;
    ReadNoticeBinding noticeBinding;
    ImageView cancel;
    MaterialButton startRead;
    UpdateRemindDialogBinding dialogBinding;
    String bookInfo;
    int time;
    TextView readTime;
    DaoUtilsStore store = new DaoUtilsStore();


    public BookShelfFragment() {
    }

    public static BookShelfFragment newInstance(String param1, String param2) {
        BookShelfFragment fragment = new BookShelfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public interface onGetFragmentValueListener{
        void choseNumber(Set<BookEntity> total);
        void show(boolean hidden);
    }

    ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        Log.i("TAG", ": "+result);
    });

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
        binding = FragmentBookShelfBinding.inflate(inflater,container,false);
        readTime = binding.zero;
        books = store.getUserDaoUtils().queryAll();
        bookFrg = new BookShelfFragment().getContext();
        rvBook = binding.rvBookList;
        TextView checkSum = binding.tvCheckNum;
        GridLayoutManager gm = new GridLayoutManager(bookFrg,3);
        rvBook.setLayoutManager(gm);
        rvBook.setHasFixedSize(true);
        rvBook.setNestedScrollingEnabled(true);
        for(int i =0;i<books.size();i++){
            mCheckedList.put(i,false);
        }
        bookAdapter = new BookAdapter(bookFrg, books, mCheckedList);
        rvBook.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        bookAdapter.setOnItemClickListener((position,sum) -> {
            if(!is_action_mode){
                permissionLauncher.launch(new String[]{Manifest.permission.WRITE_SETTINGS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE});
                Intent intent = new Intent(getActivity(), ReadBook.class);
                intent.putExtra("BookName",books.get(position).getName());
                intent.putExtra("position",position);
                ToolKits.putString(requireContext(),"lastBook",books.get(position).getName());
                books.get(position).setPosition(1);
                bookAdapter.notifyDataSetChanged();
                startActivity(intent);
            }else {
                Log.i("选到了", "onCreateView: --"+sum);
                Set<BookEntity> entities = new LinkedHashSet<>();
                int a = BookAdapter.selectedNumber();
                if(a>0){
                    String text = String.format(getResources().getString(R.string.checkNumber),a);
                    Log.i("TAG", "onCreateView: "+text);
                    for(int i :sum){
                        entities.add(books.get(i));
                    }
                    listener.choseNumber(entities);
                    checkSum.setText(text);
                }else {
                    checkSum.setText("已选择0本书");
                    binding.tvChooseAll.setText("全选");
                }
                bookAdapter.notifyDataSetChanged();
            }
        });
        menu = binding.llMenu;
        more = binding.ivMore;
        search = binding.ivSearch;
        more.setOnClickListener(v -> {
            if(menu.getVisibility() == View.GONE){
                menu.setVisibility(View.VISIBLE);
            }else {
                menu.setVisibility(View.GONE);
            }
        });
        search.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(),searchActivity.class));
        });
//        Bundle bundle = this.getArguments();
//        time = bundle.getInt("readTime");
        countTime();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView addBook = binding.noBook;
        TextView edit = binding.tvEdit;
        TextView complete = binding.tvComplete;
        top = binding.llTop;
        RelativeLayout noBook = binding.rlNoBook;
        book = binding.rlEditBook;
        if(books != null){
            noBook.setVisibility(View.GONE);
            rvBook.setVisibility(View.VISIBLE);
        }
        addBook.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),FileManageFragment.class));
        });
        edit.setOnClickListener(v -> {
            if(top.getVisibility() == View.VISIBLE){
                top.setVisibility(View.INVISIBLE);
                book.setVisibility(View.VISIBLE);
                is_action_mode = true;
                listener.show(is_action_mode);
                bookAdapter.notifyDataSetChanged();
            }
        });
        complete.setOnClickListener(v1 -> {
            top.setVisibility(View.VISIBLE);
            book.setVisibility(View.INVISIBLE);
            is_action_mode = false;
            listener.show(is_action_mode);
            for(int i =0;i<books.size();i++){
                mCheckedList.put(i,false);
            }
            BookAdapter.setChose(mCheckedList);
            bookAdapter.notifyDataSetChanged();
            refreshData(true);
        });
        TextView pickAll = binding.tvChooseAll;
        pickAll.setOnClickListener(v -> {
            int size = books.size();
            if(!is_selected_all){
                for(int i =0;i<size;i++){
                    mCheckedList.put(i,true);
                }
                BookAdapter.setChose(mCheckedList);
                bookAdapter.notifyDataSetChanged();
                //tvCheckNum.setText(String.format(getResources().getString(R.string.check_num), size));
                pickAll.setText("取消全选");
                is_selected_all = true;
                listener.show(is_action_mode);
            }else {
                pickAll.setText("全选");
                for(int i =0;i<size;i++){
                    mCheckedList.put(i,false);
                }
                BookAdapter.setChose(mCheckedList);
                bookAdapter.notifyDataSetChanged();
                String current = "已选择0本书";
                Log.i("TAG", current);
                //tvCheckNum.setText(current);
                is_selected_all = false;
                listener.show(is_action_mode);
            }
        });
        get = binding.tvGet;
        update = binding.tvUpdate;
        manage = binding.tvManageBook;
        manage.setOnClickListener(v -> {
            if(top.getVisibility() == View.VISIBLE){
                top.setVisibility(View.INVISIBLE);
                book.setVisibility(View.VISIBLE);
                is_action_mode = true;
                listener.show(is_action_mode);
                bookAdapter.notifyDataSetChanged();
            }
        });
        get.setOnClickListener(v -> {
            BookShelfFragment bookShelfFragment = new BookShelfFragment();
            FileManageFragment fileManageFragment = new FileManageFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.hide(bookShelfFragment);
            transaction.show(fileManageFragment).commitAllowingStateLoss();
        });
        update.setOnClickListener(v -> {
            dialog = new Dialog(requireContext(), R.style.BottomDialog);
            dialogBinding = UpdateRemindDialogBinding.inflate(getLayoutInflater());
            dialog.setContentView(dialogBinding.getRoot());
            ViewGroup.LayoutParams params = dialogBinding.getRoot().getLayoutParams();
            params.width = (int) (getResources().getDisplayMetrics().widthPixels*0.8);
            dialogBinding.getRoot().setLayoutParams(params);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
            dialog.show();
            TextView del = dialogBinding.tvNo;
            del.setOnClickListener(view1 -> dialog.dismiss());
        });
           bookInfo = ToolKits.getString(requireContext(),"lastBook","");
        showMessage();
        if(!bookInfo.isEmpty()){
            tipDialog.show();
        }
    }


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if(context instanceof onGetFragmentValueListener){
            this.listener = (onGetFragmentValueListener) context;
        }else {
            throw new IllegalStateException("You activity must implement the callback");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        countTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


    @Override
    public void queryAllBookSuccess(List<BookEntity> dataList) {
        LinkedList<Object> mCheckedList = null;
        if (bookAdapter == null) {
            books = dataList;
            assert false;
            mCheckedList.clear();
            for (int i = 0; i < books.size(); i++) {
                mCheckedList.add(false);
            }
            initAdapter();
            binding.rvBookList.setAdapter(bookAdapter);
        } else {
            books.clear();
            books.addAll(dataList);
            assert false;
            mCheckedList.clear();
            for (int i = 0; i < books.size(); i++) {
                mCheckedList.add(false);
            }
            bookAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {

    }

    public void refreshData(boolean refresh){
        DaoUtilsStore store;
        if(refresh){
            store = DaoUtilsStore.getInstance();
            books = store.getUserDaoUtils().queryAll();
            for(int i =0;i<books.size();i++){
                mCheckedList.put(i,false);
            }
            bookFrg = new BookShelfFragment().getContext();
            bookAdapter = new BookAdapter(bookFrg, books, mCheckedList);
            rvBook.setAdapter(bookAdapter);
            bookAdapter.notifyDataSetChanged();
        }
    }

    private void showMessage() {
        tipDialog = new Dialog(requireContext(),R.style.dialog_style);
        noticeBinding = ReadNoticeBinding.inflate(getLayoutInflater());
        tipDialog.setContentView(noticeBinding.getRoot());
        ViewGroup.LayoutParams params = noticeBinding.getRoot().getLayoutParams();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels*0.8);
        WindowManager.LayoutParams layoutParams = tipDialog.getWindow().getAttributes();
        layoutParams.y = 150;
        noticeBinding.getRoot().setLayoutParams(params);
        tipDialog.getWindow().setGravity(Gravity.BOTTOM);
        tipDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        tipDialog.getWindow().setAttributes(layoutParams);
        TextView bookName = noticeBinding.tvBookName;
        bookName.setText(bookInfo);
        cancel = noticeBinding.ivDismiss;
        cancel.setOnClickListener(v -> {
            tipDialog.cancel();
        });
        startRead = noticeBinding.mbRead;
        startRead.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(),ReadBook.class);
            intent.putExtra("BookName",bookInfo);
            if (!bookInfo.isEmpty()){
                startActivity(intent);
            }
        });
        new Handler().postDelayed(() -> {
            tipDialog.cancel();
        },5000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            DaoUtilsStore store = DaoUtilsStore.getInstance();
            books=(store.getUserDaoUtils().queryAll());
            bookFrg = new BookShelfFragment().getContext();
            for(int i =0;i<books.size();i++){
                mCheckedList.put(i,false);
            }
            bookAdapter = new BookAdapter(bookFrg, books, mCheckedList);
            rvBook.setAdapter(bookAdapter);
            bookAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void queryAllBookError(String errorMsg) {

    }

    @Override
    public void unZipEpubSuccess(String filePath, OpfData opfData) {

    }

    @Override
    public void unZipEpubError(String errorMsg) {

    }

    private void countTime(){
        for(int i =0;i<books.size();i++){
            time += books.get(i).getSecondPosition();
        }
        if(time>60){
            int min = time/60;
            readTime.setText(String.valueOf(min));
        }
    }

}