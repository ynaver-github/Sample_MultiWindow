/*
 * Mobile Communication Company, LG ELECTRONICS INC., SEOUL, KOREA
 * Copyright(c) 2017 by LG Electronics Inc.
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * Permission of LG Electronics Inc.
 */
package com.example.multiwindowsample.smartui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import androidx.annotation.NonNull;

import com.example.multiwindowsample.R;

public class ThemeUtils {
    /**
     * Returns the accent color of context.
     */
    public static int getColorAccentFromTheme(Context c) {
        TypedArray style = c.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.colorAccent });
        int color = style.getColor(0, 0);
        style.recycle();
        return color;
    }
    /**
     * Returns the Primary text color of context.
     */
    public static int getColorPrimaryFromTheme(Context c) {
        TypedArray style = c.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.textColorPrimary });
        int color = style.getColor(0, 0);
        style.recycle();
        return color;
    }
    /**
     * Returns the Secondary text color of context.
     */
    public static int getColorSecondaryFromTheme(Context c) {
        TypedArray style = c.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.textColorSecondary });
        int color = style.getColor(0, 0);
        style.recycle();
        return color;
    }

    /**
     * Returns the error color of context.
     */
    public static int getErrorColorFromTheme(Context c) {
        TypedArray style = c.getTheme().obtainStyledAttributes(
                new int[] { R.attr.colorError });
        int color = style.getColor(0, 0);
        style.recycle();
        return color;
    }

    public static int getColorOnSurfaceVariant(Context c) {
        TypedArray style = c.getTheme().obtainStyledAttributes(
                new int[] { R.attr.colorOnSurfaceVariant });
        int color = style.getColor(0, 0);
        style.recycle();
        return color;
    }

    public static void adjustSystemBars(Activity activity) {
        if (!activity.isInMultiWindowMode()) {
            adjustSystemBars(activity.getResources(), activity.getWindow());
        }
    }

    public static void adjustSystemBars(Resources resources, Window window) {
        boolean isLandscape = (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                if (isLandscape) {
                    insetsController.hide(WindowInsets.Type.statusBars());
                    insetsController.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                } else {
                    insetsController.show(WindowInsets.Type.statusBars());
                }
            }
        }
    }

    public static int getAttr(@NonNull Context context, int attr, int fallbackAttr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        if (value.resourceId != 0) {
            //Log.d(TAG, "value.resourceId : " + value.resourceId);
            return attr;
        }
        return fallbackAttr;
    }

    // ex) id = getAttrResourceId(ctx, attrs, R.styleable.LGPreference, R.styleable.LGPreference_preferenceBackgroundOverlay, -1);
    public static int getAttrResourceId(Context context, AttributeSet attrs, int[] styleable, int index, int defaultValue) {
        TypedArray a = context.obtainStyledAttributes(attrs, styleable);
        int id = a.getResourceId(index, defaultValue);
        a.recycle();
        return id;
    }

    // ex) id = getAttrResourceId(context, R.attr.dropdownPreferenceStyle, -1);
    public static int getContextResourceId(Context context, int attr, int defaultValue) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.resourceId == 0 ? defaultValue : value.resourceId;
    }

    public static String getContextAttrString(Context context, int attr, String defaultValue) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.string == null ? defaultValue : value.string.toString();
    }

    public static int getContextAttrValue(Context context, int attr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.data;
    }

    public static float getContextAttrFloat(Context context, int attr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.getFloat();
    }
}
