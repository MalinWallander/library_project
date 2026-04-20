package com.library.model.items;

public class Dvd extends Item {
    private Integer productionYear;
    private String mainDirectorName;

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
}
