package com.nounapps.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nounapps.mareu.R;
import com.nounapps.mareu.databinding.ActivityListMeetingBinding;
import com.nounapps.mareu.di.DI;
import com.nounapps.mareu.events.DeleteMeetingEvent;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Random;

public class MeetingListActivity extends AppCompatActivity {


    private ActivityListMeetingBinding binding;

    private MeetingApiService mMeetingApiService;
    private RecyclerView mRecyclerview;
    private List<Meeting> mMeetings;
    private MyListMeetingRecyclerViewAdapter myAdapter;

    private FloatingActionButton mAddMeetingButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mMeetingApiService = DI.getMeetingApiService();


        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingListActivity.this, AddMeetingActivity.class);
                startActivity(intent);

            }
        });

        mRecyclerview = (RecyclerView)findViewById(R.id.rv_meeting);

        mMeetings = mMeetingApiService.getMeetings();

        myAdapter = new MyListMeetingRecyclerViewAdapter(mMeetings);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerview.setAdapter(myAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.filter);
        SearchView searchView = (SearchView) searchItem.getActionView();
        return true;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        mMeetings = mMeetingApiService.getMeetings();
        mRecyclerview.setAdapter(new MyListMeetingRecyclerViewAdapter(mMeetings));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        mMeetingApiService.deleteMeeting(event.meeting);
        initList();
    }
}