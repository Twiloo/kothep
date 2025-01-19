package fr.twiloo.iut.kothep;

import fr.twiloo.iut.kothep.common.model.api.request.LoginUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
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

            if (authenticateUser(user, pass)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
            }
        });
    }

    @FXML
    private void goToRegister() {
        try {
            Parent signupRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/signup.fxml")));
            Stage currentStage = (Stage) btn_register.getScene().getWindow();
            // Remplacer la scène actuelle par celle de "signup"
            currentStage.setScene(new Scene(signupRoot));
        } catch (IOException e) {
            System.err.println("Error loading signup.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean authenticateUser(String pseudo, String password) {


        LoginUser user = new LoginUser(pseudo, password);
        String apiUrl = "http://localhost:8080/user/login";

        try {
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"pseudo\": \"%s\", \"password\": \"%s\"}",
                    user.pseudo(), user.password()
            );

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

