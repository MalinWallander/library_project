package com.library.model.items;

public class Dvd extends Item {
    private int productionYear;
    private String mainDirectorName;

    public Dvd(String itemId, String itemTitle, String categoryId, int productionYear, String mainDirectorName) {
        super(itemId, "Dvd", itemTitle, categoryId);
        this.productionYear = productionYear;
        this.mainDirectorName = mainDirectorName;
    }
}
