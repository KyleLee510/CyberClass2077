package com.example.cyberclass2077.views.Comment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.adapter.CommentExpandAdapter;
import com.example.cyberclass2077.bean.CommentBean;
import com.example.cyberclass2077.bean.CommentDetailBean;
import com.example.cyberclass2077.bean.ReplyDetailBean;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.Comment;
import com.example.cyberclass2077.stores.CommentStore;
import com.example.cyberclass2077.stores.UserStore;
import com.example.cyberclass2077.view.CommentExpandableListView;
import com.example.cyberclass2077.views.MainActivity;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * by moos on 2018/04/20
 */
public class DetailComment extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailComment";
    private android.support.v7.widget.Toolbar toolbar;
    private TextView bt_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    private BottomSheetDialog dialog;

    private CommentStore commentStore;
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private Comment comment1=new Comment();
    private Integer dynamicId;
    private String str_publish_userName;
    private List<CommentDetailBean>getCommentsDetailBeanList=new ArrayList<>();
    private Bitmap userBitmap;


    private void initDependencies() {

        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        commentStore = CommentStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(commentStore);
        commentStore.register(this);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        commentStore.register(this);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        commentStore.unregister(this);
//    }



    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"程序猿\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"时间是一切财富中最宝贵的财富。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\t\"commentId\": \"42\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"设计狗\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"这世界要是没有爱情，它在我们心中还会有什么意义！这就如一盏没有亮光的走马灯。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"commentId\": \"41\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"产品喵\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"笨蛋自以为聪明，聪明人才知道自己是笨蛋。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
        setContentView(R.layout.comment_detail_layout);
        ImageView image = (ImageView) findViewById(R.id.detail_page_image);//内容图片
        //下面这句表示在intent中拿到bitmap对应的数组
        byte[]res = getIntent().getByteArrayExtra("ContentPicture");
        image.setImageBitmap(getPicFromBytes(res,null));

        ImageView image1 = (ImageView) findViewById(R.id.detail_page_userLogo);//用户头像
        //下面这句表示在intent中拿到bitmap对应的数组
        byte[]res1 = getIntent().getByteArrayExtra("portrait");
        image1.setImageBitmap(getPicFromBytes(res1,null));
        userBitmap=getPicFromBytes(res1,null);
        //内容
        String str_content=getIntent().getStringExtra("Content");
        Log.e(TAG, "onCreate: "+str_content );
        TextView textView=findViewById(R.id.detail_page_story);
        textView.setText(str_content);

        //用户名
        String str_user_name=getIntent().getStringExtra("username");
        TextView text_userName=findViewById(R.id.detail_page_userName);
        text_userName.setText(str_user_name);

        //时间
        String str_time=getIntent().getStringExtra("time");
        TextView text_time=findViewById(R.id.detail_page_time);
        text_time.setText(str_time);



        //获取Dynamicid
        String sID=getIntent().getStringExtra("Dynamic_ID");
        Log.e(TAG, "onCreate: "+sID );
        comment1.setDynamicId(Integer.parseInt(sID));
        dynamicId=Integer.parseInt(sID);

        //获取用户userName填充comment1
        str_publish_userName=UserStore.getInstance().getUser().getUserName();
        comment1.setPublisherName(str_publish_userName);
        actionsCreator.getComments(dynamicId);
        initView();
    }

    //下面的这个方法是将byte数组转化为Bitmap对象的一个方法
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {

        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,  opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;

    }




    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("详情");
        commentsList = generateTestData();
//        initExpandableListView(commentsList);
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList,List<Bitmap> bitmapList,Bitmap userBitmap){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList,bitmapList,userBitmap);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(DetailComment.this,"点击了回复",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }


    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData(){
        Gson gson = new Gson();
        commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = new ArrayList<>();
        return commentList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            ToNextActivity.to_NextActivityFinish(DetailComment.this,
                    MainActivity.class,
                    ToNextActivity.FRAGMENT2);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.detail_page_do_comment){
            showCommentDialog();
        }
    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean("小明", commentContent,"刚刚");
                    adapter.addTheCommentData(detailBean);
//                    Toast.makeText(DetailComment.this,"评论成功",Toast.LENGTH_SHORT).show();
                    comment1.setContent(commentContent);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=new Date(System.currentTimeMillis());
                    String time=simpleDateFormat.format(date);
                    comment1.setCommentTime(time);

                    Log.e(TAG, "onClick: comment" );

                    actionsCreator.sendComment(comment1);
//                    actionsCreator.getComments(dynamicId);

                }else {
                    Toast.makeText(DetailComment.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
//        bt_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String replyContent = commentText.getText().toString().trim();
//                if(!TextUtils.isEmpty(replyContent)){
//
//                    dialog.dismiss();
//                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
//                    adapter.addTheReplyData(detailBean, position);
//                    expandableListView.expandGroup(position);
//                    Toast.makeText(DetailComment.this,"回复成功",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(DetailComment.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    @Subscribe
    public void onSendComent(CommentStore.SendCommentEvent event)
    {
        if(event.isSendCommentSuccessful) {
            Toast.makeText(this,
                    String.format("评论成功!"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Subscribe
    public  void onGetComements(CommentStore.GetCommentsEvent event)
    {
        Log.e(TAG, "onGetComements1: "+event.commentList.size() );

        if(event.isGetCommentsSuccessful)
        {
            for(int i=0;i<event.commentList.size();i++)
            {
                String str_nickName=event.commentList.get(i).getPublisherName();
                String str_time=event.commentList.get(i).getCommentTime();
                String str_content=event.commentList.get(i).getContent();
                CommentDetailBean commentDetailBean=new CommentDetailBean(str_nickName,str_time,str_content);
                getCommentsDetailBeanList.add(commentDetailBean);
            }
            initExpandableListView(getCommentsDetailBeanList,event.comment_portrait_list,userBitmap);
            Log.e(TAG, "onGetComements: "+event.commentList.size() );
            Toast.makeText(this,
                String.format("加载评论成功"),
                Toast.LENGTH_SHORT
        ).show();
        }
    }


}