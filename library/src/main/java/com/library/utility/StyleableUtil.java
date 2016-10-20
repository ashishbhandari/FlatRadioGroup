package com.library.utility;

import android.os.Build;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by b_ashish on 19-Oct-16.
 */

public class StyleableUtil {

    private static final String TAG = StyleableUtil.class.getSimpleName();
    private static final boolean DEBUG = true;

    private static Class R_attr = null;

    private static Class R_styleable = null;

    static {
        try {
            R_styleable = Class.forName("com.android.internal.R$styleable");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            R_attr = Class.forName("com.android.internal.R$attr");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int attr(String field) {
        if (R_attr == null) {
            if (DEBUG) {
                Log.e(TAG, "getRes(null," + field + ")");
            }
            return 0;
        }
        try {
            Field idField = R_attr.getField(field);
            return idField.getInt(field);
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "getRes(" + R_attr.getName() + ", " + field + ")");
                Log.e(TAG, "Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");
            }
        }
        return 0;
    }

    public static int[] styleables(String field) {
        try {
            if ((R_styleable != null) && (R_styleable.getDeclaredField(field).get(R_styleable) != null)
                    && (R_styleable.getDeclaredField(field).get(R_styleable).getClass().isArray()))
                return (int[]) R_styleable.getDeclaredField(field).get(R_styleable);
        } catch (Exception e) {
            e.printStackTrace();
            if (DEBUG) {
                Log.e(TAG, "styleables", e);
            }
        }
        return new int[0];
    }


    /**
     * An int value that may be updated atomically.
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }
}
