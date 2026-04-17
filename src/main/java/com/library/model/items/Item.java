package com.library.model.items;

public abstract class Item {
    private String itemId;
    private String itemType;
    private String itemTitle;
    private String categoryId;
    private String status; // Nytt fält!

    public Item(String itemId, String itemType, String itemTitle, String categoryId, String status) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.itemTitle = itemTitle;
        this.categoryId = categoryId;
        this.status = status;
    }
    // Glöm inte getter för status!
    public String getStatus() { return status; }
 
    // Getters
    public String getItemId() {
        return itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }
     public String getCreator() {
        return "";
    }
}
