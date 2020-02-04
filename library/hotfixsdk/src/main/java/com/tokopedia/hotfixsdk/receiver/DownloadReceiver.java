package com.tokopedia.hotfixsdk.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Author errysuprayogi on 03,February,2020
 */
public class DownloadReceiver extends BroadcastReceiver {

    private static final String TAG = DownloadReceiver.class.getSimpleName();
    private long downloadID;

    public void setDownloadID(long downloadID) {
        this.downloadID = downloadID;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Download onReceive");
        //Fetching the download id received with the broadcast
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        //Checking if the received broadcast is for our enqueued download by matching download id
        if (downloadID == id) {
            Log.d(TAG, "Download Completed");
//            new PatchExecutor(context, new PatchManipulateImp(),
//                    new PatchLogger()).start();
        }
    }
}
