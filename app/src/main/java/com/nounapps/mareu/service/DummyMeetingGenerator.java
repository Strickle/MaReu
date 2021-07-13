package com.nounapps.mareu.service;

import com.nounapps.mareu.R;
import com.nounapps.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.graphics.Color.CYAN;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.MAGENTA;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList  (
            new Meeting(1, "Réunion A", "Room01",new Date(1623756879000L), "yopyop@blalba.fr"),
            new Meeting(2, "Réunion B", "Room02",new Date(1623756879000L), "hehe@blabla.com"),
            new Meeting(3, "Réunion C", "Room03",new Date(1623756879000L), "bloo@ffd.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

}
