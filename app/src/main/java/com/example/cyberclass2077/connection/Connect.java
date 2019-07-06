package com.example.cyberclass2077.connection;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.cyberclass2077.views.LoginActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.model.User;

import java.io.IOException;

import static android.os.Looper.getMainLooper;
import static android.os.Looper.loop;

public class Connect <T>{
    private  volatile String responseStr="";
    public Connect(){}

    //用这个方法建立请求的body
    //这个方法只能在requestbody中加一个key:value
    //这里的type对应key:value中的key，数据以key:value的形式放入POST请求的方法体body中，
    //不能给type随意赋值，后端是以type为参数获取请求的body中的数据的。
    public FormEncodingBuilder requestBodyBuilder(String type,T data){
        FormEncodingBuilder builder = new FormEncodingBuilder();
        String dataStr=JSON.toJSONString(data);
        builder.add(type,dataStr);


        return builder;
    }

    //用这个方法向服务器发送POST请求，返回JOSN转换成的字符串
    public void postData(String urlStr,FormEncodingBuilder builder){

        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request=new Request.Builder()
                .url(urlStr)
                .post(builder.build())
                .build();
        Call call =mOkHttpClient.newCall(request);
        final Message message=Message.obtain();
        //将请求加入队列，异步方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res=response.body().string();
                message.obj=new String(res);
                handler.sendMessage(message);
            }
        });

    }
    public void postMulitiData(String urlStr,RequestBody requestBody){

        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request=new Request.Builder()
                .url(urlStr)
                .post(requestBody)
                .build();
        Call call =mOkHttpClient.newCall(request);
        final Message message=Message.obtain();
        //将请求加入队列，异步方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res=response.body().string();
                message.obj=new String(res);
                handler.sendMessage(message);
            }
        });

    }

    public String getResponseStr() {
        return responseStr;
    }
    public void setResponseStr(String str){

        responseStr=new String(str);

    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            System.out.println("查看返回消息："+msg.obj);
            setResponseStr(msg.obj.toString());
            emitRequestResult();
        }
    };
    //仅仅作为一个接口，在handleMessage中调用的是子类重写过的方法
    public void emitRequestResult(){

    }

}
