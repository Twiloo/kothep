package fr.twiloo.iut.kothep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Signup extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/signup.fxml")));
            primaryStage.setTitle("Kothep");
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/pizza.png"))));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
