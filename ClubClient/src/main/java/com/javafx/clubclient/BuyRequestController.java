package com.javafx.clubclient;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class BuyRequestController {


    @FXML
    private Label clubName;

    private Stage stage;
    private Scene scene;
    private Credentials clientProfile;
    private SocketWrapper socketWrapper;
    private String clubNameText;

    @FXML
    private TableView<BidRequest> bidsTable;

    @FXML
    private TableColumn<BidRequest, String> playerNameColumn;

    @FXML
    private TableColumn<BidRequest, String> clubNameColumn;

    @FXML
    private TableColumn<BidRequest, Integer> bidAmountColumn;

    @FXML
    private TableColumn<BidRequest, Void> approveButtonColumn;

    private ObservableList<BidRequest> bidRequests;

    public BuyRequestController(Credentials user, SocketWrapper socketWrapper, String clubName) {
        this.clientProfile = user;
        this.socketWrapper = socketWrapper;
        this.clubNameText = clubName; // Store clubName for later use
    }


    public void initialize() throws IOException, ClassNotFoundException {
        // Initialize the columns
        //playerNameColumn.setCellValueFactory(new PropertyValueFactory<BidRequest, String>("name"));

        if (clubName != null && clubNameText != null) {
            clubName.setText(clubNameText);
            clubName.setVisible(true);
        }


        socketWrapper.write(new Message("allBid", "giveAllBids"));
        Message msg = (Message) socketWrapper.read();

        List<BidRequest> allBids = (List<BidRequest>) msg.getObj();

        allBids.removeIf(b -> {
            return !b.getPrevClubName().equals(clubNameText);
        });

        allBids.sort((bid1, bid2) ->
                bid1.getPlayer().getName().compareToIgnoreCase(bid2.getPlayer().getName()));


        playerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlayer().getName()));
        clubNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClubName()));
        bidAmountColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getBidAmount()));

        // Add the Approve button to each row
        approveButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<BidRequest, Void> call(TableColumn<BidRequest, Void> param) {
                return new TableCell<>() {
                    private final Button approveButton = new Button("Approve");

                    {
                        approveButton.setOnAction(e -> {
                            BidRequest bidRequest = getTableView().getItems().get(getIndex());
                            handleApproveBid(bidRequest);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(approveButton);
                        }
                    }
                };
            }
        });

        bidRequests = FXCollections.observableArrayList(allBids);
        bidsTable.getItems().clear();
        bidsTable.setItems(bidRequests);

    }

    public void setBidRequests(List<BidRequest> bids) {
        bidRequests = FXCollections.observableArrayList(bids);
        bidsTable.setItems(bidRequests);
    }

    private void handleApproveBid(BidRequest bidRequest) {
        // Remove the approved bid and update the table
        //bidRequests.remove(bidRequest);
        System.out.println("Handle Approve Bid");


        // Send the approval to the server
        // Create a message for the server
        Message approvalMessage = new Message(bidRequest, "approveBid");
        try {
            socketWrapper.write(approvalMessage);
            if(!bidRequests.isEmpty()) {
                bidRequests.removeIf(b -> {
                    return b.getPlayer().getName().equals(bidRequest.getPlayer().getName());
                });
            }


            bidsTable.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) bidsTable.getScene().getWindow();
        stage.close();
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

    @FXML
    public void refreshTable(ActionEvent e) throws IOException, ClassNotFoundException {
        socketWrapper.write(new Message("allBid", "giveAllBids"));
        Message msg = (Message) socketWrapper.read();

        List<BidRequest> allBids = (List<BidRequest>) msg.getObj();

        allBids.sort((bid1, bid2) ->
                bid1.getPlayer().getName().compareToIgnoreCase(bid2.getPlayer().getName()));

        playerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlayer().getName()));
        clubNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClubName()));
        bidAmountColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getBidAmount()));

        // Add the Approve button to each row
        approveButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<BidRequest, Void> call(TableColumn<BidRequest, Void> param) {
                return new TableCell<>() {
                    private final Button approveButton = new Button("Approve");

                    {
                        approveButton.setOnAction(e -> {
                            BidRequest bidRequest = getTableView().getItems().get(getIndex());
                            handleApproveBid(bidRequest);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(approveButton);
                        }
                    }
                };
            }
        });

        ObservableList<BidRequest> data = FXCollections.observableArrayList(allBids);
        bidsTable.getItems().clear();
        bidsTable.setItems(data);

    }
}
