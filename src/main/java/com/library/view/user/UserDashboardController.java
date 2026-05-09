package com.library.view.user;

import com.library.App;
import com.library.config.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        if (AppContext.getInstance().authService.getCurrentSession() != null) {
            welcomeLabel.setText("Logged in as: " + AppContext.getInstance().authService.getCurrentSession().getEmail());
        }
    }

    @FXML
    private void handleLogout() {
        AppContext.getInstance().authService.logout();
        App.setRoot("first_page");
    }
}
