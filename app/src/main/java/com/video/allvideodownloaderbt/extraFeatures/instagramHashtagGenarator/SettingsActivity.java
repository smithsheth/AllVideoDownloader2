package com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public static InterstitialAd mInterstitialAd;
    public static Activity context;
    public static LinearLayout unitBanner;
    LinearLayout rate, share, contact, privacy, help, changeConsentSettings;
    TextView app_name, version_name;
    ConsentSDK consentSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int arrow;
        if (SettingsClass.supportRTL) {
            forceRTLIfSupported();
            arrow = R.drawable.ic_arrow_back_rtl;
        } else arrow = R.drawable.ic_arrow_back;
        setContentView(R.layout.activity_settings);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        this.setTitle(getResources().getString(R.string.title_activity_settings));

        consentSDK = new ConsentSDK.Builder(this)
                .addPrivacyPolicy(SettingsClass.privacy_policy_url) // Add your privacy policy url
                .addPublisherId(SettingsClass.publisherID) // Add your admob publisher id
                .build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.AdmobInterstitial));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
        unitBanner = findViewById(R.id.unitads);
        SettingsClass.admobBannerCall(this, unitBanner);
        app_name = findViewById(R.id.app_name);
        version_name = findViewById(R.id.version_name);
        app_name.setText(getResources().getString(R.string.app_name));
        try {
            version_name.setText(getString(R.string.ver) + (this.getPackageManager().getPackageInfo(getPackageName(), 0)).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        rate = findViewById(R.id.about_rate);
        share = findViewById(R.id.about_share);
        contact = findViewById(R.id.about_contact);
        privacy = findViewById(R.id.about_privacy);
        help = findViewById(R.id.about_help);
        changeConsentSettings = findViewById(R.id.about_consent);
        changeConsentSettings.setAlpha((float) 0.35);
        consentSDK.checkConsent(new ConsentSDK.ConsentCallback() {
            @Override
            public void onResult(boolean isRequestLocationInEeaOrUnknown) {
                // Your code
                if (isRequestLocationInEeaOrUnknown) {
                    changeConsentSettings.setAlpha((float) 1);
                    changeConsentSettings.setOnClickListener(SettingsActivity.this);
                } else {
                    changeConsentSettings.setAlpha((float) 0.35);
                }
            }
        });

        help.setOnClickListener(this);
        rate.setOnClickListener(this);
        share.setOnClickListener(this);
        contact.setOnClickListener(this);
        privacy.setOnClickListener(this);
        changeConsentSettings.setOnClickListener(this);
    }

    private void requestNewInterstitial() {
        mInterstitialAd.loadAd(ConsentSDK.getAdRequest(context));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_consent:
                consentSDK.checkConsent2(new ConsentSDK.ConsentCallback() {
                    @Override
                    public void onResult(boolean isRequestLocationInEeaOrUnknown) {
                        // Your code
                    }
                });
                break;
            case R.id.about_rate:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
            case R.id.about_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getResources().getString(R.string.mssg_share) + " \n https://play.google.com/store/apps/details?id=" + getPackageName() + " \n";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.subject));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));
                break;
            case R.id.about_contact:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{SettingsClass.contactMail});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.improvement));
                emailIntent.setType("text/plain");
                //emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Messg content");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                        best = info;
                if (best != null)
                    emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                startActivity(emailIntent);
                break;
            case R.id.about_help:
                startActivity(new Intent(SettingsActivity.this, HelpActivity.class));
                showFullAd(false);
                break;
            case R.id.about_privacy:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(SettingsClass.privacy_policy_url)));
                } catch (android.content.ActivityNotFoundException anfe) {
                }
                break;
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
