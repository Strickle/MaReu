package com.nounapps.mareu.service;

import com.nounapps.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Filter;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    Meeting mMeeting;

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

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
        ;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);

    }

    @Override
    public ArrayList<Meeting> getMailsFilteredByDate(Date date) {
        ArrayList<Meeting> result = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        for (int i = 0; i < meetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(meetings.get(i).getDate());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) result.add(meetings.get(i));

        }
        return result;
    }
}
