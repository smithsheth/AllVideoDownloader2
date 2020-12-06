package com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

public class HelpActivity extends AppCompatActivity {


    public static Activity context;
    public static LinearLayout unitBanner;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        context = this;
        int arrow;
        if (SettingsClass.supportRTL) {
            forceRTLIfSupported();
            arrow = R.drawable.ic_arrow_back_rtl;
        } else arrow = R.drawable.ic_arrow_back;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        this.setTitle(getResources().getString(R.string.title_activity_help));
        unitBanner = findViewById(R.id.unitads);
        SettingsClass.admobBannerCall(this, unitBanner);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.AdmobInterstitial));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    private void requestNewInterstitial() {
        mInterstitialAd.loadAd(ConsentSDK.getAdRequest(context));
    }

    private void showFullAd(boolean count) {
        if (count) {
            SettingsClass.mCount++;
            if (SettingsClass.mCount == SettingsClass.nbShowInterstitial) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else SettingsClass.mCount--;
            }
        } else if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showFullAd(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }
}
