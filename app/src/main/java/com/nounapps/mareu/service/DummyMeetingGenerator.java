package com.nounapps.mareu.service;

import com.nounapps.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList  (
            new Meeting(1, "Réunion A", "Room 1",new Date(1623756879000L), "Peach"),
            new Meeting(2, "Réunion B", "Room 2",new Date(1623756879000L), "Mario"),
            new Meeting(3, "Réunion C", "Room 3",new Date(1623756879000L), "Luigi")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
