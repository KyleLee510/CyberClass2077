package com.example.cyberclass2077.controllers;

import android.app.Activity;
import android.content.Intent;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.views.Fragment3DownloadActivity;
import com.example.cyberclass2077.views.MainActivity;

public class ToNextActivity {
    public static int FRAGMENT1 = 0;
    public static int FRAGMENT2 = 1;
    public static int FRAGMENT3 = 2;

    public static void to_NextActivity(Activity now, Class next) {
        Intent intent = new Intent(now, next);
        now.startActivity(intent);
        now.overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
    }

    public static void to_NextActivityFinish(Activity now, Class next) {
        Intent intent = new Intent(now, next);
        now.startActivity(intent);
        now.finish();
        //getActivity().finish();
        //需要在finish和startActivity之后进行
        //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
        now.overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
    }
    public static void to_NextActivityFinish(Activity now, Class next, int Fragment) {
        Intent intent = new Intent(now, MainActivity.class);
        intent.putExtra("fragment", Fragment);
        now.startActivity(intent);
        now.finish();
        now.overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
    }
}
