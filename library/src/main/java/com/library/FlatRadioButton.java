package com.library;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by b_ashish on 19-Oct-16.
 */

public class FlatRadioButton extends FlatCompoundButton {


    public FlatRadioButton(Context context) {
        this(context, null);
    }

    public FlatRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlatRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    public void toggle() {
        // we override to prevent toggle when the radio is already
        // checked (as opposed to check boxes widgets)
        if (!isChecked()) {
            super.toggle();
        }
    }

//    @Override
//    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
//        super.onInitializeAccessibilityEvent(event);
//        event.setClassName(FlatRadioButton.class.getName());
//    }
//
//    @Override
//    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
//        super.onInitializeAccessibilityNodeInfo(info);
//        info.setClassName(FlatRadioButton.class.getName());
//    }
}
