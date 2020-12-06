package com.video.allvideodownloaderbt.adapters;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.models.StoryModel;
import com.video.allvideodownloaderbt.utils.Constants;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;
    private Context context;
    private ArrayList<Object> filesList;

    public StoryAdapter(Context context, ArrayList<Object> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_statussaver, null, false);
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder holder, final int position) {
        //    int viewType = getItemViewType(position);

        final StoryModel files = (StoryModel) filesList.get(position);
        final Uri uri = Uri.parse(files.getUri().toString());
        holder.userName.setText(files.getName());
        if (files.getUri().toString().endsWith(".mp4")) {
            holder.playIcon.setVisibility(View.VISIBLE);
        } else {
            holder.playIcon.setVisibility(View.INVISIBLE);
        }
        Glide.with(context)
                .load(files.getUri())
                .into(holder.savedImage);
        holder.downloadID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFolder();
                final String path = ((StoryModel) filesList.get(position)).getPath();
                final File file = new File(path);

                String destPath = "";
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    destPath = Environment.DIRECTORY_DOWNLOADS;
//
//                } else {
//                }

                destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;

                File destFile = new File(destPath);
                try {
                    FileUtils.copyFileToDirectory(file, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(
                        context,
                        new String[]{destPath + files.getFilename()},
                        new String[]{"*/*"},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            public void onMediaScannerConnected() {
                            }

                            public void onScanCompleted(String path, Uri uri) {
                                Log.d("path: ", path);
                            }
                        });
                Toast.makeText(context, context.getString(R.string.saved_to) + destPath + files.getFilename(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();
        }
        if (isDirectoryCreated) {
            Log.d("Folder", "Already Created");
        }
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView savedImage;
        ImageView playIcon;
        ImageView downloadID;

        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.profileUserName);
            savedImage = itemView.findViewById(R.id.mainImageView);
            playIcon = itemView.findViewById(R.id.playButtonImage);
            downloadID = itemView.findViewById(R.id.downloadID);
        }
    }
}
