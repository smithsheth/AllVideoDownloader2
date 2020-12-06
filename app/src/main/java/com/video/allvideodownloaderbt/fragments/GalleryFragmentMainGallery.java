package com.video.allvideodownloaderbt.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.video.allvideodownloaderbt.adapters.InstagramImageFileListAdapter;

import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.video.allvideodownloaderbt.utils.Constants.MY_ANDROID_10_IDENTIFIER_OF_FILE;

public class GalleryFragmentMainGallery extends Fragment {
    private InstagramImageFileListAdapter instagramImageFileListAdapter;
    private ArrayList<File> fileArrayList;

    private TextView noresultfound;
    private SwipeRefreshLayout swiperefresh;
    private RecyclerView recview_insta_image;
    private Fragment InstagalleryImagesGalleryfrag;
    AsyncTask<Void, Void, Void> fetchRecordingsAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instagallery_images, container, false);


        noresultfound = (TextView) view.findViewById(R.id.noresultfound);
        swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        recview_insta_image = (RecyclerView) view.findViewById(R.id.recview_insta_image);
        fileArrayList = new ArrayList<>();
        initViews();

        fetchRecordingsAsyncTask = new FetchRecordingsAsyncTask(getActivity());
        fetchRecordingsAsyncTask.execute();
        //   getAllFiles();
        return view;
    }


    private void initViews() {


        swiperefresh.setOnRefreshListener(() -> {

            String location = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/";
            fileArrayList = new ArrayList<>();
            listFilesForFolder(new File(location));
            swiperefresh.setRefreshing(false);
        });
    }


//    private void getAllFiles(String location) {
//        fileArrayList = new ArrayList<>();
//
//        File[] files = new File(location).listFiles();
//        if (files != null) {
//            for (final File fileEntry : new File(location).listFiles()) {
//                if (fileEntry.isDirectory()) {
//                    listFilesForFolder(fileEntry);
//                } else {
//                    if (fileEntry.getName().contains(MY_ANDROID_10_IDENTIFIER_OF_FILE)) {
//                        fileArrayList.add(fileEntry);
//                    }
//                    //  System.out.println(fileEntry.getName());
//                }
//            }
//        } else {
//
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    noresultfound.setVisibility(View.VISIBLE);
//
//                }
//            });
//        }
//    }


    public void listFilesForFolder(final File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    if (fileEntry.getName().contains(MY_ANDROID_10_IDENTIFIER_OF_FILE) && fileEntry.getAbsolutePath().endsWith(".mp4")) {
                        fileArrayList.add(fileEntry);
                    }
                }
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

        instagramImageFileListAdapter = new InstagramImageFileListAdapter(getActivity(), fileArrayList);
        recview_insta_image.setAdapter(instagramImageFileListAdapter);

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
            String location = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/";

            listFilesForFolder(new File(location));
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