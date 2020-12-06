package com.video.allvideodownloaderbt;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.video.allvideodownloaderbt.models.RecDisplayAllWebsites_Model;
import com.video.allvideodownloaderbt.utils.Constants;
import com.video.allvideodownloaderbt.utils.LocaleHelper;

import java.util.ArrayList;

public class AllSupportedApps extends AppCompatActivity {

    ArrayList<RecDisplayAllWebsites_Model> recDisplayAllWebsitesModelArrayList;
    ArrayList<RecDisplayAllWebsites_Model> recDisplayAllWebsitesModelArrayList_otherwebsites;
    private RecyclerView recviewSocialnetwork;
    private RecyclerView recviewOthernetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_supported);
        recviewSocialnetwork = findViewById(R.id.recview_socialnetwork);
        recviewOthernetwork = findViewById(R.id.recview_othernetwork);
        recDisplayAllWebsitesModelArrayList = new ArrayList<>();
        recDisplayAllWebsitesModelArrayList_otherwebsites = new ArrayList<>();


        recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.facebook, "Facebook"));
        recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.tiktok, "TikTok"));
        recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.instagram, "Instagram"));
        recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.twitter, "Twitter"));
        recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.likee, "Likee"));
        recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.sharechat, "ShareChat"));
        if (Constants.showyoutube) {

            recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.ytdpic, "Youtube"));

        }
        recDisplayAllWebsitesModelArrayList.add(new RecDisplayAllWebsites_Model(R.drawable.vimeo, "Vimeo"));

        RecDisplayAllWebsitesAdapter recDisplayAllWebsitesAdapter = new RecDisplayAllWebsitesAdapter(this, recDisplayAllWebsitesModelArrayList);

        recviewSocialnetwork.setAdapter(recDisplayAllWebsitesAdapter);
        recviewSocialnetwork.setLayoutManager(new GridLayoutManager(this, 4));


        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.mxtakatak, "Mxtakatak"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.ganna, "Ganna"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.min20, "20min"));

        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.roposo_not_bg, "Roposo"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.snackvideo, "SnackVideo"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.igtv, "IGTV"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.topbuzz, "Topbuzz"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.buzzfeed, "Buzzfeed"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.dailymotion, "Dailymotion"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.espn, "ESPN"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.flickr, "Flickr"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.imdb, "IMDB"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.imgurlogo, "Imgur"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.mashable, "Mashable"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.gag, "9GAG"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.ted, "TED"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.twitch, "Twitch"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.tumblrnew, "Tumblr"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.vkontakte, "VK"));


        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.reddit, "reddit"));
        //  recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.soundcloud, "soundcloud"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.bandcamp, "bandcamp"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.bilibili, "bilibili"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.bitchute, "bitchute"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.douyin, "douyin"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.izlesene, "izlesene"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.kwai, "kwai"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.linkedin, "linkedin"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.pinterest, "pinterest"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.streamable, "streamable"));


//new apps
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.mitron, "mitron"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.josh, "josh"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.triller, "triller"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.rizzel, "rizzle"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.ifunny, "ifunny"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.trell, "trell"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.boloindia, "boloindya"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.chingari, "chingari"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.dubsmash, "dubsmash"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.bittube, "bittube"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.mp4upload, "mp4upload"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.okru, "ok.ru"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.mediafire, "mediafire"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.gphoto, "gphoto"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.uptostream, "uptostream"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.fembed, "fembed"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.cocoscope, "cocoscope"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.sendvid, "sendvid"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.vivo, "vivo"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.fourshared, "fourShared"));
        recDisplayAllWebsitesModelArrayList_otherwebsites.add(new RecDisplayAllWebsites_Model(R.drawable.hind, "hind"));


        RecDisplayAllWebsitesAdapter recDisplayAllWebsitesAdapter_otherwesites = new RecDisplayAllWebsitesAdapter(this, recDisplayAllWebsitesModelArrayList_otherwebsites);

        recviewOthernetwork.setAdapter(recDisplayAllWebsitesAdapter_otherwesites);
        recviewOthernetwork.setLayoutManager(new GridLayoutManager(this, 4));


    }


    class RecDisplayAllWebsitesAdapter extends RecyclerView.Adapter<RecDisplayAllWebsitesAdapter.RecDisplayAllWebsitesViewHolder> {

        Context context;
        ArrayList<RecDisplayAllWebsites_Model> recDisplayAllWebsitesModelArrayList;

        public RecDisplayAllWebsitesAdapter(Context context, ArrayList<RecDisplayAllWebsites_Model> recDisplayAllWebsitesModelArrayList) {
            this.context = context;
            this.recDisplayAllWebsitesModelArrayList = recDisplayAllWebsitesModelArrayList;
        }

        @NonNull
        @Override
        public RecDisplayAllWebsitesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecDisplayAllWebsitesViewHolder(LayoutInflater.from(context).inflate(R.layout.recdisplayallwebsites_item, null, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecDisplayAllWebsitesViewHolder holder, int position) {


            holder.imgRecDisplayAllWebsites.setImageResource(recDisplayAllWebsitesModelArrayList.get(position).getImageview());
            holder.txtviewRecDisplayAllWebsites.setText(recDisplayAllWebsitesModelArrayList.get(position).getText_view());

        }

        @Override
        public int getItemCount() {
            return recDisplayAllWebsitesModelArrayList.size();
        }

        class RecDisplayAllWebsitesViewHolder extends RecyclerView.ViewHolder {

            private ImageView imgRecDisplayAllWebsites;
            private TextView txtviewRecDisplayAllWebsites;

            public RecDisplayAllWebsitesViewHolder(View view) {
                super(view);
                imgRecDisplayAllWebsites = view.findViewById(R.id.img_RecDisplayAllWebsites);
                txtviewRecDisplayAllWebsites = view.findViewById(R.id.txtview_RecDisplayAllWebsites);

            }


        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }
}