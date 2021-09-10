package com.nounapps.mareu.service;

import android.widget.Toast;

import com.nounapps.mareu.model.Meeting;
import com.nounapps.mareu.ui.meeting_list.AddMeetingActivity;
import com.nounapps.mareu.ui.meeting_list.MeetingListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private MeetingListActivity mMeetingListActivity;
    private AddMeetingActivity mAddMeetingActivity;

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Meeting> getFilteredMeeting(String filterableString) {
        List<Meeting> filteredMeeting = new ArrayList<>();

        for(Meeting m : meetings){
            if (m.getLocation().contains(filterableString)){
                filteredMeeting.add(m);
            }
        }return filteredMeeting;
    }

    /**
     *
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
        ;
    }

    /**
     *
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);

//        Date meetingDateChecked = meeting.getStartDate();
//        long meetingDateCheckedMillis = meetingDateChecked.getTime();
//        int meetingDurationChecked = meeting.getMeetingDuration();
//        long meetingDurationCheckedMillis = meetingDurationChecked * 60 * 60 * 1000;
//        long EndFfMeetingCheckedMillis = meetingDateCheckedMillis + meetingDurationCheckedMillis;
//
//        for(Meeting mCreated : meetings){
//
//            long meetingsStartDateMillis = mCreated.getStartDateMillis();
////            long meetingsEndDateMillis = mCreated.getEndDateMillis();
//
//            if (mCreated.getLocation().contains(meeting.getLocation())  &&
//                    (meeting.getStartDateMillis()) >= (mCreated.getStartDateMillis()) && <= (mCreated.getEndDateMillis())) &&
//                    (mCreated.getStartDateMillis() <= EndFfMeetingCheckedMillis) & (EndFfMeetingCheckedMillis <= mCreated.getEndDateMillis())){
//            }else {
//                meetings.add(meeting);
//            }
//        }
    }


    @Override
    public ArrayList<Meeting> getMeetingFilteredByDate(Date date) {
        ArrayList<Meeting> result = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        for (int i = 0; i < meetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(meetings.get(i).getStartDate());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) result.add(meetings.get(i));

        }
        return result;
    }

    public void checkDateMeetingNotAlreadyReserved(Meeting meeting) {
    }
}
