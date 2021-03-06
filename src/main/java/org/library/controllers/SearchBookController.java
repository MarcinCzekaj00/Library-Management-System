package org.library.controllers;

import org.library.dataFromDB.BookDataFromDB;
import org.library.dataFromDB.CategoriesFromDB;
import org.library.DBConnect.DBConnection;
import org.library.data.BookData;
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

public class SearchBookController implements Initializable {

    ObservableList<BookData> list;
    ObservableList<BookData> dataList;

    @FXML
    private Label usernameLabel,searchBookMessage;

    @FXML
    private TextField txt_id, txt_title, txt_author, searchBookFilter;

    @FXML
    private ChoiceBox<String> txt_category;

    @FXML
    private DatePicker txt_date;

    @FXML
    private TableView<BookData> table_books;

    @FXML
    private TableColumn<BookData, Integer> col_id;

    @FXML
    private TableColumn<BookData, String> col_title, col_author, col_category;

    @FXML
    private TableColumn<BookData, Date> col_release_date;

    int index = -1;

    //==============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(User.getUsername());

        updateTable();
        searchBook();
        clearTextFields();
        MessagesHelper.getMessage(searchBookMessage,"","RED");

        CategoriesFromDB.getCategoriesFromDB(txt_category);
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
    private void updateBookOnAction() {

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()){

            String value1 = txt_id.getText();
            String value2 = txt_title.getText();
            String value3 = txt_category.getSelectionModel().getSelectedItem();
            String value4 = txt_author.getText();
            LocalDate value5 = txt_date.getValue();

            String query = QueryHelper.getUpdateBooks();

            PreparedStatement pstm = connectDB.prepareStatement(query);
            pstm.setString(1, value1);
            pstm.setString(2, value2);
            pstm.setString(3, value3);
            pstm.setString(4, value4);
            pstm.setObject(5, value5);
            pstm.setString(6, value1);

            pstm.executeUpdate();


            MessagesHelper.getMessage(searchBookMessage,"Success!","GREEN");
            updateTable();
            searchBook();
            clearTextFields();

        } catch (Exception e) {
            e.printStackTrace();
            MessagesHelper.getMessage(searchBookMessage,"Bad values!","RED");
        }
    }

    @FXML
    private void deleteBookOnAction() {

        DBConnection connect = new DBConnection();
        String sql = QueryHelper.getDeleteFromBooksWhereID();

        try (Connection connectDB = connect.getConnection()){

            PreparedStatement pstm = connectDB.prepareStatement(sql);
            pstm.setString(1, txt_id.getText());

            if(pstm.executeUpdate()>0) {
                MessagesHelper.getMessage(searchBookMessage,"Success!","GREEN");
                updateTable();
                searchBook();
                clearTextFields();
            } else throw new Exception("Error when deleting book");

        } catch (Exception e) {
            e.printStackTrace();
            MessagesHelper.getMessage(searchBookMessage,"Bad values!","RED");
        }
    }

    @FXML
    private void getSelected (){

        index = table_books.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_title.setText(col_title.getCellData(index));
        txt_category.setValue(col_category.getCellData(index));
        txt_author.setText(col_author.getCellData(index));
        txt_date.setValue(col_release_date.getCellData(index).toLocalDate());

    }


    @FXML
    private void searchBook() {

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_release_date.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));

        dataList = BookDataFromDB.getDataBook();
        table_books.setItems(dataList);
        FilteredList<BookData> filteredData = new FilteredList<>(dataList, b -> true);
        searchBookFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(book.getId()).contains(lowerCaseFilter)){
                    return true;
                } else return book.getRelease_date().toString().contains(lowerCaseFilter);
            });
        });

        SortedList<BookData> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_books.comparatorProperty());
        table_books.setItems(sortedData);
    }

    //==============================================================================================================


    private void updateTable(){

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_release_date.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));

        list = BookDataFromDB.getDataBook();
        table_books.setItems(list);
    }

    private void clearTextFields(){
        txt_id.setText("");
        txt_title.setText("");
        txt_category.setValue("");
        txt_author.setText("");
        txt_date.setValue(null);
    }
}