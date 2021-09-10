package com.nounapps.mareu.ui.meeting_list;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;




import com.nounapps.mareu.R;
import com.nounapps.mareu.databinding.ActivityListMeetingBinding;
import com.nounapps.mareu.di.DI;
import com.nounapps.mareu.events.DeleteMeetingEvent;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingListActivity extends AppCompatActivity {

    private ActivityListMeetingBinding binding;
    private MeetingApiService mMeetingApiService;
    private RecyclerView mRecyclerview;
    private List<Meeting> mMeetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mMeetingApiService = DI.getMeetingApiService();
        initMyListMeetingRecyclerView();
        initData();
        setButtonAddMeeting();
    }

    private void initMyListMeetingRecyclerView(){
        mRecyclerview = findViewById(R.id.rv_meeting);
        MyListMeetingRecyclerViewAdapter myAdapter = new MyListMeetingRecyclerViewAdapter(mMeetings);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerview.setAdapter(myAdapter);
    }

    private void initData(){
        mMeetings = new ArrayList<>(mMeetingApiService.getMeetings());
    }

    private void setButtonAddMeeting(){
        binding.addMeeting.setOnClickListener(v -> {
            Intent intent = new Intent(MeetingListActivity.this, AddMeetingActivity.class);
            startActivity(intent);
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.meetingNameFilter);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMeetings.clear();
                mMeetings.addAll(mMeetingApiService.getFilteredMeeting(newText));
                binding.rvMeeting.getAdapter().notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.meetingNameFilter:
                return true;
            case R.id.meetingDateFilter:
                dateDialog();
                return true;
            case R.id.reseteDateFilter:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetFilter() {
        mMeetings.clear();
        mMeetings.addAll(mMeetingApiService.getMeetings());
        binding.rvMeeting.getAdapter().notifyDataSetChanged();
    }

    private void dateDialog() {
        int selectedYear = 2021;
        int selectedMonth = 6;
        int selectedDayOfMonth = 16;

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, i, i1, i2) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(i, i1, i2);
            mMeetings.clear();
            mMeetings.addAll(mMeetingApiService.getMeetingFilteredByDate(cal.getTime()));
            binding.rvMeeting.getAdapter().notifyDataSetChanged();
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        datePickerDialog.show();
    }


    /**
     * Init the List of neighbours
     */
    private void initList() {
        mMeetings = new ArrayList<>(mMeetingApiService.getMeetings());
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

    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        mMeetingApiService.deleteMeeting(event.meeting);
        initList();
    }
}