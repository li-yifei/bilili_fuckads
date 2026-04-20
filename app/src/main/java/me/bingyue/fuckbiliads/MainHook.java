package me.bingyue.fuckbiliads;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface;

public class MainHook extends XposedModule {
    private static final String TAG = "FuckBiliAds";
    private static final String TARGET_PACKAGE = "tv.danmaku.bili";
    private static final String TARGET_CLASS = "tv.danmaku.bili.ui.splash.ad.model.Splash";
    private static final String TARGET_METHOD = "isValid";

    @Override
    public void onPackageLoaded(XposedModuleInterface.PackageLoadedParam param) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return;
        }
        if (!TARGET_PACKAGE.equals(param.getPackageName())) {
            return;
        }
        try {
            Class<?> splashClass = Class.forName(TARGET_CLASS, false, param.getDefaultClassLoader());
            Method isValid = splashClass.getDeclaredMethod(TARGET_METHOD);
            hook(isValid)
                    .setPriority(XposedInterface.PRIORITY_DEFAULT)
                    .intercept(chain -> false);
        } catch (Throwable t) {
            log(Log.ERROR, TAG, "Failed to hook Splash.isValid", t);
        }
    }
}