package com.javafx.clubclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private final String clubNameText;
    @FXML
    private Label clubName;

    @FXML
    private TableColumn<Player, String> playerName;

    @FXML
    private TableColumn<Player, Void> viewButton;

    @FXML
    private TableColumn<Player, Void> sellButton;

    @FXML
    private TableView<Player> playerTable;

    private Stage stage;
    private Scene scene;
    private Credentials clientProfile;
    private SocketWrapper socketWrapper;
    private ObservableList<Player> playerData;

    // Constructor
    public DashboardController(Credentials user, SocketWrapper socketWrapper, String clubName) {
        this.clientProfile = user;
        this.socketWrapper = socketWrapper;
        this.clubNameText = clubName; // Store clubName for later use
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (clubName != null && clubNameText != null) {
            clubName.setText(clubNameText);
            clubName.setVisible(true);
        }

        playerTable.getItems().clear();
        try {
            socketWrapper.write(new Message(null, clientProfile, "getAllPlayers"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Message newMessage = null;
        try {
            newMessage = (Message) socketWrapper.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Player> allPlayer = (List<Player>) newMessage.getObj();
        playerData = FXCollections.observableArrayList(allPlayer);
        playerTable.setItems(playerData);
        playerName.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        viewButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button("View");
                    private final HBox hbox = new HBox(btn);

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
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setStyle("-fx-padding: 5px;");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        sellButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button();
                    private final HBox hbox = new HBox(btn);

                    {
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setStyle("-fx-padding: 5px;");
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Player player = getTableView().getItems().get(getIndex());
                            btn.setText("Sell");
                            btn.setDisable(false);
                            btn.setOnAction(e -> {
                                try {
                                    handleSellButtonClick(player);
                                } catch (IOException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            });
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
        playerTable.refresh();
    }

    public void setClubName(String clubName) {
        this.clubName.setText(clubName);
        this.clubName.setVisible(true);
    }

    @FXML
    public void refreshTable(ActionEvent e) throws IOException, ClassNotFoundException {
        playerTable.getItems().clear();
        socketWrapper.write(new Message(null, clientProfile, "getAllPlayers"));
        Message newMessage = (Message) socketWrapper.read();
        List<Player> allPlayer = (List<Player>) newMessage.getObj();
        playerData = FXCollections.observableArrayList(allPlayer);
        playerTable.setItems(playerData);

        viewButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button("View");
                    private final HBox hbox = new HBox(btn);

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
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setStyle("-fx-padding: 5px;");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        sellButton.setCellFactory(new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(TableColumn<Player, Void> param) {
                return new TableCell<Player, Void>() {
                    private final Button btn = new Button();
                    private final HBox hbox = new HBox(btn);

                    {
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setStyle("-fx-padding: 5px;");
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Player player = getTableView().getItems().get(getIndex());
                            btn.setText("Sell");
                            btn.setDisable(false);
                            btn.setOnAction(e -> {
                                try {
                                    handleSellButtonClick(player);
                                } catch (IOException | ClassNotFoundException ex) {
                                    ex.printStackTrace();
                                }
                            });
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
        playerTable.refresh();
    }

    private void handleSellButtonClick(Player person) throws IOException, ClassNotFoundException {
        socketWrapper.write(new Message(person, "makePlayerOnSale"));
        refreshTable(new ActionEvent());
    }

    @FXML
    public void marketplace(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("marketplace-view.fxml"));
        MarketplaceController controller = new MarketplaceController(clientProfile, socketWrapper, clientProfile.getUsername());
        loader.setController(controller);
        Parent root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showPlayerInfoModal(Player player, String clubName) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("player-view.fxml"));
            Parent root = loader.load();

            // Get the controller and set the player info
            PlayerViewController controller = loader.getController();
            controller.setPlayerInfo(player, clubName);

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

    @FXML
    public void buyRequest(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("buyrequest-view.fxml"));
        BuyRequestController controller = new BuyRequestController(clientProfile, socketWrapper, clientProfile.getUsername());
        loader.setController(controller);
        Parent root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void logout(ActionEvent e) throws IOException, ClassNotFoundException {
        Message x = new Message("logout", clientProfile);
        socketWrapper.write(x);
        String result = (String) socketWrapper.read();
        if (result.equals("200 OK")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();

            socketWrapper.closeConnection();

            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Request");
            a.setHeaderText("Wrong Request");
            a.showAndWait();
        }
    }
}
