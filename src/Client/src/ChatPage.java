package org.Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChatPage extends Application {

    private Map<String, StringBuilder> userMessages;
    private ListView<String> userList;
    private String myName;

    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane root = new BorderPane();
        userMessages = new HashMap<>();

        GlobalVariables.out.println("\"$getMyName\"");
        myName = GlobalVariables.in.readLine();

        // Text area to display received messages
        TextArea receivedMessagesTextArea = new TextArea();
        receivedMessagesTextArea.setEditable(false);

        // Text field to enter new message
        TextField messageTextField = new TextField();
        messageTextField.setPromptText("Type your message here...");
        messageTextField.setEditable(false);

        // Button to send message
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String message = messageTextField.getText();
            if (!message.isEmpty()) {
                String selectedUser = userList.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    System.out.println(selectedUser);

                    GlobalVariables.out.println(String.format("\"$send\" \"%s\" \"%s\"", selectedUser, message));
                    receivedMessagesTextArea.appendText("You: " + message + "\n");
                }
            }
        });

        // Button to refresh user list
        Button refreshUserListButton = new Button("Refresh User List");
        refreshUserListButton.setOnAction(e -> {
            // Clear the existing user list
            this.userList.getItems().clear();
            try {
                GlobalVariables.out.println("\"$lu\"");
                String users = GlobalVariables.in.readLine();
                ArrayList<String> d = new ArrayList<>(Arrays.asList(users.trim().split("\"")));

                for(int i = 0; i<d.size(); i++){
                    d.remove(i);
                }

                for(String user: d){
                    if(!user.equals(this.myName)) {
                        userList.getItems().add(user);
                    }
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Button to refresh messages
        Button refreshMessagesButton = new Button("Refresh Messages");
        refreshMessagesButton.setOnAction(e -> {
            String selectedUser = userList.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                GlobalVariables.out.println(String.format("\"$get\", \"%s\"", selectedUser));
                try {
                    receivedMessagesTextArea.clear();
                    String allmsgs = GlobalVariables.in.readLine();
                    ArrayList<String> b = new ArrayList<>(Arrays.asList(allmsgs.trim().split("\"")));

                    for(int i = 0; i<b.size(); i++){
                        b.remove(i);
                    }

                    System.out.println(b.toString());

                    for(int i = 0; i<b.size()-2; i=i+3) {

                        StringBuilder userMessage = new StringBuilder();
                        if (myName.equals(b.get(i))) {
                            receivedMessagesTextArea.appendText("You: " + b.get(i + 2) + "\n");
                        } else {
                            receivedMessagesTextArea.appendText(b.get(i) + ": " + b.get(i + 2) + "\n");
                        }

                        userMessages.put(b.get(i), userMessage);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        userList = new ListView<>();

        GlobalVariables.out.println("\"$lu\"");
        String users = GlobalVariables.in.readLine();
        ArrayList<String> d = new ArrayList<>(Arrays.asList(users.trim().split("\"")));

        for(int i = 0; i<d.size(); i++){
            d.remove(i);
        }

        for(String user: d){
            if(!user.equals(this.myName)) {
                userList.getItems().add(user);
            }
        }

        userList.getItems().addAll(); // Example users
        userList.setPrefHeight(200);
        userList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                messageTextField.setEditable(true);
                receivedMessagesTextArea.clear();
                GlobalVariables.out.println(String.format("\"$get\", \"%s\"", newValue));

                try {
                    String allmsgs = GlobalVariables.in.readLine();
                    ArrayList<String> b = new ArrayList<>(Arrays.asList(allmsgs.trim().split("\"")));

                    for(int i = 0; i<b.size(); i++){
                        b.remove(i);
                    }

                    for(int i = 0; i<b.size()-2; i=i+3) {

                        StringBuilder userMessage = new StringBuilder();
                        if (myName.equals(b.get(i))) {
                            receivedMessagesTextArea.appendText("You: " + b.get(i + 2) + "\n");
                        } else {
                            receivedMessagesTextArea.appendText(b.get(i) + ": " + b.get(i + 2) + "\n");
                        }

                        userMessages.put(b.get(i), userMessage);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Button to logout
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            GlobalVariables.out.println("$b");
            primaryStage.close();
            openMainPage();
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                GlobalVariables.out.println("$b");
                primaryStage.close();
                openMainPage();
            }
        });

        // HBox to hold refresh buttons
        HBox refreshBox = new HBox(refreshUserListButton, refreshMessagesButton);
        refreshBox.setPadding(new Insets(10));

        // HBox to hold logoutButton
        HBox logoutBox = new HBox(logoutButton);
        logoutBox.setPadding(new Insets(10));

        // VBox to hold userList, refreshBox, and logoutBox
        VBox sidePanel = new VBox(userList, refreshBox, logoutBox);
        sidePanel.setPadding(new Insets(10));
        sidePanel.setSpacing(10);

        // VBox to hold messageTextField, sendButton, and sidePanel
        VBox inputBox = new VBox(10, messageTextField, sendButton);
        inputBox.setPadding(new Insets(10));

        root.setCenter(receivedMessagesTextArea);
        root.setBottom(inputBox);
        root.setRight(sidePanel);

        Scene scene = new Scene(root, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void openMainPage() {
        // Create a new stage for the main page
        Stage mainStage = new Stage();
        // Create the main page and start it
        MainPage mainPage = new MainPage();
        mainPage.start(mainStage);
    }
}