package com.example.cyberclass2077.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

public class SignupActivity extends AppCompatActivity{
    //在这里声明控件的引用变量
    private Button vConfirmButton;
    private Button vCancelButton;
    private EditText vUserNameEditor;
    private EditText vPassWordEditor;
    private EditText vPassWordAgainEditor;
    private TextView vCheckUsernameTextView;
    private TextView vCheckPassWordTextView;
    private TextView vCheckPassWordAgainTextView;

    //在这里声明其他引用变量
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserStore userStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        initDependencies();
        initWidget();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dispatcher.unregister(userStore);
    }
    @Override
    protected void onResume() {
        super.onResume();
        userStore.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        userStore.unregister(this);
    }


    void initWidget() {
        vUserNameEditor=(EditText)findViewById(R.id.et_username_signup);
        vPassWordEditor=(EditText)findViewById(R.id.et_password_signup);
        vPassWordAgainEditor=(EditText)findViewById(R.id.et_passwordagain_signup);
        vConfirmButton = (Button) findViewById(R.id.btn_confirm_signup);
        vCancelButton = (Button) findViewById(R.id.btn_cancel_signup);
        vCheckUsernameTextView = (TextView) findViewById(R.id.txt_hint_exist);
        vCheckPassWordTextView = (TextView) findViewById(R.id.txt_hint_empty);
        vCheckPassWordAgainTextView = (TextView) findViewById(R.id.txt_hint_consistent);

        setEdittext();

        vCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });
        //信息确认提交
        vConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        userStore = UserStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(userStore);
    }
    @Subscribe
    public void onNameCheckEvent(UserStore.NameCheckEvent event) {
        //在这里写对重名检测的业务逻辑和显示逻辑
            if(event.isNameExist) {
                vCheckUsernameTextView.setVisibility(View.VISIBLE);
                vCheckUsernameTextView.setText("用户名已存在");
            }
            else {
                vCheckUsernameTextView.setVisibility(View.INVISIBLE);
            }
    }
    @Subscribe
    public void onSignupEvent(UserStore.SignupEvent event){
        //对服务器返回的注册结果做出相应
        if(event.isSignupSuccessful==true){
            Toast.makeText(this,
                    String.format("注册成功! %n欢迎您，%s!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
            //在这里加入注册成功后的页面跳转代码
            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            //需要在finish和startActivity之后进行
            //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
            overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
        }
        else{
            Toast.makeText(this,
                    String.format("注册失败! %n请重新检查您的用户名和密码!"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    void setEdittext() {
        vUserNameEditor.setImeOptions(EditorInfo.IME_ACTION_DONE);
        vUserNameEditor.setSingleLine();
        vUserNameEditor.addTextChangedListener(new JumpTextWatcher(vUserNameEditor, vPassWordEditor));
        vUserNameEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vCheckUsernameTextView.setVisibility(View.INVISIBLE);
                }
                else {
                    //失去焦点后的判断
                    String username = vUserNameEditor.getText().toString();
                    actionsCreator.nameCheck(username);
                }
            }
        });
        vPassWordEditor.addTextChangedListener(new JumpTextWatcher(vPassWordEditor, vPassWordAgainEditor));
        vPassWordAgainEditor.addTextChangedListener(new JumpTextWatcher(vPassWordAgainEditor));
    }

    private class JumpTextWatcher implements TextWatcher {
        private EditText editText1;
        private EditText editText2;
        JumpTextWatcher(EditText editTextFirst, EditText editTextNext) {
            editText1 = editTextFirst;
            editText2 = editTextNext;
        }
        JumpTextWatcher(EditText editTextLast) {
            editText1 = editTextLast;
            editText2 = null;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            vCheckPassWordTextView.setVisibility(View.INVISIBLE); //进行二次编辑是确保密码输入的text不可见
            //密码不一致的检测
            String passWord = vPassWordEditor.getText().toString();
            String passWordAgain = vPassWordAgainEditor.getText().toString();

            if(!passWord.equals(passWordAgain)) {
                vCheckPassWordAgainTextView.setVisibility(View.VISIBLE);
                vCheckPassWordAgainTextView.setText("密码不一致");
            }
            else {
                vCheckPassWordAgainTextView.setVisibility(View.INVISIBLE);
            }
        }
    }
    //注册提交后的检测
    void signUp() {
        boolean check = true;
        String userName = vUserNameEditor.getText().toString();
        String passWord = vPassWordEditor.getText().toString();
        String passWordAgain = vPassWordAgainEditor.getText().toString();
        if(userName.equals("")) {
            check = false;
            vCheckUsernameTextView.setVisibility(View.VISIBLE);
            vCheckUsernameTextView.setText("用户名不可为空");
        }
        if(passWord.equals("")) {
            check = false;
            vCheckPassWordTextView.setVisibility(View.VISIBLE);
            vCheckPassWordTextView.setText("不可为空");
        }
        if(passWordAgain.equals("")) {
            check = false;
            vCheckPassWordAgainTextView.setVisibility(View.VISIBLE);
            vCheckPassWordAgainTextView.setText("不可为空");
        }
        //如果check仍为true，则进行密码匹配，合格就注册
        if(check) {
            if(passWord.equals(passWordAgain)) {
                vCheckUsernameTextView.setVisibility(View.INVISIBLE);
                vCheckPassWordTextView.setVisibility(View.VISIBLE);
                vCheckPassWordAgainTextView.setVisibility(View.INVISIBLE);
                actionsCreator.signup(userName, passWord);
            }
            else {
                vCheckPassWordAgainTextView.setVisibility(View.VISIBLE);
                vCheckPassWordAgainTextView.setText("密码不一致");
            }
        }
    }
}
