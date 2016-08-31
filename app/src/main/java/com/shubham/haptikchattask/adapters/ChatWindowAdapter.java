package com.shubham.haptikchattask.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shubham.haptikchattask.R;
import com.shubham.haptikchattask.helper.Messages;
import com.shubham.haptikchattask.utils.MyApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 30-08-2016.
 */
public class ChatWindowAdapter extends RecyclerView.Adapter<ChatWindowAdapter.MenuHolder> {

    public ArrayList<Messages> messages;
    private int SELF = 123;
    Context context;

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        context = parent.getContext();
        if(viewType == SELF) {
           v  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msg_outgoing, parent, false);
        }else{
            v  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msg_incoming, parent, false);
        }
        MenuHolder menuHolder = new MenuHolder(v);
        return menuHolder;
    }

    @Override
    public int getItemViewType(int position) {
        //considering shawn-t to be myself
        if (messages.get(position).getUsers().getUsername().equals("shawn-t")) {
            return SELF;
        }
        return position;
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        holder.username.setText(messages.get(position).getUsers().getUsername());
        holder.chatMessage.setText(messages.get(position).getBody());
        holder.messageTime.setText(messages.get(position).getMessage_time());

        if(!messages.get(position).getUsers().getImage_url().isEmpty())
        Picasso.with(context).load(messages.get(position).getUsers().getImage_url())
                .placeholder(R.drawable.ic_contacts)
                .into(holder.contact_image);

        if(messages.get(position).isStarred())
            holder.favourite.getDrawable().setColorFilter(MyApplication.getContext().getResources().getColor(R.color.favouriteMessage), PorterDuff.Mode.SRC_ATOP);
        else
            holder.favourite.getDrawable().setColorFilter(MyApplication.getContext().getResources().getColor(R.color.unFavouriteMessage), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
     TextView username;
        TextView chatMessage;
        TextView messageTime;
        ImageView favourite;
        ImageView contact_image;

        public MenuHolder(View itemView) {
            super(itemView);
            username = (TextView)itemView.findViewById(R.id.textView_username);
            chatMessage = (TextView)itemView.findViewById(R.id.textView_chat);
            messageTime = (TextView)itemView.findViewById(R.id.textView_time);
            favourite = (ImageView)itemView.findViewById(R.id.imageview_isStarred);
            contact_image = (ImageView)itemView.findViewById(R.id.contact_image);

        }
    }

}
