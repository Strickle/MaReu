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
import com.nounapps.mareu.databinding.ActivityAddMeetingBinding;
import com.nounapps.mareu.databinding.ActivityListMeetingBinding;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.DummyMeetingGenerator;
import com.nounapps.mareu.service.MeetingApiService;

import java.util.List;

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


        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingListActivity.this, AddMeetingActivity.class);
                startActivity(intent);

            }
        });

        mRecyclerview = (RecyclerView)findViewById(R.id.rv_meeting);

        mMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;

        myAdapter = new MyListMeetingRecyclerViewAdapter(mMeetings);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerview.setAdapter(myAdapter);
    }

//    private void initList(){
//        mMeetings=m

//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.filter);
        SearchView searchView = (SearchView) searchItem.getActionView();
        return true;

    }
}