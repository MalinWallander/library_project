package com.library.model.items;

public class Dvd extends Item {
    private Integer productionYear;
    private String mainDirectorName;

    public Dvd(String itemId,
            String type,
            String itemTitle,
            String categoryId,
            String status,
            int productionYear,
            String director) {
        super(itemId, type, itemTitle, categoryId, status);
        this.productionYear = productionYear;
        this.mainDirectorName = director;
    }

    public Dvd() {
        super(null, null, null, null, null);
    }

    // getters/setters
    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getMainDirectorName() {
        return mainDirectorName;
    }

    public void setMainDirectorName(String mainDirectorName) {
        this.mainDirectorName = mainDirectorName;
    }

    @Override
    public String getCreator() {
        return mainDirectorName;
    }
}
