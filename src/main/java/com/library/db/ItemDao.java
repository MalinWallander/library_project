package com.library.db;

import java.util.List;

import com.library.model.items.Copy;
import com.library.model.items.Item;

public interface ItemDao {
    void addItem(Item item);

    void addCopy(Copy copy);

    List<Item> searchByTitle(String title);

    void updateItem(Item item);

    Item findById(String itemId);

    void withdrawCopy(String copyId);

    List<Copy> getCopiesForItem(String itemId);

    void updateCopy(Copy copy);
}