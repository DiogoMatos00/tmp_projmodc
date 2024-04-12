package org.Client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Messaging extends Application {
    private String username;

    public Messaging() {
    }

    public Messaging(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        VBox chatBox = new VBox();

        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);

        TextField messageField = new TextField();
        messageField.setPromptText("Type your message here...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                // Append the message to the chatArea
                chatArea.appendText(username + ": " + message + "\n");
                // Clear the messageField
                messageField.clear();
            }
        });

        VBox inputBox = new VBox(10, messageField, sendButton);
        root.setCenter(chatArea);
        root.setBottom(inputBox);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle(username + "'s Messaging Interface");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}
