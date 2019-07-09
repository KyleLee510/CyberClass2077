package com.example.cyberclass2077.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

public class Fragement3CollectionActivity extends AppCompatActivity {

    private ImageButton img_to_backUserlayout;
    private ListView listView_collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3_collectionlist_layout);
        initWidget();
    }
    void initWidget(){
        listView_collection = (ListView) findViewById(R.id.id_collection_list);
        List<CourseBean> courseBeanList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            CourseBean courseBean=new CourseBean();
            courseBeanList.add(courseBean);
        }

        listView_collection.setAdapter(new CourseAdapter(Fragement3CollectionActivity.this, courseBeanList));

        //跳转到用户个人主页
        img_to_backUserlayout = findViewById(R.id.img_collection_back_button);
        img_to_backUserlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fragement3CollectionActivity.this, MainActivity.class);
                intent.putExtra("fragment",2);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });
    }
}
