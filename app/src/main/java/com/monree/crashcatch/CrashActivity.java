package com.monree.crashcatch;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        Throwable throwable = (Throwable) getIntent().getSerializableExtra("Throwable");
        postDataWithParam(throwable.toString() + Arrays.toString(throwable.getStackTrace()));
        throwable.printStackTrace();
        showDialog(throwable);
    }

    private void showDialog(Throwable e) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title1));
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setMessage(getString(R.string.dialog_content1) + e.toString().substring(0, e.toString().indexOf(":")) + "；\n" + getString(R.string.dialog_tip) + "。");
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
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("CrashReportInfo","日志上传失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()){
//                    Log.d("postTest","获得响应");
//                    Log.d("postTest","response.code()=="+response.code());
//                    Log.d("postTest","response.body().string()=="+response.body().string());
//                }
//                Log.d("CrashReportInfo","日志上传成功");
//            }
//        });
    }
}