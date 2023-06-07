package com.example.qmread.MainViewModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.qmread.Adapter.fileAdapter;
import com.example.qmread.R;
import com.example.qmread.Utils.FileUtil;
import com.example.qmread.Utils.TxtFileUtil;
import com.example.qmread.bean.BookEntity;
import com.example.qmread.bean.FileItem;
import com.example.qmread.databinding.FragmentFileManageBinding;
import com.example.qmread.greendao.gen.DaoUtilsStore;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class FileManageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REFRESH = 1;
    private static List<String> file_name;
    private static List<String> file_txt_path;
    private final String[] list = {"全部格式","Txt","其他","文件管理"};
    private List<FileItem> files = new ArrayList<>();
    private final List<FileItem> scanList = new ArrayList<>();
    private final HashMap<Integer,Boolean> checkNumber =  new HashMap<>();
    boolean allChecked = false;
    private Spinner spinner;
    private BookEntity bookEntity;
    private File file;
    private ArrayList<Map<String, String>> listItems;
    private ProgressDialog mProgressDialog;
    private FragmentFileManageBinding binding;
    private fileAdapter adapter;
    private BookShelfFragment bookShelfFragment;
    private String mParam1;
    private String mParam2;
    private List<Uri> res = new ArrayList<>();
    private ListView listView;
    private boolean scanComplete = false;

    ActivityResultLauncher<String[]> loadOtherTypeLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(),result -> {
        Log.i("所有其他格式文件：", String.valueOf(result));
    }) ;
    ActivityResultLauncher<String[]> allTypeLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), result -> {
        if(result != null){
            Log.i("获取全部格式:", String.valueOf(result));
        }
    });
    ActivityResultLauncher<String> loadFileLauncher = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(),result -> {
        if (result != null){
            String fileName,fileSize;
            res = result;
            for(int i= 0;i<result.size();i++){
               // String res =  FileUtil.readTextFromUri(result.get(i), requireActivity());
                 fileName = FileUtil.getFileName(result.get(i),requireActivity());
                 fileSize = FileUtil.dumpImageMetaData(result.get(i),requireActivity());
                 File min = new File(requireContext().getFilesDir().getAbsolutePath());
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                 Date date = new Date(min.lastModified());
                 String time = sdf.format(date);
                 FileItem item = new FileItem(fileName,fileSize, time,"txt");
                 files.add(item);
                 Log.i("文件内容----", fileName+"----"+fileSize+"---"+time);
//                 String path = FileUtil.uriToFileApiQ(result.get(i),requireActivity()).getAbsolutePath();
//                 TxtFileUtil.cutFile(path,requireActivity());
//                String contexts;
//                contexts = FileUtil.getContent(result.get(i),requireContext());
//                Log.i("TAG", ": 121212----"+contexts);
//                FileUtil.saveFiles(contexts,requireContext(),fileName);
            }
            for(int j =0;j<files.size();j++){
                checkNumber.put(j,false);
            }
            fileAdapter.setIsSelected(checkNumber);
            adapter.notifyDataSetChanged();
        }
    });
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result != null){
            if (result.getData() != null) {
                Log.i("文件", String.valueOf(result.getData()));
                String mPath = result.getData().getStringExtra("file");
                File pathFile = new File(mPath);
                Map<String, String> pathMap = new HashMap<>();
                if (pathFile.exists()) {
                    if (pathFile.getName().endsWith(".txt")) {
                        pathMap.put("Name", pathFile.getName());
                        pathMap.put("Path", pathFile.getPath());
                        listItems.add(pathMap);
                        showFileList(listItems);
                        LinearLayout add = binding.addBook;
                        add.setOnClickListener(v -> showFileList(listItems));
                    } else {
                        Toast.makeText(FileManageFragment.newInstance(mParam1,mParam2), "请选择一个TXT文件！",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    });

    public FileManageFragment() { }

    public static Context newInstance(String param1, String param2) {
        FileManageFragment fragment = new FileManageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment.getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FileUtil.getAnyTypeFile(context,selection);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    private void initSpinner() {
        spinner = binding.spinner;
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setSelected(false);
        spinner.setSelection(0,true);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("你选择的是", list[position]);
                    parent.setVisibility(View.VISIBLE);
                    String str = (String) spinner.getSelectedItem();
                    switch (str){
                        case "文件管理":
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                            launcher.launch(intent);
                            break;
                        case "Txt":
                            loadFileLauncher.launch("text/plain");
                            break;
                        case "其他":
                            loadOtherTypeLauncher.launch(new String[]{"application/msword","application/x-zip-compressed","application/vnd.ms-excel"});
                            break;
                        case "全部格式":
                            allTypeLauncher.launch(new String[]{"application/msword", "application/pdf"});
                            break;
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.INVISIBLE);
            }
        });
        ImageButton more = binding.ibSort;
        RelativeLayout menu = binding.rlMenu;
        LinearLayout one = binding.llOne;
        LinearLayout two = binding.llTwo;
        LinearLayout three = binding.llThree;
        LinearLayout four = binding.llFour;
        more.setOnClickListener(v -> {
            if(menu.getVisibility()==View.GONE){
                menu.setVisibility(View.VISIBLE);
            }else{
                menu.setVisibility(View.GONE);
            }
            spinner.setVisibility(View.VISIBLE);
        });
        one.setOnClickListener(v -> {
            if(binding.ivAllType.getVisibility() ==View.GONE){
                binding.ivAllType.setVisibility(View.VISIBLE);
            }
            loadOtherTypeLauncher.launch(new String[]{"application/msword","application/x-zip-compressed","application/vnd.ms-excel"});
            binding.ivTxt.setVisibility(View.GONE);
            binding.ivOther.setVisibility(View.GONE);
            menu.setVisibility(View.GONE);
        });
        two.setOnClickListener(v -> {
            if(binding.ivTxt.getVisibility() ==View.GONE){
                binding.ivTxt.setVisibility(View.VISIBLE);
            }
            loadFileLauncher.launch("text/plain");
            binding.ivAllType.setVisibility(View.GONE);
            binding.ivOther.setVisibility(View.GONE);
            menu.setVisibility(View.GONE);
        });
        three.setOnClickListener(v -> {
            if(binding.ivOther.getVisibility() ==View.GONE){
                binding.ivOther.setVisibility(View.VISIBLE);
            }
            loadFileLauncher.launch("application/pdf");
            binding.ivAllType.setVisibility(View.GONE);
            binding.ivTxt.setVisibility(View.GONE);
            menu.setVisibility(View.GONE);
        });
        four.setOnClickListener(v -> {
            menu.setVisibility(View.GONE);
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            launcher.launch(intent);
        });
    }

    private void checkBoxListener(View view){
        fileAdapter.ViewHolder viewHolder = (fileAdapter.ViewHolder) view.getTag();
        viewHolder.mCheckBox.setChecked(!viewHolder.mCheckBox.isChecked());
        int n= fileAdapter.selectedNumber();
        if(n>0){
            String text = String.format(getResources().getString(R.string.checkNumber),n);
            binding.tvSelected.setText(text);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFileManageBinding.inflate(inflater,container,false);
        initSpinner();
        //checkBoxListener(binding.getRoot());
        bookEntity = new BookEntity();
        listView = binding.lvFile;
        for(int i=0;i<files.size();i++){
            checkNumber.put(i,false);
        }
        adapter = new fileAdapter(files,getActivity(),checkNumber);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            fileAdapter.ViewHolder holder = (fileAdapter.ViewHolder) view.getTag();
            // 调整选定条目
            holder.mCheckBox.setChecked(!holder.mCheckBox.isChecked());
            int n = fileAdapter.selectedNumber();
            if(n>0){
                String text = String.format(getResources().getString(R.string.checkNumber),n);
                binding.tvSelected.setText(text);
            }else {
                binding.tvSelected.setText("全选");
            }
            adapter.notifyDataSetChanged();
        });
        LinearLayout checkAll = binding.checkNum;
        checkAll.setOnClickListener(v->{
                if(!allChecked){
                    dataChanged();
                    allChecked = true;
                }else {
                    for(int i = 0;i < files.size();i++){
                        checkNumber.put(i,false);
                    }
                    fileAdapter.setIsSelected(checkNumber);
                    adapter.notifyDataSetChanged();
                    binding.tvSelected.setText("全选");
                    allChecked = false;
                }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.select_type,menu);
    }

    private void dataChanged() {
        for(int i = 0;i < files.size();i++){
                checkNumber.put(i,true);
        }
        fileAdapter.setIsSelected(checkNumber);
        adapter.notifyDataSetChanged();
        int checkNum = files.size();
        String choice = "已选择"+ checkNum +"项";
        binding.tvSelected.setText(choice);
    }

    /**
     * 递归查找SD卡上所有书籍
     * @param file-
     */
    public void listFileTxt(File file) {
        File file1 = new File(file.getAbsolutePath());
        file_name = new ArrayList<>();
        file_txt_path = new ArrayList<>();
        if(!file1.exists()){
            System.out.println("目录不存在");
            return;
        }
        File[] fileList = file1.listFiles();
        try {
            if(fileList != null && fileList.length>0){
                for (File f : fileList) {
                    if(f.isDirectory()){
                        listFileTxt(f);
                        //Log.i("TAG", "输出的文件：--"+f.getName());
                    } else if(f.isFile() && f.getName().endsWith(".txt")){
                            System.out.println(f.getName());
                            String itemName = f.getName().substring(0, f.getName().lastIndexOf("."));
                            String itemSize = FileUtil.byteToMB(f.length());
                            String itemTime = FileUtil.transformTime(f);
                            FileItem item = new FileItem(itemName,itemSize,itemTime,"txt");
                            scanList.add(item);
                            //files.add(item);
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean onOptionsItemSelected(MenuItem menuitem) {
        menuitem.getItemId();
        return true;
    }


    /**
     * 接收返回的路径
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("reader", "receivingPath");
        if (data != null) {
            Log.d("reader", "onActivityResult");
            String mPath = data.getStringExtra("file");
            File pathFile = new File(mPath);
            Map<String, String> pathMap = new HashMap<>();
            if (pathFile.exists()) {
                if (pathFile.getName().endsWith(".txt")) {
                    pathMap.put("Name", pathFile.getName());
                    pathMap.put("Path", pathFile.getPath());
                    listItems.add(pathMap);
                    showFileList(listItems);
                    LinearLayout add = binding.addBook;
                    add.setOnClickListener(v -> showFileList(listItems));
                } else {
                    Toast.makeText(FileManageFragment.newInstance(mParam1,mParam2), "请选择一个TXT文件！",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    /**
     * 将保存在List<Map<String,String>>中的书籍信息显示到ListView中
     * @param listItems-
     */
    private void showFileList(ArrayList<Map<String, String>> listItems){
        if(file_name!=null){
            for(int i = 0;i < file_name.size();i++){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("Name",file_name.get(i));
                hashMap.put("Path",file_txt_path.get(i));
                listItems.add(hashMap);
                ListView mListView = binding.lvFile;
                SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),listItems,R.layout.file_item,new String[]{"Name"},new int[]{R.id.book_name});
                mListView.setAdapter(simpleAdapter);
//                fileAdapter adapter = new fileAdapter(listItems,FileManageFragment.newInstance(mParam1,mParam2));
//                MultiCallback callback = new MultiCallback();
//                mListView.setChoiceMode(3);
//                mListView.setMultiChoiceModeListener((MultiChoiceModeListener) callback);
//                mListView.setAdapter(adapter);
            }
        }else{
            failAddingDialog();
        }
//        if (mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
    }
    private void failAddingDialog() {
       Context content=FileManageFragment.newInstance(mParam1,mParam2);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(content);
        builder.setTitle("添加书籍失败");
        builder.setPositiveButton("确定", (dialoginterface, i) -> dialoginterface.dismiss());
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int m = fileAdapter.selectedNumber();
        if(m>0){
            String text = String.format(getResources().getString(R.string.checkNumber),m);
            binding.tvSelected.setText(text);
        }else {
            binding.tvSelected.setText("全选");
        }
        adapter.notifyDataSetChanged();
        LinearLayout scanFile = binding.scanFile;
        listItems = new ArrayList<>();
        scanFile.setOnClickListener(v -> {
            // files = FileUtil.getAnyTypeFile(requireContext(),new String[]{"text/plain"});
            Log.d("FILE:","开始扫描");
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("正在搜索书籍，请稍候 ……");
            mProgressDialog.show();
            mProgressDialog.setCanceledOnTouchOutside(true);
            if(!mProgressDialog.isShowing()){
                Toast.makeText(requireContext(),"已取消扫描",Toast.LENGTH_SHORT).show();
            }else if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //file = Environment.getExternalStorageDirectory();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    File externalFileRootDir = requireContext().getExternalFilesDir("");
                    do {
                        externalFileRootDir = Objects.requireNonNull(externalFileRootDir).getParentFile();
                    } while (Objects.requireNonNull(externalFileRootDir).getAbsolutePath().contains("/Android"));
                    file = Objects.requireNonNull(externalFileRootDir);
                } else {
                    file = Environment.getExternalStorageDirectory();
                }
                Log.i("当前目录为：", "onViewCreated: "+file.getAbsolutePath());
                new Thread(){
                    public boolean exit = false;
                    public void run(){
                        super.run();
                        while (!exit){
                            listFileTxt(file);
                            exit = true;
                            listView.postInvalidate();
                        }
                        mProgressDialog.cancel();
                        Looper.prepare();
                        Toast.makeText(getContext(),"扫描完成",Toast.LENGTH_SHORT).show();
                        Log.i("0000000000", "listFileTxt: "+files.size());
                        listView.postInvalidate();
                        new Thread(new refreshUi()).start();
                        scanComplete = true;
                        Looper.loop();
                    }
                }.start();
            }
        });
        ImageView addShelf = binding.ivAddShelf;
        addShelf.setOnClickListener(v -> {
            bookShelfFragment = new BookShelfFragment();
            Set<Integer> n = fileAdapter.selected();
            Random random = new Random();
            if(!n.isEmpty()){
                for(int i : n){
                    bookEntity.setName(files.get(i).getFileName());
                    bookEntity.setId((long) (random.nextInt(100-1)+1));
                    bookEntity.setCover(String.valueOf(R.mipmap.moleskine_80px));
                    bookEntity.setChapterIndex(0);
                    bookEntity.setPosition(0);
                    bookEntity.setSecondPosition(0);
                    bookEntity.setType("TXT");
                    DaoUtilsStore.getInstance().getUserDaoUtils().insert(bookEntity);
                    if(!res.isEmpty()){
                        String contexts;
                        contexts = FileUtil.getContent(res.get(i),requireContext());
                        FileUtil.saveFiles(contexts,requireContext(),files.get(i).getFileName());
                    }
                }
                Log.i("加入书架有：", String.valueOf(DaoUtilsStore.getInstance().getUserDaoUtils().queryAll().size()));
                Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == FileManageFragment.REFRESH) {
                listView.invalidate();
                if(scanList.size() >20){
                    files = scanList.subList(0,20);
                }else {
                    files = scanList.subList(0,scanList.size());
                }

                for(int i=0;i<files.size();i++){
                    checkNumber.put(i,false);
                }
                adapter = new fileAdapter(files,getActivity(),checkNumber);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            super.handleMessage(msg);
        }
    };

    class refreshUi implements Runnable{
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                Message message = new Message();
                message.what = FileManageFragment.REFRESH;
                FileManageFragment.this.handler.sendMessage(message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: 有什么发生吗");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}