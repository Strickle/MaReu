package com.nounapps.mareu.service;

import com.nounapps.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {

    /**
     * Get all Meeting
     * @return {@link List}
     */
    List<Meeting> getMeetings();


    /**
     * Delete a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

}
