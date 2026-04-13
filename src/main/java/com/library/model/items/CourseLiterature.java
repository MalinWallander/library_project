package com.library.model.items;

public class CourseLiterature extends Item {
    private String courseName;
    private String author;

    public CourseLiterature(String itemId, String itemTitle, String categoryId, String courseName, String author) {
        super(itemId, "CourseLiterature", itemTitle, categoryId);
        this.courseName = courseName;
        this.author = author;
    }


}
