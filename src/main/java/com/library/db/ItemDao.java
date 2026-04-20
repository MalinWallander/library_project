package com.library.db;

import com.library.model.items.Copy;
import com.library.model.items.Item;

public interface ItemDao {
	void addItem(Item item);

	void addCopy(Copy copy);

}