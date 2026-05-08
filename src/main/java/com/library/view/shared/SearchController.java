package com.library.view.shared;

import java.util.List;

import com.library.config.AppContext;
import com.library.model.items.Item;
import com.library.service.SearchService;
import com.library.view.user.ReservationController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchController {

    @FXML
    private TextField searchField;
    @FXML
    private TextField creatorField;
    @FXML
    private ComboBox<String> typeDropdown;
    @FXML
    private TableView<Item> resultsTable;
    @FXML
    private TableColumn<Item, String> titleColumn;
    @FXML
    private TableColumn<Item, String> creatorColumn;
    @FXML
    private TableColumn<Item, String> statusColumn;
    @FXML
    private TableColumn<Item, Void> reserveColumn;

    private SearchService searchService;

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @FXML
    public void initialize() {
        // 1. Hämta servicen från AppContext
        this.searchService = AppContext.getInstance().searchService;

        // 2. Standardinställningar för tabellen
        resultsTable.setPlaceholder(new Label("Använd sökfältet för att hitta böcker eller filmer."));

        // 3. Fyll dropdown-menyn
        typeDropdown.setItems(FXCollections.observableArrayList("Alla", "Book", "Dvd", "Periodical"));
        typeDropdown.getSelectionModel().selectFirst();

        // 4. Mappa vanliga text-kolumner
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));

        // 5. Skapa knappen "Reservera" i den sista kolumnen
        reserveColumn.setCellFactory(col -> new TableCell<Item, Void>() {
            private final Button btn = new Button("Reservera");

            {
                btn.setOnAction(e -> {
                    Item item = getTableView().getItems().get(getIndex());
                    openReservationWindow(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Item currentItem = getTableView().getItems().get(getIndex());

                if ("Periodical".equalsIgnoreCase(currentItem.getItemType())) {
                    setGraphic(null);
                    return;
                }

                if ("Available".equalsIgnoreCase(currentItem.getStatus())) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

    }
    // Slut på initialize

    private void openReservationWindow(Item item) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/reservations_view.fxml"));

            Parent root = loader.load();

            // Skicka med objektet till controllern
            ReservationController controller = loader.getController();
            controller.setItem(item);

            Stage stage = new Stage();
            stage.setTitle("Reservera: " + item.getItemTitle());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            System.err.println("Kunde inte ladda reservations_view.fxml. Kontrollera filnamn och sökväg!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        try {
            String titleQuery = searchField.getText().trim();
            String creatorQuery = creatorField.getText().trim();
            String selectedType = typeDropdown.getValue();

            // Rensa söksträngar om de är tomma
            if (titleQuery.isEmpty())
                titleQuery = null;
            if (creatorQuery.isEmpty())
                creatorQuery = null;
            if ("Alla".equals(selectedType))
                selectedType = null;

            System.out.println("Söker efter: " + titleQuery + " | " + selectedType);

            // Anropa backend
            List<Item> searchResults = searchService.searchItems(titleQuery, creatorQuery, selectedType);

            // Uppdatera UI
            if (searchResults == null || searchResults.isEmpty()) {
                resultsTable.setPlaceholder(new Label("0 träffar hittade."));
                resultsTable.setItems(FXCollections.observableArrayList());
            } else {
                resultsTable.setItems(FXCollections.observableArrayList(searchResults));
            }

            resultsTable.refresh();

        } catch (Exception e) {
            System.err.println("Fel i handleSearch:");
            e.printStackTrace();
            resultsTable.setPlaceholder(new Label("Ett tekniskt fel uppstod."));
        }
    }
} // Slut på hela klassen