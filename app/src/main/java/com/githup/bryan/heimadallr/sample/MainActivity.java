package com.githup.bryan.heimadallr.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, HEIMADALLR_PERMISSION);
            }
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("====================", "onClick of R.id.button1: ", e);
        }
    }

    // 要申请的权限
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int HEIMADALLR_PERMISSION = 0x1111;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case HEIMADALLR_PERMISSION:
                if (grantResults.length > 0){
                    boolean b=false;
                    for(int permission : grantResults){
                        if(permission != PackageManager.PERMISSION_GRANTED){
                            b=true;
                        }
                    }

                    if(b){
                        Toast.makeText(this, "权限被禁止，无法使用Heimadallr功能！", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
        }
    }
}
