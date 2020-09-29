package com.monree.crashcatch;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        CrashHandler.getInstance().init();
        CrashReport.initCrashReport(getApplicationContext(), "e7defc4a5a", true);
        Log.d("CrashReportTest", "CrashReport初始化");
    }
}
