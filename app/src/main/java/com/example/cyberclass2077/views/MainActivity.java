package com.example.cyberclass2077.views;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v4.app.Fragment;

import com.example.cyberclass2077.R;

public class MainActivity extends AppCompatActivity {

    Fragment firstLayout;
    Fragment secondLayout;
    Fragment thirdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //nothing change
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        //透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,firstLayout).commit();
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
                    selectFragment=firstLayout;
                    break;
                case R.id.navigation_dashboard:
                    selectFragment=secondLayout;
                    break;
                case R.id.navigation_notifications:
                    selectFragment=thirdLayout;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectFragment).commit();
            return true;
        }
    };
    /*
    @Override
    protected void onResume() {
        super.onResume();
        int id= getIntent().getIntExtra("fragment", 0);
        switch (id) {
            case 0: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,firstLayout).commit();
                break;
            }
            case 1: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,secondLayout).commit();
                break;
            }
            case 2: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,thirdLayout).commit();
                break;
            }
        }
    }
    */
    void initFragment() {
        firstLayout = new Fragment1();
        secondLayout = new Fragment2();
        thirdLayout = new Fragment3();
    }
}
