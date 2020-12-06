package com.video.allvideodownloaderbt.tasks;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.exoplayer2.upstream.DataSchemeDataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.htetznaing.lowcostvideo.LowCostVideo;
import com.htetznaing.lowcostvideo.Model.XModel;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.models.TikTokNoWaterMarkApi;
import com.video.allvideodownloaderbt.models.TopBuzzDownloader;
import com.video.allvideodownloaderbt.models.VimeoVideoDownloader;
import com.video.allvideodownloaderbt.restapiclientsstuff.RetrofitApiInterface;
import com.video.allvideodownloaderbt.restapiclientsstuff.RetrofitClient;
import com.video.allvideodownloaderbt.utils.VollySingltonClass;
import com.video.allvideodownloaderbt.utils.iUtils;
import com.yxcorp.gifshow.util.CPU;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WINDOW_SERVICE;

import static com.video.allvideodownloaderbt.utils.Constants.RedditApiUrl;
import static com.video.allvideodownloaderbt.utils.Constants.SoundApiUrl;
import static com.video.allvideodownloaderbt.utils.Constants.TiktokApiNowatermark;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class downloadVideo {


    public static Context Mcontext;
    public static ProgressDialog pd;
    public static Dialog dialog;
    public static SharedPreferences prefs;
    public static Boolean fromService;
    static String SessionID, Title;
    static int error = 1;
    static LinearLayout mainLayout;
    static Dialog dialogquality;
    static WindowManager windowManager2;
    static WindowManager.LayoutParams params;
    static View mChatHeadView;
    static ImageView img_dialog;
    static ArrayList dataModelArrayList;

    static String myURLIS = "";
    static Dialog dialog_quality_allvids;
    static ImageView show_ytd_inpip;
    public static String VideoUrl;

    public static StringBuilder m2858a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static String m2854a(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static final String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 255);
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getLocalizedMessage());
            return "";
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void Start(final Context context, String url, Boolean service) {

        Mcontext = context;
        fromService = service;
        Log.i("LOGClipboard111111 clip", "work 2");

        myURLIS = url;
        if (!fromService) {
            pd = new ProgressDialog(context);
            pd.setMessage(Mcontext.getResources().getString(R.string.genarating_download_link));
            pd.setCancelable(false);
            pd.show();
        }
        if (url.contains("tiktok")) {


//            new AlertDialog.Builder(context)
//                    .setTitle("Tiktok")
//                    .setMessage("Tiktok download is moved to another section, kindly use the 3 dots and click on Tiktok Downlaod")
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();

            CallsoundData(url, false);

//            String valueOf = String.valueOf((Calendar.getInstance().getTime().getTime() / 1000) / 60);
//
//            String str2 = "ssstiktok.io/1.0|" + "api2.ssstiktok.io" + "|" + valueOf + "|";
//
//            String str3 = "";
//            for (char valueOf2 : str2.toCharArray()) {
//                StringBuilder a2 = downloadVideo.m2858a(str3);
//                a2.append(String.format("%03d", new Object[]{Integer.valueOf(valueOf2)}));
//                str3 = a2.toString();
//            }
//            String md5 = downloadVideo.md5(str3);
//            //my ip 39.43.81.115
//           String a =  downloadVideo.m2854a(" ssstiktok.io/1.0@|addr:", "39.43.65.56", "/com.sss.video.downloader");
//String token = "d9a97b094b5a1cdbfaab98d117031de5f01e4faec165c5a6bdc452d1a52fc268";
//
//
//            Log.d("REQUEST_ORIGIN", "simpleIntStrConvert text\t" + str2);
//            Log.d("REQUEST_ORIGIN", "simpleIntStrConvert\t" + str3);
//            Log.d("REQUEST_ORIGIN", "ip\t" + "39.43.65.56");
//            Log.d("REQUEST_ORIGIN", "url\t" + "https://www.tiktok.com/@maimoonashah_/video/6890902265385848065");
//            Log.d("REQUEST_ORIGIN", "secretKey\t" + "81af582fc70eb75b066ed2e0d9eb99c29a7084598267c51gz3");
//            Log.d("REQUEST_ORIGIN", "ts\t" + valueOf);
//            Log.d("REQUEST_ORIGIN", "Tokenmd5\t" + md5);
//            Log.d("REQUEST_ORIGIN", "Token\t" + token);
//            Log.d("REQUEST_ORIGIN", "Token A \t" + a);
//        //    dVar.f18418a.getVideoInfo_V2(obj2, "en", md5, valueOf, a, "d9a97b094b5a1cdbfaab98d117031de5f01e4faec165c5a6bdc452d1a52fc268").mo12865a(new C4687b(dVar, obj2));
//
//
//            Map<String,String> map = new HashMap<>();
//            map.put( "id",url);
//            map.put( "locale", "en");
//            map.put(  "tt", md5);
//            map.put( "ts", valueOf);
//            map.put( "User-Agent", "ssstiktok.io/1.0@|addr:39.43.65.56/com.sss.video.downloader");
//            map.put( "Authorization", "d9a97b094b5a1cdbfaab98d117031de5f01e4faec165c5a6bdc452d1a52fc268");
//
//
//
//
//
//
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .build();
//            MediaType mediaType = MediaType.parse("text/plain");
//            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("id", "https://www.tiktok.com/@maimoonashah_/video/6890902265385848065")
//                    .addFormDataPart("locale", "en")
//                    .addFormDataPart("tt", "779a7d96ef51ac3943028e274d454e0c")
//                    .addFormDataPart("ts", "26744248")
//                    .addFormDataPart("User-Agent", "ssstiktok.io/1.0@|addr:39.43.65.56/com.sss.video.downloader")
//                    .addFormDataPart("Authorization", "d9a97b094b5a1cdbfaab98d117031de5f01e4faec165c5a6bdc452d1a52fc268")
//                    .build();
//            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url("https://api2.ssstiktok.io/1/fetch")
//                    .method("POST", body)
//                    .addHeader("Cookie", "PHPSESSID=693a28cc4b8f35fc4f0d583b2f3d5ed9")
//                    .build();
//
//            try {
//
//
//                client.newCall(request).enqueue(new okhttp3.Callback() {
//                    @Override
//                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
//                        Log.d("REQUEST_ORIGIN_err1", "ddd:" + e+"______"+call);
//                    }
//
//                    @Override
//                    public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
//                        Log.d("REQUEST_ORIGIN_resnse1", "ddd:" + response+"______"+call);
//
//                    }
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            RetrofitApiInterface apiService = RetrofitClient.getClientTiktok().create(RetrofitApiInterface.class);
//
//
//            Call<Html> callResult = apiService.getVideo_Info_tiktok(map);
//
//
//            callResult.enqueue(new Callback<Html>() {
//                @Override
//                public void onResponse(Call<Html> call, retrofit2.Response<Html> response) {
//                    Log.d("REQUEST_ORIGIN_resnse1", "ddd" + response+"______"+call);
//                }
//
//                @Override
//                public void onFailure(Call<Html> call, Throwable t) {
//                    Log.d("REQUEST_ORIGIN_err1", "ddd" + t+"______"+call);
//                }
//            });


        } else if (url.contains("likee")) {


//            LikeeVideoDownloader lIkeeVideoDownloader = new LikeeVideoDownloader(Mcontext, url);
//            lIkeeVideoDownloader.DownloadVideo();

            new CallLikeeData().execute(url);

            //   getAllDataForLikee(url);
        } else if (url.contains("sharechat.com")) {
            Log.i("LOGClipboard111111 clip", "work 66666");

            new callGetShareChatData().execute(url);
            Log.i("LOGClipboard111111 clip", "work 1111111");


//            TiktokVideoDownloader downloader = new TiktokVideoDownloader(Mcontext, url);
//            downloader.DownloadVideo();

            //   getAllDataForLikee(url);
        } else if (url.contains("roposo.com")) {
            Log.i("LOGClipboard111111 clip", "work 66666");

            new callGetRoposoData().execute(url);
            Log.i("LOGClipboard111111 clip", "work 1111111");

//
//            TiktokVideoDownloader downloader = new TiktokVideoDownloader(Mcontext, url);
//            downloader.DownloadVideo();

            //   getAllDataForLikee(url);
        } else if (url.contains("snackvideo.com") || url.contains("sck.io")) {

            //    new callGetSnackAppData().execute(url);

            if (url.contains("snackvideo.com") || url.contains("sck.io")) {
                new callGetSnackAppData().execute(url);
            } else if (url.contains("sck.io")) {
                getSnackVideoData(url, Mcontext);

            }

        } else if (url.contains("facebook.com")) {
//            //   GetFacebookData(url);
//            //new CallFacebookData().execute(url);
//            // getAllDataForLikee2(url, true);
//            FbVideoDownloader fbVideoDownloader = new FbVideoDownloader(Mcontext, url);
//            fbVideoDownloader.DownloadVideo();
            CallDailymotionData(url, true);
        } else if (url.contains("blogspot.com")) {
//            //   GetFacebookData(url);
//            //new CallFacebookData().execute(url);
//            // getAllDataForLikee2(url, true);
//            FbVideoDownloader fbVideoDownloader = new FbVideoDownloader(Mcontext, url);
//            fbVideoDownloader.DownloadVideo();
            CallDailymotionData(url, true);
        } else if (url.contains("instagram.com")) {

            new GetInstagramVideo().execute(url);

            // downloadVideo.getAllDataForLikee(url);
        } else if (url.contains("bilibili.com")) {

            new callGetbilibiliAppData().execute(url);

            // downloadVideo.getAllDataForLikee(url);
        } else if (url.contains("mitron.tv")) {

            new CallMitronData().execute(url);
        } else if (url.contains("josh")) {

            new CallJoshData().execute(url);
        } else if (url.contains("triller")) {

            new CallTrillerData().execute(url);
        } else if (url.contains("rizzle")) {

            new CallRizzleData().execute(url);
        } else if (url.contains("ifunny")) {

            new CallIfunnyData().execute(url);
        } else if (url.contains("trell.co")) {

            new CalltrellData().execute(url);
        } else if (url.contains("boloindya.com")) {

            new CallBoloindyaData().execute(url);
        } else if (url.contains("chingari.io")) {

            new CallchingariData().execute(url);
        } else if (url.contains("dubsmash")) {

            new CalldubsmashData().execute(url);
        } else if (url.contains("bittube")) {

            String myurlis1 = url;
            if (myurlis1.contains(".tv")) {
                String str = "/";
                myurlis1 = myurlis1.split(str)[myurlis1.split(str).length - 1];
                myurlis1 = "https://bittube.video/videos/watch/" +
                        myurlis1;
            }
            new CallgdriveData().execute(myurlis1);

        } else if (url.contains("drive.google.com") ||
                url.contains("mp4upload") ||

                url.contains("ok.ru") ||

                url.contains("mediafire") ||
                url.contains("gphoto") ||
                url.contains("uptostream") ||

                url.contains("fembed") ||
                url.contains("cocoscope") ||
                url.contains("sendvid") ||

                url.contains("vivo") ||
                url.contains("fourShared")) {


            new CallgdriveData().execute(url);
        } else if (url.contains("hind")) {

            new CallhindData().execute(url);
        }

//        else if (url.contains("zilivideo.com")) {
//
//            new CallziliData().execute(url);
//        }

        else if (url.contains("topbuzz.com")) {

            TopBuzzDownloader downloader = new TopBuzzDownloader(Mcontext, url, 12);
            downloader.DownloadVideo();

        } else if (url.contains("vimeo.com")) {
            VimeoVideoDownloader downloader = new VimeoVideoDownloader(Mcontext, url);
            downloader.DownloadVideo();

        } else if (url.contains("twitter.com")) {
//            TwitterVideoDownloader downloader = new TwitterVideoDownloader(Mcontext, url);
//            downloader.DownloadVideo();
            CallDailymotionData(url, true);
        }
        //new
        //working
        else if (url.contains("gag.com")) {
            CallsoundData(url, false);

        } else if (url.contains("buzzfeed.com")) {
            if (!fromService) {
                pd.dismiss();

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));

            }

        }

        //TODO Add quality list
        else if (url.contains("flickr")) {
            CallDailymotionData(url, true);

        } else if (url.contains("streamable")) {
            CallDailymotionData(url, true);

        } else if (url.contains("vk.com")) {


            CallVKData(url, true);


        } else if (url.contains("redd.it") || url.contains("reddit")) {


            CallREditData(url, true);


        } else if (url.contains("soundcloud")) {

            url = url.replace("//m.", "//");


            CallsoundData(url, false);


        } else if (url.contains("bandcamp")) {


            CallsoundData(url, false);


        } else if (url.contains("mxtakatak")) {


            CallsoundData(url, false);


        } else if (url.contains("cocoscope")) {


            CallsoundData(url, false);


        } else if (url.contains("20min.ch")) {


            CallsoundData(url, false);


        } else if (url.contains("ganna")) {


            CallsoundData(url, false);


        } else if (url.contains("izlesene")) {


            CallsoundData(url, false);


        } else if (url.contains("linkedin")) {


            CallsoundData(url, false);


        } else if (url.contains("kwai") || url.contains("kw.ai")) {


            CallsoundData(url, false);


        } else if (url.contains("bitchute")) {


            CallsoundData(url, false);


        } else if (url.contains("douyin")) {


            CallsoundData(url, false);


        } else if (url.contains("dailymotion.com") || url.contains("dai.ly")) {
            CallDailymotionData(url, true);

        } else if (url.contains("espn.com")) {
            CallDailymotionData(url, true);

        } else if (url.contains("mashable.com")) {
            CallsoundData(url, true);

        } else if (url.contains("ted.com")) {

            CallDailymotionData(url, true);
        } else if (url.contains("twitch")) {
            CallDailymotionData(url, true);

        } else if (url.contains("imdb.com")) {
            CallsoundData(url, false);

        } else if (url.contains("pinterest")) {
            CallsoundData(url, false);

        } else if (url.contains("imgur.com")) {
            url = url.replace("//m.", "//");
            CallsoundData(url, false);

        } else if (url.contains("tumblr.com")) {

            new CalltumblerData().execute(url);
        }


//TODO youtube from here

//        else if (url.contains("youtube.com") || url.contains("youtu.be")) {
//            //  String youtubeLink = "https://www.youtube.com/watch?v=668nUCeBHyY";
//            if (Constants.showyoutube) {
//                Log.i("LOGClipboard111111 clip", "work 3");
//                getYoutubeDownloadUrl(url);
//            } else {
//                if (!fromService) {
//                    pd.dismiss();
//
//                    iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
//
//                }
//            }
//
//        }


//TODO Till Here
        else {
            if (!fromService) {
                pd.dismiss();


                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));

            }
        }


        prefs = Mcontext.getSharedPreferences("AppConfig", MODE_PRIVATE);
    }


    //TODO youtube comment them from here

//    private static void getYoutubeDownloadUrl(String youtubeLink) {
//
//        new YouTubeExtractor(Mcontext) {
//
//            @Override
//            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
//                //    mainProgressBar.setVisibility(View.GONE);
//
//                if (ytFiles != null) {
//
//                    if (!fromService) {
//                        pd.dismiss();
//                    }
//
//
//                    windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
//                    LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                    mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);
//
//                    mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);
//
//                    img_dialog = mChatHeadView.findViewById(R.id.img_dialog);
//
//
//                    dialogquality = new Dialog(Mcontext);
//                    dialogquality.setContentView(R.layout.dialog_quality_ytd);
//                    mainLayout = dialogquality.findViewById(R.id.linlayout_dialog);
//                    img_dialog = dialogquality.findViewById(R.id.img_dialog);
//                    show_ytd_inpip = dialogquality.findViewById(R.id.show_ytd_inpip);
//
//                    show_ytd_inpip.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            for (int i = 0, itag; i < ytFiles.size(); i++) {
//                                itag = ytFiles.keyAt(i);
//                                // ytFile represents one file with its url and meta data
//                                YtFile ytFile = ytFiles.get(itag);
//
//                                // Just add videos in a decent format => height -1 = audio
//                                if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
//                                    // addButtonToMainLayouttest(vMeta.getTitle(), ytFile);
//
//                                    Mcontext.startActivity(new Intent(Mcontext, PlayActivity.class).putExtra("videourl", ytFile.getUrl()).putExtra(AppMeasurementSdk.ConditionalUserProperty.NAME, vMeta.getTitle()));
//                                    return;
//                                }
//                            }
//
//
//                        }
//                    });
//
//
//                    int size = 0;
//
//                    try {
//                        DisplayMetrics displayMetrics = new DisplayMetrics();
//                        ((Activity) Mcontext).getWindowManager()
//                                .getDefaultDisplay()
//                                .getMetrics(displayMetrics);
//
//                        int height = displayMetrics.heightPixels;
//                        int width = displayMetrics.widthPixels;
//
//                        size = width / 2;
//
//                    } catch (Exception e) {
//                        size = WindowManager.LayoutParams.WRAP_CONTENT;
//                    }
//
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        params = new WindowManager.LayoutParams(
//                                size,
//                                WindowManager.LayoutParams.WRAP_CONTENT,
//                                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                PixelFormat.TRANSLUCENT);
//
//                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                        params.x = 0;
//                        params.y = 100;
//                    } else {
//                        params = new WindowManager.LayoutParams(
//                                size,
//                                WindowManager.LayoutParams.WRAP_CONTENT,
//                                WindowManager.LayoutParams.TYPE_PHONE,
//                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                PixelFormat.TRANSLUCENT);
//
//                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                        params.x = 0;
//                        params.y = 100;
//                    }
//
//
//                    // mainLayout.setLayoutParams(params);
//
//
//                    for (int i = 0, itag; i < ytFiles.size(); i++) {
//                        itag = ytFiles.keyAt(i);
//                        // ytFile represents one file with its url and meta data
//                        YtFile ytFile = ytFiles.get(itag);
//
//                        // Just add videos in a decent format => height -1 = audio
//                        if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
//                            addButtonToMainLayouttest(vMeta.getTitle(), ytFile);
//                        }
//                    }
//
//                    img_dialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialogquality.dismiss();
//                        }
//                    });
//
//                    dialogquality.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                    dialogquality.getWindow().setAttributes(params);
//                    //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));
//
//                    dialogquality.show();
//
//                } else {
//                    if (!fromService) {
//                        pd.dismiss();
//                    }
//
//                    iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
//
//
//                }
//
//
//            }
//        }.extract(youtubeLink, true, false);
//    }
//
//    private static void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
//
//
//        // Display some buttons and let the user choose the format
//        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " +
//                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
//                ytfile.getFormat().getHeight() + "p";
//        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";
//        Button btn = new Button(Mcontext);
//
//        btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//
//        btn.setText(btnText);
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (windowManager2 != null) {
//                    try {
//                        windowManager2.removeView(mChatHeadView);
//                    } catch (Exception e) {
//                        Log.i("LOGClipboard111111", "error is " + e.getMessage());
//
//                    }
//                }
//
//                String filename;
//                if (videoTitle.length() > 55) {
//                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
//                } else {
//                    filename = videoTitle + "." + ytfile.getFormat().getExt();
//                }
//                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
//
////                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
////
////
////                String downloadUrl = ytFiles.get(itag).getUrl();
//
//
//                new downloadFile().Downloading(Mcontext, ytfile.getUrl(), filename, ".mp4");
//
//
//                dialogquality.dismiss();
//            }
//        });
//        mainLayout.addView(btn);
//    }
//
//    private static void addButtonToMainLayouttest(final String videoTitle, final YtFile ytfile) {
//
//
//        // Display some buttons and let the user choose the format
//        String btnText = (ytfile.getFormat().getHeight() == -1) ? "MP3 " +
//                ytfile.getFormat().getAudioBitrate() + " kbit/s" :
//                ytfile.getFormat().getHeight() + "p";
//        btnText += (ytfile.getFormat().isDashContainer()) ? " No Audio" : "";
//        Button btn = new Button(Mcontext);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        params.setMargins(8, 8, 8, 8);
//        btn.setLayoutParams(params);
//
//        // btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        btn.setBackground(Mcontext.getResources().getDrawable(R.drawable.btn_bg_download_screen));
//        btn.setTextColor(Color.WHITE);
//
//        btn.setText(btnText);
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (windowManager2 != null) {
//                    try {
//                        windowManager2.removeView(mChatHeadView);
//                    } catch (Exception e) {
//                        Log.i("LOGClipboard111111", "error is " + e.getMessage());
//
//                    }
//                }
//
//                String filename;
//                if (videoTitle.length() > 55) {
//                    filename = videoTitle.substring(0, 55);
//                } else {
//                    filename = videoTitle;
//                }
//                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
//
////                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
////
////
////                String downloadUrl = ytFiles.get(itag).getUrl();
//
//
//                if (ytfile.getFormat().getExt().equals("m4a")) {
//                    new downloadFile().Downloading(Mcontext, ytfile.getUrl(), filename, ".mp3");
//                } else {
//                    new downloadFile().Downloading(Mcontext, ytfile.getUrl(), filename, "." + ytfile.getFormat().getExt());
//
//                }
//
//                dialogquality.dismiss();
//            }
//        });
//        mainLayout.addView(btn);
//    }
//

    //TODO youtube comment till here

    public static void download(String url12) {
        String readLine;
        URL url = null;
        try {
            url = new URL(url12);


            Log.d("ThumbnailURL11111_1 ", url12);


//        URLConnection openConnection = url.openConnection();
//        openConnection.setRequestProperty("ModelUserInstagram-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            //       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));


            URL url1 = new URL(url12);
            URLConnection connection = url1.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));


            while ((readLine = bufferedReader.readLine()) != null) {
                //  readLine = bufferedReader.readLine();
                Log.d("ThumbnailURL11111_2  ", readLine);


                readLine = readLine.substring(readLine.indexOf("VideoObject"));
                String substring = readLine.substring(readLine.indexOf("thumbnailUrl") + 16);
                substring = substring.substring(0, substring.indexOf("\""));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ThumbnailURL: ");
                stringBuilder.append(substring);

                Log.d("ThumbnailURL", substring);
                readLine = readLine.substring(readLine.indexOf("contentUrl") + 13);
                readLine = readLine.substring(0, readLine.indexOf("?"));
                stringBuilder = new StringBuilder();
                stringBuilder.append("ContentURL: ");
                stringBuilder.append(readLine);

                Log.d("ContentURL1111 thumb  ", substring);
                Log.d("ContentURL1111", stringBuilder.toString());


                if (readLine == null) {
                    break;
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
//            Log.d("ContentURL1111 errrr", e.getMessage());
            e.printStackTrace();
        }
        // new downloadFile().Downloading(Mcontext, URL, Title, ".mp4");
        //   new DownloadFileFromURL().execute(new String[]{readLine});
    }

    public static void getAllData(String url, String watermark) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, TiktokApiNowatermark, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("rescccccccc " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);


                    if (jsonObject.getString("status").equals("success")) {

                        Gson gson = new Gson();
                        TikTokNoWaterMarkApi tikTokNoWaterMarkApidata = gson.fromJson(jsonObject.toString(), TikTokNoWaterMarkApi.class);

                        System.out.println("resccccccccdataFull_Vide " + tikTokNoWaterMarkApidata.video_full_title);
                        System.out.println("resccccccccdataORG " + tikTokNoWaterMarkApidata.ogvideourl);
                        System.out.println("resccccccccdatawaterORG " + tikTokNoWaterMarkApidata.videourl);
                        System.out.println("resccccccccdatamusicORG " + tikTokNoWaterMarkApidata.musicplayurl);

                        if (watermark.equals("true")) {
//
//                            if (tikTokNoWaterMarkApidata.video_full_title.contains("null")){
//                                tikTokNoWaterMarkApidata.video_full_title = "Tiktokvideo"+System.currentTimeMillis();
//
//                            }


                            new downloadFile().Downloading(Mcontext, tikTokNoWaterMarkApidata.ogvideourl, tikTokNoWaterMarkApidata.video_full_title + "_" + tikTokNoWaterMarkApidata.username, ".mp4");

                            if (!fromService) {
                                pd.dismiss();


                                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));


                            }

                        } else if (watermark.equals("false") && tikTokNoWaterMarkApidata.watermark_removed.equals("yes")) {

//                            if (tikTokNoWaterMarkApidata.video_full_title.contains("null")){
//                                tikTokNoWaterMarkApidata.video_full_title = "Tiktokvideo"+System.currentTimeMillis();
//
//                            }
                            new downloadFile().Downloading(Mcontext, tikTokNoWaterMarkApidata.videourl, tikTokNoWaterMarkApidata.video_full_title + "_" + tikTokNoWaterMarkApidata.username, ".mp4");
                            //   new DownloadFileFromURL(Mcontext,tikTokNoWaterMarkApidata.name).execute(tikTokNoWaterMarkApidata.videourl);


                            if (!fromService) {
                                pd.dismiss();


                                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));

                            }

                        } else if (watermark.equals("mp3")) {

//                            if (tikTokNoWaterMarkApidata.video_full_title.contains("null")){
//                                tikTokNoWaterMarkApidata.video_full_title = "Tiktokmp3"+System.currentTimeMillis();
//
//                            }
                            new downloadFile().Downloading(Mcontext, tikTokNoWaterMarkApidata.musicplayurl, tikTokNoWaterMarkApidata.video_full_title + "_" + tikTokNoWaterMarkApidata.username, ".mp3");


                            if (!fromService) {
                                pd.dismiss();


                                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));


                            }


                        }


                    } else {
                        if (!fromService) {
                            pd.dismiss();


                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));


                        }
                    }


                } catch (Exception e) {
                    if (!fromService) {
                        pd.dismiss();


                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));


                    }
                    System.out.println("i ah error " + e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("i ah error " + error.getMessage());

                if (!fromService) {
                    pd.dismiss();


                    iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));


                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();
                params.put("tikurl", url);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VollySingltonClass.getmInstance(Mcontext).addToRequsetque(stringRequest);


    }


//    @Keep
//    public static void getAllDataForLikee2(String url, boolean hasQualityOption) {
//
//        AndroidNetworking.get(LikeeApiUrl + url)
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        System.out.println("reccccc VVKK " + response);
//
//
//                        System.out.println("rescccccccc " + response + "     myurl is " + LikeeApiUrl + url);
//
//                        ArrayList<VideoModel> videoModelArrayList = new ArrayList<>();
//
//                        try {
//                            // JSONObject jSONObject = new JSONObject(response);
//
//                            String videotitleis = response.getString("title");
//
//                            JSONArray str = response.getJSONArray("links");
//
//
//                            for (int i = 0; i < str.length(); i++) {
//                                VideoModel videoModel = new VideoModel();
//                                JSONObject jSONObject2 = str.getJSONObject(i);
//                                videoModel.setTitle(videotitleis);
//                                videoModel.setUrl(jSONObject2.getString("url"));
//
//
//                                System.out.println("reccccc VVKK URLLL " + jSONObject2.getString("url"));
//
//
//                                videoModel.setType(jSONObject2.getString("type"));
//                                videoModel.setSize(jSONObject2.getString("size"));
//                                videoModel.setQuality(jSONObject2.getString("quality"));
//
//                                videoModelArrayList.add(videoModel);
//
//                            }
//
//
//                            if (hasQualityOption) {
//
//                                dialog_quality_allvids = new Dialog(Mcontext);
//
//
//                                if (!fromService) {
//                                    pd.dismiss();
//                                }
//
//
//                                windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
//                                LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                                mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);
//
//
//                                dialog_quality_allvids.setContentView(mChatHeadView);
//
//
//                                mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);
//
//                                img_dialog = mChatHeadView.findViewById(R.id.img_dialog);
//
//                                mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
//                                img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);
//
//
//                                int size = 0;
//
//                                try {
//                                    DisplayMetrics displayMetrics = new DisplayMetrics();
//                                    ((Activity) Mcontext).getWindowManager()
//                                            .getDefaultDisplay()
//                                            .getMetrics(displayMetrics);
//
//                                    int height = displayMetrics.heightPixels;
//                                    int width = displayMetrics.widthPixels;
//
//                                    size = width / 2;
//
//                                } catch (Exception e) {
//                                    size = WindowManager.LayoutParams.WRAP_CONTENT;
//                                }
//
//
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    params = new WindowManager.LayoutParams(
//                                            size,
//                                            WindowManager.LayoutParams.WRAP_CONTENT,
//                                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                            PixelFormat.TRANSLUCENT);
//
//                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                                    params.x = 0;
//                                    params.y = 100;
//                                } else {
//                                    params = new WindowManager.LayoutParams(
//                                            size,
//                                            WindowManager.LayoutParams.WRAP_CONTENT,
//                                            WindowManager.LayoutParams.TYPE_PHONE,
//                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                            PixelFormat.TRANSLUCENT);
//
//                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                                    params.x = 0;
//                                    params.y = 100;
//                                }
//
//
//                                // mainLayout.setLayoutParams(params);
//
//
//                                for (int i = 0; i < videoModelArrayList.size(); i++) {
//
//
//
//                                    addButtonToMainLayouttest_allvideo(videoModelArrayList.get(i).getQuality(), videoModelArrayList.get(i).getUrl(), videoModelArrayList.get(i).getTitle());
//
//                                }
//
//                                img_dialog.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog_quality_allvids.dismiss();
//                                    }
//                                });
//
//                                dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                                dialog_quality_allvids.getWindow().setAttributes(params);
//                                //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));
//
//                                dialog_quality_allvids.show();
//
//
//                                dialog_quality_allvids.show();
//                            } else {
//                                if (url.contains("tiktok")) {
//
//                                    String outputFileName = MY_ANDROID_10_IDENTIFIER_OF_FILE + getFilenameFromURL("https://www.tiktok.com/@beauty_0f_nature/video/6825315100933639426") + ".mp4";
//                                    String output = "";
//
//                                    if (outputFileName.length() > 100)
//                                        outputFileName = outputFileName.substring(0, 100);
//
//
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                                        output = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + outputFileName;
//
//                                    } else {
//                                        output = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + outputFileName;
//
//                                        //   output = new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + DOWNLOAD_DIRECTORY + "/" + outputFileName));
//
//                                    }
//
//
//                                    //  new DownloadFileFromURL1().execute("https://v16-web-newkey.tiktokcdn.com/99818623022622f41b12900e3d086eb4/5f6e1cf9/video/tos/useast2a/tos-useast2a-ve-0068c003/590e2a192cb8428ca4b2509167b01bfa/?a=1988&br=4926&bt=2463&cr=0&cs=0&cv=1&dr=0&ds=3&er=&l=202009251038010101151510600F065F52&lr=tiktok_m&mime_type=video_mp4&qs=0&rc=ajVqbW52cWdzdTMzZzczM0ApNTQ3ZTo2aDxoNzVoaDs6NWdsMWwzajNnc29fLS1fMTZzc19hYGExNTUxX2MxLl8zMDY6Yw%3D%3D&vl=&vr=");
//                                    AndroidNetworking
//                                            .download("https://v16-web-newkey.tiktokcdn.com/99818623022622f41b12900e3d086eb4/5f6e1cf9/video/tos/useast2a/tos-useast2a-ve-0068c003/590e2a192cb8428ca4b2509167b01bfa/?a=1988&br=4926&bt=2463&cr=0&cs=0&cv=1&dr=0&ds=3&er=&l=202009251038010101151510600F065F52&lr=tiktok_m&mime_type=video_mp4&qs=0&rc=ajVqbW52cWdzdTMzZzczM0ApNTQ3ZTo2aDxoNzVoaDs6NWdsMWwzajNnc29fLS1fMTZzc19hYGExNTUxX2MxLl8zMDY6Yw%3D%3D&vl=&vr="
//                                                    , output, outputFileName)
//                                            .setTag("downloadTest")
//                                            .setPriority(Priority.MEDIUM)
//                                            .build()
//                                            .setDownloadProgressListener(new DownloadProgressListener() {
//                                                @Override
//                                                public void onProgress(long bytesDownloaded, long totalBytes) {
//                                                    // do anything with progress
//                                                }
//                                            })
//                                            .startDownload(new DownloadListener() {
//                                                @Override
//                                                public void onDownloadComplete() {
//                                                    if (!fromService) {
//                                                        pd.dismiss();
//
//
//                                                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.exo_download_completed));
//
//
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onError(ANError error) {
//                                                    if (!fromService) {
//                                                        pd.dismiss();
//
//
//                                                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));
//
//
//                                                    }
//                                                }
//                                            });
//                                } else {
//
//                                    new downloadFile().Downloading(Mcontext, videoModelArrayList.get(0).getUrl(), getFilenameFromURL(videoModelArrayList.get(0).getUrl()), ".mp4");
//                                }
//                                if (!fromService) {
//                                    pd.dismiss();
//
//
//                                    iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));
//
//
//                                }
//                            }
//
//
//                        } catch (Exception str2) {
//                            str2.printStackTrace();
//                            // Toast.makeText(Mcontext, "Invalid URL", 0).show();
//
//                            if (!fromService) {
//                                pd.dismiss();
//                                iUtils.ShowToast(Mcontext, response + ":   " + Mcontext.getResources().getString(R.string.invalid_url));
//                            }
//                        }
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        System.out.println("reccccc VVKK error " + error);
//
//                    }
//                });
//    }
//
//
//    public static void getAllDataForLikee(String urlp, boolean hasQualityOption) {
//
//        System.out.println("resccccccccURL " + urlp);
//        //  url = "https://vm.tiktok.com/KEpK7n/";
//
//
//        System.out.println("resccccccccmyurl is " + LikeeApiUrl + urlp);
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.nobots.in/videodownloader/system/api.php?url=" + urlp, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                System.out.println("rescccccccc " + response + "     myurl is " + LikeeApiUrl + urlp);
//
//                ArrayList<VideoModel> videoModelArrayList = new ArrayList<>();
//
//                try {
//                    JSONObject jSONObject = new JSONObject(response);
//
//                    String videotitleis = jSONObject.getString("title");
//
//                    JSONArray str = jSONObject.getJSONArray("links");
//
//
//                    for (int i = 0; i < str.length(); i++) {
//                        VideoModel videoModel = new VideoModel();
//                        JSONObject jSONObject2 = str.getJSONObject(i);
//                        videoModel.setTitle(videotitleis);
//                        videoModel.setUrl(jSONObject2.getString("url"));
//                        videoModel.setType(jSONObject2.getString("type"));
//                        videoModel.setSize(jSONObject2.getString("size"));
//                        videoModel.setQuality(jSONObject2.getString("quality"));
//
//                        videoModelArrayList.add(videoModel);
//
//                    }
//
//
//                    if (hasQualityOption) {
//
//                        dialog_quality_allvids = new Dialog(Mcontext);
//
//
//                        if (!fromService) {
//                            pd.dismiss();
//                        }
//
//
//                        windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
//                        LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                        mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);
//
//
//                        dialog_quality_allvids.setContentView(mChatHeadView);
//
//
//                        mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);
//
//                        img_dialog = mChatHeadView.findViewById(R.id.img_dialog);
//
//                        mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
//                        img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);
//
//
//                        int size = 0;
//
//                        try {
//                            DisplayMetrics displayMetrics = new DisplayMetrics();
//                            ((Activity) Mcontext).getWindowManager()
//                                    .getDefaultDisplay()
//                                    .getMetrics(displayMetrics);
//
//                            int height = displayMetrics.heightPixels;
//                            int width = displayMetrics.widthPixels;
//
//                            size = width / 2;
//
//                        } catch (Exception e) {
//                            size = WindowManager.LayoutParams.WRAP_CONTENT;
//                        }
//
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            params = new WindowManager.LayoutParams(
//                                    size,
//                                    WindowManager.LayoutParams.WRAP_CONTENT,
//                                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                    PixelFormat.TRANSLUCENT);
//
//                            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                            params.x = 0;
//                            params.y = 100;
//                        } else {
//                            params = new WindowManager.LayoutParams(
//                                    size,
//                                    WindowManager.LayoutParams.WRAP_CONTENT,
//                                    WindowManager.LayoutParams.TYPE_PHONE,
//                                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                                    PixelFormat.TRANSLUCENT);
//
//                            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
//                            params.x = 0;
//                            params.y = 100;
//                        }
//
//
//                        // mainLayout.setLayoutParams(params);
//
//
//                        for (int i = 0; i < videoModelArrayList.size(); i++) {
//
//
//                            addButtonToMainLayouttest_allvideo(videoModelArrayList.get(i).getQuality(), videoModelArrayList.get(i).getUrl(), videoModelArrayList.get(i).getTitle());
//
//                        }
//
//                        img_dialog.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog_quality_allvids.dismiss();
//                            }
//                        });
//
//                        dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                        dialog_quality_allvids.getWindow().setAttributes(params);
//                        //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));
//
//                        dialog_quality_allvids.show();
//
//
//                        dialog_quality_allvids.show();
//                    } else {
//
//
//                        new downloadFile().Downloading(Mcontext, videoModelArrayList.get(0).getUrl(), getFilenameFromURL(videoModelArrayList.get(0).getUrl()), ".mp4");
//                    }
//                    if (!fromService) {
//                        pd.dismiss();
//
//
//                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));
//
//
//                    }
//
//
//                } catch (Exception str2) {
//                    str2.printStackTrace();
//                    // Toast.makeText(Mcontext, "Invalid URL", 0).show();
//
//                    if (!fromService) {
//                        pd.dismiss();
//                        iUtils.ShowToast(Mcontext, response + ":   " + Mcontext.getResources().getString(R.string.invalid_url));
//                    }
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("i ah error " + error.getMessage());
//
//                if (!fromService) {
//                    pd.dismiss();
//
//                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
//
//                }
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//
//                Map<String, String> params = new HashMap<String, String>();
//
//                //  params.put("url", urlp);
//
//
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                3000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VollySingltonClass.getmInstance(Mcontext).addToRequsetque(stringRequest);
//
//
//    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private static void addButtonToMainLayouttest_allvideo(final String videoTitle, String ytfile, String video_title) {


        // Display some buttons and let the user choose the format
        String btnText = videoTitle;
        Button btn = new Button(Mcontext);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        btn.setLayoutParams(params);

        // btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn.setBackground(Mcontext.getResources().getDrawable(R.drawable.btn_bg_download_screen));
        btn.setTextColor(Color.WHITE);

        btn.setText(btnText);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (windowManager2 != null) {
                    try {
                        windowManager2.removeView(mChatHeadView);
                    } catch (Exception e) {
                        Log.i("LOGClipboard111111", "error is " + e.getMessage());

                    }
                }


//                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
//
//
//                String downloadUrl = ytFiles.get(itag).getUrl();

                if (btnText.equals("audio/mp4")) {
                    new downloadFile().Downloading(Mcontext, ytfile, video_title + "_" + videoTitle, ".mp3");
                } else {
                    new downloadFile().Downloading(Mcontext, ytfile, video_title + "_" + videoTitle, ".mp4");

                }
                dialog_quality_allvids.dismiss();
            }
        });
        mainLayout.addView(btn);
    }


    private static class GetInstagramVideo extends AsyncTask<String, Void, Document> {
        Document doc;
        private Handler mHandler;

        @Override
        protected Document doInBackground(String... urls) {

            System.out.println("mydahjsdgadashas2244  " + urls[0]);

            try {
                doc = Jsoup.connect(urls[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: Error" + e.getMessage());
            }
            return doc;

        }

        protected void onPostExecute(Document result) {
            if (!fromService) {

                pd.dismiss();
            }

            try {
                String URL = result.select("meta[property=\"og:video\"]").last().attr("content");
                Title = result.title();
                System.out.println("mydahjsdgadashas  " + Title);

                new downloadFile().Downloading(Mcontext, URL, Title + ".mp4", ".mp4");

            } catch (Exception e) {
                System.out.println("mydahjsdgadashas22  " + e.getMessage());
                e.printStackTrace();

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));

            }
        }
    }


    private static class callGetShareChatData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;

        callGetShareChatData() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            try {
                this.ShareChatDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues", "doInBackground: Error");
            }
            return this.ShareChatDoc;
        }

        protected void onPostExecute(Document document) {
            String charSequence = "";

            try {

                if (!fromService) {

                    pd.dismiss();
                }


                VideoUrl = document.select("meta[property=\"og:video:secure_url\"]").last().attr("content");
                Log.e("onPostExecute: ", VideoUrl);
                if (!VideoUrl.equals(charSequence)) {


                    try {
                        String myurldocument = VideoUrl;


                        String nametitle = "sharechat_" +
                                System.currentTimeMillis() +
                                ".mp4";

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = charSequence;
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }
                }
            } catch (Exception document22) {
                document22.printStackTrace();
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }
    }


    private static class callGetRoposoData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;

        callGetRoposoData() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            try {
                this.ShareChatDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues roposo_", "doInBackground: Error");
            }
            return this.ShareChatDoc;
        }

        protected void onPostExecute(Document document) {
            String charSequence = "";

            try {

                if (!fromService) {

                    pd.dismiss();
                }


                VideoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
                Log.e("onPostExecute:roposo_ ", VideoUrl);
                if (!VideoUrl.equals(charSequence)) {


                    try {
                        String myurldocument = VideoUrl;


                        String nametitle = "roposo_" +
                                System.currentTimeMillis() +
                                ".mp4";

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = charSequence;
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }
                }
            } catch (Exception document22) {
                document22.printStackTrace();
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }
    }


    public static class CallMitronData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            String str;
            try {
                String str2 = strArr[0];
                if (str2.contains("api.mitron.tv")) {
                    String[] split = str2.split("=");
                    str = "https://web.mitron.tv/video/" + split[split.length - 1];
                } else {
                    str = strArr[0];
                }
                this.RoposoDoc = Jsoup.connect(str).get();
            } catch (IOException e) {
                e.printStackTrace();
                if (!fromService) {

                    pd.dismiss();
                }
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {

            //   System.out.println("myresponseis111 " + document.html());

            try {

                if (!fromService) {

                    pd.dismiss();
                }

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(html).getJSONObject("props").getJSONObject("pageProps").getJSONObject(MimeTypes.BASE_TYPE_VIDEO).get("videoUrl"));
                    if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                        try {


                            String myurldocument = VideoUrl;


                            String nametitle = "mitron_" +
                                    System.currentTimeMillis();

                            new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());

                            document2.printStackTrace();
                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                        }

                        return;
                    }
                    return;
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }


    public static class CallJoshData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(html).getJSONObject("props").getJSONObject("pageProps").getJSONObject("detail").getJSONObject(DataSchemeDataSource.SCHEME_DATA).get("mp4_url"));
                    this.VideoUrl = this.VideoUrl.replace("{quality}", "720");
                    this.VideoUrl = this.VideoUrl.replace("{resolution}", "720");

                    if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                        try {


                            String myurldocument = VideoUrl;


                            String nametitle = "joshvideo_" +
                                    System.currentTimeMillis();

                            new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());

                            document2.printStackTrace();
                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                        }

                        return;
                    }
                    return;
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }


    public static class CallTrillerData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                System.out.println("myresponseis111 0exp1 " + strArr[0]);

                this.RoposoDoc = Jsoup.connect(strArr[0].replace("-", "")).get();
                System.out.println("myresponseis111 1exp1 " + strArr[0].replace("-", ""));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {
            System.out.println("myresponseis111 2exp1 " + document.body());


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                this.VideoUrl = document.select(MimeTypes.BASE_TYPE_VIDEO).last().attr("src");
                if (this.VideoUrl.startsWith("//")) {
                    this.VideoUrl = "https:" + this.VideoUrl;
                }

                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "trillervideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CallRizzleData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(html).getJSONObject("props").getJSONObject("pageProps").getJSONObject("post").getJSONObject(MimeTypes.BASE_TYPE_VIDEO).get("originalUrl"));
                    if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                        try {


                            String myurldocument = VideoUrl;


                            String nametitle = "rizzlevideo_" +
                                    System.currentTimeMillis();

                            new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());

                            document2.printStackTrace();
                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                        }

                        return;
                    }
                    return;
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CallIfunnyData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                String html = document.select("script[class=\"js-media-template\"]").first().html();
                new Element(html);
                Matcher matcher = Pattern.compile("<video[^>]+poster\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(html);
                while (matcher.find()) {
                    this.VideoUrl = matcher.group(1).replace("jpg", "mp4").replace("images", "videos").replace("_3", "_1");
                }
                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "ifunnyvideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CallLikeeData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                String str = strArr[0];
                if (str.contains("com")) {
                    str = str.replace("com", MimeTypes.BASE_TYPE_VIDEO);
                }
                this.RoposoDoc = Jsoup.connect("https://likeedownloader.com/results").data("id", str).userAgent("Mozilla").post();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                this.VideoUrl = document.select("a.without_watermark").last().attr("href");

                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "Likeevideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }


    public static class CalltrellData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                String html = document.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if (!html.equals("")) {
                    this.VideoUrl = String.valueOf(new JSONObject(new JSONObject(html)
                            .getJSONObject("props")
                            .getJSONObject("pageProps")
                            .getJSONObject("result")
                            .getJSONObject("result").getJSONObject("trail")
                            .getJSONArray("posts").get(0).toString())
                            .get(MimeTypes.BASE_TYPE_VIDEO));

                    System.out.println("myresponseis111 exp991 " + VideoUrl);


                    if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                        try {


                            String myurldocument = VideoUrl;


                            String nametitle = "trellvideo_" +
                                    System.currentTimeMillis();

                            new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                            //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                            VideoUrl = "";
                            //   binding.etText.setText(charSequence);

                        } catch (Exception document2) {
                            System.out.println("myresponseis111 exp1 " + document2.getMessage());

                            document2.printStackTrace();
                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                        }

                        return;
                    }
                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CallBoloindyaData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                Iterator it = document.getElementsByTag("script").iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Element element = (Element) it.next();
                    if (element.data().contains("videoFileCDN")) {
                        for (String str : element.data().split(StringUtils.LF)) {
                            if (str.contains("var videoFileCDN=\"https")) {
                                this.VideoUrl = str.split("=")[1]
                                        .replace("\"", "")
                                        .replace("\"", "")
                                        .replace(";", "");
                            }
                        }
                    }
                }
                if (this.VideoUrl.startsWith("//")) {
                    this.VideoUrl = "https:" + this.VideoUrl;
                }
                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "Boloindyavideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CallchingariData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                this.VideoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "chingarivideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CallhindData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                Iterator it = document.getElementsByTag("script").iterator();
                while (it.hasNext()) {
                    Element element = (Element) it.next();
                    if (element.data().contains("window.__STATE__")) {
                        String replace = element.html().replace("window.__STATE__", "").replace(";", "")
                                .replace("=", "");
                        this.VideoUrl = String.valueOf(new JSONObject(new JSONObject(new JSONArray("[" + replace + "]")
                                .get(0).toString()).getJSONObject("feed").getJSONArray("feed")
                                .get(0).toString()).get("download_media"));
                        if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                            try {


                                String myurldocument = VideoUrl;


                                String nametitle = "hindvideo_" +
                                        System.currentTimeMillis();

                                new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                                //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                                VideoUrl = "";
                                //   binding.etText.setText(charSequence);

                            } catch (Exception document2) {
                                System.out.println("myresponseis111 exp1 " + document2.getMessage());

                                document2.printStackTrace();
                                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                            }

                            return;
                        }
                    }
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }


    public static class CalldubsmashData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                this.VideoUrl = document.select(MimeTypes.BASE_TYPE_VIDEO).last().attr("src");
                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "dubsmashvideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CallziliData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }


        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

//                System.out.println("myresponseis111 exp166 " + document.select("source[src]").first().attr("src"));
//                System.out.println("myresponseis111 exp177 " + document.select("video[src]").first().attr("src"));

                // this.VideoUrl = this.RoposoDoc.select(MimeTypes.BASE_TYPE_VIDEO).last().attr("src");

                // = document.select("video-player__video");
                //VideoUrl= document.getElementsByClass("video-player__video").attr("src");


                //  System.out.println("myresponseis111 exp166 " + VideoUrl);
                for (Element element : document.getElementsByTag("script")) {


                    System.out.println("myresponseis111 exp16600 " + element.data());

                    if (element.html().contains("src")) {
                        String replace = element.html();
                        System.out.println("myresponseis111 333 " + replace);
                        VideoUrl = String.valueOf(new JSONObject(new JSONObject(new JSONArray("[" + replace + "]").get(0).toString()).getJSONObject("feed").getJSONArray("feed").get(0).toString()).get("download_media"));

                    }
                }


                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;

                        String nametitle = "zilivideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }

    public static class CalltumblerData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }
                // System.out.println("myresponseis111 exp166 " + document);
//                System.out.println("myresponseis111 exp166 " + document.select("source[src]").first().attr("src"));
//                System.out.println("myresponseis111 exp177 " + document.select("video[src]").first().attr("src"));

                this.VideoUrl = document.select("source").last().attr("src");

                System.out.println("myresponseis111 exp1 " + VideoUrl);

                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;

                        String nametitle = "tumbler_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }


    public static class CallgdriveData extends AsyncTask<String, Void, String> {
        String VideoUrl = "";
        LowCostVideo xGetter;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            this.xGetter = new LowCostVideo(Mcontext);
            this.xGetter.onFinish(new LowCostVideo.OnTaskCompleted() {
                public void onTaskCompleted(ArrayList<XModel> arrayList, boolean z) {
                    if (!z) {

                        System.out.println("myresponseis111 exp122 " + arrayList.get(0));

                        CallgdriveData.this.done(arrayList.get(0));


                    } else if (arrayList != null) {
                        System.out.println("myresponseis111 exp133 " + arrayList.get(0));

                        CallgdriveData.this.multipleQualityDialog(arrayList);
                    } else {


                        if (!fromService) {

                            pd.dismiss();
                        }
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }
                }

                public void onError() {


                    if (!fromService) {

                        pd.dismiss();
                    }
                    iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                }
            });

        }

        public String doInBackground(String... strArr) {
            return strArr[0];
        }

        public void onPostExecute(String str) {
            System.out.println("myresponseis111 exp13344 " + str);

            if (xGetter != null) {
                this.xGetter.find(str);
                //   System.out.println("myresponseis111 exp13344 " + xGetter.find(str));

            } else {
                this.xGetter = new LowCostVideo(Mcontext);
                this.xGetter.onFinish(new LowCostVideo.OnTaskCompleted() {
                    public void onTaskCompleted(ArrayList<XModel> arrayList, boolean z) {

                        System.out.println("myresponseis111 exp133 " + arrayList.get(0));

                        if (!z) {

                            System.out.println("myresponseis111 exp122 " + arrayList.get(0));

                            CallgdriveData.this.done(arrayList.get(0));


                        } else if (arrayList != null) {
                            System.out.println("myresponseis111 exp133 " + arrayList.get(0));

                            CallgdriveData.this.multipleQualityDialog(arrayList);
                        } else {


                            if (!fromService) {

                                pd.dismiss();
                            }
                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                        }


                    }

                    public void onError() {


                        if (!fromService) {

                            pd.dismiss();
                        }
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }
                });
                this.xGetter.find(str);
            }
        }

        public void multipleQualityDialog(final ArrayList<XModel> arrayList) {
            CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                charSequenceArr[i] = arrayList.get(i).getQuality();
            }
            new AlertDialog.Builder(Mcontext).setTitle("Quality!").setItems(charSequenceArr, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    CallgdriveData.this.done((XModel) arrayList.get(i));
                }
            }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!fromService) {

                        pd.dismiss();
                    }
                }
            }).setCancelable(false).show();
        }


        public void done(XModel xModel) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                this.VideoUrl = xModel.getUrl();
                if (!this.VideoUrl.equals("") && this.VideoUrl != null) {
                    try {


                        String myurldocument = VideoUrl;


                        String nametitle = "Allvideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }

        }

    }

    @Keep
    public static void CallVKData(String url, boolean hasQualityOption) {
        AndroidNetworking.get("https://api.vk.com/method/video.search?q=" + url + "&from=wall-51189706_396016&oauth=1&search_own=0&adult=0&search_own=0&count=1&extended=1&files=1&access_token=d9f1c406aeec6341131a62556d9eb76c7fe6d53defca0d9ce54535299664abf46e0a37af79004c30eb9b3&v=5.124")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("reccccc VVKK " + response);

                        try {
                            JSONObject reponsobj = response.getJSONObject("response");
                            JSONObject itemsarr = reponsobj.getJSONArray("items").getJSONObject(0);
                            JSONObject filesobj = itemsarr.getJSONObject("files");


                            ArrayList<String> mp4List = new ArrayList<>();
                            ArrayList<String> qualitylist = new ArrayList<>();


                            if (!filesobj.getString("mp4_240").isEmpty()) {
                                String mp4_240 = filesobj.getString("mp4_240");
                                mp4List.add(mp4_240);
                                qualitylist.add("240p");
                            }
                            if (!filesobj.getString("mp4_360").isEmpty()) {
                                String mp4_360 = filesobj.getString("mp4_360");
                                mp4List.add(mp4_360);
                                qualitylist.add("360p");

                            }
                            if (!filesobj.getString("mp4_480").isEmpty()) {
                                String mp4_480 = filesobj.getString("mp4_480");
                                mp4List.add(mp4_480);
                                qualitylist.add("480p");

                            }
                            if (!filesobj.getString("mp4_720").isEmpty()) {
                                String mp4_720 = filesobj.getString("mp4_720");
                                mp4List.add(mp4_720);
                                qualitylist.add("720p");

                            }
                            if (!filesobj.getString("mp4_1080").isEmpty()) {
                                String mp4_1080 = filesobj.getString("mp4_1080");
                                mp4List.add(mp4_1080);
                                qualitylist.add("1080p");

                            }


                            if (hasQualityOption) {

                                dialog_quality_allvids = new Dialog(Mcontext);


                                if (!fromService) {
                                    pd.dismiss();
                                }


                                windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
                                LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);


                                dialog_quality_allvids.setContentView(mChatHeadView);


                                mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);

                                img_dialog = mChatHeadView.findViewById(R.id.img_dialog);

                                mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
                                img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);


                                int size = 0;

                                try {
                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    ((Activity) Mcontext).getWindowManager()
                                            .getDefaultDisplay()
                                            .getMetrics(displayMetrics);

                                    int height = displayMetrics.heightPixels;
                                    int width = displayMetrics.widthPixels;

                                    size = width / 2;

                                } catch (Exception e) {
                                    size = WindowManager.LayoutParams.WRAP_CONTENT;
                                }


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    params = new WindowManager.LayoutParams(
                                            size,
                                            WindowManager.LayoutParams.WRAP_CONTENT,
                                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                            PixelFormat.TRANSLUCENT);

                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                    params.x = 0;
                                    params.y = 100;
                                } else {
                                    params = new WindowManager.LayoutParams(
                                            size,
                                            WindowManager.LayoutParams.WRAP_CONTENT,
                                            WindowManager.LayoutParams.TYPE_PHONE,
                                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                            PixelFormat.TRANSLUCENT);

                                    params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                    params.x = 0;
                                    params.y = 100;
                                }


                                // mainLayout.setLayoutParams(params);


                                for (int i = 0; i < mp4List.size(); i++) {


                                    addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4List.get(i), "VK_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                }

                                img_dialog.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_quality_allvids.dismiss();
                                    }
                                });

                                dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                                dialog_quality_allvids.getWindow().setAttributes(params);
                                //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));

                                dialog_quality_allvids.show();


                                dialog_quality_allvids.show();
                            } else {


                                new downloadFile().Downloading(Mcontext, mp4List.get(0), "VK_240p" + System.currentTimeMillis(), ".mp4");

                                if (!fromService) {
                                    pd.dismiss();


                                    iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));


                                }
                            }


                        } catch (Exception str2) {
                            str2.printStackTrace();
                            // Toast.makeText(Mcontext, "Invalid URL", 0).show();

                            if (!fromService) {
                                pd.dismiss();
                                iUtils.ShowToast(Mcontext, response + ":   " + Mcontext.getResources().getString(R.string.invalid_url));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println("reccccc VVKK error " + error);

                    }
                });

    }

    @Keep
    public static void CallREditData(String url, boolean hasQualityOption) {
        AndroidNetworking.get(RedditApiUrl + url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("reccccc VVKK " + response);

                        try {
                            if (response.getJSONArray("links").length() > 0) {
                                ArrayList<String> mp4urlList = new ArrayList<>();
                                ArrayList<String> qualitylist = new ArrayList<>();

                                for (int i = 0; i < response.getJSONArray("links").length(); i++) {
                                    JSONObject itemsarr = response.getJSONArray("links").getJSONObject(i);

                                    mp4urlList.add(itemsarr.getString("url"));
                                    if (!itemsarr.getString("size").equals("")) {
                                        qualitylist.add(itemsarr.getString("quality") + " (" + itemsarr.getString("size") + ")");
                                    } else {
                                        qualitylist.add(itemsarr.getString("quality") + " (Unknown)");
                                    }

                                }


                                if (hasQualityOption) {

                                    dialog_quality_allvids = new Dialog(Mcontext);


                                    if (!fromService) {
                                        pd.dismiss();
                                    }


                                    windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
                                    LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                    mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);


                                    dialog_quality_allvids.setContentView(mChatHeadView);


                                    mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);

                                    img_dialog = mChatHeadView.findViewById(R.id.img_dialog);

                                    mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
                                    img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);


                                    int size = 0;

                                    try {
                                        DisplayMetrics displayMetrics = new DisplayMetrics();
                                        ((Activity) Mcontext).getWindowManager()
                                                .getDefaultDisplay()
                                                .getMetrics(displayMetrics);

                                        int height = displayMetrics.heightPixels;
                                        int width = displayMetrics.widthPixels;

                                        size = width / 2;

                                    } catch (Exception e) {
                                        size = WindowManager.LayoutParams.WRAP_CONTENT;
                                    }


                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        params = new WindowManager.LayoutParams(
                                                size,
                                                WindowManager.LayoutParams.WRAP_CONTENT,
                                                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                                PixelFormat.TRANSLUCENT);

                                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                        params.x = 0;
                                        params.y = 100;
                                    } else {
                                        params = new WindowManager.LayoutParams(
                                                size,
                                                WindowManager.LayoutParams.WRAP_CONTENT,
                                                WindowManager.LayoutParams.TYPE_PHONE,
                                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                                PixelFormat.TRANSLUCENT);

                                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                        params.x = 0;
                                        params.y = 100;
                                    }


                                    // mainLayout.setLayoutParams(params);


                                    for (int i = 0; i < mp4urlList.size(); i++) {

                                        addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "Reddit_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                    }

                                    img_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog_quality_allvids.dismiss();
                                        }
                                    });

                                    dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                                    dialog_quality_allvids.getWindow().setAttributes(params);
                                    //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));

                                    dialog_quality_allvids.show();


                                    dialog_quality_allvids.show();
                                } else {


                                    new downloadFile().Downloading(Mcontext, mp4urlList.get(1), "Reddit_240p" + System.currentTimeMillis(), ".mp4");

                                    if (!fromService) {
                                        pd.dismiss();


                                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));


                                    }
                                }


                            } else {
                                Toast.makeText(Mcontext, "No links found", Toast.LENGTH_SHORT).show();
                                if (!fromService) {
                                    pd.dismiss();
                                    iUtils.ShowToast(Mcontext, response + ":   " + Mcontext.getResources().getString(R.string.invalid_url));
                                }
                            }


                        } catch (Exception str2) {
                            str2.printStackTrace();
                            // Toast.makeText(Mcontext, "Invalid URL", 0).show();

                            if (!fromService) {
                                pd.dismiss();
                                iUtils.ShowToast(Mcontext, response + ":   " + Mcontext.getResources().getString(R.string.invalid_url));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println("reccccc VVKK error " + error);
                        if (!fromService) {
                            pd.dismiss();
                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.invalid_url));
                        }
                    }
                });

    }

    @Keep
    public static void CallDailymotionData(String url, boolean hasQualityOption) {
        AndroidNetworking.get(SoundApiUrl + url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("reccccc VVKK " + response);

                        try {
                            if (response.getJSONArray("links").length() > 0) {
                                ArrayList<String> mp4urlList = new ArrayList<>();
                                ArrayList<String> qualitylist = new ArrayList<>();

                                for (int i = 0; i < response.getJSONArray("links").length(); i++) {
                                    JSONObject itemsarr = response.getJSONArray("links").getJSONObject(i);


                                    mp4urlList.add(itemsarr.getString("url"));
                                    if (!itemsarr.getString("size").equals("")) {
                                        qualitylist.add(itemsarr.getString("quality") + " (" + itemsarr.getString("size") + ")");
                                    } else {
                                        qualitylist.add(itemsarr.getString("quality") + " (Unknown)");
                                    }
                                }


                                if (hasQualityOption) {

                                    dialog_quality_allvids = new Dialog(Mcontext);


                                    if (!fromService) {
                                        pd.dismiss();
                                    }


                                    windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
                                    LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                    mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);


                                    dialog_quality_allvids.setContentView(mChatHeadView);


                                    mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);

                                    img_dialog = mChatHeadView.findViewById(R.id.img_dialog);

                                    mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
                                    img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);


                                    int size = 0;

                                    try {
                                        DisplayMetrics displayMetrics = new DisplayMetrics();
                                        ((Activity) Mcontext).getWindowManager()
                                                .getDefaultDisplay()
                                                .getMetrics(displayMetrics);

                                        int height = displayMetrics.heightPixels;
                                        int width = displayMetrics.widthPixels;

                                        size = width / 2;

                                    } catch (Exception e) {
                                        size = WindowManager.LayoutParams.WRAP_CONTENT;
                                    }


                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        params = new WindowManager.LayoutParams(
                                                size,
                                                WindowManager.LayoutParams.WRAP_CONTENT,
                                                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                                PixelFormat.TRANSLUCENT);

                                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                        params.x = 0;
                                        params.y = 100;
                                    } else {
                                        params = new WindowManager.LayoutParams(
                                                size,
                                                WindowManager.LayoutParams.WRAP_CONTENT,
                                                WindowManager.LayoutParams.TYPE_PHONE,
                                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                                PixelFormat.TRANSLUCENT);

                                        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                        params.x = 0;
                                        params.y = 100;
                                    }


                                    // mainLayout.setLayoutParams(params);


                                    for (int i = 0; i < mp4urlList.size(); i++) {

                                        if (url.contains("dailymotion.com") || url.contains("dai.ly")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "dailymotion_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("twitch")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "twitch_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("ted")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "ted_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("espn")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "espn_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("flickr")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "flickr_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("streamable")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "streamable_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("facebook")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "facebook_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("twitter")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "twitter_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        } else if (url.contains("blogspot")) {
                                            addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4urlList.get(i), "blogspot_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                                        }

                                    }

                                    img_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog_quality_allvids.dismiss();
                                        }
                                    });

                                    dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                                    dialog_quality_allvids.getWindow().setAttributes(params);
                                    //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));

                                    dialog_quality_allvids.show();


                                    dialog_quality_allvids.show();
                                } else {


                                    new downloadFile().Downloading(Mcontext, mp4urlList.get(1), "Reddit_240p" + System.currentTimeMillis(), ".mp4");

                                    if (!fromService) {
                                        pd.dismiss();


                                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));


                                    }
                                }


                            } else {
                                Toast.makeText(Mcontext, "No links found", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception str2) {
                            str2.printStackTrace();
                            // Toast.makeText(Mcontext, "Invalid URL", 0).show();

                            if (!fromService) {
                                pd.dismiss();
                                iUtils.ShowToast(Mcontext, response + ":   " + Mcontext.getResources().getString(R.string.invalid_url));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println("reccccc VVKK error " + error);

                    }
                });

    }

    @Keep
    public static void CallsoundData(String url, boolean hasQualityOption) {
        AndroidNetworking.get(SoundApiUrl + url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("reccccc VVKK " + response);

                        try {
                            if (response.getJSONArray("links").length() > 0) {


                                JSONObject itemsarr = response.getJSONArray("links").getJSONObject(0);

                                //    mp4urlList.add(itemsarr.getString("url"));
                                String myurlis = itemsarr.getString("url");

                                if (url.contains("bitchute")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "bitchute_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("soundcloud")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "Soundcloud_" + System.currentTimeMillis(), ".mp3");

                                } else if (url.contains("mxtakatak")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "Mxtakatak_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("ganna")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "Ganna_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("cocoscope")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "Cocoscope_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("20min.ch")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "20min_ch_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("bandcamp")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "bandcamp_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("douyin")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "douyin_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("izlesene")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "izlesene_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("linkedin")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "linkedin_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("kwai") || url.contains("kw.ai")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "kwai_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("mashable")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "mashable_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("gag")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "gag_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("imgur")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "imgur_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("imdb")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "imdb_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("pinterest")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "pinterest_" + System.currentTimeMillis(), ".mp4");

                                } else if (url.contains("tiktok")) {
                                    new downloadFile().Downloading(Mcontext, myurlis, "tiktok_" + System.currentTimeMillis(), ".mp4");

                                }

                                if (!fromService) {
                                    pd.dismiss();


                                    iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));


                                }


                            } else {
                                Toast.makeText(Mcontext, "No links found", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception str2) {
                            str2.printStackTrace();
                            // Toast.makeText(Mcontext, "Invalid URL", 0).show();

                            if (!fromService) {
                                pd.dismiss();
                                iUtils.ShowToast(Mcontext, response + ":   " + Mcontext.getResources().getString(R.string.invalid_url));
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println("reccccc VVKK error " + error);
                        if (!fromService) {
                            pd.dismiss();
                            iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.invalid_url));
                        }
                    }
                });

    }


    private static class callGetSnackAppData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;
        private Iterator<Element> abk;

        callGetSnackAppData() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            try {
                this.ShareChatDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues roposo_", "doInBackground: Error");
            }
            return this.ShareChatDoc;
        }

        protected void onPostExecute(Document document) {
            String charSequence = "";

            try {


                String data = "";

                Iterator<Element> documentitrator = document.select("script").iterator();

                do {
                    if (!documentitrator.hasNext()) {

                        break;
                    }
                    data = ((Element) documentitrator.next()).data();
                    Log.e("onP4342424te:datais ", data);


                } while (!data.contains("window.__INITIAL_STATE__"));


                String stringbuil = data.substring(data.indexOf("{"), data.indexOf("};")) + "}";
                Log.e("onPostbjnkjh:oso_11 ", stringbuil);


                if (!document.equals("")) {
                    try {
                        JSONObject jSONObject = new JSONObject(stringbuil.toString());
                        VideoUrl = jSONObject.getJSONObject("sharePhoto").getString("mp4Url");

                        Log.e("onPostExecute:roposo_ ", VideoUrl);

                        getSnackVideoData(jSONObject.getString("shortUrl"), Mcontext);
                        VideoUrl = charSequence;

                    } catch (Exception document2) {
                        document2.printStackTrace();

                        System.out.println("respossss112212121qerrr " + document2.getMessage());

                        if (!fromService) {

                            pd.dismiss();
                        }
                    }
                }


                //   VideoUrl = document.select("video[src]").first().attr("src");


                //  VideoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
//                Log.e("onPostExecute:roposo_ ", VideoUrl);
//                if (!VideoUrl.equals(charSequence)) {
//
//
//                    try {
//                        String myurldocument = VideoUrl;
//
//
//                        String nametitle = "snackvideo_" +
//                                System.currentTimeMillis() +
//                                ".mp4";
//
//                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");
//
//                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
//                        VideoUrl = charSequence;
//                        //   binding.etText.setText(charSequence);
//
//                    } catch (Exception document2) {
//                        document2.printStackTrace();
//                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
//                    }
//                }


            } catch (Exception document22) {
                if (!fromService) {

                    pd.dismiss();
                }
                document22.printStackTrace();
                System.out.println("respossss112212121qerrr " + document22.getMessage());

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }
    }


    private static class callGetbilibiliAppData extends AsyncTask<String, Void, Document> {
        Document ShareChatDoc;
        private Iterator<Element> abk;

        callGetbilibiliAppData() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Document doInBackground(String... strArr) {
            try {
                this.ShareChatDoc = Jsoup.connect(strArr[0]).get();
            } catch (Exception strArr2) {
                strArr2.printStackTrace();
                Log.d("ContentValues roposo_", "doInBackground: Error");
            }
            return this.ShareChatDoc;
        }

        protected void onPostExecute(Document document) {
            String charSequence = "";

            try {

                ArrayList<String> mp4List = new ArrayList<>();
                ArrayList<String> qualitylist = new ArrayList<>();

                String data = "";

                Iterator<Element> documentitrator = document.select("script").iterator();

                do {
                    if (!documentitrator.hasNext()) {

                        break;
                    }
                    data = ((Element) documentitrator.next()).data();
                    Log.e("onP4342424te:datais ", data);


                } while (!data.contains("window.__playinfo__="));


                String stringbuil = data.substring(data.indexOf("{"), data.lastIndexOf("}"));

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(stringbuil);
                stringBuilder.append("}");

                Log.e("onPostbjnkjhoso_11 ", stringBuilder.toString());
                if (!document.equals("")) {
                    try {
                        JSONObject jSONObject = new JSONObject(stringBuilder.toString());
                        JSONObject datajSONObject = jSONObject.getJSONObject("data");
                        JSONObject dashjSONObject1 = datajSONObject.getJSONObject("dash");
                        JSONArray videojSONObject1 = dashjSONObject1.getJSONArray("video");


                        System.out.println("respossss112212121URL)) " + videojSONObject1.getJSONObject(0).getString("base_url"));


                        for (int i = 0; i < videojSONObject1.length(); i++) {


                            JSONObject jsonObject12 = videojSONObject1.getJSONObject(i);
                            mp4List.add(jsonObject12.getString("base_url"));
                            qualitylist.add(jsonObject12.getString("width"));


                            System.out.println("respossss112212121URL " + jsonObject12.getString("base_url"));

                        }

                        try {
                            JSONArray audiojSONObject1 = dashjSONObject1.getJSONArray("audio");
                            for (int i = 0; i < audiojSONObject1.length(); i++) {


                                JSONObject jsonObject12 = audiojSONObject1.getJSONObject(i);
                                mp4List.add(jsonObject12.getString("base_url"));
                                qualitylist.add(jsonObject12.getString("mime_type"));


                                System.out.println("respossss112212121URL " + jsonObject12.getString("base_url"));

                            }

                        } catch (Exception e) {

                        }


                        if (videojSONObject1.length() > 0) {

                            dialog_quality_allvids = new Dialog(Mcontext);


                            if (!fromService) {
                                pd.dismiss();
                            }


                            windowManager2 = (WindowManager) Mcontext.getSystemService(WINDOW_SERVICE);
                            LayoutInflater layoutInflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            mChatHeadView = layoutInflater.inflate(R.layout.dialog_quality_ytd, null);


                            dialog_quality_allvids.setContentView(mChatHeadView);


                            mainLayout = mChatHeadView.findViewById(R.id.linlayout_dialog);

                            img_dialog = mChatHeadView.findViewById(R.id.img_dialog);

                            mainLayout = dialog_quality_allvids.findViewById(R.id.linlayout_dialog);
                            img_dialog = dialog_quality_allvids.findViewById(R.id.img_dialog);


                            int size = 0;

                            try {
                                DisplayMetrics displayMetrics = new DisplayMetrics();
                                ((Activity) Mcontext).getWindowManager()
                                        .getDefaultDisplay()
                                        .getMetrics(displayMetrics);

                                int height = displayMetrics.heightPixels;
                                int width = displayMetrics.widthPixels;

                                size = width / 2;

                            } catch (Exception e) {
                                size = WindowManager.LayoutParams.WRAP_CONTENT;
                            }


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                params = new WindowManager.LayoutParams(
                                        size,
                                        WindowManager.LayoutParams.WRAP_CONTENT,
                                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                        PixelFormat.TRANSLUCENT);

                                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                params.x = 0;
                                params.y = 100;
                            } else {
                                params = new WindowManager.LayoutParams(
                                        size,
                                        WindowManager.LayoutParams.WRAP_CONTENT,
                                        WindowManager.LayoutParams.TYPE_PHONE,
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                                                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                                        PixelFormat.TRANSLUCENT);

                                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                                params.x = 0;
                                params.y = 100;
                            }


                            // mainLayout.setLayoutParams(params);


                            for (int i = 0; i < mp4List.size(); i++) {


                                addButtonToMainLayouttest_allvideo(qualitylist.get(i), mp4List.get(i), "Bilibili_" + qualitylist.get(i) + "_" + System.currentTimeMillis());

                            }

                            img_dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog_quality_allvids.dismiss();
                                }
                            });

                            dialog_quality_allvids.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                            dialog_quality_allvids.getWindow().setAttributes(params);
                            //  dialogquality.getWindow().setColorMode(Mcontext.getResources().getColor(R.color.colorAccent));

                            dialog_quality_allvids.show();


                            dialog_quality_allvids.show();
                        } else {


                            new downloadFile().Downloading(Mcontext, mp4List.get(0), "Bilibili_" + System.currentTimeMillis(), ".mp4");

                            if (!fromService) {
                                pd.dismiss();


                                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.starting_download));


                            }
                        }


                    } catch (Exception document2) {
                        document2.printStackTrace();

                        System.out.println("respossss112212121qerrr " + document2.getMessage());

                        if (!fromService) {

                            pd.dismiss();
                        }
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }
                }


            } catch (Exception document22) {
                if (!fromService) {

                    pd.dismiss();
                }
                document22.printStackTrace();
                System.out.println("respossss112212121qerrr " + document22.getMessage());

                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }
    }


    public static void callSnackVideoResult(String URL, String shortKey, String os, String sig, String client_key) {


        RetrofitApiInterface apiService = RetrofitClient.getClient().create(RetrofitApiInterface.class);


        Call<JsonObject> callResult = apiService.getsnackvideoresult(URL + "&" + shortKey + "&" + os + "&sig=" + sig + "&" + client_key);


        callResult.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {


                VideoUrl = response.body().getAsJsonObject("photo").get("main_mv_urls").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();


                System.out.println("response1122334455worURL:   " + VideoUrl);

                if (!VideoUrl.equals("")) {


                    try {

                        if (!fromService) {

                            pd.dismiss();
                        }


                        String myurldocument = VideoUrl;


                        String nametitle = "snackvideo_" +
                                System.currentTimeMillis();

                        new downloadFile().Downloading(Mcontext, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        document2.printStackTrace();
                        iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("response1122334455:   " + "Failed0 " + call);

                if (!fromService) {

                    pd.dismiss();
                }

            }
        });

    }

    public static void getSnackVideoData(String str, Context vc) {
        URI uri;
        try {
            uri = new URI(str);
        } catch (Exception e) {
            e.printStackTrace();
            uri = null;
            if (!fromService) {

                pd.dismiss();
            }
        }
        assert uri != null;
        String[] uripath = uri.getPath().split("/");
        String uripath2 = uripath[uripath.length - 1];
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("mod=OnePlus(ONEPLUS A5000)");
        arrayList.add("lon=0");
        arrayList.add("country_code=in");
        String mydid = "did=" +
                "ANDROID_" + Settings.Secure.getString(vc.getContentResolver(), "android_id");

        arrayList.add(mydid);
        arrayList.add("app=1");
        arrayList.add("oc=UNKNOWN");
        arrayList.add("egid=");
        arrayList.add("ud=0");
        arrayList.add("c=GOOGLE_PLAY");
        arrayList.add("sys=KWAI_BULLDOG_ANDROID_9");
        arrayList.add("appver=2.7.1.153");
        arrayList.add("mcc=0");
        arrayList.add("language=en-in");
        arrayList.add("lat=0");
        arrayList.add("ver=2.7");


        ArrayList arrayList2 = new ArrayList(arrayList);

        String shortKey = "shortKey=" +
                uripath2;
        arrayList2.add(shortKey);

        String os = "os=" +
                "android";
        arrayList2.add(os);
        String client_key = "client_key=" +
                "8c46a905";
        arrayList2.add(client_key);

        try {
            Collections.sort(arrayList2);

        } catch (Exception str225) {
            str225.printStackTrace();
            if (!fromService) {

                pd.dismiss();
            }
        }


        String clockData = CPU.getClockData(Mcontext, TextUtils.join("", arrayList2).getBytes(Charset.forName("UTF-8")), 0);

        String nowaterurl = "https://g-api.snackvideo.com/rest/bulldog/share/get?" + TextUtils.join("&", arrayList);

        System.out.println("respossss112212121q " + nowaterurl + "_______" + shortKey + os + clockData + client_key);


        callSnackVideoResult(nowaterurl, shortKey, os, clockData, client_key);


    }


}
