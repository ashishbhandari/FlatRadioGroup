package com.library.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.Checkable;

import com.library.listener.OnStateButtonCheckedListener;

/**
 * Created by b_ashish on 19-Oct-16.
 */

public class FlatStateButton extends Button implements Checkable {

    private static final boolean DEBUG = true;
    private static final String TAG = FlatStateButton.class.getSimpleName();

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    private OnStateButtonCheckedListener mOnStateButtonCheckedListener;
    private boolean mChecked;

    public FlatStateButton(Context context) {
        super(context);
    }

    public FlatStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlatStateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setChecked(boolean checked) {
        if (DEBUG) {
            Log.d("setChecked :%s", String.valueOf(checked));
        }
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
            if (mOnStateButtonCheckedListener != null) {
                mOnStateButtonCheckedListener.onCheckedChanged(mChecked);
            }
        }

    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
        if (mOnStateButtonCheckedListener != null) {
            mOnStateButtonCheckedListener.onCheckedToggle();
        }
    }

    @Override
    public boolean performClick() {
        if (DEBUG) {
            Log.d(TAG, "performClick");
        }
        if (!isChecked()) {
            toggle();
        }
        return super.performClick();
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        /**
         * Buttons in Lollipop and higher have a default elevation to them which causes them to always draw on top.
         * You can change this by overriding the default StateListAnimator.
         * http://stackoverflow.com/questions/33017735/incorrect-overlay-behavior-in-framelayout/33018254#33018254
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setStateListAnimator(null);
        }
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void setOnStateButtonCheckedListener(OnStateButtonCheckedListener listener) {
        this.mOnStateButtonCheckedListener = listener;
    }

}
