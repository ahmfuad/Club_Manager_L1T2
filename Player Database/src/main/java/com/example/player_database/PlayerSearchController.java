package com.example.player_database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayerSearchController {

    private Database database;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setDatabase(Database database) {
        this.database = database;
    }

    @FXML
    private TextField searchName;
    //private VBox vbox;
    @FXML
    private Label resultLabelName;

    @FXML
    public void SearchPlayerByName(ActionEvent e) throws IOException {
        String name = searchName.getText();
        Player searchResult =  database.searchByName(name);
        resultLabelName.setVisible(true);
        if(searchResult == null) {
            resultLabelName.setText("No Such Player With This Name");
        }
        else resultLabelName.setText(String.valueOf(searchResult));
    }


    // search by Club and Country
    @FXML
    private TextField searchClub;
    @FXML
    private TextField searchCountry;
    @FXML
    private TableColumn<Player, String> nameByCountryClub;
    @FXML
    private TableColumn<Player,String> detailsByCountryClub;
    @FXML
    private TableView<Player> tableByCountryClub;

    @FXML
    public void searchPlayerByCountryClub(ActionEvent e) throws IOException {
        String country = searchCountry.getText();
        String club = searchClub.getText();
        List<Player> searchResult = database.searchByClubAndCountry(club, country);
        if(searchResult.isEmpty()) {
//            System.out.println("ITS NULL");

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Player Not Found");
            a.setHeaderText("Player Not Found");
            a.showAndWait();
        }
        else {
            ObservableList<Player> data = FXCollections.observableArrayList(searchResult);

            nameByCountryClub.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            detailsByCountryClub.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
            tableByCountryClub.setVisible(true);
            nameByCountryClub.setVisible(true);
            detailsByCountryClub.setVisible(true);

            tableByCountryClub.setItems(data);

        }

    }

    // search by position

    @FXML
    private TextField searchPoisition;
    @FXML
    private TableColumn<Player, String> nameByPosition;
    @FXML
    private TableColumn<Player, String> detailsByPosition;
    @FXML
    private TableView<Player> tableByPosition;

    public void searchPlayerByPosition(ActionEvent e) throws IOException {
        String position = searchPoisition.getText();

        List<Player> result = database.searchByPosition(position);

        if(result.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Player Not Found");
            a.setHeaderText("Player Not Found");
            a.showAndWait();
        }
        else {
            nameByPosition.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            detailsByPosition.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
            nameByPosition.setVisible(true);
            detailsByPosition.setVisible(true);
            tableByPosition.setVisible(true);

            ObservableList<Player> data = FXCollections.observableArrayList(result);
            tableByPosition.setItems(data);
        }

    }

    // by salary Range

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);  // Try parsing it as a double (works for both integers and decimals)
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    private TextField minSalary;
    @FXML
    private TextField maxSalary;
    @FXML
    private TableColumn<Player, String> nameBySalary;
    @FXML
    private TableColumn<Player, String> detailsBySalary;
    @FXML
    private TableView<Player> tableBySalary;

    public void searchPlayerBySalary(ActionEvent e) throws IOException {
        String _minSalary = minSalary.getText();
        String _maxSalary = maxSalary.getText();

        if(!isNumber(_minSalary) || !isNumber(_maxSalary)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Enter Valid Number");
            a.setHeaderText("Enter Valid Number");
            a.showAndWait();
            return;
        }
        int __minSalary = Integer.parseInt(_minSalary);
        int __maxSalary = Integer.parseInt(_maxSalary);
        List<Player> result = database.searchBySalaryRange(__minSalary, __maxSalary);

        //if(result.isEmpty())

        if(result.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Player Not Found");
            a.setHeaderText("Player Not Found");
            a.showAndWait();
        }
        else {
            nameBySalary.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
            detailsBySalary.setCellValueFactory(new PropertyValueFactory<Player, String>("button"));
            nameBySalary.setVisible(true);
            detailsBySalary.setVisible(true);
            tableBySalary.setVisible(true);

            ObservableList<Player> data = FXCollections.observableArrayList(result);
            tableBySalary.setItems(data);
        }

    }


    // CountryWise Count
    @FXML
    private TableColumn<CountryWiseCount, String> country;
    @FXML
    private TableColumn<CountryWiseCount, String> playerCount;
    @FXML
    private TableView<CountryWiseCount> tableCountryWise;

    @FXML
    public void countryWisePlayerCount(ActionEvent e) {
        //Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();


        Map<String, Integer> result = database.countryWisePlayerCount();
        List<CountryWiseCount> modifiedResult = new ArrayList<>();

        for(Map.Entry<String, Integer> x: result.entrySet()) {
            CountryWiseCount newCountry = new CountryWiseCount(x.getKey(), x.getValue());
            System.out.println("newCountry");
            modifiedResult.add(newCountry);

        }


        if(modifiedResult.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("No Country in Database");
            a.setHeaderText("No Country in Database");
            a.showAndWait();
        }
        else {
            country.setCellValueFactory(new PropertyValueFactory<CountryWiseCount, String>("country"));
            playerCount.setCellValueFactory(new PropertyValueFactory<CountryWiseCount, String>("count"));
            ObservableList<CountryWiseCount> data = FXCollections.observableArrayList(modifiedResult);

            tableCountryWise.setItems(data);
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
