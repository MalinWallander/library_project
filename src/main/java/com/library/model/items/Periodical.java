package com.library.model.items;


public class Periodical extends Item {

    private String publisher;
    private String issn;

    public Periodical(String itemId, String type, String itemTitle,
                      String categoryId, String status,
                      String publisher, String issn) {

        super(itemId, type, itemTitle, categoryId, status);
        this.publisher = publisher;
        this.issn = issn;
    }

    @Override
    public String getCreator() {
        return publisher;
    }
}