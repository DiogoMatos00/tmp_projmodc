import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.sql.*;

public class MainServer {
    private ServerSocket serverSocket;
    private ArrayList<User> connections;
    private Connection conn;

    public void start(int port) throws IOException, SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager
                .getConnection("jdbc:mysql://localhost/modc_project?"
                        + "user=root&password=admin");

        connections = new ArrayList<>();
        serverSocket = new ServerSocket(port);
        acceptUsers();
    }

    public void acceptUsers() throws IOException, SQLException {
        while(true){
            User user = new User(serverSocket.accept(), this, conn);
            connections.add(user);
            user.start();
            System.out.println("New user connected!");
        }
    }

    public void stopServer() throws IOException {
        serverSocket.close();
    }

    public void disconnectUser(User user) throws IOException {
        user.getClientSocket().close();
        connections.remove(user);
        System.out.println(connections.size());
    }

    public ArrayList<User> getConnections(){
        return connections;
    }
}
