package com.library.view.shared;

import com.library.App;
import com.library.config.AppContext;
import com.library.model.auth.AuthRole;
import com.library.view.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private void handleStaffAccess() {
        openLoginForRole(AuthRole.EMPLOYEE, "Staff Login");
    }

    @FXML
    private void handleBorrowerAccess() {
        openLoginForRole(AuthRole.BORROWER, "Borrower Login");
    }

    private void openLoginForRole(AuthRole role, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setAuthService(AppContext.getInstance().authService);
            loginController.setRequiredRole(role);
            loginController.setLoginTitle(title);

            Stage popupStage = new Stage();
            popupStage.setTitle(title);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            if (AppContext.getInstance().authService.isLoggedIn()) {
                switch (AppContext.getInstance().authService.getCurrentRole()) {
                    case EMPLOYEE -> App.setRoot("librarian_dashboard");
                    case BORROWER -> App.setRoot("user_dashboard");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
