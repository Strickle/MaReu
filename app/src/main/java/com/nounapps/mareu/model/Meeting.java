package com.nounapps.mareu.model;


import java.io.Serializable;
import java.util.Date;

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

    public Meeting(long id, String object, String location, Date date, String participants) {
        this.id = id;
        this.object = object;
        this.location = location;
        this.date = date;
        this.participants = participants;
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
}
