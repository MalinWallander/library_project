package com.library.model.items;

public class Book extends Item {
    private String isbn;
    private String publisherId;
    private String genre;
    private String mainAuthorName;

    public Book(String itemId, String itemType, String itemTitle, String categoryId, String status, 
                String isbn, String publisherId, String genre, String mainAuthorName) {
    super(itemId, itemType, itemTitle, categoryId, status); // 5 parametrar till super
    this.isbn = isbn;
    this.publisherId = publisherId;
    this.genre = genre;
    this.mainAuthorName = mainAuthorName;
    }
    
    // Getters
    public String getIsbn() { return isbn; }
    public String getPublisherId() { return publisherId; }
    public String getGenre() { return genre; }
    public String getMainAuthorName() { return mainAuthorName; }
}