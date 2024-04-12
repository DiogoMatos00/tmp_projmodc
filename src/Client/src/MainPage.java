package org.Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainPage extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Título da página
        Label titleLabel = new Label("Message Sharing");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Botões
        Button registerButton = new Button("Registar");
        Button loginButton = new Button("Login");
        Button disconnectButton = new Button("Desconectar");

        // Ação do botão Registrar
        registerButton.setOnAction(event -> {
            try {
                openRegisterPage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Ação do botão Login
        loginButton.setOnAction(event -> openLoginPage());

        // Ação do botão Desconectar
        disconnectButton.setOnAction(event -> disconnect());

        // Layout dos elementos
        VBox root = new VBox(20, titleLabel, registerButton, loginButton, disconnectButton);
        root.setPadding(new Insets(20));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                GlobalVariables.out.println("$d");
                primaryStage.close();
            }
        });

        // Cena
        Scene scene = new Scene(root, 300, 200);

        // Configurações do palco
        primaryStage.setScene(scene);
        primaryStage.setTitle("Message Sharing - Página Inicial");
        primaryStage.show();
    }

    private void openLoginPage() {
        Login login = new Login();
        login.start(new Stage());
        primaryStage.close();
    }

    private void openRegisterPage() throws IOException {
        RegisterUser registerUser = new RegisterUser();
        registerUser.start(new Stage());
        primaryStage.close();
    }

    private void disconnect() {
        GlobalVariables.out.println("$d");
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
