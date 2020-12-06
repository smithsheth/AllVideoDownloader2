package com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.SettingsClass;
import com.video.allvideodownloaderbt.extraFeatures.instagramHashtagGenarator.module.Categorie;

import java.util.Collections;
import java.util.List;

public class CategorieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Categorie> mCategorie = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public CategorieAdapter(Context mContext, List<Categorie> mCategorie) {
        this.mContext = mContext;
        this.mCategorie = mCategorie;
        this.mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View menuItemLayoutView;
        if (SettingsClass.supportRTL)
            menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.categorie_view_rtl, parent, false);
        else menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.categorie_view, parent, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        menuItemHolder.title.setText(mCategorie.get(position).getName().trim());
    }

    @Override
    public long getItemId(int position) {
        return mCategorie.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mCategorie.size();
    }

    // convenience method for getting data at click position
    public Object getItem(int id) {
        return mCategorie.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
