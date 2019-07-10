package com.example.cyberclass2077.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.bean.DynamicItem;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.views.Comment.DetailComment;

import java.util.List;


public class DynamicRecycleAdapter extends RecyclerView.Adapter<DynamicRecycleAdapter.DynamicBeanHolder> {
    private Context mContext;
    private List<DynamicItem> mDynamicBeans;

    public DynamicRecycleAdapter(List<DynamicItem> DynamicBeans, Context context) {
        mDynamicBeans= DynamicBeans;
        mContext = context;
    }

    @Override
    public DynamicBeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dynamiclayout, parent, false);
        return new DynamicBeanHolder(view);
    }

    @Override
    public void onBindViewHolder(final DynamicBeanHolder holder, int position) {
        final DynamicItem dynamicBean = mDynamicBeans.get(position);
        holder.bind(dynamicBean);  //调用holder的数值赋予
        //提供评论跳转
        holder.img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailComment.class);
                intent.putExtra("Dynamic_ID", dynamicBean.int_dynamic);
                mContext.startActivity(intent);
            }
        });



    }
    //获取List的数量来决定显示数量
    @Override
    public int getItemCount() {
        return mDynamicBeans.size();
    }

    //添加新动态
    public void addData(int position, DynamicItem newDynamic) {
        mDynamicBeans.add(newDynamic);
        //添加动画
        notifyItemInserted(position);
    }

    //删除数据
    public void removeData(int position, DynamicItem deleteDynamicBean) {
        mDynamicBeans.remove(deleteDynamicBean);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    //ViewHold容纳View
    public class DynamicBeanHolder extends RecyclerView.ViewHolder {
        ImageView img_portrait; //用户头像
        TextView txt_userName;  //用户名
        TextView txt_content;   //用户发布文本内容
        ImageView img_photo;    //用户发布图片内容
        ImageView img_favorite; //用户点赞
        ImageView img_chat; //跳转评论
        TextView txt_publishTime; //动态发布时间


        public DynamicBeanHolder(@NonNull View itemView) {
            super(itemView);
            img_portrait = itemView.findViewById(R.id.id_group_img);
            txt_userName = itemView.findViewById(R.id.id_group_name);
            txt_content = itemView.findViewById(R.id.id_describe);
            img_photo = itemView.findViewById(R.id.id_list_img);
            img_favorite = itemView.findViewById(R.id.id_favorite);
            img_chat = itemView.findViewById(R.id.id_chat);
            txt_publishTime = itemView.findViewById(R.id.id_time_publish);
        }
        private DynamicItem mdynamicBean;
        public void bind(DynamicItem dynamicBean){
            mdynamicBean = dynamicBean;
            img_portrait.setImageBitmap(mdynamicBean.bit_user_portrait);
            txt_userName.setText(mdynamicBean.str_user_name);
            txt_content.setText(mdynamicBean.str_describe);
            img_photo.setImageBitmap(mdynamicBean.img_dis); //用户的发表图片
            img_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp); //点赞
            img_chat.setImageResource(R.drawable.ic_chat_bubble_outline_black_24dp); //评论
            txt_publishTime.setText(mdynamicBean.str_time);
        }
    }
}
