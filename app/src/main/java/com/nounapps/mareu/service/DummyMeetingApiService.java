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
    public boolean createMeeting(Meeting meeting) {

        for(Meeting mCreated : meetings){
        Date meetingStartDateChecked = meeting.getStartDate();
        long meetingDateCheckedMillis = meetingStartDateChecked.getTime();
        int meetingDurationChecked = meeting.getMeetingDuration();
        long meetingDurationCheckedMillis = meetingDurationChecked * 60 * 60 * 1000;
        long EndFfMeetingCheckedMillis = meetingDateCheckedMillis + meetingDurationCheckedMillis;
        Date meetingEndDateChecked = new Date(EndFfMeetingCheckedMillis);


            if (mCreated.getLocation().contains(meeting.getLocation()) &&
                    ((mCreated.getStartDate().before(meetingStartDateChecked) && mCreated.getEndDate().after(meetingStartDateChecked)) ||
                            (mCreated.getStartDate().before(meetingEndDateChecked) && mCreated.getEndDate().after(meetingEndDateChecked)) ||
                            ((mCreated.getStartDate().equals(meetingStartDateChecked)) && (mCreated.getStartDate().before(meetingEndDateChecked))) ||
                            ((mCreated.getStartDate().after(meetingStartDateChecked)) && (mCreated.getEndDate().equals(meetingEndDateChecked))) ||
                            ((mCreated.getStartDate().equals(meetingStartDateChecked)) && (mCreated.getEndDate().equals(meetingEndDateChecked))) ||
                            ((mCreated.getStartDate().after(meetingStartDateChecked)) && (mCreated.getEndDate().before(meetingEndDateChecked))))) {
                            return false;
                    }
        }
        meetings.add(meeting);
        return true;
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
}
//    Date meetingStartDateChecked = meeting.getStartDate();
//    long meetingDateCheckedMillis = meetingStartDateChecked.getTime();
//    int meetingDurationChecked = meeting.getMeetingDuration();
//    long meetingDurationCheckedMillis = meetingDurationChecked * 60 * 60 * 1000;
//    long EndFfMeetingCheckedMillis = meetingDateCheckedMillis + meetingDurationCheckedMillis;
//    Date meetingEndDateChecked = new Date(EndFfMeetingCheckedMillis);
//
//        for(Meeting mCreated : meetings){
//                if ((!mCreated.getLocation().contains(meeting.getLocation()) ||
//                (!mCreated.getStartDate().before(meetingStartDateChecked)  || !mCreated.getEndDate().after(meetingStartDateChecked))) &&
//                (!mCreated.getStartDate().before(meetingEndDateChecked) || !mCreated.getEndDate().after(meetingEndDateChecked))) {
//                meetings.add(meeting);
//                return true;
//                }
//                }return false;
//                }