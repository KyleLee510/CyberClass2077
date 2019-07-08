package com.example.cyberclass2077;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cyberclass2077.views.Comment.DetailComment;
import com.example.cyberclass2077.views.MainActivity;

public class start_atcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_atcivity);

        Button btn_tranfrom=findViewById(R.id.btn_transform);
        btn_tranfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_tranform_click(view);
            }
        });

        Button btn_main=findViewById(R.id.btn_main_activity);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_main_click(view);
            }
        });
    }


    public void btn_tranform_click(View v)
    {
        Intent intent=new Intent(this,DetailComment.class);
        startActivity(intent);
    }

    public void btn_main_click(View v)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
