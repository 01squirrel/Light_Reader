package com.example.qmread.Utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.provider.MediaStore.Files.FileColumns;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.documentfile.provider.DocumentFile;

import com.example.qmread.bean.FileItem;

public class FileUtil {
    private static final String TAG = "FileUtil";
    private Context context1;
    /**
     * 通过图片的 filePath 加载本地图片
     */
    public static Bitmap loadLocalPicture(String filePath) {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取指定类型文件
     * @return 文件
     */
    public static ArrayList<FileItem> getAnyTypeFile(Context context, String[] extension){
        String[] selectionArgs = new String[]{"text/plain", "application/msword", "application/pdf", "application/vnd.ms-powerpoint", "application/vnd.ms-excel"};
        ArrayList<FileItem> files = new ArrayList<>();
        //从外存中获取
        Uri fileUri = Files.getContentUri("external");
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MIME_TYPE};
        //构造筛选语句
//        StringBuilder selection = new StringBuilder();
//        for(int i = 0; i<extension.length; i++){
//           if(i!=0){
//               selection.append("or");
//           }
//           selection.append(FileColumns.DATA).append("LIKE '%").append(extension[i]).append("'");
//       }
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        String sortOrder = FileColumns.DATE_MODIFIED ;
        //获取内容解析器对象
        ContentResolver resolver = context.getContentResolver();
        //获取游标
        try (Cursor cursor = resolver.query(fileUri, projection, selection.toString(), extension, sortOrder)) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                Log.i(TAG, "getAnyTypeFile: -0-0-0-0-0-0");
                String fileSize = cursor.getString(cursor.getColumnIndex(FileColumns.SIZE));
                String fileName = cursor.getString(cursor.getColumnIndex(FileColumns.TITLE));
                String filePath = cursor.getString(cursor.getColumnIndex(FileColumns.DATA));
                String fileType = cursor.getString(cursor.getColumnIndexOrThrow(FileColumns.MIME_TYPE));
                String fileDate = cursor.getString(cursor.getColumnIndexOrThrow(FileColumns.DATE_MODIFIED));
                Log.i(TAG, fileName + " -- " + fileSize + " -- " + fileType + "--" + fileDate + "--" + filePath);

                FileItem fileItem = new FileItem(fileName, fileSize, fileDate, fileType);

                files.add(fileItem);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     return files;
    }

    /**
     * 根据TXT文件uri获取内容--有效
     */
    public static String getContent(Uri uri,Context context){
        Log.i(TAG, "getContent: "+uri.getScheme());
        ContentResolver resolver = context.getContentResolver();
        InputStream stream = null;
        InputStreamReader reader;
        try {
            stream = resolver.openInputStream(uri);;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while ((line = bufferedReader.readLine())!=null){
                sb.append(line);
                sb.append("\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        Log.i(TAG, "getContent: "+sb);
        return sb.toString();
    }

    /**
     * 检查文档元数据--有效
     */
    public static String dumpImageMetaData(Uri uri,Context context){
        ContentResolver resolver = context.getContentResolver();
        String size = null;//转换的
        String outSize = null;//输出的
        try (Cursor cursor = resolver.query(uri, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i(TAG, "Display Name: " + displayName);
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (!cursor.isNull(sizeIndex)) {
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }
                Log.i(TAG, "Size: " + size);
            }
        }
        if(size != null){
            if(Integer.parseInt(size) < 1000){
                outSize = size + "B";
                Log.i(TAG, "dumpImageMetaData: "+outSize);
                return outSize;
            }
            double kb = Math.round(Integer.parseInt(size)*0.001);
            outSize = kb +"KB";
            Log.i(TAG, "dumpImageMetaData: "+outSize);
            if(kb > 1024){
                double mb = Math.round(kb*0.001);
                outSize = mb + "MB";
                Log.i(TAG, "dumpImageMetaData: "+outSize);
            }
        }
        return outSize;
    }

    /**
     * 根据Uri打开文件-有效
     * @param uri URI
     * @param context 上下文
     * @throws IOException 异常
     * @return 打开文件
     */
    public static String readTextFromUri(Uri uri, Context context) throws IOException {
        ContentResolver resolver = context.getContentResolver();
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     resolver.openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
      return stringBuilder.toString();
    }

    /*
     *根据Uri打开图片
     */
    public Bitmap getBitmapFromUri(Uri uri,Context context) throws IOException {
        ContentResolver resolver = context.getContentResolver();
        ParcelFileDescriptor parcelFileDescriptor =
                resolver.openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * 根据URI获取文件名-有效
     * @param uri uri
     * @param context  上下文
     * @return  文件名
     */
    public static String getFileName(Uri uri,Context context){
        ContentResolver resolver = context.getContentResolver();
        if (uri == null) return null;
        DocumentFile documentFile = DocumentFile.fromSingleUri(context, uri);
        if (documentFile == null) return null;
        return documentFile.getName();
    }


    /*
     *文件扫描-暂时无效
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int scanFiles(File path){
        ArrayList<String> mFileList = new ArrayList<>();
        boolean mIsRunning = true;
        Stack<File> folderStack = new Stack<>();
        folderStack.push(path);

        long startTime = System.currentTimeMillis();

        while (!folderStack.empty()){
            path = folderStack.pop();
            File[] files = path.listFiles();

            //　内部排序
            assert files != null;
            Arrays.sort(files, (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));

            for (File value : files) {
                String fullFilePath = value.getAbsolutePath();

                if (value.isDirectory() && !"./".equals(value.getName())) {
                    folderStack.push(value);
                    mFileList.add(fullFilePath);
                } else {
                    mFileList.add(fullFilePath);
                }
            }
        }

        long timeuse = System.currentTimeMillis() - startTime;

        Log.d(TAG, String.format("TimeUse: %d", timeuse));

        Arrays.sort(mFileList.toArray());

        //　外部排序
        mFileList.sort(String::compareTo);

        Log.d(TAG, String.format("File count: %d", mFileList.size()));

//        for(int i = 0; i < mFileList.size(); i++){
//            Log.d(LOG_TAG, mFileList.get(i));
//        }

        return mFileList.size();
    }

    /**
     * 获取媒体文件（图片，音频之类的）路径方法一
     * @param fileUrl 文件URI
     * @return 文件路径
     */
    public static String getRealPath( Uri fileUrl,Context context) {
        Log.i(TAG, "getRealPath: ----"+fileUrl);
        ContentResolver resolver = context.getContentResolver();
        String fileName = null;
        String column = "_data";
        String selection = "_id=?";
        String[] projection = {column};
        if( fileUrl != null ) {
            // content://开头的uri
            //if( fileUrl.getScheme( ).compareTo( "content" ) == 0 )
            Log.i(TAG, "getRealPath: -- "+fileUrl.getScheme()+"--");
           // if(ContentResolver.SCHEME_CONTENT.equals(fileUrl.getScheme()))

                Log.i(TAG, "getRealPath: 11111111111111");
                if(DocumentsContract.isDocumentUri(context,fileUrl)){
                    Log.i(TAG, "getRealPath: 2222222222222");
                    String docId = DocumentsContract.getDocumentId(fileUrl);
                    String[] split = docId.split(":");
                    String type = split[0];
                    String[] selectionArgs = new String[]{split[1]};
                    if(isExternalStorageDocument(fileUrl)) {
                        Log.i(TAG, "getRealPath: 3333333333333333");
                        if ("primary".equalsIgnoreCase(type)) {
                            Log.i(TAG, "getRealPath: 44444444444444");
                            fileName = Environment.getExternalStorageDirectory() + "/" + split[1];
                            return fileName;
                        }
                    }else if (isDownloadsDocument(fileUrl)){
                        Log.i(TAG, "getRealPath: 55555555555555");
                        final String id = DocumentsContract.getDocumentId(fileUrl);
                        final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                                Long.parseLong(id));
                        try (Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null)) {
                            if (cursor != null && cursor.moveToFirst()) {
                                final int column_index = cursor.getColumnIndexOrThrow(column);
                                return cursor.getString(column_index);
                            }
                        }
                        return null;
                    }else if(isMediaDocument(fileUrl)){
                        Log.i(TAG, "getRealPath: 6666666666666");
                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        Cursor cursor = resolver.query( contentUri, projection, selection,selectionArgs , null );
                        if( cursor != null && cursor.moveToFirst( ) ) {
                            try {
                                int column_index = cursor.getColumnIndexOrThrow( column );
                                fileName = cursor.getString( column_index ); // 取出文件路径
                            } catch( IllegalArgumentException e ) {
                                e.printStackTrace();
                            }finally{
                                cursor.close( );
                            }
                        }
                    }
                }
             else if( ContentResolver.SCHEME_FILE.equals(fileUrl.getScheme())) // file:///开头的uri
            {
                Log.i(TAG, "getRealPath: 7777777777");
                fileName = fileUrl.getPath( );
            }else if(ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(fileUrl.getScheme())){
                Log.i(TAG, "getRealPath: 8888888888");
                if (isGooglePhotosUri(fileUrl))
                    return fileUrl.getLastPathSegment();
                Cursor cursor = resolver.query( fileUrl, projection, null,null , null );
                if( cursor != null && cursor.moveToFirst( ) ) {
                    try {
                        int column_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
                        fileName = cursor.getString( column_index ); // 取出文件路径
                    } catch( IllegalArgumentException e ) {
                        e.printStackTrace();
                    }finally{
                        cursor.close( );
                    }
                }
            }
        }
        Log.i(TAG, "getRealPath: "+fileName);
        return fileName;
    }

    /**
     * URI获取文件路径-经测试有效
     * @param uri-URI
     * @param context-context
     * @return -file
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File uriToFile(Uri uri, Context context) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(context.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    cursor.close();
                }
            }
        }
        return file;
    }

    public static String byteToMB(long size){
        long kb = 1024;
        long mb = kb*1024;
        long gb = mb*1024;
        if (size >= gb){
            return String.format("%.1f GB",(float)size/gb);
        }else if (size >= mb){
            float f = (float) size/mb;
            return String.format(f > 100 ?"%.0f MB":"%.1f MB",f);
        }else if (size > kb){
            float f = (float) size / kb;
            return String.format(f>100?"%.0f KB":"%.1f KB",f);
        }else {
            return String.format("%d B",size);
        }
    }

    public static String transformTime(File file){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date(file.lastModified());
        return sdf.format(date);
    }
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    private static boolean isMediaDocument(Uri uri) {  return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static void saveFiles(String content,Context context,String fileName){
        try{
            FileOutputStream outputStream = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            try{
                writer.write(content);
                Log.i(TAG, "saveFiles: 保存成功");
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
