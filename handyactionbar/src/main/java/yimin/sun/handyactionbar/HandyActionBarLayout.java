package yimin.sun.handyactionbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yzsh-sym on 2017/8/10.
 */

public class HandyActionBarLayout extends RelativeLayout {

    public static final int POSITION_L = 0;
    public static final int POSITION_R1 = 1;
    public static final int POSITION_R2 = 2;
    public static final int POSITION_M = 3;

    public static final int LtoL = 0;
    public static final int LtoR1 = 1;
    public static final int LtoR2 = 2;

    private FrameLayout frameL, frameM, frameR1, frameR2;

    private ImageView imageBg;

    private int middlePartHorizontalBounds;

    private int middlePartTextAppearance;

    private int sidePartTextAppearance;

    private int middlePartElementGravity;

    private int backDrawable;

    private int moreDrawable;


    private MenuBuilder r1Menu;
    private Map<String, Runnable> r1MenuCallbackMap;

    // ##fields to add:


    // ##methods to add:

    // setXWithCustomView(pos, layout, listener) // 要忽略custom view的match_parent dimen (height is fine, width of match_parent must be ignored)

    // setXWithCustomView(pos, view, listener) // 要忽略custom view的match_parent dimen (height is fine, width of match_parent must be ignored)

    // addToR1WithImagedText(drawable, text, listener)

    // addToR1WithMenuRes(R.menu.some_menu, MenuBuilder.Callback cb)



    public HandyActionBarLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        final TypedArray a;

        // whether current theme specified a global HandyActionBarStyle
        boolean globalStyleIsSpecifiedInTheme = context.getTheme().resolveAttribute(R.attr.handyActionBarStyle, new TypedValue(), true);

        // this avoids AS's layout editor's complains
        if (globalStyleIsSpecifiedInTheme) {
            a = context.obtainStyledAttributes(attrs, R.styleable.HandyActionBarLayout, R.attr.handyActionBarStyle, R.style.HandyActionBarDefaultStyle);
        } else {
            a = context.obtainStyledAttributes(attrs, R.styleable.HandyActionBarLayout, 0, R.style.HandyActionBarDefaultStyle);
        }

        middlePartHorizontalBounds = a.getInt(R.styleable.HandyActionBarLayout_middlePartHorizontalBounds, LtoL);
        middlePartTextAppearance = a.getResourceId(R.styleable.HandyActionBarLayout_middlePartTextAppearance, 0);
        sidePartTextAppearance = a.getResourceId(R.styleable.HandyActionBarLayout_sidePartTextAppearance, 0);
        middlePartElementGravity = a.getInt(R.styleable.HandyActionBarLayout_middlePartElementGravity, Gravity.CENTER);
        backDrawable = a.getResourceId(R.styleable.HandyActionBarLayout_backDrawable, 0);
        moreDrawable = a.getResourceId(R.styleable.HandyActionBarLayout_moreDrawable, 0);
        Drawable bgDrawable = a.getDrawable(R.styleable.HandyActionBarLayout_background);
        float minHeight = a.getDimension(R.styleable.HandyActionBarLayout_android_minHeight, 0f);
        setMinimumHeight((int) minHeight);
        a.recycle();


        imageBg = new ImageView(context);
        imageBg.setImageDrawable(bgDrawable);
        RelativeLayout.LayoutParams paramsBg = new LayoutParams(-1, -2);
        paramsBg.addRule(ALIGN_PARENT_TOP);
        paramsBg.addRule(ALIGN_BOTTOM, R.id.frame_l);
        addView(imageBg, paramsBg);

        frameL = new FrameLayout(context);
        frameL.setId(R.id.frame_l);
        RelativeLayout.LayoutParams paramsL = new LayoutParams(-2, -2); // -2 = wrap_content
        addView(frameL, paramsL);

        frameR1 = new FrameLayout(context);
        frameR1.setId(R.id.frame_r1);
        RelativeLayout.LayoutParams paramsR1 = new LayoutParams(-2, -2);
        paramsR1.addRule(ALIGN_PARENT_RIGHT);
        paramsR1.addRule(ALIGN_TOP, R.id.frame_l);
        paramsR1.addRule(ALIGN_BOTTOM, R.id.frame_l);
        addView(frameR1, paramsR1);

        frameR2 = new FrameLayout(context);
        frameR2.setId(R.id.frame_r2);
        RelativeLayout.LayoutParams paramsR2 = new LayoutParams(-2, -2);
        paramsR2.addRule(LEFT_OF, R.id.frame_r1);
        paramsR2.addRule(ALIGN_TOP, R.id.frame_l);
        paramsR2.addRule(ALIGN_BOTTOM, R.id.frame_l);
        addView(frameR2, paramsR2);

        frameM = new FrameLayout(context);
        frameM.setId(R.id.frame_m);
        RelativeLayout.LayoutParams paramsM = new LayoutParams(-1, -2); // -1 = match_parent
        paramsM.addRule(ALIGN_TOP, R.id.frame_l);
        paramsM.addRule(ALIGN_BOTTOM, R.id.frame_l);
        addView(frameM, paramsM);


        ViewCompat.setOnApplyWindowInsetsListener(frameL, new android.support.v4.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                params.topMargin = insets.getSystemWindowInsetTop();
                v.setLayoutParams(params);

                return insets;
            }
        });

    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height  = getSuggestedMinimumHeight();

        RelativeLayout.LayoutParams paramsL = (LayoutParams) frameL.getLayoutParams();
        paramsL.height = height;

        if (frameL.getChildCount() == 0) {
            frameL.setMinimumWidth(0);
        } else {
            frameL.setMinimumWidth(height);
        }

        if (frameR1.getChildCount() == 0) {
            frameR1.setMinimumWidth(0);
        } else {
            frameR1.setMinimumWidth(height);
        }

        if (frameR2.getChildCount() == 0) {
            frameR2.setMinimumWidth(0);
        } else {
            frameR2.setMinimumWidth(height);
        }


        measureChildWithMargins(frameL, widthMeasureSpec, 0, heightMeasureSpec, 0);

        RelativeLayout.LayoutParams paramsM = (LayoutParams) frameM.getLayoutParams();

        paramsM.leftMargin = frameL.getMeasuredWidth();

        if (middlePartHorizontalBounds == LtoL) {
            paramsM.rightMargin = frameL.getMeasuredWidth();
        } else {
            measureChildWithMargins(frameR1, widthMeasureSpec, 0, heightMeasureSpec, 0);
            if (middlePartHorizontalBounds == LtoR1) {
                paramsM.rightMargin = frameR1.getMeasuredWidth();
            } else {
                // LtoR2
                measureChildWithMargins(frameR2, widthMeasureSpec, 0, heightMeasureSpec, 0);
                paramsM.rightMargin = frameR1.getMeasuredWidth() + frameR2.getMeasuredWidth();
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setXWithText(int position, int stringId, OnClickListener onClickListener) {
        String text = getResources().getString(stringId);
        setXWithText(position, text, onClickListener);
    }

    public void setXWithText(int position, String text, OnClickListener onClickListener) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, getResources().getDisplayMetrics());
        params.setMargins(margin, 0, margin, 0);
        if (position == POSITION_M) {
            params.gravity = middlePartElementGravity;
        } else {
            params.gravity = Gravity.CENTER;
        }

        setXWithText(position, text, params, onClickListener);
    }

    public void setXWithText(int position, int stringId, FrameLayout.LayoutParams params, OnClickListener onClickListener) {
        String text = getResources().getString(stringId);
        setXWithText(position, text, params, onClickListener);
    }

    /**
     * @param position POSITION_L, etc.
     * @param params describes how the TextView is added to the frame
     */
    public void setXWithText(int position, String text, FrameLayout.LayoutParams params, OnClickListener onClickListener) {

        FrameLayout frame = getFrame(position);
        frame.removeAllViews();

        TextView textView = new TextView(getContext());
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(text);

        if (position == POSITION_M) {
            TextViewCompat.setTextAppearance(textView, middlePartTextAppearance);
        } else {
            TextViewCompat.setTextAppearance(textView, sidePartTextAppearance);
        }

        frame.setOnClickListener(onClickListener);
        frame.addView(textView, params);
    }


    public void setXWithImage(int position, int drawableId, OnClickListener onClickListener) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        if (position == POSITION_M) {
            params.gravity = middlePartElementGravity;
        } else {
            params.gravity = Gravity.CENTER;
        }
        setXWithImage(position, drawableId, params, onClickListener);
    }


    public void setXWithImage(int position, int drawableId, FrameLayout.LayoutParams params, OnClickListener onClickListener) {
        FrameLayout frame = getFrame(position);
        frame.removeAllViews();

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(drawableId);
        frame.setOnClickListener(onClickListener);
        frame.addView(imageView, params);
    }


    public void setLeftAsBack(final Activity activity) {
        setXWithImage(POSITION_L, backDrawable, new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }


    public void resetX(int position) {
        getFrame(position).removeAllViews();

        if (position == POSITION_R1 && r1Menu != null) {
            r1Menu = null;
            r1MenuCallbackMap = null;
        }
    }


    public void addToR1WithText(String title, Runnable clickCallback) {

        if (r1MenuCallbackMap == null) {
            r1MenuCallbackMap = new HashMap<>();
        }
        r1MenuCallbackMap.put(title, clickCallback);

        if (r1Menu == null) {
            r1Menu = new MenuBuilder(getContext());
            r1Menu.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    if (r1MenuCallbackMap != null) {
                        Runnable runnable = r1MenuCallbackMap.get((String)item.getTitle());
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                    return true;
                }

                @Override
                public void onMenuModeChange(MenuBuilder menu) {

                }
            });
        }

        r1Menu.add(title);

        setXWithImage(POSITION_R1, moreDrawable, new OnClickListener() {
            @Override
            public void onClick(View v) {

                MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), r1Menu, frameR1);
                /*menuHelper.setForceShowIcon(true);*/ // if you want to use icons
                /*menuHelper.setGravity(Gravity.END);*/
                menuHelper.show();
            }
        });
    }

    /**
     * 返回某个位置的View，注意不是容器FrameLayout，而是里面的实际View，当没有时返回null
     */
    public View getViewAt(int position) {
        return getFrame(position).getChildAt(0);
    }

    /**
     * 返回背景ImageView，我们的ActionBar的背景是一个独立的imageView
     */
    public ImageView getBgImageView() {
        return imageBg;
    }


    private FrameLayout getFrame(int position) {
        switch (position) {
            case POSITION_L:
                return frameL;
            case POSITION_R1:
                return frameR1;
            case POSITION_R2:
                return frameR2;
            case POSITION_M:
                return frameM;
            default:
                return null;
        }
    }
}
