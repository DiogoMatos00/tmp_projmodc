package org.Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login extends Application {
    private Map<String, String> users;

    @Override
    public void start(Stage primaryStage) {
        users = new HashMap<>();
        // Populate users (you can fetch from a database in real scenario)
        users.put("user1", "password1");
        users.put("user2", "password2");

        // Create UI elements
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            GlobalVariables.out.println(String.format("\"$login\" \"%s\" \"%s\"", username, password));

            String success;

            try {
                success = GlobalVariables.in.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (success.matches("success")) {
                // Login successful, switch to chat page
                ChatPage chatPage = new ChatPage();
                try {
                    chatPage.start(primaryStage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                showErrorAlert(success);
            }
        });

        Button backButton = new Button("Menu principal");
        backButton.setOnAction(e -> {
            // Close the registration stage
            primaryStage.close();
            // Open the main page
            openMainPage();
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                primaryStage.close();
                openMainPage();
            }
        });

        // Layout UI elements
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, titleLabel);
        gridPane.addRow(1, usernameLabel, usernameField);
        gridPane.addRow(2, passwordLabel, passwordField);
        gridPane.addRow(3, loginButton);
        gridPane.addRow(4, backButton);

        VBox root = new VBox(20, gridPane);
        root.setPadding(new Insets(20));

        // Set up the scene and show the stage
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private boolean login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false; // Invalid username or password
        }
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openMainPage() {
        // Create a new stage for the main page
        Stage mainStage = new Stage();
        // Create the main page and start it
        MainPage mainPage = new MainPage();
        mainPage.start(mainStage);
    }

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}
