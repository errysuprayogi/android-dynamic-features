package com.tokopedia.hotfixsdk.patch

import android.util.Log
import com.meituan.robust.Patch
import com.meituan.robust.RobustCallBack

/**
 * Author errysuprayogi on 01,February,2020
 */
class PatchLogger : RobustCallBack {
    override fun onPatchListFetched(result: Boolean, isNet: Boolean, patches: List<Patch>) {
        Log.d(TAG, "onPatchListFetched result: $result")
        Log.d(TAG, "onPatchListFetched isNet: $isNet")
        for (patch in patches) {
            Log.d(TAG, "onPatchListFetched patch: " + patch.name)
        }
    }

    override fun onPatchFetched(result: Boolean, isNet: Boolean, patch: Patch) {
        Log.d(TAG, "onPatchFetched result: $result")
        Log.d(TAG, "onPatchFetched isNet: $isNet")
        Log.d(TAG, "onPatchFetched patch: " + patch.name)
    }

    override fun onPatchApplied(result: Boolean, patch: Patch) {
        Log.d(TAG, "onPatchApplied result: $result")
        Log.d(TAG, "onPatchApplied patch: " + patch.name)
    }

    override fun logNotify(log: String, where: String) {
        Log.d(TAG, "logNotify log: $log")
        Log.d(TAG, "logNotify where: $where")
    }

    override fun exceptionNotify(throwable: Throwable, where: String) {
        Log.e(TAG, "exceptionNotify where: $where", throwable)
    }

    companion object {
        private val TAG = PatchLogger::class.java.simpleName
    }
}