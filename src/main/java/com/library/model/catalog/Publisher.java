package com.library.model.catalog;

public class Publisher {
    private String publisherId;
    private String publisherFName;
    private String publisherLname;

    public Publisher(String publisherId, String publisherName, String publisherLname) {
        this.publisherId = publisherId;
        this.publisherFName = publisherName;
        this.publisherLname = publisherName;
    }

    public String getFullName() {
        return publisherFName + " " + publisherLname;
    }
}
