package com.shubham.haptikchattask.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shubham.haptikchattask.R;
import com.shubham.haptikchattask.fragments.ChatWindowFragment;
import com.shubham.haptikchattask.fragments.ChatWindowFragment_;
import com.shubham.haptikchattask.fragments.ParticipantsListFragment;
import com.shubham.haptikchattask.fragments.ParticipantsListFragment_;
import com.shubham.haptikchattask.helper.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 30-08-2016.
 */
public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.MenuHolder> {
    public ArrayList<Users> users;
    Context context;
    @Override
    public ParticipantsAdapter.MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        context = parent.getContext();
        v  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participants_item_layout, parent, false);
        MenuHolder menuHolder = new MenuHolder(v);
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(ParticipantsAdapter.MenuHolder holder, int position) {
        holder.contactName.setText(users.get(position).getName());
        holder.noOfMessages.setText(users.get(position).getNoOfMessages() + " messages");
        holder.noOfFavouriteMessages.setText(users.get(position).getNoOfFavouriteMessages() + " favourites");

        if(!users.get(position).getImage_url().isEmpty())
            Picasso.with(context).load(users.get(position).getImage_url())
                    .placeholder(R.drawable.ic_contacts)
                    .into(holder.contactImage);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
        ImageView contactImage;
        TextView contactName;
        TextView noOfMessages;
        TextView noOfFavouriteMessages;

        public MenuHolder(View itemView) {
            super(itemView);
            contactName = (TextView)itemView.findViewById(R.id.contact_name);
            contactImage = (ImageView)itemView.findViewById(R.id.contact_image);
            noOfMessages = (TextView)itemView.findViewById(R.id.textView_messages);
            noOfFavouriteMessages = (TextView)itemView.findViewById(R.id.textView_favourite);
        }
    }

    public static class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        ParticipantsListFragment participantsListFragment;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    ChatWindowFragment chatWindowFragment = new ChatWindowFragment_();
                    return chatWindowFragment;
                case 1:
                    participantsListFragment = new ParticipantsListFragment_();
                    return participantsListFragment;
                default:
                    return null;
            }
        }

        public ParticipantsListFragment getParticipantsListFragment() {
            return participantsListFragment;
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}
