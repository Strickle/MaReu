package com.nounapps.mareu.model;


import android.content.res.Resources;
import android.graphics.Color;

import com.nounapps.mareu.R;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Model objet representing a Meeting
 */
public class Meeting implements Serializable {

    /** Identifier */
    private long id;

    /** Objet of meeting */
    private String object;

    /** Location of meeting */
    private String location;

    /** date of meeting */
    private Date date;

    /** Participant's mail */
    private String participants;

    /** Meeting's color */
    private int meetingColor;

    public Meeting(long id, String object, String location, Date date, String participants) {
        this.id = id;
        this.object = object;
        this.location = location;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
