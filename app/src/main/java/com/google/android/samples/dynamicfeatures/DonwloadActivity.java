package com.google.android.samples.dynamicfeatures;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

/**
 * Author errysuprayogi on 03,February,2020
 */
public class DonwloadActivity extends AppCompatActivity {

    private Button button;
    private long downloadID;

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(DonwloadActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        button = findViewById(R.id.download);
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginDownload();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    private void beginDownload() {
        final String PATH = "out.apatch";
        File file = new File(getDir("patch", Context.MODE_PRIVATE).getAbsolutePath(), PATH);
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://www.androidtutorialpoint.com/wp-content/uploads/2016/09/Beauty.jpg"))
                .setTitle("Image File")// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
//                .setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,"AndroidTutorialPoint.jpg")
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
    }

}
