package com.video.allvideodownloaderbt.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.video.allvideodownloaderbt.Interfaces.UserListInStoryListner;
import com.video.allvideodownloaderbt.R;
import com.video.allvideodownloaderbt.models.storymodels.ModelUsrTray;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryUsersListAdapter extends RecyclerView.Adapter<StoryUsersListAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<ModelUsrTray> userListIninstaStory;
    private ArrayList<ModelUsrTray> userListIninstaStoryOrignal;
    UserListInStoryListner userListInStoryListner;


    public StoryUsersListAdapter(Context context, ArrayList<ModelUsrTray> userListIninstaStory, UserListInStoryListner userListInStoryListner) {
        this.context = context;
        this.userListIninstaStory = userListIninstaStory;
        this.userListInStoryListner = userListInStoryListner;
        this.userListIninstaStoryOrignal = userListIninstaStory;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_userlist_placeholder, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        System.out.println("prooooddd111 " + userListIninstaStory.get(position).getUser().getProfile_pic_url());

        viewHolder.rec_item_username.setText(userListIninstaStory.get(position).getUser().getUsername());
        viewHolder.rec_user_fullname.setText(userListIninstaStory.get(position).getUser().getFull_name());
        Glide.with(context).load(userListIninstaStory.get(position).getUser().getProfile_pic_url())
                .thumbnail(0.2f).into(viewHolder.story_item_imgview);

        viewHolder.rec_item_relativelative_layot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("response1122ff334455:   workingggg");
                userListInStoryListner.onclickUserStoryListeItem(position, userListIninstaStory.get(position));


            }
        });

        Glide.with(context).load(userListIninstaStory.get(position).getUser().getProfile_pic_url())
                .thumbnail(0.2f).into(viewHolder.story_item_imgview);
    }

    @Override
    public int getItemCount() {
        return userListIninstaStory == null ? 0 : userListIninstaStory.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userListIninstaStory = (ArrayList<ModelUsrTray>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ModelUsrTray> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = userListIninstaStoryOrignal;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<ModelUsrTray> getFilteredResults(String constraint) {
        List<ModelUsrTray> results = new ArrayList<>();

        for (ModelUsrTray item : userListIninstaStoryOrignal) {
            if (item.getUser().getUsername().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rec_item_relativelative_layot;
        private CircleImageView story_item_imgview;
        private TextView rec_item_username, rec_user_fullname;

        public ViewHolder(View view) {
            super(view);

            rec_item_relativelative_layot = (LinearLayout) view.findViewById(R.id.rec_item_relativelative_layot);
            story_item_imgview = (CircleImageView) view.findViewById(R.id.story_item_imgview);
            rec_item_username = (TextView) view.findViewById(R.id.rec_item_username);
            rec_user_fullname = (TextView) view.findViewById(R.id.rec_user_fullname);

        }
    }
}