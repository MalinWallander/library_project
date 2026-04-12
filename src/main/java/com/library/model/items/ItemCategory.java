package com.library.model.items;

public class ItemCategory {
    private int categoryId;
    private String categoryName;
    private int maxLoanTime; // Olika för Kurslitteratur vs Skönlitteratur

    public ItemCategory(int categoryId, String categoryName, int maxLoanTime) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.maxLoanTime = maxLoanTime;
    }
}