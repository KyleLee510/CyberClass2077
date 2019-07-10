package com.example.cyberclass2077.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.bean.UserListBean;
import com.example.cyberclass2077.controllers.CircleImageView;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<UserListBean> listDynamicBean;
    private LayoutInflater inflater;

    public UserListAdapter(Context context,List<UserListBean> listDynamicBean)
    {
        this.context=context;
        this.listDynamicBean=listDynamicBean;
        this.inflater=LayoutInflater.from(context);
    }

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
            convertView=inflater.inflate(R.layout.followee_list_layout,null);
            viewHolderGroup=new ViewHolderGroup();
            viewHolderGroup.followee_image = convertView.findViewById(R.id.followee_list_user_image);
            viewHolderGroup.followee_nickname = convertView.findViewById(R.id.followee_list_nickname);
            viewHolderGroup.followee_sex = convertView.findViewById(R.id.followee_list_user_sex);
            viewHolderGroup.followee_isfollow = convertView.findViewById(R.id.followee_list_is_followee);



        }else
        {
            viewHolderGroup=(ViewHolderGroup)convertView.getTag();
        }
        return convertView;
    }


    class ViewHolderGroup{
        CircleImageView followee_image;
        TextView followee_nickname;
        ImageView followee_sex;
        TextView followee_isfollow;
    }
}
