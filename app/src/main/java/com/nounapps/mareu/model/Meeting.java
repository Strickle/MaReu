package com.nounapps.mareu.model;


import android.graphics.Color;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Model objet representing a Meeting
 */
public class Meeting implements Serializable {
    public Meeting(String object, String location, Date startDate, int meetingDuration, String participants) {
        this.id = System.currentTimeMillis();
        this.object = object;
        this.location = location;
        this.startDate = startDate;
        this.meetingDuration = meetingDuration;
        this.participants = participants;
        setRandomColor();

    }

    /** Identifier */
    private long id;

    /** Objet of meeting */
    private String object;

    /** Location of meeting */
    private String location;

    /** date of meeting begin */
    private Date startDate;

    /** date of meeting finish */
    private int meetingDuration;

    /** Participant's mail */
    private String participants;

    /** Meeting's color */
    private int meetingColor;

    public Meeting(long id, String object, String location, Date startDate, int meetingDuration, String participants) {
        this.id = id;
        this.object = object;
        this.location = location;
        this.startDate = startDate;
        this.meetingDuration = meetingDuration;
        this.participants = participants;
        setRandomColor();
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getMeetingDuration() { return meetingDuration; }

    public void setMeetingDuration(int meetingDuration) { this.meetingDuration = meetingDuration; }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public int getMeetingColor() {
        return meetingColor;
    }

    public void setMeetingColor(int meetingColor) {
        this.meetingColor = meetingColor;
    }

    public void setRandomColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        meetingColor = color;
    }
}
