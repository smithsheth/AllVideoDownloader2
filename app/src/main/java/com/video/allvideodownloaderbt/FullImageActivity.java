package com.video.allvideodownloaderbt;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class FullImageActivity extends AppCompatActivity {
    private ImageView imageviewFullimg;
    private ImageView imgclose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_view);

        initViews();

        if (getIntent().getStringExtra("myimgfile") != null) {
            String filepath = getIntent().getStringExtra("myimgfile");
            Glide.with(this)
                    .load(filepath)
                    .into(imageviewFullimg);

        }


    }

    public void initViews() {
        imageviewFullimg = (ImageView) findViewById(R.id.imageviewFullimg);
        imgclose = (ImageView) findViewById(R.id.imgclose);
        imgclose.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
