package com.shubham.haptikchattask.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shubham.haptikchattask.R;
import com.shubham.haptikchattask.adapters.ChatWindowAdapter;
import com.shubham.haptikchattask.helper.Messages;
import com.shubham.haptikchattask.helper.Users;
import com.shubham.haptikchattask.listeners.UsersListListener;
import com.shubham.haptikchattask.utils.Config;
import com.shubham.haptikchattask.utils.MyApplication;
import com.shubham.haptikchattask.utils.RecyclerItemClickListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 30-08-2016.
 */
@EFragment(R.layout.chat_fragment_layout)
public class ChatWindowFragment extends Fragment {

    private static final String TAG = "ChatWindowFragment";

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;
    ChatWindowAdapter chatWindowAdapter;
    private UsersListListener usersListListener;
    ArrayList<Messages> messages = new ArrayList();
    ArrayList<Users> users;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            usersListListener = (UsersListListener) activity;
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
    }
    @AfterViews
    void init() {

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        onClickRecyclerView(recyclerView);

        try {
            MyApplication.initCache(getActivity().getExternalCacheDir());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        request();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void request() throws Exception {
        Request request = new Request.Builder()
                .url(Config.URL_TEST)
                .build();

        Response response = MyApplication.client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray messagesArray = jsonObject.getJSONArray("messages");

        users = new ArrayList<>();
        for (int i = 0; i < messagesArray.length(); i++) {
            Messages message = new Messages(messagesArray.getJSONObject(i),users);
            messages.add(message);
            users = message.getArrayList();
        }
        setAdapter(messages);
        updateOtherFragment(users);
    }

    @UiThread
    void setAdapter(ArrayList<Messages> messages) {
        chatWindowAdapter = new ChatWindowAdapter();
        recyclerView.setAdapter(chatWindowAdapter);
        chatWindowAdapter.messages = messages;
        chatWindowAdapter.notifyDataSetChanged();

    }

    @UiThread
    void updateOtherFragment(ArrayList<Users> users){
        Log.i("CWragment","in update"+users.size());
        usersListListener.updateList(users);
    }

    public void onClickRecyclerView(final RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        messages.get(position).setStarred(!messages.get(position).isStarred());
                        chatWindowAdapter.messages = messages;
                        chatWindowAdapter.notifyDataSetChanged();
                        findUsernameAndUpdate(messages.get(position));
                        usersListListener.updateList(users);
                        /**/
                        /*Intent intent = new Intent(HomeActivity.this, EmailDetailActivity_.class);
                        intent.putExtra("id",messageListAdapter.messages.get(position).getId());
                        startActivity(intent);*/
                    }
                })
        );
    }

    void findUsernameAndUpdate(Messages message){
        for(int i=0;i<users.size();i++){
         if(users.get(i).getUsername().equals(message.getUsers().getUsername())){

                users.get(i).updateMessage(message);
                break;
            }
        }
    }

}
