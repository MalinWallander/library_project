package com.library.model.catalog;

// TODO: Class never used
public class Author {
    private String authorId;
    private String authorFName;
    private String authorLName;

    public Author(String authorId, String authorFName, String authorLName) {
        this.authorId = authorId;
        this.authorFName = authorFName;
        this.authorLName = authorLName;
    }

    public String getFullName() {
        return authorFName + " " + authorLName;
    }
}
