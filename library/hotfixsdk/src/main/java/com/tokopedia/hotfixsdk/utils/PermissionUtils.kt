package com.tokopedia.hotfixsdk.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Author errysuprayogi on 01,February,2020
 */
object PermissionUtils {
    /**
     * 是否有权限
     *
     * @param context
     * @return
     */
    fun checkSelfPermission(context: Context?, permission: String?): Boolean {
        if (null == context) {
            return false
        }
        val per = ContextCompat.checkSelfPermission(context, permission!!)
        return per == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value [PackageManager.PERMISSION_GRANTED].
     *
     * @see Activity.onRequestPermissionsResult
     */
    fun verifyPermissions(grantResults: IntArray?): Boolean { // At least one result must be checked.
        if (null == grantResults || grantResults.size < 1) {
            return false
        }
        // Verify that each required permission has been granted, otherwise return false.
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun isGrantSDCardReadPermission(context: Context?): Boolean {
        return checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun requestSDCardReadPermission(activity: Activity?, requestCode: Int) {
        ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
    }
}