package com.nounapps.mareu.service;

import com.nounapps.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    /**
     * Get all Meeting
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    List<Meeting> getFilteredMeeting(String filterableString);


    /**
     * Delete a meeting
     * @param  meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    boolean createMeeting(Meeting meeting);


    ArrayList<Meeting> getMeetingFilteredByDate(Date date);
}
