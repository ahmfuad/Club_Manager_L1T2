import com.javafx.clubclient.BidRequest;
import com.javafx.clubclient.Credentials;
import com.javafx.clubclient.Message;
import com.javafx.clubclient.Player;
import com.javafx.clubclient.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ReadThreadServer implements Runnable {
    private Thread thread;
    private SocketWrapper socketWrapper;
    private ConcurrentHashMap<Credentials, SocketWrapper> clientMap;
    private Database database;

    public ReadThreadServer(ConcurrentHashMap<Credentials, SocketWrapper> clientMap, SocketWrapper socketWrapper) {
        this.clientMap = clientMap;
        this.socketWrapper = socketWrapper;
        this.database = Database.getInstance(); // Using the singleton instance
        this.thread = new Thread(this);
        thread.start();
    }

    public ReadThreadServer(ConcurrentHashMap<Credentials, SocketWrapper> clientMap, SocketWrapper socketWrapper, Database database) {
        this.clientMap = clientMap;
        this.socketWrapper = socketWrapper;
        this.database = database; // Using the singleton instance
        this.thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();

                if (o instanceof Message) {
                    handleClientMessage((Message) o);
                } else if (o instanceof Credentials) {
                    handleClientLogin((Credentials) o);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in ReadThreadServer: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void handleClientMessage(Message message) throws IOException {
        String type = message.getType();

        switch (type) {
            case "logout":
                handleLogout(message);
                break;
            case "getAllPlayers":
                handleGetAllPlayers(message);
                break;
            case "makePlayerOnSale":
                handleMakePlayerOnSale(message);
                break;
            case "allPlayersOnSale":
                handleAllPlayersOnSale();
                break;
            case "placeBid":
                handlePlaceBid(message);
                break;
            case "giveAllBids":
                handleGiveAllBids();
                break;
            case "approveBid":
                handleApproveBids(message);
                break;
            default:
                System.out.println("Unknown message type: " + type);
                break;
        }
    }

    private void handleClientLogin(Credentials credentials) throws IOException {
        String ok = "200 OK";
        String notOk = "401 Unauthorized";



        //synchronized (clientMap) {
            if (!clientMap.containsKey(credentials)) {
                clientMap.put(credentials, socketWrapper);
                socketWrapper.write(ok);
            } else {
                socketWrapper.write(notOk);
            }
        //}
    }

    private void handleLogout(Message message) throws IOException {

        System.out.println("IN LOGOUT");
        Credentials clientProfile = (Credentials) message.getClientProfile();
        //synchronized (clientMap) {
            if (clientMap.containsKey(clientProfile)) {
                clientMap.remove(clientProfile);
                socketWrapper.write("200 OK");
            } else {
                System.out.println("Its not there");
                socketWrapper.write("402 ERROR");
            }
       // }
    }

    private void handleGetAllPlayers(Message message) throws IOException {
        List<Player> players = database.searchByClubAndCountry(
                message.getClientProfile().getUsername(), "ANY"
        );
        players.removeIf(player -> player.isInMarketPlace());
        socketWrapper.write(new Message(players, null, "playerList"));
    }

    private void handleMakePlayerOnSale(Message message) {
        Player player = (Player) message.getObj();
        database.changePlayerInMarketplace(player);
    }

    private void handleAllPlayersOnSale() throws IOException {
        List<Player> playersOnSale = database.searchByMarketplace();
        socketWrapper.write(new Message(playersOnSale, null, "onSalePlayersList"));
    }

    private void handlePlaceBid(Message message) {
        BidRequest bid = (BidRequest) message.getObj();
        database.addBid(bid);
    }

    private void handleGiveAllBids() throws IOException {
        List<BidRequest> allBids = database.getAllBids();
        socketWrapper.write(new Message(allBids, "takeAllBids"));
    }

    private void handleApproveBids(Message message) {
        BidRequest bidRequest = (BidRequest) message.getObj();
        database.transferClub(bidRequest.getPlayer(), bidRequest.getClubName());
        database.removeBid(bidRequest);
    }

    private void closeConnection() {
        try {
            socketWrapper.closeConnection();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
