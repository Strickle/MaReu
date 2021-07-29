package com.nounapps.mareu.service;


import com.nounapps.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList  (
            new Meeting(1, "Réunion A", "Mario",new Date(1623756879000L), "yopyop@blalba.fr"),
            new Meeting(2, "Réunion B", "Peach",new Date(1623756879000L), "hehe@blabla.com"),
            new Meeting(3, "Réunion C", "Toad",new Date(1659960000000L), "bloo@ffd.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
