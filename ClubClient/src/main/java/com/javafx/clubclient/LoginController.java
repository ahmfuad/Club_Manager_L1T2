package com.javafx.clubclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private String serverAddress = "127.0.0.1";
    private int serverPort = 33333;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField username;
    @FXML
    private TextField password;

    private Credentials user;
    private SocketWrapper socketWrapper;

    public void setSocketWrapper(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    public void login(ActionEvent e) throws IOException, ClassNotFoundException {
        String _username = username.getText();
        String _password = password.getText();

        if(_username.trim().isEmpty() || _password.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Input Field Empty");
            a.showAndWait();
            return;
        }


        user = new Credentials(_username, _password);
        socketWrapper = new SocketWrapper(this.serverAddress, this.serverPort);
        this.socketWrapper.write(user);
        String auth = (String) socketWrapper.read();
        if(auth.equals("200 OK")) {
            System.out.println("It is okay");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
            DashboardController controller = new DashboardController(user, socketWrapper, user.getUsername());
            loader.setController(controller);
            Parent root = loader.load();

            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
//
//            Parent root = loader.load();
//            DashboardController dashboardController = loader.getController();
//            dashboardController.setClientProfile(user);
//            dashboardController.setSocketWrapper(socketWrapper);
//            dashboardController.setClubName(user.getUsername());
//
//            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
//            scene = new Scene(root);
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
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("User Not Recognized");
            a.showAndWait();
        }

    }
}
