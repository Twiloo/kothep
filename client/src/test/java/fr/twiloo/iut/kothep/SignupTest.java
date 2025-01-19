package fr.twiloo.iut.kothep;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SignupTest {
    @Test
    void testSignupRequest() {
        // Test data
        String email = "test@example.com";
        String pseudo = "testuser";
        String password = "Test$123456";

        // Create the JSON payload expected by the API
        String jsonInput = String.format(
                "{\"email\": \"%s\", \"pseudo\": \"%s\", \"password\": \"%s\"}",
                email, pseudo, password
        );

        System.out.println("JSON sent: " + jsonInput);

        try {
            // Send the request and retrieve the HTTP response code
            int responseCode = sendSignupRequest(jsonInput);

            // Check if the response code is 200 (OK)
            assertEquals(HttpURLConnection.HTTP_OK, responseCode, "The response code should be 200");

        } catch (IOException e) {
            // Fail the test if an IOException occurs
            fail("An IOException occurred: " + e.getMessage());
        }
    }

    /**
     * Sends a signup request to the API and returns the HTTP response code.
     *
     * @param jsonInput The JSON payload to send in the request body
     * @return The HTTP response code from the server
     * @throws IOException If an I/O error occurs
     */
    private int sendSignupRequest(String jsonInput) throws IOException {
        // API URL
        String apiUrl = "http://localhost:8080/user/register";
        URI uri = URI.create(apiUrl);
        URL url = uri.toURL();

        // Open an HTTP connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Return the HTTP response code
        return connection.getResponseCode();
    }
}
