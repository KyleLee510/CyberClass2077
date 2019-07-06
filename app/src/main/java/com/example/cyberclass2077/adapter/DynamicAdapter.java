package com.example.cyberclass2077.adapter;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.cyberclass2077.R;
import com.example.cyberclass2077.bean.DynamicBean;
import com.example.cyberclass2077.start_atcivity;
import com.example.cyberclass2077.views.DetailComment;

import java.util.List;

public class DynamicAdapter extends BaseAdapter {

    private Context context;
    private List<DynamicBean> listDynamicBean;
    private LayoutInflater inflater;
    public DynamicAdapter(Context context,List<DynamicBean> listDynamicBean)
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
            convertView=inflater.inflate(R.layout.dynamiclayout,null);
            viewHolderGroup=new ViewHolderGroup();
            viewHolderGroup.img_head=convertView.findViewById(R.id.id_group_img);
            viewHolderGroup.str_user_name=convertView.findViewById(R.id.id_group_name);
            viewHolderGroup.str_describe=convertView.findViewById(R.id.id_describe);
            viewHolderGroup.img_dis=convertView.findViewById(R.id.id_list_img);
            viewHolderGroup.int_amount_favorite=convertView.findViewById(R.id.id_amount_favorite);
            viewHolderGroup.str_first_comemnt=convertView.findViewById(R.id.id_first_comment);
            viewHolderGroup.int_amout_comment=convertView.findViewById(R.id.id_amount_comment);
            viewHolderGroup.str_time=convertView.findViewById(R.id.id_time_publish);
            viewHolderGroup.img_favorite=convertView.findViewById(R.id.id_favorite);
            viewHolderGroup.img_chat=convertView.findViewById(R.id.id_chat);
            convertView.setTag(viewHolderGroup);
        }else
        {
            viewHolderGroup=(ViewHolderGroup)convertView.getTag();
        }

        viewHolderGroup.img_head.setImageResource(R.drawable.ic_close_black_24dp);
        viewHolderGroup.img_chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.e("expand", "onClick: " );
            }
        });
        viewHolderGroup.str_first_comemnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context, DetailComment.class);
                context.startActivity(intent1);
            }
        });
//        viewHolderGroup.str_first_comemnt

        return convertView;
    }
    class ViewHolderGroup {
        ImageView img_head;
        TextView str_user_name;
        ImageView img_dis;
        TextView int_amout_comment;
        TextView str_describe;
        TextView int_amount_favorite;
        TextView str_first_comemnt;
        TextView str_time;
        ImageView img_favorite;
        ImageView img_chat;
    }
}
