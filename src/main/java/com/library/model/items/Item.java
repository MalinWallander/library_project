package com.library.model.items;

public class Item {
    private String itemId;
    private String itemType; // "Book", "DVD", "Magazine"
    private String itemTitle;
    private String categoryId;
    private String creator;
    private String status; // Nytt fält!

    public Item(String itemId, String itemType, String itemTitle, String categoryId, String status) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.itemTitle = itemTitle;
        this.categoryId = categoryId;
        this.status = status;
    }

    // Getters and setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
