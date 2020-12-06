package com.video.allvideodownloaderbt.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TikTokNoWaterMarkApi {

    @SerializedName("status")
    public String status;

    @SerializedName("name")
    public String name;

    @SerializedName("profileurl")
    public String profileurl;

    @SerializedName("username")
    public String username;

    @SerializedName("flag")
    public String flag;

    @SerializedName("thumbnailUrl")
    public String thumbnailUrl;

    @SerializedName("songurl")
    public String songurl;

    @SerializedName("watermark_removed")
    public String watermark_removed;

    @SerializedName("videourl")
    public String videourl;

    @SerializedName("ogvideourl")
    public String ogvideourl;

    @SerializedName("musictitle")
    public String musictitle;

    @SerializedName("musicauthor")
    public String musicauthor;

    @SerializedName("musiccover")
    public String musiccover;

    @SerializedName("musicplayurl")
    public String musicplayurl;

    @SerializedName("musicflag")
    public String musicflag;

    @SerializedName("musicurl")
    public String musicurl;

    @SerializedName("video_full_title")
    public String video_full_title;

    @SerializedName("profile_pic_url")
    public ArrayList<String> profile_pic_url;


    public TikTokNoWaterMarkApi(String status, String name, String profileurl, String username, String flag, String thumbnailUrl, String songurl, String watermark_removed, String videourl, String ogvideourl, String musictitle, String musicauthor, String musiccover, String musicplayurl, String musicflag, String musicurl, String video_full_title, ArrayList<String> profile_pic_url) {
        this.status = status;
        this.name = name;
        this.profileurl = profileurl;
        this.username = username;
        this.flag = flag;
        this.thumbnailUrl = thumbnailUrl;
        this.songurl = songurl;
        this.watermark_removed = watermark_removed;
        this.videourl = videourl;
        this.ogvideourl = ogvideourl;
        this.musictitle = musictitle;
        this.musicauthor = musicauthor;
        this.musiccover = musiccover;
        this.musicplayurl = musicplayurl;
        this.musicflag = musicflag;
        this.musicurl = musicurl;
        this.video_full_title = video_full_title;
        this.profile_pic_url = profile_pic_url;
    }
}