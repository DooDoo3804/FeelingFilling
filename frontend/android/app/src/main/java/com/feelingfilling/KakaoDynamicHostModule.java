package com.feelingfilling;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.ReactPackage;
import com.facebook.react.uimanager.ViewManager;
import java.util.Collections;
import java.util.List;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// public class KakaoDynamicHostModule extends ReactContextBaseJavaModule implements ReactPackage {
// public class KakaoDynamicHostModule implements ReactPackage {
public class KakaoDynamicHostModule extends ReactContextBaseJavaModule {

    // private final ReactApplicationContext reactContext;
    private ReactApplicationContext reactContext;

    public KakaoDynamicHostModule(ReactApplicationContext reactContext) {
    this.reactContext = reactContext;
    }
    // public KakaoDynamicHostModule(ReactApplicationContext reactContext) {
    //     super(reactContext);
    //     this.reactContext = reactContext;
    // }

    // @Override
    public String getName() {
        return "KakaoDynamicHost";
    }

    // @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    // @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new KakaoDynamicHostModule(reactContext));
    }

    @ReactMethod
    public void registerDynamicHost(String kakaoAppKey) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.VIEW");
        intentFilter.addDataScheme("kakao" + kakaoAppKey);
        // intentFilter.addDataHost("oauth");
        intentFilter.addDataAuthority("oauth", null);
        intentFilter.addCategory("android.intent.category.DEFAULT");
        intentFilter.addCategory("android.intent.category.BROWSABLE");

        BroadcastReceiver dynamicHostReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Handle the received intent
            }
        };

        reactContext.registerReceiver(dynamicHostReceiver, intentFilter);
    }
}
