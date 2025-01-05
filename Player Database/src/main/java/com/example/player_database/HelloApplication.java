package com.example.player_database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database database = new Database();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        //System.out.println(database.getPlayersList());
        WelcomeToMainController welcomeToMainController = fxmlLoader.getController();
        welcomeToMainController.setDatabase(database);


        // taken from online to save on every close/exit
        stage.setOnCloseRequest(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Window");
            alert.setHeaderText("Are you sure you want to close the application?");
            alert.setContentText("Click OK to close or Cancel to keep the window open.");


            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    synchronized (this) {
                        if(database.saveToFile()) {
                            //database.showPlayerInfo();
                            //System.out.println("THIKTHAk");
                            stage.close();
                        }
                        else {
                            event.consume();
                        }
                    }


                } else {

                    event.consume();
                }
            });


            event.consume();
        });

        stage.setTitle("Player Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}