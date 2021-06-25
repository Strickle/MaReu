package com.nounapps.mareu.ui.meeting_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nounapps.mareu.R;
import com.nounapps.mareu.model.Meeting;

import java.util.List;


public class MyListMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyListMeetingRecyclerViewAdapter.MyViewHolder> {

    List<Meeting> meetings;

    MyListMeetingRecyclerViewAdapter (List<Meeting> meetings){
        this.meetings = meetings;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.display(meetings.get(position));
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTVmeeting;
        private TextView mTVparticipants;
        private ImageView mIVcolorCircle;
        private FloatingActionButton mFAaddButton;
        private ImageButton mIBdeleteButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTVmeeting = (TextView) itemView.findViewById(R.id.tv_meeting);
            mTVparticipants = (TextView) itemView.findViewById(R.id.tv_participants);
            mFAaddButton = (FloatingActionButton) itemView.findViewById(R.id.add_neighbour);
            mIVcolorCircle = (ImageView) itemView.findViewById(R.id.item_list_color_circle);
            mIBdeleteButton = (ImageButton) itemView.findViewById(R.id.item_list_delete_button);
        }

        void display(Meeting meeting) {
            mTVmeeting.setText(meeting.getTitle() + meeting.getDate() + meeting.getLocation());
        }


    }
}

