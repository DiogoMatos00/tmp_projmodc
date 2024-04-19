package org.Client;

import javafx.application.Application;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String ip = null;
        String port = null;
        try{
            File configFile = new File("config.txt");
            if(configFile.createNewFile()){
                System.out.println("CREATED A NEW FILE");
                FileWriter myWriter = new FileWriter("config.txt");
                myWriter.write("ServerIP:127.0.0.1\nPort:6666");
                myWriter.close();
            }

            try {
                File myObj = new File("config.txt");
                Scanner myReader = new Scanner(myObj);
                String data = myReader.nextLine();
                String[] d = data.split(":");
                ip = d[1];
                data = myReader.nextLine();
                d = data.split(":");
                port = d[1];
                myReader.close();
                System.out.println(ip + " " + port);
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            Socket serverSocket = new Socket(ip, Integer.parseInt(port));
            GlobalVariables.out = new PrintWriter(serverSocket.getOutputStream(), true);
            GlobalVariables.in  = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        }catch(IOException e){
            throw new RuntimeException(e);
        };

        Application.launch(MainPage.class, args);
    }
}