package com.video.allvideodownloaderbt.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VollySingltonClass {


    private static VollySingltonClass mInstance;
    private static Context mCtx;
    private RequestQueue requestQueue;

    private VollySingltonClass(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VollySingltonClass getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VollySingltonClass(context);
        }
        return mInstance;

    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequsetque(Request<T> request) {
        requestQueue.add(request);
    }

}
