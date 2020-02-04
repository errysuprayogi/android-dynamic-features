package com.tokopedia.hotfixsdk.sevice;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Author errysuprayogi on 03,February,2020
 */
public class DownloadService {

    private long downloadID;

    public DownloadService() {
    }

    private void beginDownload(Context context){
        File file=new File(context.getExternalFilesDir(null),"Dummy");
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("http://speedtest.ftp.otenet.gr/files/test10Mb.db"))
                .setTitle("Dummy File")// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
    }

}
