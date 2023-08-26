package com.example.multiwindowsample.smartui.popup;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.multiwindowsample.R;
import com.example.multiwindowsample.smartui.utils.ThemeUtils;

public class LGNormalPopup extends LGPopup<LGNormalPopup> {

    LGNormalPopup() {
        super(LGNormalPopup.class);
    }

    public static Builder build(@NonNull Context context) {
        return new Builder(context, ThemeUtils.getContextResourceId(
                context, R.attr.lgNormalPopupStyle, 0));
    }

    public static Builder build(@NonNull Context context, int ridPopupStyle) {
        return new Builder(context, ridPopupStyle);
    }

    public static class Builder extends CommonBuilder<Builder, LGNormalPopup> {

        protected Builder(@NonNull Context context, int ridPopupStyle) {
            super(context, Builder.class, LGNormalPopup.class, ridPopupStyle);
        }

    }

}
