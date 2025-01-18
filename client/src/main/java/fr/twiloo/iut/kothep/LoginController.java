package fr.twiloo.iut.kothep;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_register;

    @FXML
    private void initialize() {
        btn_login.setOnAction(event -> {
            String user = username.getText();
            String pass = password.getText();
            System.out.println("Username: " + user + ", Password: " + pass);
        });

        btn_register.setOnAction(event -> {
            try {
                Parent signupRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/signup.fxml")));
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Remplacer la scène actuelle par celle de "signup"
                currentStage.setScene(new Scene(signupRoot));
            } catch (IOException e) {
                System.err.println("Error loading signup.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
