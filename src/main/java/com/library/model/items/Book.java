package com.library.model.items;

public class Book extends Item {
    private String isbn;
    private String genre;
    private String mainAuthorName;
    private String publisherId;
    // getters/setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMainAuthorName() {
        return mainAuthorName;
    }

    public void setMainAuthorName(String mainAuthorName) {
        this.mainAuthorName = mainAuthorName;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}