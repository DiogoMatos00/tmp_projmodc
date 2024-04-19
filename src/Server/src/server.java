import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class server {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        MainServer server = new MainServer();
        server.start(6666);
    }
}