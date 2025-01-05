package com.javafx.clubclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarketplaceController {

    @FXML
    private Label clubName;

    private Stage stage;
    private Scene scene;
    private Credentials clientProfile;
    private SocketWrapper socketWrapper;
    private String clubNameText;

    @FXML
    private TableColumn<Player, String> playerName;

    @FXML
    private TableColumn<Player, Void> viewButton;

    @FXML
    private TableColumn<Player, Void> bidButton;

    @FXML
    private TableView<Player> playerTable;

    // Modified constructor to store the clubName parameter in a field
    public MarketplaceController(Credentials user, SocketWrapper socketWrapper, String clubName) {
        this.clientProfile = user;
        this.socketWrapper = socketWrapper;
        this.clubNameText = clubName; // Store clubName for later use
    }

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        // Set the club name after FXML fields are initialized
        if (clubName != null && clubNameText != null) {
            clubName.setText(clubNameText);
            clubName.setVisible(true);
        }


        socketWrapper.write(new Message("giveItAll", "allPlayersOnSale"));
        Message nM = (Message) socketWrapper.read();
        List<Player> allPlayersOnSale = (List<Player>) nM.getObj();
        for(Player x: allPlayersOnSale) {
            System.out.println(x);
        }
        //Message nm2 = (Message) socketWrapper.read();
        //List<Boolean> pp = (List<Boolean>) nm2.getObj();

        //System.out.println(pp);

        playerName.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        //viewButton.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
        //socketWrapper.write(new Message(null, clientProfile, "getAllPlayers"));
        //Message newMessage = (Message) socketWrapper.read();
        //System.out.println("READ_all_player");
        //if(newMessage.getType().equals("playerList")) System.out.println("List Received but not retrieved");
        //List<Player> allPlayer = (List<Player>) newMessage.getObj();

//        List<PlayerWithButton> dataOfPlayers = new ArrayList<>();
//        for(Player x: allPlayer) {
//            //dataOfPlayers.add(new PlayerWithButton(x));
//            if(x.isInMarketPlace()) {
//                System.out.println(x);
//            }
//        }
        viewButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button("View");
                    private final HBox hbox = new HBox(btn);  // Create an HBox to hold the button

                    {
                        btn.setOnAction(e -> {
//                            Player player = getTableView().getItems().get(getIndex());
//                            Alert a = new Alert(Alert.AlertType.INFORMATION);
//                            a.setTitle("Player Info");
//                            a.setHeaderText("Player Info");
//                            a.setContentText(String.valueOf(player));
//                            a.showAndWait();
                            Player player = getTableView().getItems().get(getIndex());
                            showPlayerInfoModal(player, clubName.getText());
                        });

                        // Center align the button within the HBox
                        hbox.setAlignment(Pos.CENTER);  // Align the button to the center of the cell
                        hbox.setStyle("-fx-padding: 5px;");  // Optional: Adds some padding around the button
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);  // Remove button if the row is empty
                        } else {
                            setGraphic(hbox);  // Set the HBox with the centered button
                        }
                    }
                };
            }
        });

        bidButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button();
                    private final HBox hbox = new HBox(btn);

                    {
                        // Center align the button within the HBox
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setStyle("-fx-padding: 5px;");
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null); // Remove button if the row is empty
                        } else {
                            Player player = getTableView().getItems().get(getIndex());
                            System.out.println(player.getClub()+ " " + clubName.getText());
                            if (player.getClub().equals(clubName.getText())) {
                                btn.setText("Cancel Sell");
                                btn.setOnAction(e -> {
                                    try {
                                        handleSellButtonClick(player); // Handle sell button click
                                    } catch (IOException | ClassNotFoundException ex) {
                                        ex.printStackTrace();
                                        throw new RuntimeException(ex);
                                    }
                                });
                                //btn.setDisable(true); // Disable button if the player is already in the marketplace
                            } else {
                                //Player player = getTableView().getItems().get(getIndex());
                                btn.setText("Bid");
                                btn.setDisable(false); // Enable button if the player is not in the marketplace
                                btn.setOnAction(e -> {

                                    handleBidButtonClick(player);
                                    //try {
                                    //handleSellButtonClick(player); // Handle sell button click
//                                    } catch (IOException | ClassNotFoundException ex) {
//                                        ex.printStackTrace();
//                                        throw new RuntimeException(ex);
//                                    }
                                });
                            }
                            setGraphic(hbox); // Set the HBox with the button
                        }
                    }
                };
            }
        });

        ObservableList<Player> data = FXCollections.observableArrayList(allPlayersOnSale);
        playerTable.getItems().clear();
        playerTable.setItems(data);
    }

    private void handleSellButtonClick(Player person) throws IOException, ClassNotFoundException {
        // Handle button click action for the specific row's person
        System.out.println("Button clicked for " + person.getName());
//        Message m =
//        System.out.println("HERE1");
//        Message x = (Message) this.socketWrapper.read();
        socketWrapper.write(new Message(person, "makePlayerOnSale"));
        System.out.println("HERE2");

//        Message x = (Message) this.socketWrapper.read();
//        System.out.println(x.getType());
        refreshTable(new ActionEvent());
    }


    @FXML
    private void dashboard(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
        DashboardController controller = new DashboardController(clientProfile, socketWrapper, clientProfile.getUsername());
        loader.setController(controller);
        Parent root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
//
//        Parent root = loader.load();
//        DashboardController dashboardController = loader.getController();
//        dashboardController.setClientProfile(clientProfile);
//        dashboardController.setSocketWrapper(socketWrapper);
//        dashboardController.setClubName(clientProfile.getUsername());
//
//        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
//        scene = new Scene(root);
        stage.setOnCloseRequest(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Window");
            alert.setHeaderText("Are you sure you want to close the application?");
            alert.setContentText("Click OK to close or Cancel to keep the window open.");


            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    synchronized (this) {
//                            if(database.saveToFile()) {
//                                //database.showPlayerInfo();
//                                //System.out.println("THIKTHAk");
                        stage.close();
//                            }
//                            else {
                        try {
                            socketWrapper.closeConnection();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        stage.close();
                        //}
                    }
                } else {

                    event.consume();
                }
            });


            event.consume();
        });
        stage.setScene(scene);
        stage.show();
    }

    public void refreshTable(ActionEvent e) throws IOException, ClassNotFoundException {
        socketWrapper.write(new Message("giveItAll", "allPlayersOnSale"));
        Message nM = (Message) socketWrapper.read();
        List<Player> allPlayersOnSale = (List<Player>) nM.getObj();
        for(Player x: allPlayersOnSale) {
            System.out.println(x);
        }

        playerName.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        //viewButton.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
        //socketWrapper.write(new Message(null, clientProfile, "getAllPlayers"));
        //Message newMessage = (Message) socketWrapper.read();
        //System.out.println("READ_all_player");
        //if(newMessage.getType().equals("playerList")) System.out.println("List Received but not retrieved");
        //List<Player> allPlayer = (List<Player>) newMessage.getObj();

//        List<PlayerWithButton> dataOfPlayers = new ArrayList<>();
//        for(Player x: allPlayer) {
//            //dataOfPlayers.add(new PlayerWithButton(x));
//            if(x.isInMarketPlace()) {
//                System.out.println(x);
//            }
//        }
        viewButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button("View");
                    private final HBox hbox = new HBox(btn);  // Create an HBox to hold the button

                    {
                        btn.setOnAction(e -> {
                            //Player player = getTableView().getItems().get(getIndex());
//                            Alert a = new Alert(Alert.AlertType.INFORMATION);
//                            a.setTitle("Player Info");
//                            a.setHeaderText("Player Info");
//                            a.setContentText(String.valueOf(player));
//                            a.showAndWait();
                            Player player = getTableView().getItems().get(getIndex());
                            showPlayerInfoModal(player, player.getClub());
                        });

                        // Center align the button within the HBox
                        hbox.setAlignment(Pos.CENTER);  // Align the button to the center of the cell
                        hbox.setStyle("-fx-padding: 5px;");  // Optional: Adds some padding around the button
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);  // Remove button if the row is empty
                        } else {
                            setGraphic(hbox);  // Set the HBox with the centered button
                        }
                    }
                };
            }
        });
        bidButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button();
                    private final HBox hbox = new HBox(btn);

                    {
                        // Center align the button within the HBox
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setStyle("-fx-padding: 5px;");
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null); // Remove button if the row is empty
                        } else {
                            Player player = getTableView().getItems().get(getIndex());
                            System.out.println(player.getClub()+ " " + clubName.getText());
                            if (player.getClub().equals(clubName.getText())) {
                                btn.setText("Cancel Sell");
                                btn.setOnAction(e -> {
                                    try {
                                        handleSellButtonClick(player); // Handle sell button click
                                    } catch (IOException | ClassNotFoundException ex) {
                                        ex.printStackTrace();
                                        throw new RuntimeException(ex);
                                    }
                                });
                                //btn.setDisable(true); // Disable button if the player is already in the marketplace
                            } else {
                                //Player player = getTableView().getItems().get(getIndex());
                                btn.setText("Bid");
                                btn.setDisable(false); // Enable button if the player is not in the marketplace
                                btn.setOnAction(e -> {

                                    handleBidButtonClick(player);
                                    //try {
                                    //handleSellButtonClick(player); // Handle sell button click
//                                    } catch (IOException | ClassNotFoundException ex) {
//                                        ex.printStackTrace();
//                                        throw new RuntimeException(ex);
//                                    }
                                });
                            }
                            setGraphic(hbox); // Set the HBox with the button
                        }
                    }
                };
            }
        });

        ObservableList<Player> data = FXCollections.observableArrayList(allPlayersOnSale);
        playerTable.getItems().clear();
        playerTable.setItems(data);
        playerTable.refresh();
    }

    private void showPlayerInfoModal(Player player, String clubName) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-view.fxml"));
            Parent root = loader.load();

            // Get the controller and set the player info
            PlayerViewController controller = loader.getController();
            controller.setPlayerInfo(player, player.getClub());

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            controller.setStage(modalStage);
            modalStage.setTitle("Player Info");
            modalStage.setScene(new Scene(root));
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleBidButtonClick(Player player) {
        try {
            // Load the modal FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modal-view.fxml"));
            Parent modalRoot = loader.load();

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            modalStage.setTitle("Bid for Player: " + player.getName());
            modalStage.setScene(new Scene(modalRoot));
            modalStage.showAndWait(); // Wait for the modal to close

            // Retrieve the bid amount from the modal controller
            BidModalController modalController = loader.getController();
            if (modalController.isSubmitted()) {
                int bidAmount = modalController.getBidAmount();

                // Send the bid to the server
                Message bidMessage = new Message(new BidRequest(player, player.getClub(), clientProfile.getUsername(), bidAmount), "placeBid");
                socketWrapper.write(bidMessage);

                System.out.println("Bid of " + bidAmount + " placed for player " + player.getName());
            } else {
                System.out.println("Bid canceled for player " + player.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading bid modal.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void logout(ActionEvent e) throws IOException, ClassNotFoundException {
        Message x = new Message("logout", clientProfile);
        socketWrapper.write(x);
        String result = (String) socketWrapper.read();
        if(result.equals("200 OK")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));

            Parent root = loader.load();

            socketWrapper.closeConnection();

            //LoginController loginController = loader.getController();
            //loginController.setSocketWrapper(socketWrapper);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            //LoginController loginController =

        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Request");
            a.setHeaderText("Wrong Request");
            a.showAndWait();
        }

    }
}
