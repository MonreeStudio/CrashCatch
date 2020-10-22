package com.monree.crashcatch;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CrashActivity extends AppCompatActivity {
    private final String logTag = "MonreeTestLog";
    private final String activityName = "CrashActivity";

    private TextView tv_exception;
    private TextView tv_help;
    private Button btn_close;
    private Button btn_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        Log.d(logTag, activityName + "->生命周期onCreate");
        initTextView();
        initButton();
        Throwable throwable = (Throwable) getIntent().getSerializableExtra("Throwable");
        postDataWithParam(throwable.toString() + Arrays.toString(throwable.getStackTrace()));
        throwable.printStackTrace();
        initContent(throwable);
    }

    private void initButton() {
        btn_close = findViewById(R.id.btn_close);
        btn_restart = findViewById(R.id.btn_restart);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initTextView() {
        tv_exception = findViewById(R.id.tv_exception);
        tv_help = findViewById(R.id.tv_help);
    }

    @SuppressLint("SetTextI18n")
    private void initContent(Throwable e) {
        if(e.toString().contains(":"))
            tv_exception.setText(getString(R.string.dialog_content1) + e.toString().substring(0, e.toString().indexOf(":")) + "\n" + getString(R.string.dialog_tip));
        else
            tv_exception.setText(getString(R.string.dialog_content1) + e.toString() + "\n" + getString(R.string.dialog_tip));
        tv_help.setText(getString(R.string.dialog_customer_service_tip) + CrashHandlerConfig.supportNumber);
    }

    private void showDialog(Throwable e) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title1));
        builder.setIcon(R.mipmap.ic_launcher_round);
        if (e.toString().contains(":"))
            builder.setMessage(getString(R.string.dialog_content1) + e.toString().substring(0, e.toString().indexOf(":")) + "；\n" + getString(R.string.dialog_tip) + "。");
        else
            builder.setMessage(getString(R.string.dialog_content1) + e.toString() + ";\n" + getString(R.string.dialog_tip) + "。");
        builder.setPositiveButton(getString(R.string.dialog_button_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                System.exit(1);
            }
        });
        builder.create().show();
    }

    private void postDataWithParam(String stackTrace) {
        Log.d("CrashReportInfo","尝试上传日志");
        final OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("stackTrace", stackTrace);
        final Request request = new Request.Builder()
                .url("https://www.baidu.com/")
                .post(formBody.build())
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    Log.d("CrashReportInfo", "上传日志成功：" + response.body().string());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.d("CrashReportInfo", "上传日志失败：" + e);
                }
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(logTag, activityName + "->生命周期onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(logTag, activityName + "->生命周期onResume");
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
    protected void onDestroy() {
        super.onDestroy();
        Log.d(logTag, activityName + "->生命周期onDestroy");
    }

}