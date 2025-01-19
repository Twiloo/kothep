package fr.twiloo.iut.kothep;

import fr.twiloo.iut.kothep.common.model.api.request.RegisterUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class SignupController {

    @FXML
    public Button btn_backtologin;

    @FXML
    public Button btn_signup;

    @FXML
    public PasswordField password;

    @FXML
    public TextField email;

    @FXML
    public TextField username;

    @FXML
    public Label errorMessage;

    @FXML
    public Label outMessage;

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

    @FXML
    private void signup() {
        String email = this.email.getText();
        String pseudo = this.username.getText();
        String password = this.password.getText();

        if (email.isEmpty() || pseudo.isEmpty() || password.isEmpty()) {
            errorMessage.setText("All fields must be filled!");
            return;
        }

        RegisterUser user = new RegisterUser(email, pseudo, password);
        String apiUrl = "http://localhost:8080/user/register";

        try {
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"email\": \"%s\", \"pseudo\": \"%s\", \"password\": \"%s\"}",
                    user.email(), user.pseudo(), user.password()
            );

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("User registered successfully");
                errorMessage.setText("");
                outMessage.setText("User registered successfully. Please return to the login page.");

            } else {
                errorMessage.setText("Registration failed! Please try again.");
            }

        } catch (IOException e) {
            errorMessage.setText("Error connecting to the server.");
            throw new RuntimeException(e);
        }
    }
}
