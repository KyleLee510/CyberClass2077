package com.example.cyberclass2077.views;

import android.content.Context;
import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView txt_cancel;
    private TextView txt_save;
    private TextView txt_hint_right;
    private TextView txt_hint_equal;
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_new_passwordAgain;

    //在这里声明其他引用变量
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserStore userStore;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change_layout);
        initDependencies();
        initWidget();
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

    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        userStore = UserStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(userStore);
        user = userStore.getUser();
    }

    @Subscribe
    public void onUpdatePasswordEvent(UserStore.UpdatePasswordEvent event) {
        if(event.isUpdatePasswordSuccessful) {
            Toast.makeText(this,
                    String.format("修改成功!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
        else {
            Toast.makeText(this,
                    String.format("更改失败! %n，%s!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    void initWidget() {
        txt_cancel = (TextView) findViewById(R.id.txt_cancel_changePassword);
        txt_save = (TextView) findViewById(R.id.txt_save_changePassword);
        txt_hint_right = (TextView) findViewById(R.id.txt_hint_equal_old);
        txt_hint_equal = (TextView) findViewById(R.id.txt_hint_equal_new);
        et_old_password = (EditText) findViewById(R.id.et_change_password_now_password);
        et_new_password = (EditText) findViewById(R.id.et_change_password_new_password);
        et_new_passwordAgain = (EditText) findViewById(R.id.et_change_password_confirm_password);
        setEdittext();
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
        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = et_new_password.getText().toString();
                String newPasswordAgain = et_new_passwordAgain.getText().toString();
                if (newPassword.equals(newPasswordAgain)) {
                    actionsCreator.updatePassword(user.getUserName(), newPassword);//密码一致才会去注册
                }

            }
        });
    }

    void setEdittext() {
        //et_old_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
        et_old_password.addTextChangedListener(new JumpTextWatcher(et_old_password, et_new_password, true));
        et_new_password.addTextChangedListener(new JumpTextWatcher(et_new_password, et_new_passwordAgain, false));
        et_new_passwordAgain.addTextChangedListener(new JumpTextWatcher(et_new_passwordAgain));
    }

    private class JumpTextWatcher implements TextWatcher {
        private EditText editText1;
        private EditText editText2;
        private boolean isFirst;
        JumpTextWatcher(EditText editTextFirst, EditText editTextNext, boolean is) {
            editText1 = editTextFirst;
            editText2 = editTextNext;
            isFirst = is;
        }
        JumpTextWatcher(EditText editTextLast) {
            editText1 = editTextLast;
            editText2 = null;
            isFirst = false;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            //对于旧用户名的检测
            if(isFirst) {
                String oldpassWord = editText1.getText().toString();
                if(oldpassWord.equals(user.getPassWord())) {
                    txt_hint_right.setVisibility(View.INVISIBLE);
                }
                else {
                    txt_hint_right.setVisibility(View.VISIBLE);
                }
            }
            //对于两次新密码的输入匹配问题
            String passWord = et_new_password.getText().toString();
            String passWordAgain = et_new_passwordAgain.getText().toString();

            if(!passWord.equals(passWordAgain)) {
                txt_hint_equal.setVisibility(View.VISIBLE);

            }
            else {
                txt_hint_equal.setVisibility(View.INVISIBLE);
            }
        }
    }
}
