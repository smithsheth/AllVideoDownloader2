package com.video.allvideodownloaderbt.tasks;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.utils.iUtils;

import java.io.File;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.video.allvideodownloaderbt.utils.Constants.DOWNLOAD_DIRECTORY;
import static com.video.allvideodownloaderbt.utils.Constants.MY_ANDROID_10_IDENTIFIER_OF_FILE;
import static com.video.allvideodownloaderbt.utils.Constants.PREF_APPNAME;
import static com.video.allvideodownloaderbt.utils.Constants.directoryInstaShoryDirectorydownload_images;
import static com.video.allvideodownloaderbt.utils.Constants.directoryInstaShoryDirectorydownload_videos;

public class downloadFile {
    public static DownloadManager downloadManager;
    public static long downloadID;
    private static String mBaseFolderPath;


    public static void Downloading(final Context context, String url, String title, String ext) {
        String cutTitle = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
        } else {
            if (!title.equals("") && !title.equals("null")) {
                cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
            } else {
                cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
            }
        }

        if (ext.equals(".mp3")) {
            cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
        }

        if (ext.equals(".png")) {
            cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
        }


        String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
        cutTitle = cutTitle.replaceAll(characterFilter, "");
        cutTitle = cutTitle.replaceAll("['+.^:,#\"]", "");
        cutTitle = cutTitle.replace(" ", "-").replace("!", "").replace(":", "") + ext;
        if (cutTitle.length() > 100) {
            cutTitle = cutTitle.substring(0, 100) + ext;
        }
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //  Uri mainUri = FileProvider.get(context, context.getApplicationContext().getPackageName() + ".provider", url);


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription(context.getString(R.string.downloading_des));

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String folderName = DOWNLOAD_DIRECTORY;
        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdir();
        }

        SharedPreferences preferences = context.getSharedPreferences(PREF_APPNAME, Context.MODE_PRIVATE);

        if (!preferences.getString("path", "DEFAULT").equals("DEFAULT")) {

            mBaseFolderPath = preferences.getString("path", "DEFAULT");
        } else {

            mBaseFolderPath = Environment.getExternalStorageDirectory().toString() + File.separator + folderName;
            System.out.println("myerroris5555555555 " + context.getExternalFilesDir(null).getAbsolutePath() + File.separator + folderName);
            // mBaseFolderPath = android.os.Environment.getDataDirectory() + File.separator + folderName;
        }

        File mBaseFolderPathfile = new File(mBaseFolderPath);

        if (!mBaseFolderPathfile.exists()) {
            mBaseFolderPathfile.mkdir();
        }
        String[] bits = mBaseFolderPath.split("/");
        String Dir = bits[bits.length - 1];
        //  request.setDestinationUri(new File(mBaseFolderPath).);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, cutTitle);

        } else {
            if (cutTitle.contains(MY_ANDROID_10_IDENTIFIER_OF_FILE)) {

                request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, cutTitle);
            } else {

                request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, MY_ANDROID_10_IDENTIFIER_OF_FILE + cutTitle);

            }
        }


        request.allowScanningByMediaScanner();
        downloadID = downloadManager.enqueue(request);
        Log.e("downloadFileName", cutTitle);


        iUtils.ShowToast(context, context.getResources().getString(R.string.don_start));


    }


    public static void DownloadingInsta(final Context context, String url, String title, String ext) {
        String cutTitle = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
        } else {
            if (!title.equals("") && !title.equals("null")) {
                cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
            } else {
                cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
            }
        }

        if (ext.equals(".mp3")) {
            cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
        }

        if (ext.equals(".png")) {
            cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + title;
        }


        String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
        cutTitle = cutTitle.replaceAll(characterFilter, "");
        cutTitle = cutTitle.replaceAll("['+.^:,#\"]", "");
        cutTitle = cutTitle.replace(" ", "-").replace("!", "").replace(":", "") + ext;
        if (cutTitle.length() > 100)
            cutTitle = cutTitle.substring(0, 100) + ext;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //  Uri mainUri = FileProvider.get(context, context.getApplicationContext().getPackageName() + ".provider", url);


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);  // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);

        request.setDescription(context.getString(R.string.downloading_des));

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        String folderName = DOWNLOAD_DIRECTORY;
        File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + folderName);
        if (!file.exists()) {
            file.mkdir();
        }

        SharedPreferences preferences = context.getSharedPreferences(PREF_APPNAME, Context.MODE_PRIVATE);

        if (!preferences.getString("path", "DEFAULT").equals("DEFAULT")) {

            mBaseFolderPath = preferences.getString("path", "DEFAULT");
        } else {

            mBaseFolderPath = Environment.getExternalStorageDirectory().toString() + File.separator + folderName;
            System.out.println("myerroris5555555555 " + context.getExternalFilesDir(null).getAbsolutePath() + File.separator + folderName);
            // mBaseFolderPath = android.os.Environment.getDataDirectory() + File.separator + folderName;
        }

        File mBaseFolderPathfile = new File(mBaseFolderPath);

        if (!mBaseFolderPathfile.exists()) {
            mBaseFolderPathfile.mkdir();
        }
        String[] bits = mBaseFolderPath.split("/");
        String Dir = bits[bits.length - 1];
        //  request.setDestinationUri(new File(mBaseFolderPath).);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            if (ext.equals(".png")) {
                System.out.println("dirrrrnbrjjjn " + Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + directoryInstaShoryDirectorydownload_images);

                request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, directoryInstaShoryDirectorydownload_images + cutTitle);
                System.out.println("dirrrrnbrjjjn img " + Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + directoryInstaShoryDirectorydownload_images);

            } else if (ext.equals(".mp4")) {

                System.out.println("dirrrrnbrjjjn " + DIRECTORY_DOWNLOADS + directoryInstaShoryDirectorydownload_videos);
                //  request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS+directoryInstaShoryDirectorydownload_videos, cutTitle);
                request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, directoryInstaShoryDirectorydownload_videos + cutTitle);
                System.out.println("dirrrrnbrjjjn img " + Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + directoryInstaShoryDirectorydownload_images);

            }


        } else {
            if (ext.equals(".png")) {
                System.out.println("dirrrrnbrjjjn " + Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + directoryInstaShoryDirectorydownload_images);

                request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, directoryInstaShoryDirectorydownload_images + cutTitle);
                System.out.println("dirrrrnbrjjjn img " + Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + directoryInstaShoryDirectorydownload_images);

            } else if (ext.equals(".mp4")) {

                System.out.println("dirrrrnbrjjjn " + DIRECTORY_DOWNLOADS + directoryInstaShoryDirectorydownload_videos);
                //  request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS+directoryInstaShoryDirectorydownload_videos, cutTitle);
                request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, directoryInstaShoryDirectorydownload_videos + cutTitle);
                System.out.println("dirrrrnbrjjjn img " + Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + directoryInstaShoryDirectorydownload_images);

            }

        }


        request.allowScanningByMediaScanner();
        downloadID = downloadManager.enqueue(request);
        Log.e("downloadFileName", cutTitle);

        if (ext.equals(".png")) {
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    MediaScannerConnection.scanFile(context, new String[]{new File(DIRECTORY_DOWNLOADS + "/" + directoryInstaShoryDirectorydownload_images + cutTitle).getAbsolutePath()},
                            null, new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                }
                            });
                } else {
                    context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(DIRECTORY_DOWNLOADS + "/" + directoryInstaShoryDirectorydownload_images + cutTitle))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ext.equals(".mp4")) {

            if (cutTitle.contains(MY_ANDROID_10_IDENTIFIER_OF_FILE)) {

                try {
                    if (Build.VERSION.SDK_INT >= 19) {
                        MediaScannerConnection.scanFile(context, new String[]{new File(DIRECTORY_DOWNLOADS + "/" + directoryInstaShoryDirectorydownload_videos + cutTitle).getAbsolutePath()},
                                null, new MediaScannerConnection.OnScanCompletedListener() {
                                    public void onScanCompleted(String path, Uri uri) {
                                    }
                                });
                    } else {
                        context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(DIRECTORY_DOWNLOADS + "/" + directoryInstaShoryDirectorydownload_videos + cutTitle))));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                cutTitle = MY_ANDROID_10_IDENTIFIER_OF_FILE + cutTitle;

                try {
                    if (Build.VERSION.SDK_INT >= 19) {
                        MediaScannerConnection.scanFile(context, new String[]{new File(DIRECTORY_DOWNLOADS + "/" + directoryInstaShoryDirectorydownload_videos + cutTitle).getAbsolutePath()},
                                null, new MediaScannerConnection.OnScanCompletedListener() {
                                    public void onScanCompleted(String path, Uri uri) {
                                    }
                                });
                    } else {
                        context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(DIRECTORY_DOWNLOADS + "/" + directoryInstaShoryDirectorydownload_videos + cutTitle))));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }

        iUtils.ShowToast(context, context.getResources().getString(R.string.don_start));


    }


}