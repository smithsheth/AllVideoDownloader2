package com.video.allvideodownloaderbt;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.ads.AudienceNetworkAds;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Appcontroller extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AudienceNetworkAds.initialize(this);

        SharedPreferences prefs = getSharedPreferences("lang_pref", MODE_PRIVATE);
        String lang = prefs.getString("lang", "en");//"No name defined" is the default value.


        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.ENGLISH);
        locales.add(new Locale("ar", "ARABIC"));
        locales.add(new Locale("ur", "URDU"));
        locales.add(new Locale("tr", "Turkish"));
        locales.add(new Locale("hi", "Hindi"));
        LocaleHelper.setLocale(getApplicationContext(), lang);

    }
}
