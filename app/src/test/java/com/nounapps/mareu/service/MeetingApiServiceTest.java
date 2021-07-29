package com.nounapps.mareu.service;

import com.nounapps.mareu.di.DI;
import com.nounapps.mareu.model.Meeting;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MeetingApiServiceTest {

    private MeetingApiService service;

    @Before
public void setUp(){service = DI.getNewInstanceApiService();}

    @Test
    public void getMeetingsSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertTrue(meetings.containsAll(expectedMeetings));
    }

    @Test
    public void getFilteredMeetingSuccess() {
        List<Meeting> meetings = service.getMeetings();
        meetings.clear();
        meetings.add(new Meeting(0,"Reunion D","Wario",new Date(1678197600000L),"example@example.com"));
        meetings.add(new Meeting(1,"Reunion E","Peach",new Date(1673107200000L),"example@example.com"));
        meetings.add(new Meeting(2,"Reunion F","Koopa",new Date(1659960000000L),"example@example.com"));
        List<Meeting> filteredMeetingList = new ArrayList<>();
        filteredMeetingList.addAll(service.getFilteredMeeting("Wario"));
        boolean goodLocation = false;
        for (Meeting m : filteredMeetingList) {
            if (m.getLocation().equals("Wario")) {
                goodLocation = true;
            }
        }
        assertTrue(goodLocation);
    }



    @Test
    public void deleteMeetingSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void createMeetingSuccess() {
        Meeting meetingCreated = service.getMeetings().get(0);
        service.createMeeting(meetingCreated);
        assertTrue(service.getMeetings().contains(meetingCreated));
    }

    @Test
    public void getMeetingFilteredByDateSuccess() {
        List<Meeting> meetings = service.getMeetings();
        meetings.clear();
        meetings.add(new Meeting(0,"Reunion D","Wario",new Date(1678197600000L),"example@example.com"));
        meetings.add(new Meeting(1,"Reunion E","Peach",new Date(1673107200000L),"example@example.com"));
        meetings.add(new Meeting(2,"Reunion F","Koopa",new Date(1659960000000L),"example@example.com"));
        List<Meeting> result = new ArrayList<>();
        Date dateFilter= new Date();
        dateFilter.setTime(1659960000000L);
        result.addAll(service.getMeetingFilteredByDate(dateFilter));
        boolean sameDate = false;
        for (Meeting m : result) {
            if (m.getDate().getTime() == 1659960000000L) {
                sameDate = true;
            }
        }
        assertTrue(sameDate);
    }
}