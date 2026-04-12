package com.library.model.items;

public abstract class Item {
    private String itemId;
    private String itemType;
    private String itemTitle;
    private String categoryId;

    public Item(String itemId, String itemType, String itemTitle, String categoryId) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.itemTitle = itemTitle;
        this.categoryId = categoryId;
    }

    // Getters
    public String itemId() {
        return itemId;
    }

    public String itemType() {
        return itemType;
    }

    public String itemTitle() {
        return itemTitle;
    }

    public String categoryId() {
        return categoryId;
    }
}
