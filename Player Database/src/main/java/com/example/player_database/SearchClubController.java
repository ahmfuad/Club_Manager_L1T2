package com.example.player_database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchClubController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Database database;

    public void setDatabase(Database database) {
        this.database = database;
    }

    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private TextField clubName;

    @FXML
    private Label choiceLabel;
    @FXML
    private Label clubNameLabel;
    @FXML
    private Label salaryLabel;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice.setValue("Select an option");
        choice.getItems().addAll(
                "Player(s) With Maximum Salary",
                "Oldest Player(s)",
                "Tallest Player(s)",
                "Total Yearly Salary");
        choice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadInfo(newValue);
        });
    }

    private void loadInfo(String newValue) {
        switch(newValue) {
            case "Player(s) With Maximum Salary":
                showMaxSalary(clubName);
                break;
            case "Oldest Player(s)":
                showOldestPlayer(clubName);
                break;
            case "Tallest Player(s)":
                showTallestPlayer(clubName);
                break;
            case "Total Yearly Salary":
                showTotalYearlySalary(clubName);
                break;
        }
    }

    @FXML
    private TableColumn<Player, String> nameByClub;
    @FXML
    private TableColumn<Player, String> countryByClub;
    @FXML
    private TableColumn<Player, String> button;
    @FXML
    private TableView<Player> tableByClub;


    private void showMaxSalary(TextField clubName) {
        if(clubName.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Club Field Empty");
            a.setHeaderText("Club Field Empty!");
            a.showAndWait();

        }
        else {
            tableByClub.getItems().clear();
            choiceLabel.setVisible(false);
            nameByClub.setVisible(false);
            countryByClub.setVisible(false);
            button.setVisible(false);
            tableByClub.setVisible(false);
            choiceLabel.setText("Player(s) with Maximum Salary");
            choiceLabel.setVisible(true);
            nameByClub.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            countryByClub.setCellValueFactory(new PropertyValueFactory<Player, String>("country"));
            button.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
            ObservableList<Player> data = FXCollections.observableArrayList(database.maxSalaryOfClub(clubName.getText()));
            tableByClub.setItems(data);
            tableByClub.setVisible(true);
            nameByClub.setVisible(true);
            countryByClub.setVisible(true);
            button.setVisible(true);
//            clubNameLabel.setText(clubName.getText());
//            clubNameLabel.setVisible(true);
//            salaryLabel.setText(String.valueOf(database.totalSalaryOfClub(clubName.getText())));
//            salaryLabel.setVisible(true);
        }
    }
    private void showOldestPlayer(TextField clubName) {
        if(clubName.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Club Field Empty");
            a.setHeaderText("Club Field Empty!");
            a.showAndWait();

        }
        else {
            tableByClub.getItems().clear();
            choiceLabel.setVisible(false);
            nameByClub.setVisible(false);
            countryByClub.setVisible(false);
            button.setVisible(false);
            tableByClub.setVisible(false);
            choiceLabel.setText("Player(s) with Maximum Age");
            choiceLabel.setVisible(true);
            nameByClub.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            countryByClub.setCellValueFactory(new PropertyValueFactory<Player, String>("country"));
            button.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
            ObservableList<Player> data = FXCollections.observableArrayList(database.maxAgeOfClub(clubName.getText()));
            tableByClub.setItems(data);
            tableByClub.setVisible(true);
            nameByClub.setVisible(true);
            countryByClub.setVisible(true);
            button.setVisible(true);
//            clubNameLabel.setText(clubName.getText());
//            clubNameLabel.setVisible(true);
//            salaryLabel.setText(String.valueOf(database.totalSalaryOfClub(clubName.getText())));
//            salaryLabel.setVisible(true);
        }

    }
    private void showTallestPlayer(TextField clubName) {
        if(clubName.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Club Field Empty");
            a.setHeaderText("Club Field Empty!");
            a.showAndWait();

        }
        else {
            tableByClub.getItems().clear();
            choiceLabel.setVisible(false);
            nameByClub.setVisible(false);
            countryByClub.setVisible(false);
            button.setVisible(false);
            tableByClub.setVisible(false);
            choiceLabel.setText("Player(s) with Maximum Height");
            choiceLabel.setVisible(true);
            nameByClub.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            countryByClub.setCellValueFactory(new PropertyValueFactory<Player, String>("country"));
            button.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
            ObservableList<Player> data = FXCollections.observableArrayList(database.maxHeightOfClub(clubName.getText()));
            tableByClub.setItems(data);
            tableByClub.setVisible(true);
            nameByClub.setVisible(true);
            countryByClub.setVisible(true);
            button.setVisible(true);
//            clubNameLabel.setText(clubName.getText());
//            clubNameLabel.setVisible(true);
//            salaryLabel.setText(String.valueOf(database.totalSalaryOfClub(clubName.getText())));
//            salaryLabel.setVisible(true);
        }
    }
    private void showTotalYearlySalary(TextField clubName) {
        if(clubName.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Club Field Empty");
            a.setHeaderText("Club Field Empty!");
            a.showAndWait();

        }
        else {
            nameByClub.setVisible(false);
            countryByClub.setVisible(false);
            button.setVisible(false);
            tableByClub.setVisible(false);
            tableByClub.getItems().clear();
            choiceLabel.setText("Total Yearly Salary");
            choiceLabel.setVisible(true);
            clubNameLabel.setText(clubName.getText());
            clubNameLabel.setVisible(true);
            salaryLabel.setText(String.valueOf(database.totalSalaryOfClub(clubName.getText())) + "$");
            salaryLabel.setVisible(true);
        }

    }

    // back button
    public void goBack(ActionEvent event) throws IOException {
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
