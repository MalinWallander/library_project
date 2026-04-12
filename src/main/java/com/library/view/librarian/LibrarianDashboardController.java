package com.library.view.librarian;

import java.io.IOException;
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

			// Inject service from AppContext into the popup controller
			CreateUserController controller = loader.getController();
			controller.setUserService(AppContext.getInstance().userService);

			Stage popupStage = new Stage();
			popupStage.setTitle("Create User");
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setScene(new Scene(root));
			popupStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
