import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Observable;


public class User extends Thread {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MainServer server;
    private Connection conn;
    private Statement statement;
    private String username;

    private boolean newMessage;


    private boolean disconnect = false;

    public User(Socket socket, MainServer server, Connection conn) throws SQLException {
        this.clientSocket = socket;
        this.server = server;
        this.conn = conn;
        this.statement = conn.createStatement();

    }

    public void run() {
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = this.in.readLine()) != null) {
                if (message.equals("$d")) {
                    System.out.println("User disconnected");
                    disconnect();
                    break;
                }

                ArrayList<String> b = new ArrayList<>(Arrays.asList(message.trim().split("\"")));

                for(int i = 0; i<b.size(); i++){
                    b.remove(i);
                }

                if (b.get(0).equals("$register")) {
                    String username = b.get(1);
                    String password = b.get(2);

                    if(statement.executeQuery(String.format("SELECT Name FROM Users WHERE Name = '%s'", username)).next()){
                        // System.out.println("Exist");
                        this.out.println("Utilizador já existente!");
                    } else{
                        statement.executeUpdate(String.format("INSERT INTO Users (Name, Password) " +
                                "VALUES ('%s', '%s')", username, password));

                        System.out.println("User successfully registered!");
                        this.out.println("success");
                    }
                } else if (b.get(0).equals("$login")) {
                    String username = b.get(1);
                    String password = b.get(2);

                    //System.out.println(String.format("SELECT Name FROM Users WHERE Name = '%s' AND Password = '%s'", username, password));

                    if(statement.executeQuery(String.format("SELECT Name FROM Users WHERE Name = '%s' AND Password = '%s'", username, password)).next()) {
                        this.username = username;
                        System.out.println("Logged in!");
                        this.out.println("success");
                        connect();
                    } else {
                        System.out.println("User e password não estão corretas!");
                        this.out.println("User e password não estão corretas!");
                    }

                    if (disconnect) {
                        disconnect();
                        break;
                    }
                }
            }
        } catch (IOException | SQLException e) {
            this.out.println("$error " + e);
            System.out.println(e);
            this.run();
        }
    }

    private void disconnect() throws IOException {
        this.in.close();
        this.out.close();
        this.clientSocket.close();
        server.disconnectUser(this);

    }

    private void connect() throws IOException, SQLException {
        String userReceiver = null;
        String message;
        while ((message = this.in.readLine()) != null) {

            if (message.equals("$d")) {
                System.out.println("User disconnected");
                disconnect = true;
                break;
            } else if (message.equals("$b")) {
                this.username = null;
                break;
            }

            ArrayList<String> b = new ArrayList<>(Arrays.asList(message.trim().split("\"")));

            for(int i = 0; i<b.size(); i++){
                b.remove(i);
            }

            System.out.println(" ");


            if (b.get(0).equals("$lu")){
                ResultSet resultSet = statement.executeQuery("SELECT Name FROM Users");
                ArrayList<String> luser = new ArrayList<String>();
                while(resultSet.next()){
                    luser.add(resultSet.getString("Name"));
                }

                String send = "";
                for(String name:luser){
                    send += "\"" + name + "\" ";
                }

                this.out.println(send);
            } else if (b.get(0).equals("$send")) {
                User userA = null;
                for(User user : server.getConnections()){
                    if(user.getUsername().equals(b.get(1))) {
                        user.setNewMessage(true);
                        System.out.println("Está Online!");
                        userA = user;
                        break;
                    }
                }

                String receiver = b.get(1);
                String content = b.get(2);

                statement.executeUpdate(String.format("INSERT INTO Messages (NameUser, Content, Receiver) " +
                        "VALUES ('%s', '%s', '%s')", username, content, receiver));

                //System.out.println("To: " + receiver);
                //System.out.println("msg: " + content);

                //userA.out.println("$newMsg " + username + b.get(2));
            } else if (b.get(0).equals("$get")) {
                this.setNewMessage(false);

                String a = "";
                ResultSet resultSet = statement.executeQuery(String.format("SELECT NameUser, Receiver, Content FROM Messages WHERE NameUser = '%s' or Receiver = '%s'", username, username));
                while(resultSet.next()) {
                    if (resultSet.getString("NameUser").equals(b.get(1)) || resultSet.getString("Receiver").equals(b.get(1))) {
                        a += String.format("\"%s\" \"%s\" \"%s\"", resultSet.getString("NameUser"), resultSet.getString("Receiver"), resultSet.getString("Content"));
                    }
                }

                //System.out.println(a);
                this.out.println(a);
            } else if (b.get(0).equals("$getMyName")) {
                this.out.println(username);
            }
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getUsername(){
        return this.username;
    }

    public void setNewMessage(boolean set){
        this.newMessage = set;
    }
}