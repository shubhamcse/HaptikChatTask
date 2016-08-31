package com.shubham.haptikchattask.helper;

import android.util.Log;

import com.shubham.haptikchattask.utils.Config;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 31-08-2016.
 */
public class Users {
    String username;
    String name;
    String image_url;
    ArrayList<Messages> messages = new ArrayList<>();
    int starredMessages;



    public  Users(JSONObject jsonObject, Messages message){
        username = jsonObject.optString(Config.KEY_USERNAME);
        name = jsonObject.optString(Config.KEY_NAME);
        image_url = jsonObject.optString(Config.KEY_IMAGE_URL);
        addMessages(message);
    }
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void addMessages(Messages message) {
       messages.add(message);
    }

    public int getNoOfMessages(){
        Log.i("USers","size:"+messages.size());
        return messages.size();
    }

    public int getNoOfFavouriteMessages(){
        starredMessages=0;
        for(int i=0;i<messages.size();i++){
            if(messages.get(i).isStarred()){
                starredMessages ++;
            }
        }
        return starredMessages;
    }

    public void updateMessage(Messages message){
        for(int i=0;i<messages.size();i++){
            if(messages.get(i).equals(message)){
                messages.remove(i);
                messages.add(message);
                break;
            }
    }
}
}
