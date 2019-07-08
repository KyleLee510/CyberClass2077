package com.example.cyberclass2077.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.bean.CourseBean;
import com.example.cyberclass2077.controllers.CircleImageView;

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
            viewHolderGroup.videoView=convertView.findViewById(R.id.course_video);
            viewHolderGroup.course_uploader=convertView.findViewById(R.id.course_uploader_image);
            viewHolderGroup.txt_video_title=convertView.findViewById(R.id.course_video_title);
            viewHolderGroup.txt_nick_name=convertView.findViewById(R.id.course_nick_name);
            viewHolderGroup.txt_remark=convertView.findViewById(R.id.course_remark);
            viewHolderGroup.ibtn_favorite=convertView.findViewById(R.id.course_favorite);
        }else
        {
            viewHolderGroup=(ViewHolderGroup)convertView.getTag();
        }
        return convertView;
    }

    class ViewHolderGroup{
        VideoView videoView;
        CircleImageView course_uploader;
        TextView txt_video_title;
        TextView txt_nick_name;
        TextView txt_remark;
        ImageButton ibtn_favorite;
    }
}
