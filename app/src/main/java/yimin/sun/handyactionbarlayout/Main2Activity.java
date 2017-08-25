package yimin.sun.handyactionbarlayout;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yimin.sun.statusbarfucker.StatusBarFucker;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        StatusBarFucker fu = new StatusBarFucker();
        fu.setWindowExtend(1);
        fu.setStatusBarColor(Color.parseColor("#33662266"));
        fu.fuck(getWindow());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame, new BlankFragment()).commit();
    }

}
