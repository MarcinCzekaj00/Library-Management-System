package org.library.controllers;

import org.library.DBConnect.DBConnection;
import org.library.dataFromDB.ReadersDataFromDB;
import org.library.data.ReaderData;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.library.App;
import org.library.messages.MessagesHelper;
import org.library.service.QueryHelper;
import org.library.userData.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SearchReaderController implements Initializable {

    ObservableList<ReaderData> list;
    ObservableList<ReaderData> dataList;

    @FXML
    private Label usernameLabel,searchReaderMessage;

    @FXML
    private TextField txt_id, txt_name, txt_surname, searchReaderFilter;

    @FXML
    private DatePicker txt_date;

    @FXML
    private TableView<ReaderData> table_readers;

    @FXML
    private TableColumn<ReaderData, Integer> col_id;

    @FXML
    private TableColumn<ReaderData, String> col_name, col_surname;

    @FXML
    private TableColumn<ReaderData, Date> col_date_of_birth;

    int index = -1;

    //==============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(User.getUsername());

        updateTable();
        searchUser();
        clearTextFields();
        MessagesHelper.getMessage(searchReaderMessage,"","GREEN");
    }

    @FXML
    public void addReaderButtonOnAction() throws IOException {
        App.setRoot("addReader");
    }

    @FXML
    public void addNewBookButtonOnAction() throws IOException {
        App.setRoot("addNewBook");
    }

    @FXML
    public void infoButtonOnAction() throws IOException {
        App.setRoot("info");
    }

    @FXML
    public void borrowBookButtonOnAction() throws IOException {
        App.setRoot("borrowBook");
    }

    @FXML
    public void returnBookButtonOnAction() throws IOException {
        App.setRoot("returnBook");
    }

    @FXML
    public void logoutButtonOnAction() throws IOException {
        App.setRoot("login");
    }

    //==============================================================================================================

    @FXML
    private void closeSearchOnAction() throws IOException {
        App.setRoot("info");
    }

    @FXML
    private void updateReaderOnAction() {

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()){

            String value1 = txt_id.getText();
            String value2 = txt_name.getText();
            String value3 = txt_surname.getText();
            LocalDate value4 = txt_date.getValue();

            String query = QueryHelper.getUpdateReaders();

            PreparedStatement pstm = connectDB.prepareStatement(query);
            pstm.setString(1, value1);
            pstm.setString(2, value2);
            pstm.setString(3, value3);
            pstm.setObject(4, value4);
            pstm.setString(5, value1);

            pstm.executeUpdate();

            MessagesHelper.getMessage(searchReaderMessage,"Success!","GREEN");
            updateTable();
            searchUser();
            clearTextFields();

        } catch (Exception e) {
            e.printStackTrace();
            MessagesHelper.getMessage(searchReaderMessage,"Bad values!","RED");
        }
    }

    @FXML
    private void deleteReaderOnAction() {

        DBConnection connect = new DBConnection();
        String sql = QueryHelper.getDeleteFromReadersWhereID();

        try (Connection connectDB = connect.getConnection()){

            PreparedStatement pstm = connectDB.prepareStatement(sql);
            pstm.setString(1, txt_id.getText());

            if(pstm.executeUpdate()>0) {
                MessagesHelper.getMessage(searchReaderMessage,"Success!","GREEN");
                updateTable();
                searchUser();
                clearTextFields();
            } else throw new Exception("Error when deleting user");

        } catch (Exception e) {
            e.printStackTrace();
            MessagesHelper.getMessage(searchReaderMessage,"Bad values!","RED");;
        }
    }

    @FXML
    private void getSelected (){

        index = table_readers.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index));
        txt_surname.setText(col_surname.getCellData(index));
        txt_date.setValue(col_date_of_birth.getCellData(index).toLocalDate());

    }


    @FXML
    private void searchUser() {

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        col_date_of_birth.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));

        dataList = ReadersDataFromDB.getDataUsers();
        table_readers.setItems(dataList);
        FilteredList<ReaderData> filteredData = new FilteredList<>(dataList, b -> true);
        searchReaderFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(person.getId()).contains(lowerCaseFilter)){
                    return true;
                } else return person.getDate_of_birth().toString().contains(lowerCaseFilter);
            });
        });

        SortedList<ReaderData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_readers.comparatorProperty());
        table_readers.setItems(sortedData);
    }

    //==============================================================================================================

    private void updateTable(){

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        col_date_of_birth.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));

        list = ReadersDataFromDB.getDataUsers();
        table_readers.setItems(list);
    }

    private void clearTextFields(){
        txt_id.setText("");
        txt_name.setText("");
        txt_surname.setText("");
        txt_date.setValue(null);
    }
}