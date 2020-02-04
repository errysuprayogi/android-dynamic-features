package com.tokopedia.hotfixsdk

import android.app.Application
import android.app.DownloadManager
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.meituan.robust.PatchExecutor
import com.tokopedia.hotfixsdk.patch.PatchLogger
import com.tokopedia.hotfixsdk.patch.PatchManipulateImp
import com.tokopedia.hotfixsdk.receiver.DownloadReceiver

/**
 * Author errysuprayogi on 03,February,2020
 */
class HotfixSDK private constructor(private val app: Application) : LifecycleObserver {

    private val receiver = DownloadReceiver()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun registerDownloadReceiver() {
        Log.d(TAG, "App onCreate")
        app.applicationContext.registerReceiver(receiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun unregisterDownloadReceiver() {
        app.applicationContext.unregisterReceiver(receiver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        Log.d(TAG, "App onBackground")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onForegroud() {
        Log.d(TAG, "App onForegroud")
        PatchExecutor(app, PatchManipulateImp(), PatchLogger()).start()
    }

    companion object {
        private val TAG = HotfixSDK::class.java.simpleName
        @JvmStatic
        fun init(application: Application) {
            HotfixSDK(application)
        }
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}