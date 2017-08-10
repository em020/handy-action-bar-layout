package yimin.sun.handyactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by yzsh-sym on 2017/8/10.
 */

public class HandyActionBarLayout extends ViewGroup {

    private FrameLayout frameL, frameM, frameR1, frameR2;

    public HandyActionBarLayout(Context context) {
        this(context, null);
    }

    public HandyActionBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.handyActionBarStyle);
    }

    public HandyActionBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*this(context, attrs, defStyleAttr, R.style.actionBarStyle);*/


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HandyActionBarLayout, defStyleAttr, R.style.actionBarStyle);
        float minHeight = a.getDimension(R.styleable.HandyActionBarLayout_android_minHeight, 0);
        setMinimumHeight((int) minHeight);

        a.recycle();


        // create and add the four frames
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*super.onMeasure(widthMeasureSpec, heightMeasureSpec);*/
        /*setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));*/
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getSuggestedMinimumHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
