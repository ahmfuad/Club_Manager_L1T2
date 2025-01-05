import com.javafx.clubclient.Credentials;
import com.javafx.clubclient.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private ServerSocket serverSocket;
    private ConcurrentHashMap<Credentials, SocketWrapper> clientMap;
    private Database database;

    public Server(Database database) {
        this.database = database;
        clientMap = new ConcurrentHashMap<>();
        try {
            serverSocket = new ServerSocket(33333);
            System.out.println("Server Started.....");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Client Accepted");
                serve(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private boolean checkLogin(Credentials credentials, SocketWrapper socketWrapper) throws IOException {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        // Assuming a ClubDatabase class exists to validate credentials
        ClubDatabase clubDatabase = new ClubDatabase();
        if (clubDatabase.checkCredentials(username, password)) {
            return !clientMap.containsKey(credentials);
        }
        return false;
    }

    private void serve(Socket clientSocket) throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        System.out.println("Serving client...");
        try {
            Credentials clientProfile = (Credentials) socketWrapper.read();
            String ok = "200 OK";
            String notOk = "401 Unauthorized";

            if (checkLogin(clientProfile, socketWrapper)) {
                clientMap.put(clientProfile, socketWrapper);
                socketWrapper.write(ok);
                new ReadThreadServer(clientMap, socketWrapper, database);
            } else {
                socketWrapper.write(notOk);
            }
        } catch (Exception e) {
            System.out.println("Error serving client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Database database = Database.getInstance(); // Use singleton instance
        new Server(database);
    }
}
