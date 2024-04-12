import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class server {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        MainServer server = new MainServer();
        server.start(6666);


        //sleep(10000);
        //server.stop();
    }

    //TODO: MANDATORY:
    //      Create/Connect to the DB
    //      Send messages to the client side

    //TODO: NOT MANDATORY:
    //      A WAY TO CHECK IF A CONNECTION is closed.

}