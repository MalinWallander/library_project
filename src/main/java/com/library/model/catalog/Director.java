package com.library.model.catalog;

public class Director {
    private String directorId;
    private String directorFName;
    private String directorLName;

    public Director(String directorId, String directorFName, String directorLName) {
        this.directorId = directorId;
        this.directorFName = directorFName;
        this.directorLName = directorLName;
    }
    public String getFullName() {
        return directorFName + " " + directorLName;
    }
}