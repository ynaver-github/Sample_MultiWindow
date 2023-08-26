package com.example.multiwindowsample.smartui.popup;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.color.DynamicColors;
import com.example.multiwindowsample.R;
import com.example.multiwindowsample.smartui.utils.ThemeUtils;

class InnerPopupBuilder<TB, TP extends LGPopup<?>> {

    private final Class<TB> mBuilderClass;
    private final Class<TP> mPopupClass;
    private final int mRidBasePopupStyle;
    private final Context mContext;
    private int mRidBaseTheme, mRidDialogStyle;
    private CharSequence mTitle, mDesc;
    private CharSequence mPositiveBtnText, mNegativeBtnText, mNeutralBtnText;
    private boolean mApplyDynamicColor;
    private Integer mGravity;
    private LGPopup.OnClickListener<TP> mPositiveBtnListener, mNegativeBtnListener, mNeutralBtnListener;
    private LGPopup.OnAttachedListener<TP> mCustomOnAttachedListener;
    private LGPopup.OnDismissListener<TP> mOnDismissListener;
    private CharSequence[] mListItems;
    private IListAdapter.OnBindViewListener mListItemBindListener; // [LGSMARTUI-115]
    private int mListItemLayoutId;
    private int mListItemLayoutTitleId;
    private LGPopup.OnInnerItemClickListener<TP> mListItemClickListener;
    private int[] mListItemSelected;
    private boolean mListMultiChoice;
    private View mCustomView;
    private int mCustomDescriptionId;
    private boolean mCancelable = true;
    private int mReservedLayout;
    private boolean mEnableReservedLayout;
    private int mAdditionalOverlay;
    private CharSequence mNotAgainText;
    private boolean mNotAgainDefault;
    private LGPopup.OnClickListener<TP> mNotAgainListener;

    protected InnerPopupBuilder(
            @NonNull Context context,
            Class<TB> builderClass,
            Class<TP> popupClass,
            int ridPopupStyle
    ) {
        mRidBasePopupStyle = ridPopupStyle;
        mContext = mRidBasePopupStyle == 0 ? context
                : new ContextThemeWrapper(context, mRidBasePopupStyle);
        mBuilderClass = builderClass;
        mPopupClass = popupClass;
    }

    protected Context getDlgWrappedContext() {
        return mContext;
    }

    protected int getDlgWrappedContextResourceId(int attr, int defaultValue) {
        return ThemeUtils.getContextResourceId(mContext, attr, defaultValue);
    }

    protected void enableReservedLayout() {
        mEnableReservedLayout = true;
        mReservedLayout = ThemeUtils.getContextResourceId(
                mContext, R.attr.lgPopupReservedLayout, 0);
    }

    protected TB setExplicitCustomReservedLayout(int ridLayout) {
        mReservedLayout = ridLayout;
        return mBuilderClass.cast(this);
    }

    protected CharSequence getDescription() {
        return mDesc;
    }

    // temporary option for testing...
    protected TB setBaseTheme(int ridBaseTheme) {
        mRidBaseTheme = ridBaseTheme;
        return mBuilderClass.cast(this);
    }

    protected TB setDialogStyle(int ridDialogStyle) {
        mRidDialogStyle = ridDialogStyle;
        return mBuilderClass.cast(this);
    }

    protected TB setDynamicColorApplicable(boolean use) {
        mApplyDynamicColor = use;
        return mBuilderClass.cast(this);
    }

    protected TB setGravity(int gravity) {
        mGravity = gravity;
        return mBuilderClass.cast(this);
    }

    protected TB setTitle(int ridTitle) {
        mTitle = mContext.getResources().getText(ridTitle);
        return mBuilderClass.cast(this);
    }

    protected TB setTitle(CharSequence title) {
        mTitle = title;
        return mBuilderClass.cast(this);
    }

    protected TB setDescription(int ridDesc) {
        mDesc = mContext.getResources().getText(ridDesc);
        return mBuilderClass.cast(this);
    }

    protected TB setDescription(CharSequence desc) {
        mDesc = desc;
        return mBuilderClass.cast(this);
    }

    protected TB setPositiveButton(CharSequence text, LGPopup.OnClickListener<TP> listener) {
        mPositiveBtnText = text;
        mPositiveBtnListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setPositiveButton(int ridText, LGPopup.OnClickListener<TP> listener) {
        mPositiveBtnText = mContext.getResources().getText(ridText);
        mPositiveBtnListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setNegativeButton(CharSequence text, LGPopup.OnClickListener<TP> listener) {
        mNegativeBtnText = text;
        mNegativeBtnListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setNegativeButton(int ridText, LGPopup.OnClickListener<TP> listener) {
        mNegativeBtnText = mContext.getResources().getText(ridText);
        mNegativeBtnListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setNeutralButton(CharSequence text, LGPopup.OnClickListener<TP> listener) {
        mNeutralBtnText = text;
        mNeutralBtnListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setNeutralButton(int ridText, LGPopup.OnClickListener<TP> listener) {
        mNeutralBtnText = mContext.getResources().getText(ridText);
        mNeutralBtnListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setNotAgainLine(CharSequence text, boolean defaultChecked, LGPopup.OnClickListener<TP> listener) {
        mNotAgainText = text;
        mNotAgainDefault = defaultChecked;
        mNotAgainListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setNotAgainLine(int ridText, boolean defaultChecked, LGPopup.OnClickListener<TP> listener) {
        mNotAgainText = mContext.getResources().getText(ridText);
        mNotAgainDefault = defaultChecked;
        mNotAgainListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setOnAttachedListener(LGPopup.OnAttachedListener<TP> listener) {
        mCustomOnAttachedListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setDismissListener(LGPopup.OnDismissListener<TP> listener) {
        mOnDismissListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setSingleChoiceItems(CharSequence[] items, int itemSelected, LGPopup.OnInnerItemClickListener<TP> listener) {
        mListItemClickListener = listener;
        mListItems = items;
        mListItemSelected = new int[1];
        mListItemSelected[0] = itemSelected;
        mListMultiChoice = false;
        return mBuilderClass.cast(this);
    }

    protected TB setMultiChoiceItems(CharSequence[] items, int[] itemSelected, LGPopup.OnInnerItemClickListener<TP> listener) {
        mListItemClickListener = listener;
        mListItems = items;
        mListItemSelected = itemSelected;
        mListMultiChoice = true;
        return mBuilderClass.cast(this);
    }

    //  [LGSMARTUI-115] customizable list item view
    protected TB setListItemViewLayout(int ridLayout, int ridText) {
        mListItemLayoutId = ridLayout;
        mListItemLayoutTitleId = ridText;
        return mBuilderClass.cast(this);
    }

    //  [LGSMARTUI-115] customizable list item view
    protected TB setOnBindItemViewListener(IListAdapter.OnBindViewListener listener) {
        mListItemBindListener = listener;
        return mBuilderClass.cast(this);
    }

    protected TB setCustomView(View view) {
        mCustomView = view;
        return mBuilderClass.cast(this);
    }

    protected TB setCustomDescriptionId(int id) {
        mCustomDescriptionId = id;
        return mBuilderClass.cast(this);
    }

    protected TB setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return mBuilderClass.cast(this);
    }

    protected void setAdditionalOverlay(int overlay) {
        mAdditionalOverlay = overlay;
    }

    protected TP create() {
        TP popup;
        try {
            popup = mPopupClass.newInstance();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("show() Bad default constructor for class " + mPopupClass.getSimpleName());
        }
        build(popup);
        popup.onCreate(this);
        return mPopupClass.cast(popup);
    }

    protected TP show() {
        TP popup = create();
        return mPopupClass.cast(popup.show());
    }

    private void build(LGPopup<?> popup) {
        int dlgStyle = mRidDialogStyle != 0 ? mRidDialogStyle : mRidBasePopupStyle;
        AlertDialog dlg;
        Context context = mContext;
        if (mRidBaseTheme != 0) {
            context = new ContextThemeWrapper(context, mRidBaseTheme);
        }
        if (dlgStyle != mRidBasePopupStyle) {
            context = new ContextThemeWrapper(context, dlgStyle);
        }
        if (mAdditionalOverlay != 0) {
            context = new ContextThemeWrapper(context, mAdditionalOverlay);
        }
        if (mApplyDynamicColor) {
            context = DynamicColors.wrapContextIfAvailable(context);
        }
        InnerAlertDialogBuilder builder = new InnerAlertDialogBuilder(context, dlgStyle);
        if (mGravity != null) {
            builder.setGravity(mGravity);
        }
        if (mTitle != null) {
            builder.setTitle(mTitle);
        }
        if (mDesc != null) {
            if (mCustomDescriptionId != 0 && mCustomView != null) {
                TextView text = mCustomView.findViewById(mCustomDescriptionId);
                if (text != null) {
                    text.setText(mDesc);
                }
            } else {
                builder.setMessage(mDesc);
            }
        }
        if (mPositiveBtnText != null && mPositiveBtnText.length() > 0) {
            builder.setPositiveButton(mPositiveBtnText, mPositiveBtnListener == null ? null
                    : (d, i) -> mPositiveBtnListener.onClick(mPopupClass.cast(popup)));
        }
        if (mNegativeBtnText != null && mNegativeBtnText.length() > 0) {
            builder.setNegativeButton(mNegativeBtnText, mNegativeBtnListener == null ? null
                    : (d, i) -> mNegativeBtnListener.onClick(mPopupClass.cast(popup)));
        }
        if (mNeutralBtnText != null && mNeutralBtnText.length() > 0) {
            builder.setNeutralButton(mNeutralBtnText, mNeutralBtnListener == null ? null
                    : (d, i) -> mNeutralBtnListener.onClick(mPopupClass.cast(popup)));
        }
        if (mListItems != null) {
            if (mListMultiChoice) {
                if (mListItemBindListener != null) {
                    throw new RuntimeException("OnBindItemViewListener not allowed for multi choice list so far !!!");
                }
                boolean[] selected = new boolean[mListItems.length];
                for (int value : mListItemSelected) {
                    selected[value] = true;
                }
                builder.setMultiChoiceItems(mListItems, selected, mListItemClickListener == null ? null
                        : (d, i, checked) -> mListItemClickListener.onInnerItemClick(mPopupClass.cast(popup), i, checked));
            } else if (mListItemBindListener != null || mListItemLayoutId > 0) { // [LGSMARTUI-115]
                IListAdapter adapter = new IListAdapter(mContext,
                        mListItems, mListItemLayoutId, mListItemLayoutTitleId, mListItemBindListener);
                builder.setSingleChoiceItems(adapter,
                        mListItemSelected[0], mListItemClickListener == null ? null
                        : (d, i) -> mListItemClickListener.onInnerItemClick(mPopupClass.cast(popup), i, true));
            } else {
                builder.setSingleChoiceItems(mListItems, mListItemSelected[0], mListItemClickListener == null ? null
                        : (d, i) -> mListItemClickListener.onInnerItemClick(mPopupClass.cast(popup), i, true));
            }
        }
        if (mCustomView != null) {
            builder.setView(mCustomView);
        }
        builder.setOnDismissListener((d)-> {
            if (mOnDismissListener != null) {
                //todo: ensure running on UI thread
                mOnDismissListener.onDismiss(mPopupClass.cast(popup));
            }
        });
        builder.setCancelable(mCancelable);
        dlg = builder.create();
        if (mEnableReservedLayout) {
            attachReservedView(context, dlg, mReservedLayout);
        }
        if (mCustomOnAttachedListener != null) {
            ((InnerAlertDialog)dlg).setPostAttachedListener(() -> {
                mCustomOnAttachedListener.onAttached(mPopupClass.cast(popup));
                mCustomOnAttachedListener = null; // one-time listener
            });
        }
        if (mNotAgainText != null) {
            ((InnerAlertDialog)dlg).setNotAgainLine(mNotAgainText, mNotAgainDefault,
                    mNotAgainListener == null ? null
                            : () -> mNotAgainListener.onClick(mPopupClass.cast(popup)));
        }
        popup.setInnerParameters((InnerAlertDialog)dlg);
    }

    private void attachReservedView(Context context, AlertDialog dlg, int ridLayout) {
        ((InnerAlertDialog)dlg).addOnAttachedListener(true, ()-> {
            ViewGroup layout = dlg.findViewById(R.id.reservedPanel);
            if (layout != null) {
                layout.setVisibility(View.VISIBLE);
                layout = dlg.findViewById(R.id.reservedContainer);
                if (layout == null) {
                    IUtil.Log.e("attachReservedView() bad reservedContainer");
                } else if (ridLayout <= 0) {
                    IUtil.Log.e("attachReservedView() no rsvd layout id, ", ridLayout);
                } else {
                    layout.addView(LayoutInflater.from(context).inflate(ridLayout, null));
                }
            } else {
                IUtil.Log.e("attachReservedView() bad reservedPanel");
            }
        });
    }
}
