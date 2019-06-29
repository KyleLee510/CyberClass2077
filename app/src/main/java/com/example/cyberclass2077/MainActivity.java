package com.example.cyberclass2077;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.support.v4.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //nothing change
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fraement_layout);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        BottomNavigationView navView = findViewById(R.id.bnv);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment=null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectFragment=new Fragment1();
                    break;
                case R.id.navigation_dashboard:
                    selectFragment=new Fragment2();
                    break;
                case R.id.navigation_notifications:
                    selectFragment=new Fragment3();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectFragment).commit();
            return true;
        }
    };
}
