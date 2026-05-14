package com.library.model.items;

import java.time.LocalDate;

public class Copy {
    private String copyId;
    private String status;
    private String barcode;
    private boolean referenceCopy;
    private LocalDate purchaseDate;
    private String location;
    private LocalDate lastOnLoan;
    private String itemTitle;
    private String itemId;

    public Copy() {
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isReferenceCopy() {
        return referenceCopy;
    }

    public void setReferenceCopy(boolean referenceCopy) {
        this.referenceCopy = referenceCopy;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getLastOnLoan() {
        return lastOnLoan;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}