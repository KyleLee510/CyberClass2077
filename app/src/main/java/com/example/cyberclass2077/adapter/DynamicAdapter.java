package com.example.cyberclass2077.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.cyberclass2077.R;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.views.Comment.DetailComment;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class DynamicAdapter extends BaseAdapter {
    byte[] result;  //将bitmap转化的byte数组
    Bitmap bitmap;
    private Context context;
    private List<DynamicPublishBean> listDynamicBean;
    private List<Bitmap> listportrait;
    private LayoutInflater inflater;
    public DynamicAdapter(Context context, List<DynamicPublishBean> listDynamicBean, List<Bitmap> listportrait)
    {
        this.context = context;
        this.listDynamicBean = listDynamicBean;
        this.listportrait = listportrait;
        this.inflater = LayoutInflater.from(context);
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

    public void addDate(int position, DynamicPublishBean dynamicBean, Bitmap portrait) {
        listDynamicBean.add(position, dynamicBean);
        listportrait.add(position, portrait);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolderGroup viewHolderGroup;
        if(inflater == null)
        {
            inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.dynamiclayout,null);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.img_head = convertView.findViewById(R.id.id_group_img); //头像
            viewHolderGroup.str_user_name = convertView.findViewById(R.id.id_group_name); //用户名
            viewHolderGroup.str_describe = convertView.findViewById(R.id.id_describe); //发表内容
            viewHolderGroup.img_dis = convertView.findViewById(R.id.id_list_img);   //用户发表的图片
            viewHolderGroup.int_amount_favorite = convertView.findViewById(R.id.id_amount_favorite);
            viewHolderGroup.str_first_comemnt = convertView.findViewById(R.id.id_first_comment);
            viewHolderGroup.int_amout_comment = convertView.findViewById(R.id.id_amount_comment);
            viewHolderGroup.str_time = convertView.findViewById(R.id.id_time_publish);
            viewHolderGroup.img_favorite = convertView.findViewById(R.id.id_favorite);
            viewHolderGroup.img_chat = convertView.findViewById(R.id.id_chat);
            convertView.setTag(viewHolderGroup);
        }else
        {
            viewHolderGroup=(ViewHolderGroup)convertView.getTag();
        }

        Log.e(TAG, "getView: " );
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

        viewHolderGroup.img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClickimg_chat: " );
                bitmap = BitmapFactory.decodeResource(null,R.drawable.lizi);
                ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
                result = output.toByteArray();//转换成功了  result就是一个bit的资源数组
//                Intent intent1=new Intent(context, DetailComment.class);
//                intent1.putExtra("bitmap",result);
//                context.startActivity(intent1);
            }
        });

        viewHolderGroup.str_user_name.setText(listDynamicBean.get(position).getUserName());
        viewHolderGroup.img_head.setImageBitmap(listportrait.get(position));
        viewHolderGroup.str_time.setText(listDynamicBean.get(position).getDate());
        viewHolderGroup.int_amount_favorite.setText(listDynamicBean.get(position).getLikeNum().toString()+"👍");
        viewHolderGroup.int_amout_comment.setText(listDynamicBean.get(position).getCommentNum().toString()+"评论");
        viewHolderGroup.str_describe.setText(listDynamicBean.get(position).getContent());
//        关注
//        viewHolderGroup.img_favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(listDynamicBean.get(position).getIsLike()){
//                    listDynamicBean.get(position).setIsLike(false);
//                    viewHolderGroup.img_favorite.setColorFilter(Color.parseColor("#aaaaaa"));
////                   listDynamicBean.get(position).setInt_amount_favorite();
//
//                }else {
//                    listDynamicBean.get(position).setIsLike(true);
//                    viewHolderGroup.img_favorite.setColorFilter(Color.parseColor("#FF5C5C"));
//                }
//            }
//        });
//        if(listDynamicBean.get(position).getIsLike())
//        {
//            viewHolderGroup.img_favorite.setColorFilter(Color.parseColor("#FF5C5C"));
//        }
//        else
//        {
//            viewHolderGroup.img_favorite.setColorFilter(Color.parseColor("#aaaaaa"));
//        }
//
//        Integer int_amount_favorite=listDynamicBean.get(position).getInt_amount_favorite();
//        String str_amount_favorite=int_amount_favorite.toString();
//        viewHolderGroup.int_amount_favorite.setText(str_amount_favorite+"👍");

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
