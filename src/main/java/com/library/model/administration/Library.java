package com.library.model.administration;

public class Library {
    private String libraryId;
    private String libraryName;
    private String locationName;
    private String address;

    public Library(String libraryId, String libraryName, String locationName, String address) {
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.locationName = locationName;
        this.address = address;
    }
}