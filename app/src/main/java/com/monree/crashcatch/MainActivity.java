package com.monree.crashcatch;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initANRCacheHelper();
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
    }

    private void initTextView() {
        tv_test = findViewById(R.id.tv_test);
    }

    private void initANRCacheHelper() {
        new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                // Handle the error. For example, log it to HockeyApp:
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
                TextView textView = null;
                textView.setText(getString(R.string.app_name));
                break;
            case R.id.btn_exception2:
                //ArrayIndexOutOfBoundsException
                int[] testArray = new int[2];
                Log.i("ExceptionTest",testArray[3] + "");
                break;
            case R.id.btn_exception3:
                //IndexOutOfBoundsException
                List<String> list = new ArrayList<>();
                list.add("0");
                Log.i("ExceptionTest", list.get(1));
                break;
            case R.id.btn_exception4:
                //OutOfMemoryError
                List<String> objectList = new ArrayList<>();
                while (true)
                    objectList.add(new String());
            case R.id.btn_exception5:
                //CalledFromWrongThreadException
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tv_test.setText("hhh");
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
            default:
                break;
        }
    }
}
