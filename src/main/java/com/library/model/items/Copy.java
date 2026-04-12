package com.library.model.items;

import java.util.Date;

public class Copy {
    private int copyId;
    private int itemId;
    private String status; // t.ex. "Available", "Loaned", "Reserved"
    private String barcode;
    private boolean referenceCopy; // Din idé: Sant om boken ej får lämna bibblan
    private Date purchaseDate;
    private String location;

    public Copy(int copyId, int itemId, String barcode, boolean referenceCopy) {
        this.copyId = copyId;
        this.itemId = itemId;
        this.barcode = barcode;
        this.referenceCopy = referenceCopy;
    }
}