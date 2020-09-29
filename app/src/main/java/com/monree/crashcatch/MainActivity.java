package com.monree.crashcatch;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        //initUncaughtExceptionHandler();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_exception1:
                TextView textView = null;
                textView.setText(getString(R.string.app_name));
                break;
            case R.id.btn_exception2:
                int[] testArray = new int[2];
                Log.i("ExceptionTest",testArray[3] + "");
                break;
            case R.id.btn_exception3:
                List<String> list = new ArrayList<>();
                list.add("0");
                Log.i("ExceptionTest", list.get(1));
                break;
            case R.id.btn_exception4:
                List<String> objectList = new ArrayList<>();
                while (true)
                    objectList.add(new String());
            case R.id.btn_exception5:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tv_test.setText("hhh");
                    }
                }).start();
                break;
            case R.id.btn_exception6:
                CrashReport.testJavaCrash();
                break;
            case R.id.btn_exception7:
                CrashReport.testANRCrash();
                break;
            case R.id.btn_exception8:
                CrashReport.testNativeCrash();
                break;
            case R.id.btn_test1:
                tv_test.setText(JNI.test());
                JNI.test2();
//                try {
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;
            default:
                break;
        }
    }

//    private void initUncaughtExceptionHandler() {
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, final Throwable throwable) {
//                throwable.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void run() {
//                        showDialog(throwable);
//                        tv_test.setText(throwable.toString() + Arrays.toString(throwable.getStackTrace()));
//                        postDataWithParam(throwable.toString() + Arrays.toString(throwable.getStackTrace()));
//                    }
//                });
//            }
//        });
//    }

//    private void showDialog(Exception e) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.dialog_title1));
//        builder.setIcon(R.mipmap.ic_launcher_round);
//        builder.setMessage(getString(R.string.dialog_content1) + e.toString().substring(0, e.toString().indexOf(":")) + "；\n" + getString(R.string.dialog_tip) + "。");
//        builder.setPositiveButton(getString(R.string.dialog_button_positive), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                Toast.makeText(MainActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
//                dialogInterface.dismiss();
//            }
//        });
//        builder.create().show();
//    }
//
//    private void showDialog(Error e) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.dialog_title2));
//        builder.setIcon(R.mipmap.ic_launcher_round);
//        builder.setMessage(getString(R.string.dialog_content2) + e.toString().substring(0, e.toString().indexOf(":")) + "；\n" + getString(R.string.dialog_tip) + "。");
//        builder.setPositiveButton(getString(R.string.dialog_button_positive), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                Toast.makeText(MainActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
//                dialogInterface.dismiss();
//            }
//        });
//        builder.create().show();
//    }
//
//    private void showDialog(Throwable e) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.dialog_title1));
//        builder.setIcon(R.mipmap.ic_launcher_round);
//        builder.setMessage(getString(R.string.dialog_content1) + e.toString().substring(0, e.toString().indexOf(":")) + "；\n" + getString(R.string.dialog_tip) + "。");
//        builder.setPositiveButton(getString(R.string.dialog_button_positive), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                Toast.makeText(MainActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
//                dialogInterface.dismiss();
//            }
//        });
//        builder.create().show();
//    }
//
//    private void postDataWithParam(String stackTrace) {
//        OkHttpClient client = new OkHttpClient();
//        FormBody.Builder formBody = new FormBody.Builder();
//        formBody.add("stackTrace", stackTrace);
//        Request request = new Request.Builder()
//                .url("https://www.baidu.com/")
//                .post(formBody.build())
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Toast.makeText(MainActivity.this, "日志上传失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()){
//                    Log.d("postTest","获得响应");
//                    Log.d("postTest","response.code()=="+response.code());
//                    Log.d("postTest","response.body().string()=="+response.body().string());
//                }
//                Log.d("postTest","日志上传成功");
//            }
//        });
//    }
}
