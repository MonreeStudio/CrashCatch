package com.monree.crashcatch;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;
    private Context context;

    public static CrashHandler getInstance() {
        return CrashHandlerHolder.INSTANCE;
    }

    public void init() {
        context = MyApplication.context;
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d("CrashReportInfo", "异常捕获");
        Intent intent = new Intent(context, CrashActivity.class);
        intent.putExtra("Throwable", throwable);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        mDefaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
    }

    private static class CrashHandlerHolder {
        static final CrashHandler INSTANCE = new CrashHandler();
    }

    private CrashHandler() { }
}
