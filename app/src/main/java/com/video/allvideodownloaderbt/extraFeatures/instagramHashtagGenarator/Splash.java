package com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

public class Splash extends AppCompatActivity {

    public static InterstitialAd mInterstitialAd;
    public static Activity context;
    Button start;
    ConsentSDK consentSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_ff);
        int arrow;
        if (SettingsClass.supportRTL) {
            forceRTLIfSupported();
        }

        context = this;
        consentSDK = new ConsentSDK.Builder(this)
                .addPrivacyPolicy(SettingsClass.privacy_policy_url) // Add your privacy policy url
                .addPublisherId(SettingsClass.publisherID) // Add your admob publisher id
                .build();

        consentSDK.checkConsent(new ConsentSDK.ConsentCallback() {
            @Override
            public void onResult(boolean isRequestLocationInEeaOrUnknown) {
                // Your code
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.AdmobInterstitial));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this, MainActivityInstagramHashtag.class));
                showFullAd(false);
                Splash.this.finish();

            }
        });
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
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }
}
