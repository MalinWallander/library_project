package com.library.view.shared;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

import com.library.model.items.Item;
import com.library.service.SearchService;
import com.library.config.AppContext;

public class SearchController {

    @FXML private TextField searchField;
    @FXML private TextField creatorField;
    @FXML private TableView<Item> resultsTable;
    @FXML private TableColumn<Item, String> titleColumn;
    @FXML private TableColumn<Item, String> creatorColumn;
    @FXML private TableColumn<Item, String> statusColumn;
    
    private SearchService searchService;

    @FXML
    public void initialize() {
        try {
            // Hämta servicen
            this.searchService = AppContext.getInstance().searchService;
            
            resultsTable.setPlaceholder(new Label("Använd sökfältet för att hitta böcker eller filmer."));

            // Mappa kolumnerna korrekt mot Item.java getters
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
            
        } catch (Exception e) {
            System.out.println("FEL vid initialize: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch() {
        try {
            String titleQuery = searchField.getText().trim();
            String creatorQuery = creatorField.getText().trim();

            if (titleQuery.isEmpty()) titleQuery = null;
            if (creatorQuery.isEmpty()) creatorQuery = null;

            System.out.println("DEBUG: Startar sökning..."); // Syns denna?
            
            if (searchService == null) {
                System.out.println("ERROR: searchService är NULL!");
                return;
            }

            List<Item> searchResults = searchService.searchItems(titleQuery, creatorQuery, null);

            System.out.println("Antal träffar hittade: " + (searchResults != null ? searchResults.size() : 0));

            ObservableList<Item> observableData = FXCollections.observableArrayList(searchResults);
            resultsTable.setItems(observableData);

        } catch (Exception e) {
            // Detta skriver ut det EXAKTA felet i terminalen (t.ex. om databasen är nere)
            System.out.println("--- KRASCH I handleSearch ---");
            e.printStackTrace(); 
        }
    }
}