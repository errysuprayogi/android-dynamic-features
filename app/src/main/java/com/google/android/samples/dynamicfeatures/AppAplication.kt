package com.google.android.samples.dynamicfeatures

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.google.android.play.core.splitcompat.SplitCompat

/**
 * Author errysuprayogi on 27,January,2020
 */
class AppAplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}