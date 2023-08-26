package com.example.multiwindowsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.multiwindowsample.AppLog.TAG;

import com.example.multiwindowsample.smartui.popup.LGNormalPopup;


public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppLog.d(TAG, "onCreate()-Start ");
        super.onCreate(savedInstanceState);
        mContext =  this;
        if (isInMultiWindowMode()) {
            AppLog.d(TAG, "onCreate() isInMultiWindowMode() true");
            multiWindowView();
            displayMultiWindowPopup();
            AppLog.i(TAG, "onCreate() isInMultiWindowMode() return");
            return;
        }

        mainView();
//        setContentView(R.layout.activity_main);
//        Button btn1 = findViewById(R.id.btn1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppLog.d(TAG, "btn1 onClick()-Start ");
//                Toast.makeText(mContext, "btn1 clicked() ", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Button btn2 = findViewById(R.id.btn2);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppLog.d(TAG, "btn2 onClick()-Start ");
//                Toast.makeText(mContext, "btn2 clicked() ", Toast.LENGTH_SHORT).show();
//            }
//        });
        AppLog.d(TAG, "onCreate()-End ");
    }

    @Override
    protected void onStart() {
        AppLog.d(TAG, "onStart() ");
        super.onStart();

    }

    @Override
    protected void onResume() {
        AppLog.d(TAG, "onResume()-Start ");
        super.onResume();
        if(isInMultiWindowMode()) {
            AppLog.d(TAG, "onResume() isInMultiWindowMode() true");
//            displayMultiWindowPopup();
//            setContentView(R.layout.activity_multiwindow);
//            AppLog.d(TAG, "onResume() isInMultiWindowMode return");
//            return;
        }
        AppLog.d(TAG, "onResume()-End ");
    }

    @Override
    protected void onPause() {
        AppLog.d(TAG, "onPause()-Start ");
        super.onPause();
        if(isInMultiWindowMode()) {
            AppLog.d(TAG, "onPause() isInMultiWindowMode() true");
////            displayMultiWindowPopup();
//            AppLog.d(TAG, "onPause() isInMultiWindowMode return");
//            return;
        }
        AppLog.d(TAG, "onPause()-End ");
    }

    @Override
    protected void onStop() {
        AppLog.d(TAG, "onStop()-Start ");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        AppLog.d(TAG, "onDestroy()-Start ");
        super.onDestroy();
        if(isInMultiWindowMode()) {
            AppLog.d(TAG, "onDestroy() isInMultiWindowMode() true");
////            displayMultiWindowPopup();
//            AppLog.d(TAG, "onPause() isInMultiWindowMode return");
//            return;
        }
        AppLog.d(TAG, "onDestroy()-End ");
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        AppLog.d(TAG, "onMultiWindowModeChanged()-Start " + isInMultiWindowMode);
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
        AppLog.i(TAG, "onMultiWindowModeChanged() " + newConfig.toString());
        if (isInMultiWindowMode) {
            // Show multi window while in multi window
            AppLog.i(TAG, "MW: Show MultiWindow: " + isInMultiWindowMode);
            multiWindowView();
//            showDiag();
            displayMultiWindowPopup();
        } else {
            // Hide multi window
            AppLog.i(TAG, "MW: Hide MultiWindow: " + isInMultiWindowMode);
//            mainView();
            AppLog.d(TAG, "Start finish()");
            finish();
        }
        AppLog.d(TAG, "onMultiWindowModeChanged()-End");
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        AppLog.d(TAG, "onConfigurationChanged()-Start " + config.toString());
        super.onConfigurationChanged(config);

    }

    @Override
    public void onTopResumedActivityChanged(boolean topResumed) {
        AppLog.d(TAG, "onTopResumedActivityChanged()-Start " + topResumed );
        if (topResumed) {
            // Top resumed activity
            // Can be a signal to re-acquire exclusive resources
        } else {
            // No longer the top resumed activity
        }
    }

    public void mainView(){
        AppLog.d(TAG, "mainView()-Start ");
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.d(TAG, "btn1 onClick()-Start ");
                Toast.makeText(mContext, "btn1 clicked() ", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.d(TAG, "btn2 onClick()-Start ");
                Toast.makeText(mContext, "btn2 clicked() ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void multiWindowView(){
        AppLog.d(TAG, "multiWindowView()-Start ");
        setContentView(R.layout.activity_multiwindow);
        Button exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLog.d(TAG, "exitIcon onClick()-Start ");
                AppLog.d(TAG, "Start: finish()");
                finish();
            }
        });
    }

    public void showDiag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("인사말");
        builder.setMessage("반갑습니다");
        builder.setPositiveButton("Ok", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

//        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
//        builder2.setTitle("인사말");
//        builder2.setMessage("반갑습니다");
//        builder2.setPositiveButton("Ok", null);
//        AlertDialog alertDialog2 = builder2.create();
//        alertDialog2.show();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                if(alertDialog2 != null) {
//                    alertDialog2.dismiss();
//                }
//            }
//        }, 1500);  // 1500 seconds

    }


    public void displayMultiWindowPopup() {
        AppLog.d(TAG, "displayMultiWindowPopup()-Start");
//        public static void displayMultiWindowPopup(boolean alreadyDisplay) {
//        AppLog.d(TAG, "Not support Multi Window mode. from : " + ((Activity)context).getLocalClassName());
//        LGNormalPopup.build(mContext)
//                .setDescription(mContext.getString(R.string.not_support_multi_window))
//                .setPositiveButton(R.string.dialog_close, dialog -> {
////                    Activity activity = (Activity) mContext;
////                    if(!alreadyDisplay) {
////                        Intent intent = new Intent();
////                        activity.setResult(Activity.RESULT_FIRST_USER, intent);
////                    }
//                    dialog.dismiss();
//                    AppLog.d(TAG, "Start: finish()");
////                    activity.finish();
//                    finish();
//                })
//                .setCancelable(true)
//                .show();
//        LGNormalPopup.build(mContext)
//                .setTitle(mContext.getString(R.string.not_support_multi_window))
//                .setDescription(mContext.getString(R.string.not_support_multi_window))
//                .setPositiveButton(R.string.ok_str, null)
//                .show();
        LGNormalPopup.build(mContext)
                .setDescription(mContext.getString(R.string.not_support_multi_window))
                .setPositiveButton(R.string.dialog_close, dialog -> {
//                    Activity activity = (Activity) mContext;
//                    if(!alreadyDisplay) {
//                        Intent intent = new Intent();
//                        activity.setResult(Activity.RESULT_FIRST_USER, intent);
//                    }
                    dialog.dismiss();
                    AppLog.d(TAG, "Start: finish()");
//                    activity.finish();
                    finish();
                    return;
                })
                .setCancelable(false)
                .show();
    }
}