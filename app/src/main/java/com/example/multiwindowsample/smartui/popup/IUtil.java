package com.example.multiwindowsample.smartui.popup;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

class IUtil {
    private static final String LOG_TAG = "[LGSmartLib]";
    private enum Pri { D, I, W, E }

    static class Log {
        public static void d(Object...m) {
            log(Pri.D, LOG_TAG, m);
        }
        public static void i(Object...m) {
            log(Pri.I, LOG_TAG, m);
        }
        public static void w(Object...m) {
            log(Pri.W, LOG_TAG, m);
        }
        public static void e(Object...m) {
            log(Pri.E, LOG_TAG, m);
        }
        private static void log(Pri pri, String tag, Object[] messages) {
            StringBuilder b = new StringBuilder();
            if (messages != null) {
                for (int i = 0; i < messages.length; i++) {
                    if (i > 0) {
                        b.append(' ');
                    }
                    b.append(messages[i]);
                }
            }
            switch (pri) {
                case D: android.util.Log.d(tag, b.toString());
                case I: android.util.Log.i(tag, b.toString());
                case W: android.util.Log.w(tag, b.toString());
                case E: android.util.Log.e(tag, b.toString());
            }
        }
    }

    // show IME on edit view focused.
    static boolean requestEditFocusWithIme(AlertDialog dlg, View edit) {
        Window win = dlg == null ? null : dlg.getWindow();
        if (win == null || edit == null) {
            Log.e("LGPopup::IUtil.requestEditFocusWithIme() bad parameters");
            return false;
        }
        win.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        edit.setFocusableInTouchMode(true);
        boolean ret = edit.requestFocus();
        if (ret) {
            win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        return ret;
    }
}
