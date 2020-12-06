package com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.adapter.TagsAdapter;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.db.DataBaseHelper;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.module.Tags;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class TagsActivity extends AppCompatActivity implements TagsAdapter.ItemClickListener {

    public static InterstitialAd mInterstitialAd;
    public static Activity context;
    public static LinearLayout unitBanner;
    int categorie_id;
    private RecyclerView tagsListView;
    private TagsAdapter adapter;
    private List<Tags> mTags;
    private DataBaseHelper mDBHelper;

    public static void requestNewInterstitial() {
        mInterstitialAd.loadAd(ConsentSDK.getAdRequest(context));
    }

    public static void showFullAd(boolean count) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int arrow;
        if (SettingsClass.supportRTL) {
            forceRTLIfSupported();
            arrow = R.drawable.ic_arrow_back_rtl;
        } else arrow = R.drawable.ic_arrow_back;
        setContentView(R.layout.activity_tags);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        this.setTitle(getIntent().getStringExtra("title") + " " + getResources().getString(R.string.tags_activity));

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
        mDBHelper = new DataBaseHelper(this);
        tagsListView = findViewById(R.id.tags);
        tagsListView.setHasFixedSize(true);
        tagsListView.setLayoutManager(new LinearLayoutManager(this));

        File database = getApplicationContext().getDatabasePath(DataBaseHelper.DBNAME);
        if (false == database.exists()) {
            mDBHelper.getReadableDatabase();
            if (copyDatabase(this)) {
                Toast.makeText(this, R.string.copydb, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.dopydb) + DataBaseHelper.DBLOCATION, Toast.LENGTH_LONG).show();
                return;
            }
        }
        categorie_id = getIntent().getIntExtra("id_cat", 1);
        adapterTags();
    }

    public void adapterTags() {
        //Get product list in db when db exists
        mTags = mDBHelper.getTagsGroupeFilterByIdCat(categorie_id);
        //Init adapter
        adapter = new TagsAdapter(this, mTags);
        adapter.setClickListener(this);
        //Set adapter for listview
        tagsListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
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
