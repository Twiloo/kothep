package fr.twiloo.iut.kothep;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class SignupController {

    @FXML
    public Button btn_backtologin;

    @FXML
    private void initialize() {
        btn_backtologin.setOnAction(event -> {
            try {
                Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/login.fxml")));
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Remplacer la scène actuelle par celle de "login"
                currentStage.setScene(new Scene(loginRoot));
            } catch (IOException e) {
                System.err.println("Error loading signup.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
