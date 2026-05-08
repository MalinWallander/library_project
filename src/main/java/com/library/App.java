package com.library;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private static String globalCss;

    @Override
    public void start(Stage stage) throws IOException {
        globalCss = App.class.getResource("/com/library/styles/main.css").toExternalForm();
        scene = new Scene(loadFXML("reservations_view"), 900, 650);
        scene.getStylesheets().add(globalCss);
        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        try {
            Parent root = loadFXML(fxml);
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Use this for all popup stages
    public static Scene createStyledScene(Parent root) {
        Scene s = new Scene(root);
        s.getStylesheets().add(globalCss);
        return s;
    }

    public static Scene createStyledScene(Parent root, double width, double height) {
        Scene s = new Scene(root, width, height);
        s.getStylesheets().add(globalCss);
        return s;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/com/library/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}