package com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.adapter.CategorieAdapter;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.db.DataBaseHelper;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.module.Categorie;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivityInstagramHashtag extends AppCompatActivity implements CategorieAdapter.ItemClickListener {

    public static InterstitialAd mInterstitialAd;
    public static Activity context;
    public static LinearLayout unitBanner;
    LinearLayout rate, share, settings;
    private RecyclerView categoriesListView;
    private CategorieAdapter adapter;
    private List<Categorie> mCategories = new ArrayList<>();
    private DataBaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsClass.supportRTL) {
            forceRTLIfSupported();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_instagram_hashtag);

        context = this;

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

        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .threshold(3)
                .session(7)
                .title(getResources().getString(R.string.rate_message))
                .titleTextColor(R.color.black)
                .positiveButtonText(getResources().getString(R.string.rate_later_btn))
                .negativeButtonText(getResources().getString(R.string.never_btn))
                .positiveButtonTextColor(R.color.colorPrimary)
                .negativeButtonTextColor(R.color.grey_500)
                .formTitle(getResources().getString(R.string.improvement_title))
                .formHint(getResources().getString(R.string.improvement))
                .formSubmitText(getResources().getString(R.string.submit_btn))
                .formCancelText(getResources().getString(R.string.cancel))
                .ratingBarColor(R.color.yellow)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                    }
                }).build();

        ratingDialog.show();

        DataBaseHelper.setmDatabase(this);
        mDBHelper = new DataBaseHelper(this);
        categoriesListView = findViewById(R.id.categories);
        categoriesListView.setHasFixedSize(true);
        categoriesListView.setLayoutManager(new LinearLayoutManager(this));
        File database = getApplicationContext().getDatabasePath(DataBaseHelper.DBNAME);
        if (false == database.exists()) {
            mDBHelper.getReadableDatabase();
            if (copyDatabase(this)) {
                //Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "Copy data error"+DataBaseHelper.DBLOCATION, Toast.LENGTH_LONG).show();
                return;
            }
        }

        rate = findViewById(R.id.rate);
        share = findViewById(R.id.share);
        settings = findViewById(R.id.settings);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getResources().getString(R.string.mssg_share) + " \n https://play.google.com/store/apps/details?id=" + getPackageName() + " \n";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.subject));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityInstagramHashtag.this, SettingsActivity.class));
                showFullAd(false);
            }
        });

        adapterCategories();


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

    public void adapterCategories() {
        mCategories = mDBHelper.getCategories();
        adapter = new CategorieAdapter(this, mCategories);
        adapter.setClickListener(this);
        categoriesListView.setAdapter(adapter);
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DataBaseHelper.DBNAME);
            String outFileName = DataBaseHelper.DBLOCATION + DataBaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //int id_cat = itemClicked.getId();
        Intent intent = new Intent(MainActivityInstagramHashtag.this, TagsActivity.class);
        intent.putExtra("id_cat", mCategories.get(position).getId());
        intent.putExtra("title", mCategories.get(position).getName());
        startActivityForResult(intent, 1);
        showFullAd(true);
    }

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }
}
