package com.library;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.OvershootInterpolator;
import android.widget.Checkable;
import android.widget.FrameLayout;

import com.library.listener.OnButtonCheckedChangeListener;
import com.library.listener.OnStateButtonCheckedListener;
import com.library.widget.FlatStateButton;
import com.library.widget.FlatStateText;
import com.library.widget.FlatStateTitleTick;

/**
 * Created by b_ashish on 19-Oct-16.
 */

public class FlatCompoundButton extends FrameLayout implements Checkable, OnStateButtonCheckedListener {

    private boolean mBroadcasting;

    private int mButtonTitleTickRes;

    protected Drawable mButtonTitleTickDrawable;

    protected CharSequence mText;

    protected float mTextSize;

    protected ColorStateList mColorColorStateList;

    private final FlatStateButton mButtonView;
    private final FlatStateTitleTick mButtonTitleTickIcon;

    protected FlatStateText mButtonText;

    protected boolean mChecked;
    protected boolean mAnimator;
    protected long mDuration = 300;


    private OnButtonCheckedChangeListener mOnButtonCheckedChangeListener;
    private OnButtonCheckedChangeListener mOnCheckedChangeWidgetListener;

    public FlatCompoundButton(Context context) {
        this(context, null);
    }

    public FlatCompoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlatCompoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.flat_compound_button, this, true);

        mButtonView = (FlatStateButton) findViewById(R.id.flat_compound_button_image);
        mButtonTitleTickIcon = (FlatStateTitleTick) findViewById(R.id.flat_compound_button_tick_ico);
        mButtonText = (FlatStateText) findViewById(R.id.flat_compound_button_text);

        mButtonView.setOnStateButtonCheckedListener(this);
        applyAttributeSet(context, attrs);
//        if (isAnimator()) {
//            ViewHelper.setScaleX(this, 0.85f);
//            ViewHelper.setScaleY(this, 0.85f);
//        }
    }

    private void applyAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlatCompoundButton);
        mChecked = typedArray.getBoolean(R.styleable.FlatCompoundButton_setChecked, mChecked);
        setAnimator(typedArray.getBoolean(R.styleable.FlatCompoundButton_setAnimator, isAnimator()));

        mButtonTitleTickRes = typedArray.getResourceId(R.styleable.FlatCompoundButton_setTitleTick, 0);
        if (mButtonTitleTickRes != 0) {
            setButtonTitleTickRes(mButtonTitleTickRes);
        }

        setText(typedArray.getText(R.styleable.FlatCompoundButton_setText));
        setTextSize(typedArray.getDimensionPixelSize(R.styleable.FlatCompoundButton_setTextSize, 15));
        setTextColor(typedArray.getColorStateList(R.styleable.FlatCompoundButton_setTextColor));

        typedArray.recycle();

    }

    public void setAnimator(boolean isAnim) {
        this.mAnimator = isAnim;
    }

    public boolean isAnimator() {
        return mAnimator;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public long getDuration() {
        return mDuration;
    }

    @Override
    public void setChecked(boolean checked) {
        mButtonView.setChecked(checked);
        mButtonText.setChecked(checked);
        mButtonTitleTickIcon.setChecked(checked);
    }

    @Override
    public void onCheckedChanged(boolean isChecked) {
        mButtonText.setChecked(isChecked);
        mButtonTitleTickIcon.setChecked(isChecked);
        if (mBroadcasting) {
            return;
        }
        mBroadcasting = true;
        if (mOnButtonCheckedChangeListener != null) {
            mOnButtonCheckedChangeListener.onCheckedChanged(FlatCompoundButton.this, isChecked);
        }
        if (mOnCheckedChangeWidgetListener != null) {
            mOnCheckedChangeWidgetListener.onCheckedChanged(FlatCompoundButton.this, isChecked);
        }
        mBroadcasting = false;
        if (isAnimator()) {
            if (isChecked()) {
                start();
            } else {
                end();
            }
        }
    }

    private void start() {
        if (canAnimate()) {
            clearAnimation();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleX", 1.0f));
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleY", 1.0f));
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(getDuration());
        animatorSet.start();
    }

    private void end() {
        if (canAnimate()) {
            clearAnimation();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleX", 0.85f));
        animatorSet.play(ObjectAnimator.ofFloat(this, "scaleY", 0.85f));
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(getDuration());
        animatorSet.start();
    }

    @Override
    public void onCheckedToggle() {
        mButtonText.toggle();
        mButtonTitleTickIcon.toggle();
    }


    @Override
    public boolean isChecked() {
        return mButtonView.isChecked();
    }

    @Override
    public void toggle() {
        mButtonView.toggle();
        mButtonTitleTickIcon.toggle();
    }

    public void setText(@StringRes int resId) {
        setText(getResources().getString(resId));
    }

    public void setText(CharSequence text) {
        this.mText = text;
        this.mButtonText.setText(mText);
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        this.mButtonText.setTextSize(mTextSize);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mColorColorStateList = colorStateList;
        this.mButtonText.setTextColor(mColorColorStateList);
    }

    public void setTextColor(@ColorInt int color) {
        setTextColor(ColorStateList.valueOf(color));
    }

    public void setButtonTitleTickRes(int buttonRes) {
        this.mButtonTitleTickRes = buttonRes;
        setButtonTitleTickDrawable(getResources().getDrawable(mButtonTitleTickRes));
    }

    public void setButtonTitleTickDrawable(Drawable drawable) {
        this.mButtonTitleTickDrawable = drawable;
        this.mButtonTitleTickIcon.setImageDrawable(mButtonTitleTickDrawable);
    }

    public void setOnCheckedChangeListener(OnButtonCheckedChangeListener mOnButtonCheckedChangeListener) {
        this.mOnButtonCheckedChangeListener = mOnButtonCheckedChangeListener;
    }

    public void setOnCheckedChangeWidgetListener(OnButtonCheckedChangeListener mOnCheckedChangeWidgetListener) {
        this.mOnCheckedChangeWidgetListener = mOnCheckedChangeWidgetListener;
        setChecked(mChecked);
    }

//    @Override
//    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
//        super.onInitializeAccessibilityEvent(event);
//        event.setClassName(FlatCompoundButton.class.getName());
//        event.setChecked(mButtonView.isChecked());
//    }
//
//    @Override
//    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
//        super.onInitializeAccessibilityNodeInfo(info);
//        info.setClassName(FlatCompoundButton.class.getName());
//        info.setCheckable(true);
//        info.setChecked(mButtonView.isChecked());
//    }
}
