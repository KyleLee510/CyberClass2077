package com.example.cyberclass2077.views.Dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.cyberclass2077.R;

public class DynamicPublish extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_pulish);

        //返回
        ImageButton ibtn=findViewById(R.id.dyname_publish_back_button);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //发布

    }

    @Override
    public void onClick(View v) {

    }
}
