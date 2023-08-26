package com.example.multiwindowsample.smartui.popup;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multiwindowsample.R;
import com.example.multiwindowsample.smartui.utils.ThemeUtils;


// [LGSMARTUI-115] custom list item adapter
// 'IListAdapter' duplicated from "AlertController.CheckedItemAdapter"
class IListAdapter extends ArrayAdapter<CharSequence> {

    interface OnBindViewListener {
        void onBindView(View view, int position);
    }
    private final OnBindViewListener mListener;

    IListAdapter(
            Context context,
            CharSequence[] entries,
            int ridLayout,
            int ridTitle,
            OnBindViewListener listener
    ) {
        super(context, getItemViewLayout(context, ridLayout), getTitleId(ridTitle), entries);
        mListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        // [LGSMARTUI-115] if view not checkable, need to wrap into checkable layout !!!
        if (!(view instanceof Checkable) && (view instanceof ViewGroup)
                && isChildViewCheckable((ViewGroup)view)) {
            ViewGroup wrapper = (ViewGroup)LayoutInflater.from(view.getContext()).inflate(
                    R.layout.st01_lg_choice_list_popup_checkable_layout, parent, false);
            wrapper.addView(view);
            view = wrapper;
        }

        if (mListener != null) {
            mListener.onBindView(view, position);
        }
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    private static boolean isChildViewCheckable(ViewGroup view) {
        final int N = view == null ? 0 : view.getChildCount();
        for (int i = 0; i < N; i++) {
            View child = view.getChildAt(i);
            if (child instanceof Checkable) {
                return true;
            }
        }
        return false;
    }

    private static int getItemViewLayout(Context context, int ridCustomLayout) {
        if (ridCustomLayout > 0) {
            return ridCustomLayout;
        }
        int ridAttr = ThemeUtils.getContextResourceId(context, R.attr.alertDialogStyle, 0);
        context = new ContextThemeWrapper(context, ridAttr);
        TypedArray style = context.getTheme().obtainStyledAttributes(
                new int[] { R.attr.singleChoiceItemLayout });
        int rid = style.getResourceId(0, 0);
        style.recycle();
        return rid;
    }

    private static int getTitleId(int ridCustomTitle) {
        return ridCustomTitle <= 0 ? android.R.id.text1
                : ridCustomTitle;
    }
}
