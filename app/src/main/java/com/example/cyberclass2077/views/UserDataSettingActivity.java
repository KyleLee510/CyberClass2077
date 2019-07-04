package com.example.cyberclass2077.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cyberclass2077.R;

import java.util.Calendar;

public class UserDataSettingActivity extends AppCompatActivity {

    private ImageButton to_back;

    private TextView to_DatePickerDialog;//跳转到时间选择对话框
    private Calendar cal;//显示当前日期
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data_setting_layout);

        to_back = findViewById(R.id.btn_dataSetting_back);

//        返回个人主页
        to_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件方法
                Intent intent = new Intent(UserDataSettingActivity.this, MainActivity.class);
                intent.putExtra("fragment",2);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });

        to_DatePickerDialog = (TextView)  findViewById(R.id.user_date_setting_borndate);
        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH)+1;//注意点 ，要加一
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        //日期选择界面
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        final String data =  mYear + "-" + mMonth + "-"+mDay;
                        to_DatePickerDialog.setText(data);//修改text显示
                    }
                },
                mYear, mMonth, mDay);

        //点击弹出日期选择框
        to_DatePickerDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                //点击事件方法
                datePickerDialog.show();
            }
        });


    }
}
