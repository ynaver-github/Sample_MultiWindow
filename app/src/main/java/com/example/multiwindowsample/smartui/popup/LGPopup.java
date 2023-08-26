package com.example.multiwindowsample.smartui.popup;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class LGPopup<T extends LGPopup<?>> {

    public static final int GRAVITY_CENTER = 0;
    public static final int GRAVITY_TOP = 1;
    public static final int GRAVITY_BOTTOM = 2;

    private final Class<T> mChildClass;
    InnerAlertDialog mDialog; // package private only !!!
    private ButtonInterface mPositiveBtnInterface;
    private ButtonInterface mNegativeBtnInterface;
    private ButtonInterface mNeutralBtnInterface;

    public interface OnAttachedListener<P extends LGPopup<?>> {
        void onAttached(P popup);
    }

    public interface ButtonInterface { // [LGSMARTUI-118]
        ButtonInterface setText(CharSequence text);
        ButtonInterface setText(int textId);
        ButtonInterface setEnabled(boolean enable);
        ButtonInterface setVisibility(int visibility); // View.VISIBLE / INVISIBLE / GONE
    }

    public ButtonInterface getPositiveButtonInterface() {
        return mPositiveBtnInterface != null ? mPositiveBtnInterface
                : (mPositiveBtnInterface = new ButtonInterfaceImpl(DialogInterface.BUTTON_POSITIVE));
    }

    public ButtonInterface getNegativeButtonInterface() {
        return mNegativeBtnInterface != null ? mNegativeBtnInterface
                : (mNegativeBtnInterface = new ButtonInterfaceImpl(DialogInterface.BUTTON_NEGATIVE));
    }

    public ButtonInterface getNeutralButtonInterface() {
        return mNeutralBtnInterface != null ? mNeutralBtnInterface
                : (mNeutralBtnInterface = new ButtonInterfaceImpl(DialogInterface.BUTTON_NEUTRAL));
    }

    public interface OnClickListener<P extends LGPopup<?>> {
        void onClick(P popup);
    }

    public interface OnDismissListener<P extends LGPopup<?>> {
        void onDismiss(P popup);
    }

    protected interface OnInnerItemClickListener<P extends LGPopup<?>> {
        void onInnerItemClick(P popup, int which, boolean checked);
    }

    public T show() {
        if (mDialog != null) {
            mDialog.show();
        }
        return mChildClass.cast(this);
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public <V extends View> V findViewById(int id) {
        return mDialog == null ? null
                : mDialog.findViewById(id);
    }

    public boolean isPopupShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    public boolean isNotAgainChecked() {
        return mDialog != null && mDialog.isNotAgainChecked();
    }

    protected LGPopup(Class<T> cls) {
        mChildClass = cls;
    }

    protected void onCreate(Object builderObj) {
    }

    protected void onAttached() {
    }

    void setInnerParameters(InnerAlertDialog dlg) {
        mDialog = dlg;
        dlg.addOnAttachedListener(false, this::onAttached);
    }

    private class ButtonInterfaceImpl implements ButtonInterface { // [LGSMARTUI-118]
        private final int mBtnType;
        private ButtonInterfaceImpl(int btnType) {
            mBtnType = btnType;
        }
        @Override
        public ButtonInterface setText(CharSequence text) {
            if (mDialog != null) {
                mDialog.setButtonText(mBtnType, text);
            }
            return this;
        }
        @Override
        public ButtonInterface setText(int textId) {
            if (mDialog != null) {
                mDialog.setButtonText(mBtnType, mDialog.getContext().getText(textId));
            }
            return this;
        }
        @Override
        public ButtonInterface setEnabled(boolean enable) {
            Button btn = mDialog == null ? null : mDialog.getButton(mBtnType);
            if (btn != null) {
                btn.setEnabled(enable);
            }
            return this;
        }
        @Override
        public ButtonInterface setVisibility(int visibility) {
            Button btn = mDialog == null ? null : mDialog.getButton(mBtnType);
            if (btn != null) {
                btn.setVisibility(visibility);
            }
            return null;
        }
    }

    // common use builder options
    public static class CommonBuilder<T, TP extends LGPopup<?>> extends InnerPopupBuilder<T, TP> {
        protected CommonBuilder(
                @NonNull Context context,
                Class<T> builderClass,
                Class<TP> popupClass,
                int ridPopupStyle
        ) {
            super(context, builderClass, popupClass, ridPopupStyle);
        }

        @Deprecated
        @Override
        public T setBaseTheme(int ridBaseTheme) {
            return super.setBaseTheme(ridBaseTheme);
        }

        @Deprecated
        @Override
        public T setDialogStyle(int ridDialogStyle) {
            return super.setDialogStyle(ridDialogStyle);
        }

        @Deprecated
        @Override
        public T setDynamicColorApplicable(boolean use) {
            return super.setDynamicColorApplicable(use);
        }

        @Deprecated
        @Override
        public T setGravity(int gravity) {
            return super.setGravity(gravity);
        }

        @Override
        public T setTitle(int ridTitle) {
            return super.setTitle(ridTitle);
        }

        @Override
        public T setTitle(CharSequence title) {
            return super.setTitle(title);
        }

        @Override
        public T setDescription(int ridDesc) {
            return super.setDescription(ridDesc);
        }

        @Override
        public T setDescription(CharSequence desc) {
            return super.setDescription(desc);
        }

        @Override
        public T setPositiveButton(CharSequence text, OnClickListener<TP> listener) {
            return super.setPositiveButton(text, listener);
        }

        @Override
        public T setPositiveButton(int ridText, OnClickListener<TP> listener) {
            return super.setPositiveButton(ridText, listener);
        }

        @Override
        public T setNegativeButton(CharSequence text, OnClickListener<TP> listener) {
            return super.setNegativeButton(text, listener);
        }

        @Override
        public T setNegativeButton(int ridText, OnClickListener<TP> listener) {
            return super.setNegativeButton(ridText, listener);
        }

        @Override
        public T setNeutralButton(CharSequence text, OnClickListener<TP> listener) {
            return super.setNeutralButton(text, listener);
        }

        @Override
        public T setNeutralButton(int ridText, OnClickListener<TP> listener) {
            return super.setNeutralButton(ridText, listener);
        }

        @Override
        public T setNotAgainLine(CharSequence text, boolean defaultChecked, OnClickListener<TP> listener) {
            return super.setNotAgainLine(text, defaultChecked, listener);
        }

        @Override
        public T setNotAgainLine(int ridText, boolean defaultChecked, OnClickListener<TP> listener) {
            return super.setNotAgainLine(ridText, defaultChecked, listener);
        }

        @Override
        public T setOnAttachedListener(OnAttachedListener<TP> listener) {
            return super.setOnAttachedListener(listener);
        }

        @Override
        public T setDismissListener(OnDismissListener<TP> listener) {
            return super.setDismissListener(listener);
        }

        @Override
        public T setCancelable(boolean cancelable) {
            return super.setCancelable(cancelable);
        }

        @Override
        public TP create() {
            return super.create();
        }

        @Override
        public TP show() {
            return super.show();
        }
    }

}
