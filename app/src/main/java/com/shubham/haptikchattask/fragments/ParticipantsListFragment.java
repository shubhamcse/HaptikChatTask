package com.shubham.haptikchattask.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shubham.haptikchattask.R;
import com.shubham.haptikchattask.adapters.ParticipantsAdapter;
import com.shubham.haptikchattask.customViews.DividerItemDecoration;
import com.shubham.haptikchattask.helper.Users;
import com.shubham.haptikchattask.listeners.UpdateList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 30-08-2016.
 */



@EFragment(R.layout.participants_fragment_layout)
public class ParticipantsListFragment extends Fragment implements UpdateList{

    @ViewById(R.id.recyclerView)
    RecyclerView recyclerView;
    ParticipantsAdapter participantsAdapter;

    @AfterViews
    void init(){
        Log.i("PFragment","in init");
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), this.getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha),
                        true, true, R.color.dividerColor));
        participantsAdapter = new ParticipantsAdapter();
    }


    @Override
    public void updateList(ArrayList<Users> users) {
        Log.i("PFragment","in update"+this.getId());
       // participantsAdapter = new ParticipantsAdapter();
        participantsAdapter.users = users;
        recyclerView.setAdapter(participantsAdapter);
        participantsAdapter.notifyDataSetChanged();
    }
}
