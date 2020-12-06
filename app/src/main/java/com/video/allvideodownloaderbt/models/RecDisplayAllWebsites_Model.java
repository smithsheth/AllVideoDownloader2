package com.video.allvideodownloaderbt.models;

public class RecDisplayAllWebsites_Model {


    int imageview;
    String Text_view;

    public RecDisplayAllWebsites_Model(int imageview, String text_view) {
        this.imageview = imageview;
        Text_view = text_view;
    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }

    public String getText_view() {
        return Text_view;
    }

    public void setText_view(String text_view) {
        Text_view = text_view;
    }
}
