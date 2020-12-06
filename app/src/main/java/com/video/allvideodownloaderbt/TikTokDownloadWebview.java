package com.video.allvideodownloaderbt;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.video.allvideodownloaderbt.tasks.downloadFile;
import com.video.allvideodownloaderbt.utils.iUtils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Iterator;

public class TikTokDownloadWebview extends AppCompatActivity {

    private ImageView opentiktok;

    public static Handler handler;
    private static ValueCallback<Uri[]> mUploadMessageArr;
    String TAG = "whatsapptag";
    boolean doubleBackToExitPressedOnce = false;
    ProgressBar progressBar;
    private WebView webViewscanner;


    @SuppressLint("HandlerLeak")
    private class btnInitHandlerListner extends Handler {
        @SuppressLint({"SetTextI18n"})
        public void handleMessage(Message msg) {
        }
    }

    private class webChromeClients extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e("CustomClient", consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }


    private class MyBrowser extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            Log.e(TAG, "progressbar");
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "progressbar GONE");
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tik_tok_download_webview);

        setSupportActionBar(findViewById(R.id.tool12));
        opentiktok = findViewById(R.id.opentiktok);

        InitHandler();
        this.progressBar = findViewById(R.id.progressBar);


        opentiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/"));

                    intent.setPackage("com.zhiliaoapp.musically");

                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    iUtils.ShowToast(TikTokDownloadWebview.this, "Tiktok not Installed");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 24) {
            onstart();
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            // this.webViewscanner.setWebChromeClient(new webChromeClients());
            this.webViewscanner.setWebViewClient(new MyBrowser());
            this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName("UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(true);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);


            webViewscanner.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {

                    String nametitle = "tiktokvideo_" +
                            System.currentTimeMillis();

                    new downloadFile().Downloading(TikTokDownloadWebview.this, url, nametitle, ".mp4");


                }
            });

            webViewscanner.setWebChromeClient(new WebChromeClient() {

                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);

                    }

                    progressBar.setProgress(progress);
                    if (progress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);

                    }
                }
            });


            this.webViewscanner.loadUrl("https://ssstiktok.io/");
        } else {
            onstart();
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            //   this.webViewscanner.setWebChromeClient(new webChromeClients());
            this.webViewscanner.setWebViewClient(new MyBrowser());
            this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName("UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(false);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);


            webViewscanner.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {

                    String nametitle = "tiktokvideo_" +
                            System.currentTimeMillis();

                    new downloadFile().Downloading(TikTokDownloadWebview.this, url, nametitle, ".mp4");


                }
            });

            webViewscanner.setWebChromeClient(new WebChromeClient() {

                public void onProgressChanged(WebView view, int progress) {
                    if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);

                    }

                    progressBar.setProgress(progress);
                    if (progress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);

                    }
                }
            });

            this.webViewscanner.loadUrl("https://ssstiktok.io/");
        }
    }


    public void onstart() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && Build.VERSION.SDK_INT >= 21) {
            mUploadMessageArr.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(i2, intent));
            mUploadMessageArr = null;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (keyCode == 4) {
            try {
                if (this.webViewscanner.canGoBack()) {
                    this.webViewscanner.goBack();
                    return z;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        z = super.onKeyDown(keyCode, event);
        return z;
    }


    @SuppressLint({"WrongConstant"})
    @RequiresApi(api = 21)
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press again to exit", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        this.webViewscanner.clearCache(true);
    }

    public void onDestroy() {
        super.onDestroy();
        this.webViewscanner.clearCache(true);
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        this.webViewscanner.clearCache(true);
        super.onStop();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @SuppressLint({"HandlerLeak"})
    private void InitHandler() {
        handler = new btnInitHandlerListner();
    }


    public class CalltikboldiyaData extends AsyncTask<String, Void, Document> {
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

                        new downloadFile().Downloading(TikTokDownloadWebview.this, myurldocument, nametitle, ".mp4");

                        //  Utils.startDownload(document, str, shareChatActivity, stringBuilder);
                        VideoUrl = "";
                        //   binding.etText.setText(charSequence);

                    } catch (Exception document2) {
                        System.out.println("myresponseis111 exp1 " + document2.getMessage());

                        document2.printStackTrace();
                        iUtils.ShowToast(TikTokDownloadWebview.this, TikTokDownloadWebview.this.getResources().getString(R.string.somthing));
                    }

                    return;
                }

                iUtils.ShowToast(TikTokDownloadWebview.this, TikTokDownloadWebview.this.getResources().getString(R.string.somthing));
            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());

                iUtils.ShowToast(TikTokDownloadWebview.this, TikTokDownloadWebview.this.getResources().getString(R.string.somthing));
            }
        }


    }


}
