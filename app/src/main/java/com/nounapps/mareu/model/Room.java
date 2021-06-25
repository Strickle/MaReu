package com.nounapps.mareu.model;


/**
 * Model objet representing a Room
 */
public class Room {

    /** Identifier */
    private long id;

    /** Name of room */
    private String name;

    public Room(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
