package com.example.cyberclass2077.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.bean.CourseBean;
import com.example.cyberclass2077.controllers.CircleImageView;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.pictureselector.FileUtils;
import com.example.cyberclass2077.views.CourseVideoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class CourseAdapter extends BaseAdapter {
    private Context context;
    private List<CourseBean> listDynamicBean;
    private LayoutInflater inflater;


    public CourseAdapter(Context context,List<CourseBean> listDynamicBean)
    {
        this.context=context;
        this.listDynamicBean=listDynamicBean;
        this.inflater=LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return listDynamicBean.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        if(inflater==null)
        {
            inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.course_list_layout,null);
            viewHolderGroup=new ViewHolderGroup();
            viewHolderGroup.image_to_videoView=convertView.findViewById(R.id.to_course_video);
            viewHolderGroup.course_uploader=convertView.findViewById(R.id.course_uploader_image);
            viewHolderGroup.txt_video_title=convertView.findViewById(R.id.course_video_title);
            viewHolderGroup.txt_nick_name=convertView.findViewById(R.id.course_nick_name);
            viewHolderGroup.txt_remark=convertView.findViewById(R.id.course_remark);
            viewHolderGroup.ibtn_favorite=convertView.findViewById(R.id.course_favorite);
            viewHolderGroup.itbn_download=convertView.findViewById(R.id.course_download);
        }else
        {
            viewHolderGroup=(ViewHolderGroup)convertView.getTag();
        }
            Log.e("this is test",String.valueOf(viewHolderGroup.image_to_videoView));

        viewHolderGroup.image_to_videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CourseVideoActivity.class);
                    intent.putExtra("islocal",false);
                    intent.putExtra("VideoPath","http://47.100.99.130:8080/CyberClass2077/test.mp4");
                    //intent.putExtra("VideoPath","/tencent/TIMfile_recv/20190310_174858.mp4");
                    context.startActivity(intent);
                }
            }
        );

        viewHolderGroup.itbn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoad("http://47.100.99.130:8080/CyberClass2077/test.mp4","test.mp4");
               // downLoad("http://www.google.com","nothing");
//                Log.e("download_test", "点击函数" );
//                new Thread(new Runnable() {
//                    @Override
//                        public void run() {
//                        try{
//                            Log.e("download_test", "进入下载" );
//                            URL url = new URL("http://47.100.99.130:8080/CyberClass2077/test.mp4");
//                            //打开连接
//                            Log.e("download_test", "获取URL" );
//                            URLConnection conn = url.openConnection();
//                            //打开输入流
//                            Log.e("download_test", "打开输入流" );
//                            InputStream is = conn.getInputStream();
//                            //获得长度
//                            int contentLength = conn.getContentLength();
//                            //创建文件夹 MyDownLoad，在存储卡下
//                            String dirName = Environment.getExternalStorageDirectory() + "/MyDownLoad/";
//                            File file = new File(dirName);
//                            Log.e("download_test", "创建文件夹前" );
//                            //不存在则创建
//                            if (!file.exists()) {
//                                file.mkdir();
//                            }
//                            Log.e("download_test", "创建文件夹" );
//                            //下载后的文件名
//                            String fileName = dirName + "test" +".mp4";
//                            File file1 = new File(fileName);
//                            //存在则删除
//                            if (file1.exists()) {
//                                file1.delete();
//                            }
//                            //创建字节流
//                            Log.e("download_test", "创建文件名" );
//                            byte[] bs = new byte[1024];
//                            int len;
//                            OutputStream os = new FileOutputStream(fileName);
//                            //写数据
//                            Log.e("download_test", "开始下载" );
//                            while ((len = is.read(bs)) != -1) {
//                                os.write(bs, 0, len);
//                            }
//                            //完成后关闭流
//                            os.close();
//                            is.close();
//                            Log.e("download_test", "下载完成了~" + dirName );
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//
            }
        });

        return convertView;
    }

    class ViewHolderGroup{
        ImageView image_to_videoView;
        CircleImageView course_uploader;
        TextView txt_video_title;
        TextView txt_nick_name;
        TextView txt_remark;
        ImageButton ibtn_favorite;
        ImageButton itbn_download;
    }

    public  void downLoad(final String path, final String FileName) {
        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("download_test", "进入下载" );
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(5000);
                    con.setReadTimeout(5000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("POST");
                    Log.e("download_test", "进入下载2" );
                    Log.e("download_test", String.valueOf(con.getResponseCode()));
                    Log.e("download_test", "进入下载3" );

                    if (con.getResponseCode() == 200) {
                        Log.e("download_test", "http正常" );
                        con.setRequestMethod("GET");
                        InputStream is = con.getInputStream();//获取输入流
                        FileOutputStream fileOutputStream = null;//文件输出流
                        if (is != null) {
                            FileUtils fileUtils = new FileUtils();
                            fileOutputStream = new FileOutputStream(fileUtils.createFile(FileName));//指定文件保存路径，代码看下一步
                            byte[] buf = new byte[1024];
                            int ch;
                            Log.e("download_test", "将获取到的流写入文件中" );
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class FileUtils {
        private String path = Environment.getExternalStorageDirectory().toString() + "/Mydownload";

        public FileUtils() {
            File file = new File(path);
            /**
             *如果文件夹不存在就创建
             */
            if (!file.exists()) {
                file.mkdirs();
            }
        }

        /**
         * 创建一个文件
         * @param FileName 文件名
         * @return
         */
        public File createFile(String FileName) {
            return new File(path, FileName);
        }
    }
}
