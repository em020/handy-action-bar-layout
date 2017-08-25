package yimin.sun.handyactionbarlayout;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.PopupMenuCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import yimin.sun.handyactionbar.HandyActionBarLayout;

public class MainActivity extends AppCompatActivity {

    HandyActionBarLayout actionBar;

    // use flow layout for test buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        StatusBarFucker fu = new StatusBarFucker();
//        fu.setWindowExtend(1);
//        fu.setStatusBarColor(Color.parseColor("#33666666"));
//        fu.fuck(getWindow());

        actionBar = (HandyActionBarLayout) findViewById(R.id.handyactionbar);
        onClickButtonTrick3(null);
    }

    public void onClickButtonTrick2(View view) {
        actionBar.setXWithText(HandyActionBarLayout.POSITION_L, "HHHHHHHH", null);
        actionBar.setXWithText(HandyActionBarLayout.POSITION_M, "WWWW", null);
        actionBar.setXWithText(HandyActionBarLayout.POSITION_R2, "HHH", null);
    }

    public void onClickButtonTrick3(View view) {
        actionBar.setLeftAsBack(this);
        actionBar.addToR1WithText("yyyyy!", new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "kkkkkkk", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
        actionBar.addToR1WithText("xxxxx!", new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "11111", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetL(View view) {
        actionBar.resetX(HandyActionBarLayout.POSITION_L);
    }

    public void resetM(View view) {
        actionBar.resetX(HandyActionBarLayout.POSITION_M);
    }

    public void resetR2(View view) {
        actionBar.resetX(HandyActionBarLayout.POSITION_R2);
    }

    public void resetR1(View view) {
        actionBar.resetX(HandyActionBarLayout.POSITION_R1);
    }

    public void resetTopMargin(View view) {
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) actionBar.frameL.getLayoutParams();
//        params.topMargin = 0;
//        actionBar.frameL.setLayoutParams(params);
    }
}
