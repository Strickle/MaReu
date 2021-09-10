package com.nounapps.mareu.service;


import com.nounapps.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList  (
            new Meeting(1, "Réunion A", "Mario",new Date(1637510400000L),1, new String[]{"yopyop@blalba.fr", "bloubla@blalba.fr"}),
            new Meeting(2, "Réunion B", "Peach",new Date(1637402400000L),2, new String[]{"hehe@blabla.com, bilibli@plop.com"}),
            new Meeting(3, "Réunion C", "Toad",new Date(1637917200000L),4, new String[]{"bloo@ffd.com, lolalol@youhou.net"})
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
