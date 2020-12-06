package com.video.allvideodownloaderbt.extraFeatures;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.Splash;
import com.video.allvideodownloaderbt.extraFeatures.videolivewallpaper.MainActivityLivewallpaper;
import com.video.allvideodownloaderbt.extraFeatures.youtubehashtaggenrator.MainActivityHashTag;


public class ExtraFeaturesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_extra_features, container, false);


        view.findViewById(R.id.btn_one)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getActivity(), MainActivityLivewallpaper.class));


                    }
                });
        view.findViewById(R.id.bankcardtiktokId)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getActivity(), TikTokWebview.class));


                    }
                });

        view.findViewById(R.id.bankcardhashtag)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getActivity(), MainActivityHashTag.class));


                    }
                });


        view.findViewById(R.id.bankcardinstaghramhashtag)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getActivity(), Splash.class));


                    }
                });
        view.findViewById(R.id.earnmoneycard)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getActivity(), EarningAppWebviewActivity.class));


                    }
                });

        return view;
    }
}
