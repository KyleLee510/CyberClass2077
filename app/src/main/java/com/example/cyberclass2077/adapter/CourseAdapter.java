package com.example.cyberclass2077.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.Toast;
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

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

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

    public void addPicture(int position, int courseId, Bitmap bitmap) {
        //通过位置来定位，找id匹配的
        if(listDynamicBean.get(position).getCourseID() == courseId) {
            listDynamicBean.get(position).img_cover = bitmap;
        }
    }



    ViewHolderGroup viewHolderGroup;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {
            inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.course_list_layout,null);
            viewHolderGroup=new ViewHolderGroup();
            viewHolderGroup.image_to_videoView=convertView.findViewById(R.id.to_course_video); //视频播放和封面
            viewHolderGroup.course_uploader=convertView.findViewById(R.id.course_uploader_image); //用户头像
            viewHolderGroup.txt_video_title=convertView.findViewById(R.id.course_video_title); //视频标题
            viewHolderGroup.txt_nick_name=convertView.findViewById(R.id.course_nick_name);  //用户名
            viewHolderGroup.txt_remark=convertView.findViewById(R.id.course_remark);    //标签
            viewHolderGroup.ibtn_favorite=convertView.findViewById(R.id.course_favorite); //点赞
            viewHolderGroup.itbn_download=convertView.findViewById(R.id.course_download); //下载
            convertView.setTag(viewHolderGroup);
        }
        else
        {
            viewHolderGroup=(ViewHolderGroup)convertView.getTag();
        }
        //点击视频播放
        viewHolderGroup.image_to_videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseVideoActivity.class);
                intent.putExtra("islocal",false);
                intent.putExtra("VideoPath",listDynamicBean.get(position).getVideoURL());
                //intent.putExtra("VideoPath","/tencent/TIMfile_recv/20190310_174858.mp4");
                context.startActivity(intent);
            }
        });

        //点击下载
        viewHolderGroup.itbn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "开始下载,下载地址为/Mydownload/", Toast.LENGTH_SHORT).show();
                downLoad(listDynamicBean.get(position).getVideoURL(),String.format(listDynamicBean.get(position).getVideoTitle(),".mp4"));
            }
        });

        //点击收藏
        viewHolderGroup.ibtn_favorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(listDynamicBean.get(position).getfavrot())  //收藏颜色
                {
                    Log.e("this is log","true");
                    listDynamicBean.get(position).setFavorite(false);
                    Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                    viewHolderGroup.ibtn_favorite.setColorFilter(Color.parseColor("#aaaaaa"));

                }
                else
                {
                    Log.e(TAG, "onClick: " );
                    listDynamicBean.get(position).setFavorite(true);
                    Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                    viewHolderGroup.ibtn_favorite.setColorFilter(Color.parseColor("#FF5C5C"));
                }
            }
        });

        viewHolderGroup.txt_nick_name.setText(listDynamicBean.get(position).getUserNickName());  //用户名
        viewHolderGroup.txt_video_title.setText(listDynamicBean.get(position).getVideoTitle());   //视频名字
        viewHolderGroup.txt_remark.setText(listDynamicBean.get(position).getTag());   //标签
        viewHolderGroup.course_uploader.setImageBitmap(listDynamicBean.get(position).img_user); //设置视频上传头像
        viewHolderGroup.image_to_videoView.setImageBitmap(listDynamicBean.get(position).img_cover);//设置视频上传封面

        if(listDynamicBean.get(position).getfavrot())  //收藏颜色
        {
//                listDynamicBean.get(position).setFavorite(false);
            viewHolderGroup.ibtn_favorite.setColorFilter(Color.parseColor("#FF5C5C"));
        }
        else
        {
//                listDynamicBean.get(position).setFavorite(true);
            viewHolderGroup.ibtn_favorite.setColorFilter(Color.parseColor("#aaaaaa"));
        }

        return convertView;
    }

    class ViewHolderGroup{
        ImageView image_to_videoView; //视频封面
        ImageView course_uploader; //视频上传者头像
        TextView txt_video_title;
        TextView txt_nick_name;
        TextView txt_remark;

        ImageView ibtn_favorite;

        ImageButton itbn_download; //下载控件
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
                    con.setConnectTimeout(1000);
                    con.setReadTimeout(1000);
                    //con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");
                    Log.e("download_test", "进入下载2" );
                    Log.e("download_test", String.valueOf(con.getResponseCode()));
                    Log.e("download_test", "进入下载3" );


                    if (con.getResponseCode() == 200) {
                        Log.e("download_test", "http正常" );
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
