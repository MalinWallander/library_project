package com.library.service;

import java.util.List;

import com.library.db.SearchItemDao;
import com.library.model.items.Item;

public class SearchService {

    private final SearchItemDao searchItemDao;

    public SearchService(SearchItemDao searchItemDao) {
        this.searchItemDao = searchItemDao;
    }

    public List<Item> searchItems(String title, String creator, String categoryId) {
        // Om alla fält är tomma kan vi skicka in tomma strängar eller null
        return searchItemDao.search(title, creator, categoryId);
    }
}