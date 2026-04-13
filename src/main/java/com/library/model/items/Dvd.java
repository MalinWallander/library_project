package com.library.model.items;

public class Dvd extends Item {
    private int productionYear;
    private String mainDirectorName;

    public Dvd(String itemId, String itemType, String itemTitle, String categoryId, String status, 
               int productionYear, String mainDirectorName) {
    super(itemId, itemType, itemTitle, categoryId, status); // 5 parametrar till super
    this.productionYear = productionYear;
    this.mainDirectorName = mainDirectorName;
}

    // Getters
    public int getProductionYear() { return productionYear; }
    public String getMainDirectorName() { return mainDirectorName; }
}
