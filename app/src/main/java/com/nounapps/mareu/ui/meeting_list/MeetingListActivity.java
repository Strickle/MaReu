package com.nounapps.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.nounapps.mareu.R;
import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.service.DummyMeetingGenerator;
import java.util.List;

public class MeetingListActivity extends AppCompatActivity {


    private RecyclerView mRecyclerview;
    private List<Meeting> mMeetings;
    private MyListMeetingRecyclerViewAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        mRecyclerview = (RecyclerView)findViewById(R.id.rv_meeting);

        mMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;

        myAdapter = new MyListMeetingRecyclerViewAdapter(mMeetings);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerview.setAdapter(myAdapter);
    }
}