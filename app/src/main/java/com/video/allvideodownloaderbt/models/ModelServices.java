package com.video.allvideodownloaderbt.models;

import android.graphics.drawable.Drawable;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class ModelServices {

    public enum Myservicesenum {
        ROPOSO,
        SHARECHAT,
        FACEBOOK,
        INSTAGRAM,
        TWITTER,
        YOUTUBE,
        LIKEE,
        TIKTOK,
        GDRIVE,
        DAILYMOTION,
        VIMEO,
        CHINGARI,
        RIZZELE,
        JOSH,
        ZILI,
        MITRON,
        TRELL,
        Dubsmash,
        Triller,
        BoloIndya,
        HIND,
        IFUNNY,
        MEDIAFIRE,
        OKRU,
        VK,
        SOLIDFILES,
        VIDEOZA,
        SENDVID,
        BITTUBE
    }


    private Drawable LogoImg;
    private String Name;
    private File RootDirectory;
    private String RootDirectoryStr;
    private String ServiceLink;
    private Myservicesenum enmService;

    public String getServiceLink() {
        return this.ServiceLink;
    }

    public String getRootDirectoryStr() {
        return this.RootDirectoryStr;
    }

    public Myservicesenum getEnmService() {
        return this.enmService;
    }

    public String GetSuffex() {
        return getName().replace(StringUtils.SPACE, "");
    }

    public Drawable getLogoImg() {
        return this.LogoImg;
    }

    public File getRootDirectory() {
        return this.RootDirectory;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String str) {
        this.Name = str;
    }

    public ModelServices() {
        this.RootDirectory = null;
        this.Name = null;
    }

    public ModelServices(File file, String str, Drawable drawable, Myservicesenum myservicesenum, String str2) {
        this.RootDirectory = file;
        this.Name = str;
        this.LogoImg = drawable;
        this.enmService = myservicesenum;
        this.ServiceLink = str2;
    }

    public ModelServices(File file, String str, Drawable drawable, Myservicesenum myservicesenum, String str2, String str3) {
        this.RootDirectory = file;
        this.Name = str;
        this.LogoImg = drawable;
        this.enmService = myservicesenum;
        this.RootDirectoryStr = str2;
        this.ServiceLink = str3;
    }
}
