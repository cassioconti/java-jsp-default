package com.example.appengine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StorageObject {
    private String name;
    private String id;
    private Date timeCreated;
    private long size;
    private String contentType;

    public StorageObject(String name, String id, String contentType, String size, String timeCreated) {
        this.name = name;
        this.id = id;
        this.contentType = contentType;
        this.size = Long.parseLong(size);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            this.timeCreated = formatter.parse(timeCreated.replaceAll("Z$", "+0000"));
        } catch (ParseException e) {
            this.timeCreated = new Date();
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }
}