package org.Client;

import javafx.application.Application;

import java.net.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        try{
            Socket serverSocket = new Socket("127.0.0.1", 6666);
            GlobalVariables.out = new PrintWriter(serverSocket.getOutputStream(), true);
            GlobalVariables.in  = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        }catch(IOException e){
            throw new RuntimeException(e);
        };

        javafx.application.Application.launch(MainPage.class, args);
    }
}