package com.nounapps.mareu.ui.meeting_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nounapps.mareu.R;
import com.nounapps.mareu.events.DeleteMeetingEvent;
import com.nounapps.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyListMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyListMeetingRecyclerViewAdapter.MyViewHolder>{


    private final List<Meeting> mMeetings;

    public MyListMeetingRecyclerViewAdapter (List<Meeting> meetings){ mMeetings = meetings;}

    @NonNull
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

        holder.mIBdeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTVmeeting;
        private final TextView mTVparticipants;
        private final ImageView mIVcolorCircle;
        private final ImageButton mIBdeleteButton;

        public MyViewHolder(View itemView) {
            super(itemView);


            mTVmeeting = itemView.findViewById(R.id.tv_meeting);
            mTVparticipants = itemView.findViewById(R.id.tv_participants);
            mIVcolorCircle = itemView.findViewById(R.id.item_list_color_circle);
            mIBdeleteButton = itemView.findViewById(R.id.item_list_delete_button);
        }

        void display(Meeting meeting) {

            Date date = meeting.getDate();
            //
            // Display a date in day, month, year format
            //
            DateFormat formatter = new SimpleDateFormat("hh:mm");
            String meetingTime = formatter.format(date);



            mIVcolorCircle.setColorFilter(meeting.getMeetingColor());
            mTVmeeting.setText(meeting.getObject()+" - "+meetingTime+" - "+meeting.getLocation());
            mTVparticipants.setText(meeting.getParticipants());
        }

    }

}

