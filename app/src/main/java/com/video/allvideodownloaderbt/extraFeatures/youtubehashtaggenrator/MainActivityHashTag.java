package com.video.allvideodownloaderbt.extraFeatures.youtubehashtaggenrator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivityHashTag extends AppCompatActivity {
    public static final int clickCountToShowAds = 2;
    public static Context mContext;
    public static InterstitialAd mInterstitialAd;
    public static int clickCount = 0;
    public ConsentStatus userconsentStatusChoise = ConsentStatus.UNKNOWN;
    private ConsentForm consentForm = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hashtag);


        mContext = getApplicationContext();

        //Initialize Admob Ads
        initializeAdmobAds();
        // Update ConsentStatus
        updateConsentStatus();

        getSupportActionBar().setTitle(R.string.ytd_hashtag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Fragment To show in the first time
        getSupportFragmentManager().beginTransaction().add(R.id.container_framelayout, new FragmentAllHashtags()).commit();


    }

    private void updateConsentStatus() {
        final ConsentInformation consentInformation = ConsentInformation.getInstance(mContext);
        // consentInformation.addTestDevice("BF2E9988D37B1529F723BE2B21969B98");
        // consentInformation.setDebugGeography(DebugGeography.DEBUG_GEOGRAPHY_EEA);

        String[] publisherIds = {getString(R.string.publisher_id)};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // ModelUserInstagram's consent status successfully updated.

                if (consentInformation.isRequestLocationInEeaOrUnknown()) {

                    if (consentStatus == ConsentStatus.PERSONALIZED) {
                        Log.d("CONSENT CHOISE", "USER CHOISE PERSONALIZED ADS");
                        consentInformation.setConsentStatus(consentStatus);
                        userconsentStatusChoise = consentStatus;

                    }
                    if (consentStatus == ConsentStatus.NON_PERSONALIZED) {
                        Log.d("CONSENT CHOISE", "USER CHOISE NON --- PERSONALIZED ADS");
                        consentInformation.setConsentStatus(consentStatus);
                        userconsentStatusChoise = consentStatus;

                    }
                    if (consentStatus == ConsentStatus.UNKNOWN) {
                        Log.d("CONSENT CHOISE", "USER CHOISE UNKNOW !!!");
                        showConsentAdmobForm();
                    }

                }


            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // ModelUserInstagram's consent status failed to update.
                Log.d("CONSENT UPDATE", errorDescription);

            }
        });

    }

    public void showConsentAdmobForm() {

        URL privacyUrl = null;
        try {
            privacyUrl = new URL(getString(R.string.privacy_policy_url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle error.
        }
        consentForm = new ConsentForm.Builder(MainActivityHashTag.this, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        Log.d("CONSENT FORM", "LOADED FORM");
                        showForm();
                    }


                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        // Consent form was closed.


                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        // Consent form error.
                        Log.d("CONSENT FORM", errorDescription);
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        consentForm.load();

    }

    public void showForm() {
        consentForm.show();
    }

    private void initializeAdmobAds() {


        // Ads
        MobileAds.initialize(this, getResources().getString(R.string.AdmobAppId));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.AdmobInterstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.

                if (userconsentStatusChoise == ConsentStatus.NON_PERSONALIZED) {
                    Bundle extras = new Bundle();
                    extras.putString("npa", "1");

                    AdRequest adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            .build();
                    mInterstitialAd.loadAd(adRequest);
                    Log.d("CONSENT ADS", "BUILD NON PERSONALIZED AD REQUEST");
                } else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    Log.d("CONSENT ADS", "BUILD SIMPLE AD REQUEST");
                }
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }
}

