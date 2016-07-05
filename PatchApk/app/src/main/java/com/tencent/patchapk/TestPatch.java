package com.tencent.patchapk;

import android.os.Bundle;
import android.util.Log;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;
import com.taobao.patch.IPatch;
import com.taobao.patch.PatchParam;

/**
 * author：pudgeli on 16/7/5
 * email：pudgeli@tencent.com
 */
public class TestPatch implements IPatch{
    @Override
    public void handlePatch(PatchParam patchParam) throws Throwable {
        Class<?> cls = null;
        try {
            cls = patchParam.context.getClassLoader().loadClass("com.tencent.dexposedhookdemo.MainActivity");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        DexposedBridge.findAndHookMethod(cls, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.e("Pathc","pathc execute");
            }
        });
    }
}
