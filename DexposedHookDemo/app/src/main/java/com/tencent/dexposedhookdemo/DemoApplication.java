package com.tencent.dexposedhookdemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.patch.PatchMain;
import com.taobao.patch.PatchResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * author：pudgeli on 16/7/5
 * email：pudgeli@tencent.com
 */
public class DemoApplication extends Application{
    public static final String TAG = "DexposedHookDemo";

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (DexposedBridge.canDexposed(this)){
            Log.e(TAG,"can dexposed");

//            DexposedBridge.findAndHookMethod(Log.class, "e", String.class, String.class, new XC_MethodHook() {
//                /**
//                 * Called after the invocation of the method.
//                 * <p>Can use {@link MethodHookParam#setResult(Object)} and {@link MethodHookParam#setThrowable(Throwable)}
//                 * to modify the return value of the original method.
//                 *
//                 * @param param
//                 */
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                     Log.w(TAG,"execute hook method");
//                }
//            });

            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
             + File.separator + "app-debug.apk";
            File patch = new File(this.getFilesDir(),"patch.apk");
            try {
                File file = new File(path);
                InputStream input = null;
                OutputStream output = null;
                try {
                    input = new FileInputStream(file);
                    output = new FileOutputStream(patch);
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buf)) > 0) {
                        output.write(buf, 0, bytesRead);
                    }
                } finally {
                    input.close();
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }



            PatchResult result = PatchMain.load(this,patch.getAbsolutePath(),null);
            if (result.isSuccess()){
                Log.e(TAG,"patch success");
            }else{
                Log.e(TAG,"patch failed");
            }

        }
    }


}
