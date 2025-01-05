package com.javafx.clubclient;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class ClubClient extends Application {
    private String serverAddress = "127.0.0.1";
    private int serverPort = 33333;



    public void start(Stage stage) throws IOException {

        try {

            //socketWrapper.write(clientName);
            FXMLLoader loader = new FXMLLoader(ClubClient.class.getResource("login-view.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();

            Scene scene = new Scene(root);
            stage.setOnCloseRequest(event -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Close Window");
                alert.setHeaderText("Are you sure you want to close the application?");
                alert.setContentText("Click OK to close or Cancel to keep the window open.");


                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        synchronized (this) {
                            stage.close();
                        }
                    } else {
                        event.consume();
                    }
                });


                event.consume();
            });

            stage.setTitle("Club Admin Panel");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}