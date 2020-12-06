package com.video.allvideodownloaderbt.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.adapters.GalleryAdapter;
import com.video.allvideodownloaderbt.models.GalleryModel;
import com.video.allvideodownloaderbt.utils.Constants;

import java.io.File;
import java.util.ArrayList;


public class StatusSaverGallery extends Fragment {

    private GalleryAdapter fileListAdapter;
    public ArrayList<GalleryModel> galleryModelArrayList;


    private TextView noresultfound;
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recststuslist;
    AsyncTask<Void, Void, Void> fetchRecordingsAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_saver_gallery, container, false);


        noresultfound = (TextView) view.findViewById(R.id.noresultfound);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        recststuslist = (RecyclerView) view.findViewById(R.id.recststuslist);

        galleryModelArrayList = new ArrayList<>();
        initViews();

        fetchRecordingsAsyncTask = new FetchRecordingsAsyncTask(getActivity());
        fetchRecordingsAsyncTask.execute();


        //  getAllFiles();
        return view;
    }


    private void initViews() {


        swiperefreshlayout.setOnRefreshListener(() -> {
            getAllFiles();
            swiperefreshlayout.setRefreshing(false);
        });
    }

    private void getAllFiles() {
        galleryModelArrayList = new ArrayList<>();


        String location = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;

        File[] files = new File(location).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                //  fileArrayList.add(file);

                galleryModelArrayList.add(new GalleryModel(getString(R.string.savedstt) + i, Uri.fromFile(files[i]), files[i].getAbsolutePath(), files[i].getName()));
            }

        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    noresultfound.setVisibility(View.VISIBLE);

                }
            });
        }
    }


    void setAdaptertoRecyclerView() {

        fileListAdapter = new GalleryAdapter(getActivity(), galleryModelArrayList);
        recststuslist.setAdapter(fileListAdapter);

    }


    private class FetchRecordingsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public FetchRecordingsAsyncTask(Context activity) {
            dialog = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading Data, please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... args) {
            getAllFiles();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setAdaptertoRecyclerView();
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
                if (fetchRecordingsAsyncTask != null) {
                    fetchRecordingsAsyncTask.cancel(true);
                }
            }
        }


    }

}