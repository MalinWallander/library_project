package com.library.service;

import java.util.UUID;

import com.library.db.ItemDao;
import com.library.model.items.Copy;
import com.library.model.items.Item;

public class ItemService {

	private final ItemDao itemDao;

	public ItemService(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void addItem(Item item) {
		validateItem(item);

		// Auto-generate an ID if not provided
		if (item.getItemId() == null || item.getItemId().isBlank()) {
			item.setItemId(UUID.randomUUID().toString());
		}

		itemDao.addItem(item);
	}

	public void addCopy(Copy copy) {
		validateCopy(copy);

		if (copy.getCopyId() == null || copy.getCopyId().isBlank()) {
			copy.setCopyId(UUID.randomUUID().toString());
		}

		// Default status if omitted
		if (copy.getStatus() == null || copy.getStatus().isBlank()) {
			copy.setStatus("Available");
		}

		itemDao.addCopy(copy);
	}

	private void validateItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null.");
		}
		if (item.getItemTitle() == null || item.getItemTitle().isBlank()) {
			throw new IllegalArgumentException("Item title is required.");
		}
		if (item.getItemType() == null || item.getItemType().isBlank()) {
			throw new IllegalArgumentException("Item type is required.");
		}
		if (!item.getItemType().equals("Book")
				&& !item.getItemType().equals("DVD")
				&& !item.getItemType().equals("Magazine")) {
			throw new IllegalArgumentException("Item type must be Book, DVD, or Magazine.");
		}
		if (item.getCategoryId() == null || item.getCategoryId().isBlank()) {
			throw new IllegalArgumentException("Category ID is required.");
		}
	}

	private void validateCopy(Copy copy) {
		if (copy == null) {
			throw new IllegalArgumentException("Copy cannot be null.");
		}
		if (copy.getItemId() == null || copy.getItemId().isBlank()) {
			throw new IllegalArgumentException("Copy must reference a valid item ID.");
		}
		if (copy.getBarcode() == null || copy.getBarcode().isBlank()) {
			throw new IllegalArgumentException("Barcode is required.");
		}
	}
}