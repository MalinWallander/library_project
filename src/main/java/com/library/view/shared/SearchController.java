package com.library.view.shared;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
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
    @FXML private ComboBox<String> typeDropdown; // NY: Matchar fx:id i FXML
    @FXML private TableView<Item> resultsTable;
    @FXML private TableColumn<Item, String> titleColumn;
    @FXML private TableColumn<Item, String> creatorColumn;
    @FXML private TableColumn<Item, String> statusColumn;
    
    private SearchService searchService;

    @FXML
    public void initialize() {
        // Hämta servicen
        this.searchService = AppContext.getInstance().searchService;
        
        // Standard placeholder
        resultsTable.setPlaceholder(new Label("Använd sökfältet för att hitta böcker eller filmer."));

        // Initiera dropdown med alternativ från Item-klassen
        typeDropdown.setItems(FXCollections.observableArrayList("Alla", "Book", "Dvd"));
        typeDropdown.getSelectionModel().selectFirst(); // Väljer "Alla" som standard

        // Mappa kolumnerna
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
    }

    
@FXML
private void handleSearch() {
    try {
        // 1. Hämta text från sökfälten och rensa eventuella mellanslag runt om
        String titleQuery = searchField.getText().trim();
        String creatorQuery = creatorField.getText().trim();
        
        // 2. Hämta valt värde från din nya ComboBox (dropdown)
        String selectedType = typeDropdown.getValue();

        // 3. Logik: Om fälten är tomma, skicka null till servicen/SQL 
        // så att den inte försöker söka på en tom sträng ""
        if (titleQuery.isEmpty()) titleQuery = null;
        if (creatorQuery.isEmpty()) creatorQuery = null;
        
        // Om man valt "Alla" i dropdownen, skicka null så SQL inte filtrerar på typ
        if ("Alla".equals(selectedType)) {
            selectedType = null;
        }

        System.out.println("DEBUG: Startar sökning...");
        System.out.println("Söker efter: Titel=" + titleQuery + ", Skapare=" + creatorQuery + ", Typ=" + selectedType);

        // 4. Anropa din service
        // (Vi skickar med selectedType som tredje parameter)
        List<Item> searchResults = searchService.searchItems(
            titleQuery, 
            creatorQuery, 
            selectedType
        );

        // 5. Kontrollera resultatet och uppdatera placeholder om det blev 0 träffar
        if (searchResults == null || searchResults.isEmpty()) {
            System.out.println("Antal träffar hittade: 0");
            resultsTable.setPlaceholder(new Label("0 träffar hittade."));
        } else {
            System.out.println("Antal träffar hittade: " + searchResults.size());
        }

        // 6. Konvertera listan till en ObservableList och uppdatera tabellen
        ObservableList<Item> observableData = FXCollections.observableArrayList(searchResults);
        resultsTable.setItems(observableData);
        
        // Tvinga tabellen att rita om sig för att vara säker på att data syns direkt
        resultsTable.refresh();

    } catch (Exception e) {
        // Om något går fel (t.ex. med databasen), skriv ut det här
        System.out.println("--- FEL I handleSearch ---");
        e.printStackTrace();
        resultsTable.setPlaceholder(new Label("Ett fel uppstod vid sökningen. Kontrollera anslutningen."));
    }
}
}