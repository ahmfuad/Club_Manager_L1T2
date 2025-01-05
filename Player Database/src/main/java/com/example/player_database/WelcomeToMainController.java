package com.example.player_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomeToMainController {

    protected Database database;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent root = loader.load();


        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setDatabase(database);


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
