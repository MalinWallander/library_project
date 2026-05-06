package com.library;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("first_page"), 900, 650);
        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/library/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        root.getStylesheets().add(App.class.getResource("/com/library/styles/" + fxml + ".css").toExternalForm());
        return root;
    }

    public static void main(String[] args) {
        launch();
    }
}
