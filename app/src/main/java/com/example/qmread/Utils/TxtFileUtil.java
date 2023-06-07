package com.example.qmread.Utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okio.Utf8;

public class TxtFileUtil {
    private static final String TAG = "TxtFileUtil";

    public static void cutFile(String filePath, Context context) {
        //定义一个字符串用来储存读入的小说内容
        StringBuilder src = new StringBuilder();
        //文件输入流，个人喜欢用流
        try (FileInputStream fis = new FileInputStream(filePath)) {
            //从指定路径读取小说
            byte[] bt = new byte[5440];//一个页面5440字节
//            int rlength = fis.read(bt, 0, 5440);
            //for循环读取数据，保存在src中
            while (fis.read(bt, 0, 5440) != -1) {
                String temp = new String(bt, StandardCharsets.UTF_8);
                Log.i(TAG, "cutFile: " + temp);
                src.append(temp);
            }
//            //传输的实际byte[]二进制报文流
//            byte[] file_byte = new byte[rlength];
//            if (file_byte.length >= 0)
//                System.arraycopy(bt, 0, file_byte, 0, file_byte.length);
//            src = new StringBuilder(Arrays.toString(file_byte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //记得关闭流
        cutLog(src.toString(),context);
    }

    public static void cutLog(String src, Context context) {
        //匹配规则
        String pest = "(正文)?(\\s|\\n)(第)([\\u4e00-\\u9fa5a-zA-Z0-9]{1,7})[章][^\\n]{1,35}(|\\n)";//[章节卷集部回]( )
        //替换规则
        String washpest = "(PS|ps)(.)*(|\\n)";
        //将小说内容中的PS全部替换为“”
        src = src.replaceAll(washpest, "");
        //list用来储存章节内容
        List<String> list = new ArrayList<>();
        List<String> namelist = new ArrayList<>();
        //根据匹配规则将小说分为一章一章的，并存到list
        Collections.addAll(list, src.split(pest));
        Log.i(TAG, "size--"+ list.size());
        //java正则匹配
        Pattern p = Pattern.compile(pest);
        Matcher m = p.matcher(src);
        int i = 1, j = 1;
        //存拼接章节内容和章节名后的内容
        List<String> newlist = new ArrayList<>();
        //临时字符串
        String newstr;
        //循环匹配
        while (m.find()) {
            Log.i(TAG, "cutCatlog: 0000000000000");
            //替换退格符
            String temp = Objects.requireNonNull(m.group(0)).replace(" ", "").replace("\r", "");
            if (i == list.size())
                break;
            //拼接章节名和内容
            newstr = temp + list.get(i);
            i++;
            newlist.add(newstr);
            //添加章节名在list,过滤干扰符号
            temp = temp.replaceAll("[（](.)*[）]", "").replace("：", "");
            temp = temp.replace("\\", "").replace("/", "").replace("|", "");

            temp = temp.replace("?", "").replace("*", "").replaceAll("[(](.)*[)]", "");
            //System.out.println("j=" + j + " temp=" + temp + ".txt");
            Log.i(TAG, "cutCatlog: "+"j=" + j + " temp=" + temp + ".txt");
            j++;
            namelist.add(temp.replace("\n", ".txt"));
        }
        //2.创建目录
        saveFile(newlist,namelist,context);
    }

    public static List<String> cutCatlog(String src) {
        //匹配规则
        String pest = "(正文)?(\\s|\\n)(第)([\\u4e00-\\u9fa5a-zA-Z0-9]{1,7})[章][^\\n]{1,35}(|\\n)";//[章节卷集部回]( )
        //替换规则
        String washpest = "(PS|ps)(.)*(|\\n)";
        //将小说内容中的PS全部替换为“”
        src = src.replaceAll(washpest, "");
        //list用来储存章节内容
        List<String> list = new ArrayList<>();
        List<String> namelist = new ArrayList<>();
        //根据匹配规则将小说分为一章一章的，并存到list
        Collections.addAll(list, src.split(pest));
        Log.i(TAG, "size--"+ list.size());
        //java正则匹配
        Pattern p = Pattern.compile(pest);
        Matcher m = p.matcher(src);
        int i = 1, j = 1;
        //存拼接章节内容和章节名后的内容
        List<String> newlist = new ArrayList<>();
        //临时字符串
        String newstr;
        //循环匹配
        while (m.find()) {
            Log.i(TAG, "cutCatlog: 0000000000000");
            //替换退格符
            String temp = Objects.requireNonNull(m.group(0)).replace(" ", "").replace("\r", "");
            if (i == list.size())
                break;
            //拼接章节名和内容
            newstr = temp + list.get(i);
            i++;
            newlist.add(newstr);
            //添加章节名在list,过滤干扰符号
            temp = temp.replaceAll("[（](.)*[）]", "").replace("：", "");
            temp = temp.replace("\\", "").replace("/", "").replace("|", "");

            temp = temp.replace("?", "").replace("*", "").replaceAll("[(](.)*[)]", "");
            //System.out.println("j=" + j + " temp=" + temp + ".txt");
            Log.i(TAG, "cutCatlog: "+"j=" + j + " temp=" + temp + ".txt");
            j++;
            namelist.add(temp.replace("\n", ".txt"));
        }

        return newlist;
    }



    public static void saveFile(List<String> contentList,List<String> nameList,Context context){
        File file = new File(String.valueOf(context.getFilesDir()));
        if (!file.exists()) {
            file.mkdir();
        }
        String filedir = file.getPath();

        //循环生成章节TXT文件
        for (int i = 0; i < contentList.size(); i++) {
            System.out.println("catname="+filedir+File.separator+nameList.get(i));
            //2.在目录下创建TXT文件
            StringBuilder ctl = new StringBuilder(nameList.get(i));
            String bloodbath = filedir + "\\" + ctl.append(".txt");
            System.out.println(bloodbath);

            File book = new File(bloodbath);

            FileWriter fr = null;
            try {
                fr = new FileWriter(book);
                fr.write(contentList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    assert fr != null;
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
