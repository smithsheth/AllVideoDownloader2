package com.video.allvideodownloaderbt.tasks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.video.allvideodownloaderbt.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, Integer, String> {

    int id = 123;
    OutputStream output;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder build;
    private File fileurl;
    private Context context;
    private String selectedDate;
    private String ts = "";

    public DownloadFileFromURL(Context context, String selectedDate) {
        this.context = context;
        this.selectedDate = selectedDate;

    }

    protected void onPreExecute() {
        super.onPreExecute();

        mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        build = new NotificationCompat.Builder(context);
        build.setContentTitle("Download")
                .setContentText("Download in progress")
                .setChannelId(id + "")
                .setAutoCancel(false)
                .setDefaults(0)
                .setSmallIcon(R.drawable.ic_download_color_24dp);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id + "",
                    "Social Media video Downloader",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("no sound");
            channel.setSound(null, null);
            channel.enableLights(false);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(false);
            mNotifyManager.createNotificationChannel(channel);

        }
        build.setProgress(100, 0, false);
        mNotifyManager.notify(id, build.build());
        String msg = "Download started";
        //CustomToast.showToast(context,msg);
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        ts = selectedDate.split("T")[0];
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            int lenghtOfFile = conection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);
            // Output stream
            output = new FileOutputStream(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + ts + ".mp4");
            fileurl = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + ts + ".mp4");
            byte[] data = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                int cur = (int) ((total * 100) / lenghtOfFile);

                publishProgress(Math.min(cur, 100));
                if (Math.min(cur, 100) > 98) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.d("Failure", "sleeping failure");
                    }
                }
                Log.i("currentProgress", "currentProgress: " + Math.min(cur, 100) + "\n " + cur);

                output.write(data, 0, count);
            }

            output.flush();

            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        build.setProgress(100, progress[0], false);
        mNotifyManager.notify(id, build.build());
        super.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(String file_url) {
        build.setContentText("Download complete");
        build.setProgress(0, 0, false);
        mNotifyManager.notify(id, build.build());
    }
}
