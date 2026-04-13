package com.library.service;

import com.library.db.ItemDAO; // Se till att detta matchar filnamnet exakt!
import com.library.model.items.Item;
import java.util.List;

public class SearchService {

    // Typen måste vara ItemDAO (stora bokstäver) 
    // Variabeln döper vi till itemDao (litet i)
    private final ItemDAO itemDao; 

    public SearchService(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> searchItems(String title, String creator, String categoryId) {
    // Om alla fält är tomma kan vi skicka in tomma strängar eller null
    return itemDao.search(title, creator, categoryId);
}
}