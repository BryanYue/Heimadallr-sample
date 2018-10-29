package com.githup.bryan.heimadallr_android.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.githup.bryan.heimadallr.R;
import com.githup.bryan.heimadallr_analyzer.HeimadallrContext;
import com.githup.bryan.heimadallr_android.Heimadallr;

public class PermissionActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvCont;
    private TextView btnConfirm;
    private TextView btnCancle;
    private static final int HEIMADALLR_PERMISSION = 0x1111;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public static void startPermissionActivity(Context context) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initview();
    }

    private void initview() {
        tvTitle = findViewById(R.id.tv_title);
        tvCont = findViewById(R.id.tv_cont);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancle = findViewById(R.id.btn_cancle);

        tvTitle.setText("温馨提示");
        tvCont.setText("Heimadallr正常运行需要[READ_PHONE_STATE、READ_PHONE_STATE]权限,否则无法运行！");
        btnCancle.setText("取消");
        btnConfirm.setText("去授权");
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(PermissionActivity.this, permissions, HEIMADALLR_PERMISSION);
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case HEIMADALLR_PERMISSION:
                if (grantResults.length > 0) {
                    boolean b = false;
                    for (int permission : grantResults) {
                        if (permission != PackageManager.PERMISSION_GRANTED) {
                            b = true;
                        }
                    }

                    if (b) {
                        Toast.makeText(this, "权限被禁止，无法使用Heimadallr功能！", Toast.LENGTH_SHORT).show();
                    } else {
                        Heimadallr.setEnabled(HeimadallrContext.get().provideContext(), DisplayActivity.class, HeimadallrContext.get().displayNotification());
                        Heimadallr.get().start();
                        finish();
                    }

                }

                break;
            default:
        }
    }
}
