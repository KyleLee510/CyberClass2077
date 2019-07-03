package com.example.cyberclass2077.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cyberclass2077.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView txt_cancel;
    private TextView txt_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change_layout);
        initWidget();
    }

    void initWidget() {
        txt_cancel = (TextView) findViewById(R.id.txt_cancel_changePassword);
        txt_save = (TextView) findViewById(R.id.txt_save_changePassword);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, Fragment3SettingActivity.class);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });
    }
}
