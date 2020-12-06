package com.video.allvideodownloaderbt.statussaver;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.adapters.StoryAdapter;
import com.video.allvideodownloaderbt.models.StoryModel;
import com.video.allvideodownloaderbt.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;


public class StatusSaverMainFragment extends Fragment {


    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;


    ArrayList<Object> filesList = new ArrayList<>();
    private StoryAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private File[] files;
    private SwipeRefreshLayout recyclerLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_saver_main, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerLayout = view.findViewById(R.id.swipeRecyclerViewlayout);
        recyclerLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    recyclerLayout.setRefreshing(true);
                    setUpRecyclerView();
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerLayout.setRefreshing(false);
                            Toast.makeText(getActivity(), R.string.refre, Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error in swipe refresh " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        boolean result = checkPermission();
        if (result) {
            setUpRecyclerView();

        }


        if (getAppIntro(getActivity())) {
            Intent i = new Intent(getActivity(), IntroActivityStatusSaver.class);
            startActivity(i);
        }

        return view;
    }


    private boolean getAppIntro(Activity mainActivity) {
        SharedPreferences preferences;
        preferences = mainActivity.getSharedPreferences(Constants.MyPREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean("AppIntro", true);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle(R.string.pernecessory);
                    alertBuilder.setMessage(R.string.write_neesory);
                    alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void checkAgain() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(R.string.pernecessory);
            alertBuilder.setMessage(R.string.write_neesory);
            alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpRecyclerView();
                } else {
                    checkAgain();
                }
                break;
        }
    }

    private void setUpRecyclerView() {


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewAdapter = new StoryAdapter(getActivity(), getData());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private ArrayList<Object> getData() {


        if (filesList != null) {
            filesList = new ArrayList<>();

        }
        StoryModel f;


        String targetPath = "";


        SharedPreferences prefs = getActivity().getSharedPreferences("whatsapp_pref", MODE_PRIVATE);
        String name = prefs.getString("whatsapp", "main");//"No name defined" is the default value.

        if (name.equals("main")) {
            targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FOLDER_NAME + "Media/.Statuses";
        } else {

            targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FOLDER_NAME_Whatsappbusiness + "Media/.Statuses";

        }

        File targetDirector = new File(targetPath);
        files = targetDirector.listFiles();

        try {
            Arrays.sort(files, new Comparator() {
                public int compare(Object o1, Object o2) {

                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }

            });

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                f = new StoryModel();
                f.setName(getString(R.string.stor_saver));
                f.setUri(Uri.fromFile(file));
                f.setPath(files[i].getAbsolutePath());
                f.setFilename(file.getName());

                if (!file.getName().equals(".nomedia")) {
                    filesList.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filesList;
    }


}
