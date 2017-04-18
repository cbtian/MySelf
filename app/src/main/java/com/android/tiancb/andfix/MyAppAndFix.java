package com.android.tiancb.andfix;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by tiancb on 17/2/24.
 */

public class MyAppAndFix extends Application{
    PatchManager patchManager;
    private static final String TAG = "euler";
    private static final String APATCH_PATH = "/DCIM/out.apatch";
    @Override
    public void onCreate() {
        super.onCreate();
        patchManager = new PatchManager(this);
        String appversion= null;
        try {
            appversion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        patchManager.init(appversion);
        patchManager.loadPatch();
        String patchFileString = "";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            patchFileString = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + APATCH_PATH;
        }
        File apatchPath = new File(patchFileString);
        Log.e(TAG,"文件路径 ＝ " + patchFileString);
        if (apatchPath.exists()){
            Log.e(TAG, "补丁文件存在");
            Toast.makeText(getApplicationContext(),"补丁文件存在", Toast.LENGTH_SHORT).show();
            try {
                //添加apatch文件
                patchManager.addPatch(patchFileString);
            } catch (IOException e) {
                Log.e(TAG, "打补丁出错了");
                Toast.makeText(getApplicationContext(),"打补丁出错了"+e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "补丁文件不存在");
            Toast.makeText(getApplicationContext(),"补丁文件不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
