package com.library.view.login;

import com.library.model.auth.AuthRole;
import com.library.model.auth.LoginResult;
import com.library.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label subtitleLabel;

    private AuthService authService;
    private AuthRole requiredRole;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    // TODO: Never used
    public void setRequiredRole(AuthRole requiredRole) {
        this.requiredRole = requiredRole;
        updateRoleCopy();
    }

    public void setLoginTitle(String loginTitle) {
        titleLabel.setText(loginTitle);
    }

    private void updateRoleCopy() {
        if (subtitleLabel == null || requiredRole == null) {
            return;
        }

        if (requiredRole == AuthRole.EMPLOYEE) {
            subtitleLabel.setText("Enter your staff account details to continue.");
        } else {
            subtitleLabel.setText("Enter your borrower account details to continue.");
        }
    }

    @FXML
    private void handleLogin() {
        try {
            LoginResult result = authService.login(emailField.getText(), passwordField.getText());
            if (result.isSuccess()) {
                if (requiredRole != null && result.getSession().getRole() != requiredRole) {
                    authService.logout();
                    messageLabel.setText(requiredRole == AuthRole.EMPLOYEE
                            ? "This account does not have staff access."
                            : "This account does not have borrower access.");
                    return;
                }

                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.close();
            } else {
                messageLabel.setText(result.getMessage());
            }
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
        }
    }
}
