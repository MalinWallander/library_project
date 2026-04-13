package com.library.view.shared;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

// Dessa två imports är viktigast för att få bort det röda:
import com.library.model.items.Item;
import com.library.service.SearchService;

public class SearchController {

    // Dessa ID:n måste matcha fx:id i din FXML-fil
    @FXML private TextField searchField;
    @FXML private TableView<Item> resultsTable;
    @FXML private TextField creatorField;   // Sökfält för skapare
    @FXML private ComboBox<String> categoryDropdown; // Dropdown för kategori
    
    private SearchService searchService;

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Körs automatiskt när FXML-filen laddas.
     * Här kan man ställa in t.ex. vad som ska hända om tabellen är tom.
     */
    @FXML
    public void initialize() {
        resultsTable.setPlaceholder(new javafx.scene.control.Label("Använd sökfältet för att hitta böcker eller filmer."));
    }

    /**
     * Kopplad till onAction="#handleSearch" i FXML-filen.
     */
  @FXML
private void handleSearch() {
    String titleQuery = searchField.getText().trim();
    String creatorQuery = creatorField.getText().trim();
    
    // Hämta vald kategori (om ingen är vald skickar vi null)
    String selectedCategory = categoryDropdown.getValue();

    // Nu skickar vi ALLA tre parametrar till servicen
    List<Item> searchResults = searchService.searchItems(titleQuery, creatorQuery, selectedCategory);

    ObservableList<Item> observableData = FXCollections.observableArrayList(searchResults);
    resultsTable.setItems(observableData);
        
        System.out.println("Sökning utförd på: " + titleQuery +". Hittade " + searchResults.size() + " träffar.");
    }
}



