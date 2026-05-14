package com.library.db;

import com.library.model.items.Item;
import java.util.List;

public interface SearchItemDao {
	List<Item> search(String title, String creator, String categoryId);
}
