package com.library.model.items;


public class CourseLiterature extends Item {
    private String courseName;
    private String author;

    public CourseLiterature(String itemId, String itemType, String itemTitle, String categoryId, String status, 
                            String courseName, String author) {
        super(itemId, itemType, itemTitle, categoryId, status);
        this.courseName = courseName;
        this.author = author;
    }

    // Getters
    public String getCourseName() { return courseName; }
    public String getAuthor() { return author; }
}