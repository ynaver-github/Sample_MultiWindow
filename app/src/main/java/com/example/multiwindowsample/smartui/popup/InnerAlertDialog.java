package com.example.multiwindowsample.smartui.popup;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;

import com.example.multiwindowsample.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class InnerAlertDialog extends AlertDialog {
    private final Integer mGravity;
    private final List<Pair<Runnable, Boolean>> mOnAttachedListenerList= new CopyOnWriteArrayList<>();
    private Runnable mPostAttachedListener;
    private CharSequence mNotAgainText;
    private boolean mNotAgainDefault;
    private Runnable mNotAgainListener;
    private CheckedTextView mNotAgainView;

    protected InnerAlertDialog(@NonNull Context context, int ridTheme, Integer gravity) {
        super(getThemeWrappedContext(context, ridTheme), ridTheme);
        mGravity = gravity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setForceScrollUpDownIndicator();
    }

    @Override
    public void onAttachedToWindow() {
        setup();
        super.onAttachedToWindow();
        notifyOnAttachedListener();

        // should be last logic !!!
        if (mPostAttachedListener != null) {
            mPostAttachedListener.run();
            mPostAttachedListener = null; // one-time listener
        }
    }

    public void setButtonText(int whichButton, CharSequence text) {
        int id = whichButton == DialogInterface.BUTTON_POSITIVE ? android.R.id.button1
                : whichButton == DialogInterface.BUTTON_NEGATIVE ? android.R.id.button2
                : whichButton == DialogInterface.BUTTON_NEUTRAL ? android.R.id.button3
                : -1;
        if (id >= 0) {
            Button btn = findViewById(id);
            if (btn != null) {
                btn.setText(text);
            }
        }
    }

    public boolean isNotAgainChecked() {
        return mNotAgainView != null && mNotAgainView.isChecked();
    }

    void setNotAgainLine(CharSequence text, boolean defaultChecked, Runnable listener) {
        mNotAgainText = text;
        mNotAgainDefault = defaultChecked;
        mNotAgainListener = listener;
    }

    void addOnAttachedListener(boolean oneTime, Runnable listener) {
        mOnAttachedListenerList.add(Pair.create(listener, oneTime));
    }

    void setPostAttachedListener(Runnable listener) {
        mPostAttachedListener = listener;
    }

    private void notifyOnAttachedListener() {
        List<Object> rmList = new ArrayList<>();
        mOnAttachedListenerList.forEach((p)->{
            p.first.run();
            if (p.second) {
                rmList.add(p);
            }
        });
        rmList.forEach((o) -> mOnAttachedListenerList.remove(o));
    }

    private void setup() {
        setupGravity();
        setupNotAgainLine();
        setupEmptyActionSpacer();
    }

    private void setupGravity() {
        int gravity;
        if (mGravity == null) {
            TypedValue outValue = new TypedValue();
            boolean ok = getContext().getTheme().resolveAttribute(R.attr.lgDialogGravity, outValue, true);
            gravity = ok ? outValue.data : 0;
        } else {
            gravity = mGravity;
        }
        if (gravity != 0) {
            getWindow().setGravity(gravity == 2 ? Gravity.BOTTOM : Gravity.TOP);
            //todo: blur option placed here !!!
            //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }
    }

    private void setupNotAgainLine() {
        if (mNotAgainText == null) {
            return;
        }
        mNotAgainView = findViewById(R.id.notAgainLine);
        if (mNotAgainView == null) {
            return;
        }
        mNotAgainView.setText(mNotAgainText);
        mNotAgainView.setChecked(mNotAgainDefault);
        mNotAgainView.setOnClickListener(v -> {
            mNotAgainView.toggle();
            if (mNotAgainListener != null) {
                mNotAgainListener.run();
            }
        });
        mNotAgainView.setVisibility(View.VISIBLE);
    }

    private void setupEmptyActionSpacer() {
        View actions = findViewById(R.id.buttonPanel);
        if (actions == null || actions.getVisibility() != View.GONE) {
            return;
        }
        View spacer = findViewById(R.id.spacerNoActions);
        if (spacer != null) {
            spacer.setVisibility(View.VISIBLE);
        }
    }

    private static Context getThemeWrappedContext(Context context, int themeResId) {
        return new ContextThemeWrapper(
                context, resolveDialogTheme(context, themeResId));
    }

    private static int resolveDialogTheme(@NonNull Context context, @StyleRes int resid) {
        // Check to see if this resourceId has a valid package ID.
        if (((resid >>> 24) & 0x000000ff) >= 0x00000001) {   // start of real resource IDs.
            return resid;
        } else {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.alertDialogTheme, outValue, true);
            return outValue.resourceId;
        }
    }

    // [LGSMARTUI-153] if up indicator active, force set scroll down indiactor as well.
    private void setForceScrollUpDownIndicator() {
        View contentPanel = getListView();
        if (contentPanel == null) {
            contentPanel = findViewById(R.id.scrollView);
        }
        if (contentPanel != null) {
            int indicators = ViewCompat.getScrollIndicators(contentPanel);
            if (indicators == ViewCompat.SCROLL_INDICATOR_TOP) {
                indicators = ViewCompat.SCROLL_INDICATOR_TOP | ViewCompat.SCROLL_INDICATOR_BOTTOM;
                ViewCompat.setScrollIndicators(contentPanel, indicators, indicators);
            }
        }
    }

}