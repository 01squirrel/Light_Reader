package com.example.qmread.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class FileOperateUtil {

    /**
     * 【动态申请SD卡读写的权限】
     * Android6.0之后系统对权限的管理更加严格了，不但要在AndroidManifest中添加，还要在应用运行的时候动态申请
     **/
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //【判断是否已经授予权限】
                ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 【检查文件目录是否存在，不存在就创建新的目录】
     * @param file 文件
     * @param isDir 是否为文件夹
     **/
    public static void checkFilePath(File file, boolean isDir) {
        if (file != null) {
            if (!isDir) {     //如果是文件就返回父目录
                file = file.getParentFile();
            }
            if (file != null && !file.exists()) {
                file.mkdirs();
            }
        }
    }

    /**
     * 【创建一个新的文件夹】
     **/
    public static void addFolder(String folderName) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCard = Environment.getExternalStorageDirectory();
                File newFolder = new File(sdCard + File.separator + folderName);
                if (!newFolder.exists()) {
                    boolean isSuccess = newFolder.mkdirs();
                    Log.i("TAG:", "文件夹创建状态--->" + isSuccess);
                }
                Log.i("TAG:", "文件夹所在目录：" + newFolder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 【创建文件】
     **/
    public static void addFile(String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File newFile = new File(sdCard.getCanonicalPath() + File.separator + "testFolder/" + fileName);
                if (!newFile.exists()) {
                    boolean isSuccess = newFile.createNewFile();
                    Log.i("TAG:", "文件创建状态--->" + isSuccess);
                    Log.i("TAG:", "文件所在路径：" + newFile);
                    deleteFile(newFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 【删除文件】
     **/
    public static void deleteFile(File file) {
        if (file.exists()) {                          //判断文件是否存在
            if (file.isFile()) {                      //判断是否是文件
                boolean isSuccess = file.delete();
                Log.i("TAG:", "文件删除状态--->" + isSuccess);
            } else if (file.isDirectory()) {           //判断是否是文件夹
                File[] files = file.listFiles();    //声明目录下所有文件
                assert files != null;
                for (File value : files) {   //遍历目录下所有文件
                    deleteFile(value);           //把每个文件迭代删除
                }
                boolean isSuccess = file.delete();
                Log.i("TAG:", "文件夹删除状态--->" + isSuccess);
            }
        }
    }

    /**
     * 【重写数据到文件】
     **/
    public static void writeData(String path, String fileData) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                FileOutputStream out = new FileOutputStream(file, false);
                out.write(fileData.getBytes(StandardCharsets.UTF_8));              //将数据写入到文件中
                Log.i("TAG:", "将数据写入到文件中：" + fileData);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 【续写数据到文件】
     **/
    public static void writtenFileData(String path, String data) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                RandomAccessFile raf = new RandomAccessFile(file, "rw");  //按读写方式
                raf.seek(file.length());                                        //将文件指针移到文件尾
                raf.write(data.getBytes(StandardCharsets.UTF_8));                //将数据写入到文件中
                Log.i("TAG:", "要续写进去的数据：" + data);
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 【读取文件内容】
     **/
    public static String readFileContent(String path) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                byte[] buffer = new byte[32 * 1024];
                FileInputStream fis = new FileInputStream(file);
                int len = 0;
                StringBuilder sb = new StringBuilder("");
                while ((len = fis.read(buffer)) > 0) {
                    sb.append(new String(buffer, 0, len));
                }
                fis.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 【判断文件是否存在】
     **/
    public static boolean isFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 【判断文件夹是否存在】
     **/
    public static boolean isFolderExists(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());  //如果是文件夹并且文件夹存在则返回true
    }

    /**
     * 【获取文件夹名称】
     **/
    public static String getFolderName(String folderName) {
        if (TextUtils.isEmpty(folderName)) {
            return folderName;
        }
        int filePosi = folderName.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : folderName.substring(0, filePosi);
    }

    /**
     * 【重命名文件】
     **/
    public static boolean renameFile(String oldFileName, String newFileName) {
        File oldName = new File(oldFileName);
        File newName = new File(newFileName);
        return oldName.renameTo(newName);
    }

    /**
     * 【判断文件夹里是否有文件】
     **/
    public static boolean hasFileExists(String folderPath) {
        File file = new File(folderPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            assert files != null;
            return files.length > 0;
        }
        return false;
    }

    /**
     * 【复制文件】参数为：String
     **/
    public static void copyFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream outto = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            int len = fosfrom.read(bt);
            if (len > 0) {
                outto.write(bt, 0, len);
            }
            fosfrom.close();
            outto.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 【复制文件】参数为：File
     **/
    public static int copyFile(File formFile, File toFile) {
        try {
            InputStream forform = new FileInputStream(formFile);
            OutputStream forto = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            int len = forform.read(bt);
            if (len > 0) {
                forto.write(bt, 0, len);
            }
            forform.close();
            forto.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 【复制文件】使用：AssetManager
     **/
    public static void copyFileFormAsset(Context context, String assetFile, String toFilePath) {
        if (!new File(toFilePath).exists()) {
            try {
                AssetManager assetManager = context.getAssets();
                InputStream is = assetManager.open(assetFile);
                OutputStream os = new FileOutputStream(new File(toFilePath));
                byte[] bt = new byte[1024];
                int len = 0;
                while ((is.read(bt)) > 0) {        //循环从输入流读取
                    os.write(bt, 0, len);     //将读取到的输入流写到输出流
                }
                is.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 【复制文件夹】
     **/
    public static void copyDir(String fromFolder, String toFolder) {
        File[] currentFiles;
        File root = new File(fromFolder);
        if (!root.exists()) {                     //如果文件不存在就返回出去
            return;
        }
        currentFiles = root.listFiles();        //存在则获取当前目录下的所有文件
        File targetDir = new File(toFolder);    //目标目录
        if (!targetDir.exists()) {                //不存在就创建新目录
            targetDir.mkdirs();
        }
        //进行当前函数递归操作
        assert currentFiles != null;
        for (File currentFile : currentFiles) { //遍历currentFiles下的所有文件
            if (currentFile.isDirectory()) {  //如果当前目录为子目录
                copyDir(currentFile.getPath() + "/", currentFile.getName() + "/");  /**进行当前函数递归操作**/
            } else {                              //当前为文件，则进行文件拷贝
                copyFile(currentFile.getPath(), toFolder + currentFile.getName());
            }
        }
    }


}
