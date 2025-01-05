package com.example.player_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Database database;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void switchToAddPlayer(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("add_player.fxml"));
        Parent root = loader.load();
        AddPlayerController addPlayerController = loader.getController();
        addPlayerController.setDatabase(database);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToSearchPlayer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("search_player.fxml"));
        Parent root = loader.load();
        //SimpleTableController simpleTableController = loader.getController();

        PlayerSearchController playerSearchController = loader.getController();
        playerSearchController.setDatabase(this.database);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSearchClubs(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("search_club.fxml"));
        Parent root = loader.load();
        //SimpleTableController simpleTableController = loader.getController();

        SearchClubController clubSearchController = loader.getController();
        clubSearchController.setDatabase(this.database);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
