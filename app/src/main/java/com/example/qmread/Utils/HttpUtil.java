package com.example.qmread.Utils;

import android.database.Observable;
import android.util.Log;

import com.example.qmread.Utils.callback.BaseObserver;
import com.example.qmread.Utils.callback.JsonCallBack;
import com.example.qmread.Utils.entity.Book;
import com.example.qmread.Utils.entity.JsonModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static java.lang.String.valueOf;

public class HttpUtil {
    OkHttpClient client = new OkHttpClient();


    /**
     * 工具拦截器
     */
    public static class InterceptorUtil {
        public static final String TAG = "------";

        public static HttpLoggingInterceptor LogInterceptor() {     //日志拦截器,用于打印返回请求的结果
            return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NotNull String message) {
                    Log.w(TAG, "log:" + message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        public static Interceptor HeaderInterceptor() {      //头部添加请求头信息
            return new Interceptor() {
                @Override
                public @NotNull Response intercept(@NotNull Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().
                            addHeader("Content-Type", "application/json;charSet=UTF-8").build();
                    return chain.proceed(request);
                }
            };
        }
    }

    /* 请求方法类
     */
    public interface ApiService {
        //https://api.apiopen.top/musicDetails?id=604392760  接口完整路径
        /**
         * get请求方式
         *
         * @Query 造成单个查询参数, 将接口url中追加相似于"page=1"的字符串,造成提交给服务器端的参数,
         * 主要用于Get请求数据，用于拼接在拼接在url路径后面的查询参数，一个@Query至关于拼接一个参数
         */
        String HOST = "https://api.apiopen.top";        //接口地址

        @GET("/musicDetails")
        Observable<BaseResponse<Book>> getConcat(@Query("id") String id);

        /**
         * post请求方式
         */
        @FormUrlEncoded         //post请求必需要申明该注解
        @POST("musicDetails")
        //方法名
        Observable<BaseResponse<Book>> getInfo(@Field("id") String data);//请求参数
    }

    /**
     * get请求
     *
     * @param address 请求地址
     * @param callback 结果回调
     */
    public static void sendGetRequest(final String address, BaseObserver callback) {
        new Thread(new Runnable() {
            HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-type", "text/html");
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.setRequestProperty("contentType", "utf-8");
                    connection.setConnectTimeout(60 * 1000);
                    connection.setReadTimeout(60 * 1000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e("Http", "网络错误异常！!!!");
                    }
                    InputStream in = connection.getInputStream();
                    Log.d("Http", "connection success");
                    if (callback != null) {
                        callback.onFinish(in);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Http", e.toString());
                    if (callback != null) {
                        callback.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 网络通信测试请求
     *
     * @param address 请求地址
     * @param callback 结果回调
     */
    public static void sendTestGetRequest(final String address, BaseObserver callback) {
        new Thread(new Runnable() {
            HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-type", "text/html");
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.setRequestProperty("contentType", "utf-8");
                    connection.setConnectTimeout(3 * 1000);
                    connection.setReadTimeout(3 * 1000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e("Http", "网络错误异常！!!!");
                    }
                    InputStream in = connection.getInputStream();
                    Log.d("Http", "connection success");
                    if (callback != null) {
                        callback.onFinish(in);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Http", e.toString());
                    if (callback != null) {
                        callback.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * post请求
     *
     * @param address 请求地址
     * @param output 上传的参数
     * @param callback 结果回调
     */
    public static void sendPostRequest(final String address, final String output, BaseObserver callback) {
        new Thread(new Runnable() {
            HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(60 * 1000);
                    connection.setReadTimeout(60 * 1000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    if (output != null) {
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(output);
                    }
                    InputStream in = connection.getInputStream();
                    if (callback != null) {
                        callback.onFinish(in);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 图片发送
     *
     * @param address 请求地址
     * @param callback 结果回调
     */
    public static void sendBitmapGetRequest(final String address, BaseObserver callback) {
        new Thread(new Runnable() {
            HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-type", "application/octet-stream");
                    connection.setRequestProperty("Accept-Charset", "utf-8");
                    connection.setRequestProperty("contentType", "utf-8");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e("Http", "网络错误异常！!!!");
                    }
                    Log.d("Http", "connection success");
                    if (callback != null) {
                        callback.onFinish(in);
                    }
                } catch (Exception e) {
                    Log.e("Http", e.toString());
                    if (callback != null) {
                        callback.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

        }).start();
    }


    /**
     * 生成URL
     *
     * @param p_url url
     * @param params 参数
     * @return URL
     */
    public static String makeURL(String p_url, Map<String, Object> params) {

        boolean isRSA = false;
        if (params == null) return p_url;
        StringBuilder url = new StringBuilder(p_url);
        Log.d("http", p_url);
        if (url.indexOf("?") < 0)
            url.append('?');
        for (String name : params.keySet()) {
            Log.d("http", name + "=" + params.get(name));
            url.append('&');
            url.append(name);
            url.append('=');
            try {
                if (isRSA) {
                    if (name.equals("token")) {
                        url.append(params.get(name));
                    } else {
                        //url.append(StringHelper.encode(Base64.encodeToString(RSAUtilV2.encryptByPublicKey(valueOf(params.get(name)).getBytes(), APPCONST.publicKey), Base64.DEFAULT).replace("\n", "")));
                    }
                } else {
                    url.append(params.get(name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url.toString().replace("?&", "?");
    }

    /**
     * 多文件上传请求
     *
     * @param url
     * @param files
     * @param params
     * @param callback
     */
    public static void uploadFile(String url, ArrayList<File> files, Map<String, Object> params, BaseObserver callback) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (File file : files) {
            if (file != null) {
                // MediaType.parse() 里面是上传的文件类型。
                RequestBody body = RequestBody.create(MediaType.parse("*/*"), file);
                String filename = file.getName();
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                requestBody.addFormDataPart(file.getName(), file.getName(), body);
            }
        }

        if (params != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : params.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).tag(MyApplication.getApplication()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
                Log.i("Http", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    callback.onFinish(str);
                    Log.i("Http", response.message() + " , body " + str);
                } else {
                    JsonModel jsonModel = new JsonModel();
                    jsonModel.setSuccess(false);
                    callback.onFinish(new Gson().toJson(jsonModel));
                    Log.i("Http", response.message() + " error : body " + response.body().string());
                }
            }
        });
    }

    /**
     * 多文件上传的方法
     *
     * @param actionUrl：上传的路径
     * @param uploadFilePaths：需要上传的文件路径，数组
     * @return
     */

    private static void uploadFile(String actionUrl, Map<String, Object> params, String[] uploadFilePaths, JsonCallBack callback) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String CHARSET = "utf-8"; //设置编码
        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            Log.i("http", "开始上传文件");
            // 统一资源
            URL url = new URL(makeURL(actionUrl, params));
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置请求内容类型
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            for (int i = 0; i < uploadFilePaths.length; i++) {
                String uploadFile = uploadFilePaths[i];
                String filename = uploadFile.substring(uploadFile.lastIndexOf("/") + 1);
                //设置参数
                StringBuffer sb = new StringBuffer();
                sb.append(end);
                sb.append(twoHyphens + boundary + end);
                sb.append("Content-Disposition: form-data; " + "name=\"file" + i + "\";filename=\"" + filename + "\"" + end);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + end);
                sb.append(end);
                Log.i("http", "参数：" + sb.toString());
                //写入文件数据
                ds.write(sb.toString().getBytes());
                FileInputStream fStream = new FileInputStream(uploadFile);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                int total = 0;
                while ((length = fStream.read(buffer)) != -1) {
                    ds.write(buffer, 0, length);
                    total += length;
                }
                Log.i("http", "文件的大小：" + total);
                ds.writeBytes(end);
                /* close streams */
                fStream.close();
            }
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            ds.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                callback.onError(new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode()));
//               throw new Exception(
//                       "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                tempLine = null;
                resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
                Log.i("http", resultBuffer.toString());
                if (callback != null) {
                    Gson gson = new Gson();
                    JsonModel jsonModel = gson.fromJson(resultBuffer.toString(), JsonModel.class);
//                    if (URLCONST.isRSA && !StringHelper.isEmpty(jsonModel.getResult())) {
//                        jsonModel.setResult(StringHelper.decode(new String(RSAUtilV2.decryptByPrivateKey(Base64.decode(jsonModel.getResult().replace("\n", ""), Base64.DEFAULT), APPCONST.privateKey))));
//                    }
                    callback.onFinish(jsonModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                try {
                    ds.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
