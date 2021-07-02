package com.nounapps.mareu.events;

import com.nounapps.mareu.model.Meeting;

/**
 * Event fired when a user deletes a Meeting
 */
public class DeleteMeetingEvent {

    /**
     * Neighbour to delete
     */
    public Meeting meeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}


