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
    private String title;

    /** Location of meeting */
    private String location;

    /** date of meeting */
    private Date date;

    /** Organizer's name */
    private String organizer;

    public Meeting(long id, String title, String location, Date date, String organizer) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.organizer = organizer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String object) {
        this.title = object;
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

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}
