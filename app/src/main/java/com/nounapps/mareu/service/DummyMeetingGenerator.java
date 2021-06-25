package com.nounapps.mareu.service;

import com.nounapps.mareu.model.Meeting;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {
    public static List<Meeting> getDummyMeetings() {
        return DUMMY_MEETINGS;
    }

    public static void setDummyMeetings(List<Meeting> dummyMeetings) {
        DUMMY_MEETINGS = dummyMeetings;
    }

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "Réunion A", "Room 1",new Date(1623756879000L), "Peach"),
            new Meeting(1, "Réunion B", "Room 1",new Date(1623756879000L), "Mario"),
            new Meeting(1, "Réunion C", "Room 1",new Date(1623756879000L), "Luigi")
    );

}
