package com.example.player_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class SimpleTableController implements Initializable {

    private Database database;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setDatabase(Database database) {
        this.database = database;
    }


    @FXML
    private TableColumn<Player, String> name;
    @FXML
    private TableColumn<Player, String> country;
    @FXML
    private TableView<Player> tableView;

    ObservableList<Player> data;


    @FXML
    private Button button;

    private boolean init = true;


    ObservableList<Player> initData() {
        Player p1 = new Player("Jacob", "IS", 12, 12.2, "RCB", "BAT", "123", 1234);
        Player p2 = new Player("Jacsob", "IS", 12, 12.2, "RCB", "BAT", "123", 1234);
        return FXCollections.<Player> observableArrayList(p1, p2);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        country.setCellValueFactory(new PropertyValueFactory<Player, String>("country"));

        tableView.setItems(initData());
    }


//    public void goBack(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
//        Parent root = loader.load();
//
//        MainMenuController mainMenuController = loader.getController();
//        mainMenuController.setDatabase(database);
//
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}
