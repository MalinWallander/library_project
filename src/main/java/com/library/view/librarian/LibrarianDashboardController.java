package com.library.view.librarian;

import java.io.IOException;
import com.library.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.library.config.AppContext;

public class LibrarianDashboardController {

    @FXML
    private void handleCreateUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/create_user.fxml"));
            Parent root = loader.load();

            CreateUserController userController = loader.getController();
            userController.setUserService(AppContext.getInstance().userService);

            Stage popupStage = new Stage();
            popupStage.setTitle("Create Borrower");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateEmployee(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/create_employee.fxml"));
            Parent root = loader.load();

            CreateEmployeeController employeeController = loader.getController();
            employeeController.setEmployeeService(AppContext.getInstance().employeeService);

            Stage popupStage = new Stage();
            popupStage.setTitle("Create Employee");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddItem(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/add_item.fxml"));
            Parent root = loader.load();

            AddItemController itemController = loader.getController();
            itemController.setItemService(AppContext.getInstance().itemService);

            Stage popupStage = new Stage();
            popupStage.setTitle("Add item");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        AppContext.getInstance().authService.logout();
        App.setRoot("main_view");
    }
}
