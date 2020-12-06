package com.video.allvideodownloaderbt.extraFeatures.videolivewallpaper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class MainActivityLivewallpaper extends AppCompatActivity {
    public CheckBox checkBox_battery_save;
    public CheckBox checkBox_play_begin;
    public CheckBox checkBox_sound;
    public CinimaWallService cinimaService;
    public InterstitialAd mInterstitialAd;
    public SpinKitView spin_kit;
    public ImageButton video_select_button;
    private ImageView imageView;
    private RelativeLayout main_layout;
    private String url = null;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_livevideo);
        this.checkBox_sound = findViewById(R.id.checkbox_sound);
        this.checkBox_play_begin = findViewById(R.id.checkbox_play_begin);
        this.imageView = findViewById(R.id.img_thumb);
        this.video_select_button = findViewById(R.id.video_select_button);
        this.checkBox_battery_save = findViewById(R.id.checkbox_battery);
        this.main_layout = findViewById(R.id.main_layout);
        this.spin_kit = findViewById(R.id.spin_kit);
        this.spin_kit.setVisibility(View.GONE);
        this.cinimaService = new CinimaWallService();
        this.checkBox_sound.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CinimaWallService cinimaWallService = MainActivityLivewallpaper.this.cinimaService;
                MainActivityLivewallpaper mainActivity = MainActivityLivewallpaper.this;
                cinimaWallService.setEnableVideoAudio(mainActivity, mainActivity.checkBox_sound.isChecked());
            }
        });
        this.checkBox_play_begin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CinimaWallService cinimaWallService = MainActivityLivewallpaper.this.cinimaService;
                MainActivityLivewallpaper mainActivity = MainActivityLivewallpaper.this;
                cinimaWallService.setPlayB(mainActivity, mainActivity.checkBox_play_begin.isChecked());
            }
        });
        this.checkBox_battery_save.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CinimaWallService cinimaWallService = MainActivityLivewallpaper.this.cinimaService;
                MainActivityLivewallpaper mainActivity = MainActivityLivewallpaper.this;
                cinimaWallService.setPlayBatterySaver(mainActivity, mainActivity.checkBox_battery_save.isChecked());
            }
        });
        ((AdView) findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId(getResources().getString(R.string.AdmobInterstitial));
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        this.mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                MainActivityLivewallpaper.this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }


    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 53213 && i2 == -1) {
            ArrayList stringArrayListExtra = intent.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
            for (int i3 = 0; i3 < stringArrayListExtra.size(); i3++) {
                Log.e("Path", (String) stringArrayListExtra.get(i3));
                this.url = (String) stringArrayListExtra.get(i3);
                this.spin_kit.setVisibility(View.VISIBLE);
                this.video_select_button.setVisibility(View.GONE);
                Glide.with(this).asBitmap().addListener(new RequestListener<Bitmap>() {
                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                        MainActivityLivewallpaper.this.spin_kit.setVisibility(View.GONE);
                        MainActivityLivewallpaper.this.video_select_button.setVisibility(View.VISIBLE);
                        return false;
                    }

                    public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                        MainActivityLivewallpaper.this.spin_kit.setVisibility(View.GONE);
                        MainActivityLivewallpaper.this.video_select_button.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).load(Uri.fromFile(new File((String) stringArrayListExtra.get(i3)))).into(this.imageView);
            }
        }
    }


    public void set_up_video_clicked(View view) {
        if (this.url == null) {
            Toasty.error(this, getString(R.string.please_select_video)).show();
            return;
        }
        this.cinimaService.setEnableVideoAudio(this, this.checkBox_sound.isChecked());
        this.cinimaService.setPlayB(this, this.checkBox_play_begin.isChecked());
        this.cinimaService.setPlayBatterySaver(this, this.checkBox_battery_save.isChecked());
        this.cinimaService.setVidSource(this, this.url);
        if (this.cinimaService.getVideoSource(this) == null) {
            Toasty.info(this, getString(R.string.error_emty_video)).show();
            return;
        }
        try {
            clearWallpaper();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
        intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(this, CinimaWallService.class));
        startActivity(intent);
        if (this.mInterstitialAd.isLoaded()) {
            this.mInterstitialAd.show();
        }
    }

    public void video_on_clicked(View view) {
        new VideoPicker.Builder(this).mode(VideoPicker.Mode.GALLERY).directory(VideoPicker.Directory.DEFAULT).extension(VideoPicker.Extension.MP4).enableDebuggingMode(false).build();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }
}
