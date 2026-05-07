package com.library.view.librarian;

import java.io.IOException;

import com.library.config.AppContext;

import com.library.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
            popupStage.setScene(App.createStyledScene(root));
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
            popupStage.setTitle("Add item");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(App.createStyledScene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateLoan(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/add_loan.fxml"));
            Parent root = loader.load();

            // Inject service from AppContext into the popup controller
            AddLoanController loanController = loader.getController();
            loanController.setLoanService(AppContext.getInstance().loanService);

            Stage popupStage = new Stage();
            popupStage.setTitle("Create Loan");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(App.createStyledScene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
