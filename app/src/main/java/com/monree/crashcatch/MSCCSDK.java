package com.monree.crashcatch;

import android.content.Context;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;

public class MSCCSDK {
    private MSCCSDK() {}

    private static class MSCCSDKHolder {
        private static MSCCSDK INSTANCE = new MSCCSDK();
    }

    private static MSCCSDK getInstance() {
        return MSCCSDKHolder.INSTANCE;
    }

    public static void init(Context context) {
        CrashHandler.getInstance().init(context);
        CrashReport.initCrashReport(context, CrashHandlerConfig.buglyAppId, false);
        Log.d("MSCCSDK", "初始化");
    }
}
