package com.shubham.haptikchattask.helper;

import com.shubham.haptikchattask.utils.Config;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shubham Gupta on 30-08-2016.
 */
public class Messages {
    String body;

    String message_time;
    Users users;
    boolean isStarred;
    ArrayList<Users> usersArrayList;

    public Messages(JSONObject jsonObject, ArrayList<Users> usersArrayList){
        body = jsonObject.optString(Config.KEY_BODY);
        message_time = jsonObject.optString(Config.KEY_MESSAGE_TIME);
        Users user = getUser(usersArrayList,jsonObject.optString(Config.KEY_USERNAME));
        if(user!=null) {
            user.addMessages(this);
            users = user;
        }else{
            users = new Users(jsonObject,this);
            usersArrayList.add(users);
        }
        this.usersArrayList = usersArrayList;

    }

    public ArrayList<Users> getArrayList(){
        return usersArrayList;
    }


    public String getBody() {
        return body;
    }



    public String getMessage_time() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm");

        Date d;
        String formattedTime="";
        try {
            d = sdf.parse(message_time);
          formattedTime = output.format(d);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return formattedTime;
    }

    public Users getUsers() {
        return users;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    private Users getUser(ArrayList<Users> users, String username){
        for(int i=0;i<users.size();i++){
             if(users.get(i).getUsername().equals(username)){
                 return users.get(i);
             }
        }
        return null;
    }
}
