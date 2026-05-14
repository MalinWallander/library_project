package com.library.view.librarian;

import com.library.config.AppContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class InventoryController {

	@FXML
	private AddItemController addItemController;
	@FXML
	// TODO: Never used
	private EditItemsController editItemsController;

	@FXML
	public void initialize() {
		addItemController.setItemService(AppContext.getInstance().itemService);
		// EditItemsController gets itemService from AppContext itself
	}
}
