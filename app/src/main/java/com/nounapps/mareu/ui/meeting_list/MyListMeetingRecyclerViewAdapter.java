package com.nounapps.mareu.ui.meeting_list;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.nounapps.mareu.R;
import com.nounapps.mareu.events.DeleteMeetingEvent;
import com.nounapps.mareu.model.Meeting;


import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;


public class MyListMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyListMeetingRecyclerViewAdapter.MyViewHolder> implements Filterable{

    private List<Meeting> mMeetings;

    public MyListMeetingRecyclerViewAdapter (List<Meeting> meetings){ mMeetings = meetings;}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_list_item, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


       Meeting meeting = mMeetings.get(position);
       holder.display(mMeetings.get(position));

        holder.mIBdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTVmeeting;
        private TextView mTVparticipants;
        private ImageView mIVcolorCircle;
        private ImageButton mIBdeleteButton;

        public MyViewHolder(View itemView) {
            super(itemView);


            mTVmeeting = (TextView) itemView.findViewById(R.id.tv_meeting);
            mTVparticipants = (TextView) itemView.findViewById(R.id.tv_participants);
            mIVcolorCircle = (ImageView) itemView.findViewById(R.id.item_list_color_circle);
            mIBdeleteButton = (ImageButton) itemView.findViewById(R.id.item_list_delete_button);
        }

        void display(Meeting meeting) {

            meeting.setRandomColor();
            mIVcolorCircle.setColorFilter(meeting.getMeetingColor());
            mTVmeeting.setText(meeting.getObject()+" "+meeting.getLocation());
            mTVparticipants.setText(meeting.getParticipants());
        }

    }

}

