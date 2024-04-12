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

public class RegisterUser extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI elements
        Label titleLabel = new Label("Register User");
        titleLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Registar");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            GlobalVariables.out.println(String.format("\"$register\" \"%s\" \"%s\"", username, password));

            String success;

            try {
                success = GlobalVariables.in.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if (success.matches("success")) {
                System.out.println("User registered successfully!");
                // Close the registration stage
                primaryStage.close();
                // Open the login stage
                openLoginPage();
            } else {
                showErrorAlert(success);
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                primaryStage.close();
                openMainPage();
            }
        });

        Button disconnectButton = new Button("Go Back");
        disconnectButton.setOnAction(e -> {
            // Close the program
            primaryStage.close();
            openMainPage();

        });

        Button backButton = new Button("Menu principal");
        backButton.setOnAction(e -> {
            // Close the registration stage
            primaryStage.close();
            // Open the main page
            openMainPage();
        });

        // Layout UI elements
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, titleLabel);
        gridPane.addRow(1, usernameLabel, usernameField);
        gridPane.addRow(2, passwordLabel, passwordField);
        gridPane.addRow(3, registerButton);
        gridPane.addRow(4, backButton);
        gridPane.addRow(5, disconnectButton);
        gridPane.setPadding(new Insets(20));

        // Set up the scene
        Scene scene = new Scene(gridPane, 300, 250);

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register User");
        primaryStage.show();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registration Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openLoginPage() {
        // Create a new stage for the login page
        Stage loginStage = new Stage();
        // Create the login page and start it
        Login loginPage = new Login();
        loginPage.start(loginStage);
    }

    private void openMainPage() {
        // Create a new stage for the main page
        Stage mainStage = new Stage();
        // Create the main page and start it
        MainPage mainPage = new MainPage();
        mainPage.start(mainStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

