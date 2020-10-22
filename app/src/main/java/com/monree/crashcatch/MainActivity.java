package com.monree.crashcatch;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.tencent.bugly.crashreport.CrashReport;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_test;
    private final String logTag = "MonreeTestLog";
    private final String activityName = "MainActivity";
    private boolean normalFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initANRCacheHelper();
        Log.d(logTag, activityName + "->生命周期onCreate");
    }

    private void initView() {
        initButton();
        initTextView();
    }

    private void initButton() {
        Button btn_exception1 = findViewById(R.id.btn_exception1);
        btn_exception1.setOnClickListener(this);
        Button btn_exception2 = findViewById(R.id.btn_exception2);
        btn_exception2.setOnClickListener(this);
        Button btn_exception3 = findViewById(R.id.btn_exception3);
        btn_exception3.setOnClickListener(this);
        Button btn_exception4 = findViewById(R.id.btn_exception4);
        btn_exception4.setOnClickListener(this);
        Button btn_exception5 = findViewById(R.id.btn_exception5);
        btn_exception5.setOnClickListener(this);
        Button btn_exception6 = findViewById(R.id.btn_exception6);
        btn_exception6.setOnClickListener(this);
        Button btn_exception7 = findViewById(R.id.btn_exception7);
        btn_exception7.setOnClickListener(this);
        Button btn_exception8 = findViewById(R.id.btn_exception8);
        btn_exception8.setOnClickListener(this);
        Button btn_test1 = findViewById(R.id.btn_test1);
        btn_test1.setOnClickListener(this);
        Button btn_exception9 = findViewById(R.id.btn_test2);
        btn_exception9.setOnClickListener(this);
    }

    private void initTextView() {
        tv_test = findViewById(R.id.tv_test);
    }

    private void initANRCacheHelper() {
        new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                Log.d("CrashReportInfo", "发生ANR");
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_exception1:
                //NullPointerException
                Log.d(logTag, "NullPointerException");
                throw new NullPointerException();
            case R.id.btn_exception2:
                //ArrayIndexOutOfBoundsException
                throw new ArrayIndexOutOfBoundsException();
            case R.id.btn_exception3:
                //IndexOutOfBoundsException
                throw new IndexOutOfBoundsException();
            case R.id.btn_exception4:
                //OutOfMemoryError
                throw new OutOfMemoryError();
            case R.id.btn_exception5:
                //CalledFromWrongThreadException
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        throw new RuntimeException();
                    }
                }).start();
                break;
            case R.id.btn_exception6:
                //BuglyJavaCrash
                CrashReport.testJavaCrash();
                break;
            case R.id.btn_exception7:
                //BuglyAnrCrash
                CrashReport.testANRCrash();
                break;
            case R.id.btn_exception8:
                //BuglyNativeCrash
                CrashReport.testNativeCrash();
                break;
            case R.id.btn_test1:
                //JNITest
                tv_test.setText(JNI.test());
                JNI.test2();
                break;
            case R.id.btn_test2:
                //JNI_signal_test
                JNI.test3();
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(logTag, activityName + "->生命周期onStart");
        normalFlag = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(logTag, activityName + "->生命周期onResume");
        if(!normalFlag)
            finish();
        normalFlag = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(logTag, activityName + "->生命周期onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(logTag, activityName + "->生命周期onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(logTag, activityName + "->生命周期onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(logTag, activityName + "->生命周期onDestroy");
    }
}
