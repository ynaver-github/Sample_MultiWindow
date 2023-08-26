package com.example.multiwindowsample.smartui.popup;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;

import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class InnerAlertDialogBuilder extends MaterialAlertDialogBuilder {
    private final int mTheme;
    private Object P;
    private Context mPContext;
    private boolean mPCancelable;
    private DialogInterface.OnCancelListener mPOnCancelListener;
    private DialogInterface.OnDismissListener mPOnDismissListener;
    private DialogInterface.OnKeyListener mPOnKeyListener;
    private Rect mBackgroundInsets;
    private int mGravity;

    InnerAlertDialogBuilder(@NonNull Context context, int overrideThemeResId) {
        super(context, overrideThemeResId);
        mTheme = overrideThemeResId;
    }

    void setGravity(int gravity) {
        mGravity = gravity;
    }

    @Override
    @NonNull
    public AlertDialog create() {
        buildParams();
        InnerAlertDialog dialog = createForAlertDialog(mGravity);
        createForMaterialAlertDialogBuilder(dialog);
        return dialog;
    }

    private void buildParams() {
        try {
            Field field = AlertDialog.Builder.class.getDeclaredField("P");
            field.setAccessible(true);
            P = field.get(this);
            field = P.getClass().getDeclaredField("mContext");
            field.setAccessible(true);
            mPContext = (Context)field.get(P);
            field = P.getClass().getDeclaredField("mCancelable");
            field.setAccessible(true);
            mPCancelable = (Boolean)field.get(P);
            field = P.getClass().getDeclaredField("mOnCancelListener");
            field.setAccessible(true);
            mPOnCancelListener = (DialogInterface.OnCancelListener)field.get(P);
            field = P.getClass().getDeclaredField("mOnDismissListener");
            field.setAccessible(true);
            mPOnDismissListener = (DialogInterface.OnDismissListener)field.get(P);
            field = P.getClass().getDeclaredField("mOnKeyListener");
            field.setAccessible(true);
            mPOnKeyListener = (DialogInterface.OnKeyListener)field.get(P);
            field = MaterialAlertDialogBuilder.class.getDeclaredField("backgroundInsets");
            field.setAccessible(true);
            mBackgroundInsets = (Rect)field.get(this);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("setup() error " + e.getClass().getSimpleName() + "\n" + e.getMessage());
        }
    }

    // AlertDialog.Builder.create()
    private InnerAlertDialog createForAlertDialog(int gravity) {
        // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
        // so we always have to re-set the theme
        final InnerAlertDialog dialog = new InnerAlertDialog(mPContext, mTheme, gravity);
        try { //P.apply(dialog.mAlert);
            Field field = AlertDialog.class.getDeclaredField("mAlert");
            field.setAccessible(true);
            Object dlgAlert = field.get(dialog);
            Method method = P.getClass().getMethod("apply", dlgAlert.getClass());
            method.setAccessible(true);
            method.invoke(P, dlgAlert);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("create() error " + e.getClass().getSimpleName() + "\n" + e.getMessage());
        }
        dialog.setCancelable(mPCancelable);
        if (mPCancelable) {
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.setOnCancelListener(mPOnCancelListener);
        dialog.setOnDismissListener(mPOnDismissListener);
        if (mPOnKeyListener != null) {
            dialog.setOnKeyListener(mPOnKeyListener);
        }
        return dialog;
    }

    // MaterialAlertDialogBuilder.create()
    private void createForMaterialAlertDialogBuilder(AlertDialog dialog) {
        Window window = dialog.getWindow();
        /* {@link Window#getDecorView()} should be called before any changes are made to the Window
         * as it locks in attributes and affects layout. */
        View decorView = window.getDecorView();
        Drawable background = getBackground();
        if (background instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) background).setElevation(ViewCompat.getElevation(decorView));
        }

        Drawable insetDrawable = MaterialDialogs.insetDrawable(background, mBackgroundInsets);
        window.setBackgroundDrawable(insetDrawable);
        decorView.setOnTouchListener(new InsetDialogOnTouchListener(dialog, mBackgroundInsets));
    }
}
