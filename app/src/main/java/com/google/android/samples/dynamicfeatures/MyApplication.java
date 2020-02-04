package com.google.android.samples.dynamicfeatures;


import com.google.android.play.core.splitcompat.SplitCompatApplication;

import com.tokopedia.hotfixsdk.HotfixSDK;

/**
 * Author errysuprayogi on 03,February,2020
 */
public class MyApplication extends SplitCompatApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        HotfixSDK.init(this);
    }
}
