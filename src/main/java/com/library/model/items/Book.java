package com.library.model.items;

public class Book extends Item {
    private String isbn;
    private String publisherId;
    private String genre;
    private String mainAuthorName;

    public Book(String itemId, String itemTitle, String categoryId, String isbn, String publisherId, String genre,
            String mainAuthorName) {
        super(itemId, "Book", itemTitle, categoryId);
        this.isbn = isbn;
        this.publisherId = publisherId;
        this.genre = genre;
        this.mainAuthorName = mainAuthorName;
    }
    // Getters för unika fält...
}