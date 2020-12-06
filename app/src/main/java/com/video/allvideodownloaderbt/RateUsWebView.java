package com.video.allvideodownloaderbt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.video.allvideodownloaderbt.utils.LocaleHelper;


public class RateUsWebView extends AppCompatActivity {

    private WebView binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us_web_view);

        binding = findViewById(R.id.webview_view);
//        if (getIntent().getExtras() != null) {
//            if (getIntent().getStringExtra("LINK")!=null) {
//                Intent i=new Intent(this,WebViewActivity.class);
//                i.putExtra("link",getIntent().getStringExtra("LINK"));
//                MainActivity.this.startActivity(i);
//                finish();
//            }}


        Intent i = getIntent();
        String url = i.getStringExtra("link");
        binding.loadUrl(url);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }
}