package com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.module;


public class Tags {
    int id;
    String name;
    String hashtags;

    public Tags(int id, String name, String hashtags) {
        this.id = id;
        this.name = name;
        this.hashtags = hashtags;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getHashtags() {
        return this.hashtags;
    }
}
