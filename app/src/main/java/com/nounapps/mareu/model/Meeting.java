package com.nounapps.mareu.model;


import android.graphics.Color;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Model objet representing a Meeting
 */
public class Meeting implements Serializable {
    public Meeting(String object, String location, Date startDate, int meetingDuration, String[] participants) {
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

    /** date of meeting begin in millis*/
    private long startDateMillis;

    /** date of meeting finish in millis */
    private long endDateMillis;

    /** date of meeting finish */
    private int meetingDuration;

    /** Participant's mail */
    private String[] participants;

    private String allParticipants;

    /** Meeting's color */
    private int meetingColor;

    public Meeting(long id, String object, String location, Date startDate, int meetingDuration, String[] participants) {
        this.id = id;
        this.object = object;
        this.location = location;
        this.startDate = startDate;
        this.meetingDuration = meetingDuration;
        this.participants = participants;
        setRandomColor();
        setEndDateMillis();
    }

    public long getId() {
        return id;
    }

    public String[] getParticipants() {
        return participants;
    }

    public void setParticipants(String[] participants) {
        this.participants = participants;
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

    public String getAllParticipants() {
        return removeFirstAndLast(allParticipants);
    }

    public void setEndDateMillis() {
        long durationMeetingMillis = getMeetingDuration() * 60 * 60 * 1000;
        Date meetingDateStart = getStartDate();
        long meetingDateStartMillis = meetingDateStart.getTime();
        long meetingEndDateMillis = meetingDateStartMillis + durationMeetingMillis;
        endDateMillis = meetingEndDateMillis;
        startDateMillis = meetingDateStartMillis;
    }
    public long getEndDateMillis() {
        return endDateMillis;
    }
    public long getStartDateMillis() {return startDateMillis; }

    public String removeFirstAndLast(String allParticipants){
        allParticipants = Arrays.toString(getParticipants());
        allParticipants = allParticipants.replaceAll("\\[","");
        allParticipants = allParticipants.replaceAll("\\]","");
        return allParticipants;
    }


}
